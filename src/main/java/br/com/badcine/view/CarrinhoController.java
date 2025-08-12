package br.com.badcine.view;

import br.com.badcine.controller.Sistema;
import br.com.badcine.exception.EstoqueInsuficienteException;
import br.com.badcine.model.Filme;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class CarrinhoController {

    @FXML private TableView<Filme> tabelaCarrinho;
    @FXML private TableColumn<Filme, String> colunaTitulo;
    @FXML private TableColumn<Filme, Double> colunaPreco;
    @FXML private Label labelTotal;

    private final ObservableList<Filme> listaCarrinho = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("precoAluguel"));

        atualizarTabelaEtotal();
    }

    private void atualizarTabelaEtotal() {
        List<Filme> itens = Sistema.getInstance().getItensDoCarrinho();
        listaCarrinho.setAll(itens);
        tabelaCarrinho.setItems(listaCarrinho);
        atualizarTotal();
    }

    private void atualizarTotal() {
        double total = listaCarrinho.stream().mapToDouble(Filme::getPrecoAluguel).sum();
        labelTotal.setText(String.format("Total: R$ %.2f", total));
    }

    @FXML
    private void handleVoltarCatalogo() {
        try {
            SceneSwitcher.switchScene(labelTotal, "catalogo-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleFinalizarAluguel() {
        if (listaCarrinho.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Carrinho Vazio", "Não há itens no carrinho para alugar.");
            return;
        }

        try {
            Sistema.getInstance().processarAluguel();
            showAlert(Alert.AlertType.CONFIRMATION, "Aluguel Concluído", "Obrigado por alugar com a BadCine! O estoque foi atualizado.");
            Sistema.getInstance().limparCarrinho();
            handleVoltarCatalogo();
        } catch (EstoqueInsuficienteException e) {
            showAlert(Alert.AlertType.ERROR, "Erro de Estoque", e.getMessage());
        }
    }

    @FXML
    private void handleRemoverItem() {
        Filme filmeSelecionado = tabelaCarrinho.getSelectionModel().getSelectedItem();

        if (filmeSelecionado == null) {
            showAlert(Alert.AlertType.WARNING, "Nenhum Item Selecionado", "Por favor, selecione um filme na tabela para remover.");
            return;
        }

        // Chama o método no Sistema para remover o item da lista
        Sistema.getInstance().removerDoCarrinho(filmeSelecionado);

        // Atualiza a interface gráfica
        atualizarTabelaEtotal();
        showAlert(Alert.AlertType.INFORMATION, "Item Removido", "'" + filmeSelecionado.getTitulo() + "' foi removido do seu carrinho.");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}