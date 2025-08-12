package br.com.badcine.view;

import br.com.badcine.controller.Sistema;
import br.com.badcine.model.Filme;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
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

        Image poster = carregarImagemExterna(filme.getPosterPath());
        posterImageView.setImage(poster);
    }

    private Image carregarImagemExterna(String nomeArquivo) {
        try {
            File arquivoImagem = new File("imagens/" + nomeArquivo);
            if (arquivoImagem.exists()) {
                return new Image(arquivoImagem.toURI().toString());
            } else {
                System.err.println("AVISO: Imagem '" + nomeArquivo + "' não encontrada na pasta 'imagens'. Usando imagem padrão.");
                return carregarImagemPadrao();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return carregarImagemPadrao();
        }
    }

    private Image carregarImagemPadrao() {
        // A imagem padrão pode ficar dentro dos resources, pois é parte da aplicação
        try (InputStream defaultStream = getClass().getResourceAsStream("/default.png")) { // Assumindo que default.png está na raiz de resources
            if (defaultStream != null) {
                return new Image(defaultStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("ERRO CRÍTICO: Imagem padrão 'default.png' não foi encontrada nos resources.");
        return null;
    }

    // --- CÓDIGO FALTANTE QUE FOI REINSERIDO ---
    @FXML
    private void handleAlugar() {
        // 1. Adiciona o filme ao carrinho na classe Sistema
        Sistema.getInstance().adicionarAoCarrinho(filme);

        // 2. Chama o método no CatalogoController para atualizar o contador do botão
        catalogoController.updateCarrinhoButton();

        // 3. Mostra uma confirmação para o usuário
        showAlert(Alert.AlertType.INFORMATION, "Sucesso", "'" + filme.getTitulo() + "' foi adicionado ao seu carrinho!");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    // --- FIM DO CÓDIGO FALTANTE ---
}