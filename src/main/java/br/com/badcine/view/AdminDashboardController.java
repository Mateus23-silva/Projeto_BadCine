package br.com.badcine.view;

import br.com.badcine.controller.Sistema;
import br.com.badcine.model.Filme;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class AdminDashboardController {

    // --- DECLARAÇÕES QUE ESTAVAM FALTANDO ---
    @FXML private TableView<Filme> tabelaFilmes;
    @FXML private TableColumn<Filme, Integer> colunaId;
    @FXML private TableColumn<Filme, String> colunaTitulo;
    @FXML private TableColumn<Filme, String> colunaGenero;
    @FXML private TableColumn<Filme, Integer> colunaAno;
    @FXML private TableColumn<Filme, Double> colunaPreco;
    @FXML private TableColumn<Filme, Integer> colunaEstoque;
    // ------------------------------------------

    private final ObservableList<Filme> listaFilmes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colunaGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colunaAno.setCellValueFactory(new PropertyValueFactory<>("anoLancamento"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("precoAluguel"));
        colunaEstoque.setCellValueFactory(new PropertyValueFactory<>("quantidadeEmEstoque"));

        atualizarTabela();
    }

    private void atualizarTabela() {
        listaFilmes.setAll(Sistema.getInstance().getFilmes());
        tabelaFilmes.setItems(listaFilmes);
        tabelaFilmes.refresh();
    }

    @FXML
    private void handleAdicionarFilme() {
        abrirFormularioFilme(null);
    }

    @FXML
    private void handleEditarFilme() {
        Filme filmeSelecionado = tabelaFilmes.getSelectionModel().getSelectedItem();
        if (filmeSelecionado == null) {
            showAlert(Alert.AlertType.WARNING, "Nenhum Filme Selecionado", "Por favor, selecione um filme na tabela para editar.");
            return;
        }
        abrirFormularioFilme(filmeSelecionado);
    }

    @FXML
    private void handleRemoverFilme() {
        Filme filmeSelecionado = tabelaFilmes.getSelectionModel().getSelectedItem();
        if (filmeSelecionado == null) {
            showAlert(Alert.AlertType.WARNING, "Nenhum Filme Selecionado", "Por favor, selecione um filme na tabela para remover.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Remoção");
        alert.setHeaderText("Você tem certeza que deseja remover o filme '" + filmeSelecionado.getTitulo() + "'?");
        alert.setContentText("Esta ação não poderá ser desfeita.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Sistema.getInstance().removerFilme(filmeSelecionado);
            atualizarTabela();
        }
    }

    private void abrirFormularioFilme(Filme filme) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/filme-form-view.fxml"));
            Parent root = loader.load();

            FilmeFormController controller = loader.getController();
            controller.setFilmeParaEdicao(filme);

            Stage dialogStage = new Stage();
            dialogStage.setTitle(filme == null ? "Adicionar Novo Filme" : "Editar Filme");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(tabelaFilmes.getScene().getWindow());

            Scene scene = new Scene(root);
            URL cssUrl = getClass().getResource("/style.css");
            if(cssUrl != null) scene.getStylesheets().add(cssUrl.toExternalForm());

            dialogStage.setScene(scene);

            dialogStage.showAndWait();

            atualizarTabela();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível abrir o formulário de filme.");
        }
    }

    @FXML
    private void handleLogout() {
        try {
            SceneSwitcher.switchScene(tabelaFilmes, "login-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}