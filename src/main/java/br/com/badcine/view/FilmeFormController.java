package br.com.badcine.view;

import br.com.badcine.controller.Sistema;
import br.com.badcine.model.Filme;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FilmeFormController {

    @FXML private TextField tituloField;
    @FXML private TextField generoField;
    @FXML private TextField anoField;
    @FXML private TextField precoField;
    @FXML private TextField estoqueField;
    @FXML private Label nomeImagemLabel;

    private Filme filmeParaEdicao;
    private Stage dialogStage;
    private File arquivoImagemSelecionada;

    public void setFilmeParaEdicao(Filme filme) {
        this.filmeParaEdicao = filme;
        if (filme != null) {
            tituloField.setText(filme.getTitulo());
            generoField.setText(filme.getGenero());
            anoField.setText(String.valueOf(filme.getAnoLancamento()));
            precoField.setText(String.format("%.2f", filme.getPrecoAluguel()));
            estoqueField.setText(String.valueOf(filme.getQuantidadeEmEstoque()));
            nomeImagemLabel.setText(filme.getPosterPath());
        }
    }

    @FXML
    private void handleSelecionarImagem() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivos de Imagem", "*.png", "*.jpg", "*.jpeg", "*.webp");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(getStage());
        if (file != null) {
            arquivoImagemSelecionada = file;
            nomeImagemLabel.setText(arquivoImagemSelecionada.getName());
        }
    }

    @FXML
    private void handleSalvar() {
        if (isInputValid()) {
            String posterPath;
            if (arquivoImagemSelecionada != null) {
                posterPath = copiarImagemParaProjeto(arquivoImagemSelecionada);
                if (posterPath == null) {
                    showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível copiar a imagem para a pasta do projeto.");
                    return;
                }
            } else {
                if (filmeParaEdicao != null) {
                    posterPath = filmeParaEdicao.getPosterPath();
                } else {
                    posterPath = "default.png";
                }
            }

            String titulo = tituloField.getText();
            String genero = generoField.getText();
            int ano = Integer.parseInt(anoField.getText());
            double preco = Double.parseDouble(precoField.getText().replace(",", "."));
            int estoque = Integer.parseInt(estoqueField.getText());

            if (filmeParaEdicao == null) {
                Sistema.getInstance().adicionarFilme(titulo, genero, ano, preco, estoque, posterPath);
            } else {
                filmeParaEdicao.setTitulo(titulo);
                filmeParaEdicao.setGenero(genero);
                filmeParaEdicao.setAnoLancamento(ano);
                filmeParaEdicao.setPrecoAluguel(preco);
                filmeParaEdicao.setQuantidadeEmEstoque(estoque);
                filmeParaEdicao.setPosterPath(posterPath);
                Sistema.getInstance().atualizarFilme(filmeParaEdicao);
            }
            getStage().close();
        }
    }

    private String copiarImagemParaProjeto(File arquivoOrigem) {
        try {
            // Define a pasta de destino na raiz do projeto.
            Path destinoDir = Paths.get("imagens");

            // Cria o diretório se ele não existir.
            if (!Files.exists(destinoDir)) {
                Files.createDirectories(destinoDir);
            }

            String nomeOriginal = arquivoOrigem.getName();
            String nomeUnico = Sistema.getInstance().getProximoIdFilme() + "-" + nomeOriginal;
            Path destinoArquivo = destinoDir.resolve(nomeUnico);

            Files.copy(arquivoOrigem.toPath(), destinoArquivo, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Imagem copiada para: " + destinoArquivo.toAbsolutePath().toString());
            return nomeUnico;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML private void handleCancelar() { getStage().close(); }
    private boolean isInputValid() { return true; }
    private Stage getStage() { if(dialogStage == null) { dialogStage = (Stage) tituloField.getScene().getWindow(); } return dialogStage; }
    private void showAlert(Alert.AlertType type, String title, String content) { }
}