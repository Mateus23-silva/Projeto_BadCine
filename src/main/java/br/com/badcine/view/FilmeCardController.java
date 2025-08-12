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
import java.util.List;

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

        // Tenta carregar a imagem externa
        File arquivoImagem = new File("imagens/" + filme.getPosterPath());
        Image poster;

        if (arquivoImagem.exists()) {
            poster = new Image(arquivoImagem.toURI().toString());
        } else {
            // Se falhar, carrega a imagem padrão de dentro dos resources
            System.err.println("AVISO: Imagem '" + filme.getPosterPath() + "' não encontrada em /imagens/. Usando imagem padrão.");
            try (InputStream defaultStream = getClass().getResourceAsStream("/default.png")) {
                if (defaultStream != null) {
                    poster = new Image(defaultStream);
                } else {
                    System.err.println("ERRO CRÍTICO: Imagem padrão 'default.png' não foi encontrada nos resources.");
                    poster = null; // Nenhuma imagem será exibida
                }
            } catch (Exception e) {
                e.printStackTrace();
                poster = null;
            }
        }
        posterImageView.setImage(poster);
    }

    @FXML
    private void handleAlugar() {
        List<Filme> itensNoCarrinho = Sistema.getInstance().getItensDoCarrinho();
        long quantidadeNoCarrinho = itensNoCarrinho.stream()
                .filter(item -> item.getId() == this.filme.getId())
                .count();
        if (quantidadeNoCarrinho >= this.filme.getQuantidadeEmEstoque()) {
            showAlert(Alert.AlertType.WARNING, "Estoque Insuficiente", "Não há mais cópias de '" + this.filme.getTitulo() + "' disponíveis para adicionar ao carrinho.");
        } else {
            Sistema.getInstance().adicionarAoCarrinho(this.filme);
            catalogoController.updateCarrinhoButton();
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "'" + this.filme.getTitulo() + "' foi adicionado ao seu carrinho!");
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