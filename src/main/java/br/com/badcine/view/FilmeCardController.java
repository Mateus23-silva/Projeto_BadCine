package br.com.badcine.view;

import br.com.badcine.controller.Sistema;
import br.com.badcine.model.Filme;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.InputStream;

public class FilmeCardController {

    @FXML private ImageView posterImageView;
    @FXML private Label tituloLabel;
    @FXML private Label precoLabel;
    @FXML private Button alugarButton;

    private Filme filme;
    private CatalogoController catalogoController;

    public void setData(Filme filme, CatalogoController catalogoController) {
        this.filme = filme;
        this.catalogoController = catalogoController;

        tituloLabel.setText(filme.getTitulo());
        precoLabel.setText(String.format("R$ %.2f", filme.getPrecoAluguel()));

        // Procura as imagens na pasta 'imagens' que está na raiz de 'resources'
        String imagePath = "/imagens/" + filme.getPosterPath();
        String imagePathDefault = "/imagens/default.png";

        InputStream imageStream = getClass().getResourceAsStream(imagePath);

        if (imageStream == null) {
            System.err.println("AVISO: Imagem '" + filme.getPosterPath() + "' não encontrada. Usando imagem padrão.");
            imageStream = getClass().getResourceAsStream(imagePathDefault);
        }

        if (imageStream != null) {
            posterImageView.setImage(new Image(imageStream));
        } else {
            System.err.println("ERRO CRÍTICO: Imagem padrão 'default.png' também não foi encontrada.");
        }
    }

    @FXML
    private void handleAlugar() {
        Sistema.getInstance().adicionarAoCarrinho(filme);
        catalogoController.updateCarrinhoButton();
        showAlert(Alert.AlertType.INFORMATION, "Sucesso", "'" + filme.getTitulo() + "' foi adicionado ao carrinho!");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}