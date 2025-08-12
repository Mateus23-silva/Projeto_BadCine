package br.com.badcine.view;

import br.com.badcine.controller.Sistema;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CadastroController {

    @FXML private TextField nomeField;
    @FXML private TextField emailField; // Trocado de cpfField
    @FXML private TextField loginField;
    @FXML private PasswordField senhaField;

    @FXML
    private void handleCadastrar() {
        if (isInputValid()) {
            try {
                // Agora passa o email em vez do CPF
                Sistema.getInstance().cadastrarNovoCliente(
                        nomeField.getText(),
                        emailField.getText(),
                        loginField.getText(),
                        senhaField.getText()
                );
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Usuário cadastrado com sucesso! Você já pode fazer o login.");
                handleVoltar();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Erro no Cadastro", e.getMessage());
            }
        }
    }

    @FXML
    private void handleVoltar() {
        try {
            SceneSwitcher.switchScene(nomeField, "login-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (nomeField.getText() == null || nomeField.getText().isEmpty()) errorMessage += "Nome inválido!\n";
        if (emailField.getText() == null || emailField.getText().isEmpty()) errorMessage += "Email inválido!\n"; // Trocado de CPF
        if (loginField.getText() == null || loginField.getText().isEmpty()) errorMessage += "Login inválido!\n";
        if (senhaField.getText() == null || senhaField.getText().isEmpty()) errorMessage += "Senha inválida!\n";

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlert(Alert.AlertType.ERROR, "Campos Inválidos", errorMessage);
            return false;
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