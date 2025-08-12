package br.com.badcine.view;

import br.com.badcine.controller.Sistema;
import br.com.badcine.model.Filme;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FilmeFormController {

    @FXML private TextField tituloField;
    @FXML private TextField generoField;
    @FXML private TextField anoField;
    @FXML private TextField precoField;
    @FXML private TextField estoqueField;
    @FXML private TextField posterPathField; // Novo campo

    private Filme filmeParaEdicao;
    private Stage dialogStage;

    public void setFilmeParaEdicao(Filme filme) {
        this.filmeParaEdicao = filme;
        if (filme != null) {
            tituloField.setText(filme.getTitulo());
            generoField.setText(filme.getGenero());
            anoField.setText(String.valueOf(filme.getAnoLancamento()));
            precoField.setText(String.format("%.2f", filme.getPrecoAluguel()));
            estoqueField.setText(String.valueOf(filme.getQuantidadeEmEstoque()));
            posterPathField.setText(filme.getPosterPath()); // Preenche o novo campo
        }
    }

    @FXML
    private void handleSalvar() {
        if (isInputValid()) {
            String titulo = tituloField.getText();
            String genero = generoField.getText();
            int ano = Integer.parseInt(anoField.getText());
            double preco = Double.parseDouble(precoField.getText().replace(",", "."));
            int estoque = Integer.parseInt(estoqueField.getText());
            String posterPath = posterPathField.getText(); // Lê o valor do novo campo

            if (filmeParaEdicao == null) { // Modo Adicionar
                // Chama o método com os 6 argumentos corretos
                Sistema.getInstance().adicionarFilme(titulo, genero, ano, preco, estoque, posterPath);
            } else { // Modo Editar
                filmeParaEdicao.setTitulo(titulo);
                filmeParaEdicao.setGenero(genero);
                filmeParaEdicao.setAnoLancamento(ano);
                filmeParaEdicao.setPrecoAluguel(preco);
                filmeParaEdicao.setQuantidadeEmEstoque(estoque);
                filmeParaEdicao.setPosterPath(posterPath); // Atualiza o novo campo
                Sistema.getInstance().atualizarFilme(filmeParaEdicao);
            }
            getStage().close();
        }
    }

    @FXML
    private void handleCancelar() {
        getStage().close();
    }

    private boolean isInputValid() {
        // Validação simples. Pode ser melhorada.
        String errorMessage = "";
        if (tituloField.getText() == null || tituloField.getText().isEmpty()) errorMessage += "Título inválido!\n";
        if (generoField.getText() == null || generoField.getText().isEmpty()) errorMessage += "Gênero inválido!\n";
        if (posterPathField.getText() == null || posterPathField.getText().isEmpty()) errorMessage += "Nome da imagem inválido!\n";
        try { Integer.parseInt(anoField.getText()); } catch (NumberFormatException e) { errorMessage += "Ano inválido (deve ser um número)!\n"; }
        try { Double.parseDouble(precoField.getText().replace(",", ".")); } catch (NumberFormatException e) { errorMessage += "Preço inválido (deve ser um número)!\n"; }
        try { Integer.parseInt(estoqueField.getText()); } catch (NumberFormatException e) { errorMessage += "Estoque inválido (deve ser um número)!\n"; }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlert(Alert.AlertType.ERROR, "Campos Inválidos", errorMessage);
            return false;
        }
    }

    private Stage getStage() {
        if(dialogStage == null) {
            dialogStage = (Stage) tituloField.getScene().getWindow();
        }
        return dialogStage;
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}