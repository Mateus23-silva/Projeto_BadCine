package br.com.badcine.view;

import br.com.badcine.controller.Sistema;
import br.com.badcine.model.Filme;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CatalogoController {

    @FXML private TilePane gradeDeFilmes;
    @FXML private Button verCarrinhoButton;
    @FXML private TextField buscaField;

    private List<Filme> masterList = new ArrayList<>();

    @FXML
    public void initialize() {
        carregarFilmesComEstoque();
        setupDynamicSearch();
        updateCarrinhoButton();
    }

    private void carregarFilmesComEstoque() {
        masterList = Sistema.getInstance().getFilmes().stream()
                .filter(filme -> filme.getQuantidadeEmEstoque() > 0)
                .collect(Collectors.toList());

        System.out.println("Encontrados " + masterList.size() + " filmes com estoque para exibir.");
        atualizarGrade(masterList);
    }

    private void atualizarGrade(List<Filme> filmesParaMostrar) {
        gradeDeFilmes.getChildren().clear();
        if (filmesParaMostrar.isEmpty()) {
            System.out.println("Nenhum filme para mostrar na grade.");
            return;
        }

        try {
            for (Filme filme : filmesParaMostrar) {
                URL fxmlUrl = getClass().getResource("/filme-card.fxml");
                if (fxmlUrl == null) {
                    System.err.println("ERRO CRÍTICO: O arquivo 'filme-card.fxml' não foi encontrado na raiz de resources.");
                    continue;
                }

                FXMLLoader loader = new FXMLLoader(fxmlUrl);
                VBox cardNode = loader.load();

                FilmeCardController cardController = loader.getController();
                cardController.setData(filme, this);

                gradeDeFilmes.getChildren().add(cardNode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Ocorreu um erro ao carregar o FXML dos cards de filme.");
        }
    }

    private void setupDynamicSearch() {
        buscaField.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarFilmes(newValue);
        });
    }

    private void filtrarFilmes(String textoBusca) {
        List<Filme> listaFiltrada;
        if (textoBusca == null || textoBusca.isEmpty()) {
            listaFiltrada = masterList;
        } else {
            String lowerCaseFilter = textoBusca.toLowerCase();
            listaFiltrada = masterList.stream()
                    .filter(filme -> filme.getTitulo().toLowerCase().contains(lowerCaseFilter))
                    .collect(Collectors.toList());
        }
        atualizarGrade(listaFiltrada);
    }

    @FXML
    private void handleVerCarrinho() {
        try {
            SceneSwitcher.switchScene(verCarrinhoButton, "carrinho-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        Sistema.getInstance().limparCarrinho();
        try {
            SceneSwitcher.switchScene(verCarrinhoButton, "login-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateCarrinhoButton() {
        int totalItens = Sistema.getInstance().getItensDoCarrinho().size();
        verCarrinhoButton.setText("Ver Carrinho (" + totalItens + ")");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}