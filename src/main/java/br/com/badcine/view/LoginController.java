package br.com.badcine.view;

import br.com.badcine.controller.Sistema;
import br.com.badcine.exception.LoginInvalidoException;
import br.com.badcine.model.Administrador;
import br.com.badcine.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    @FXML public TextField loginField;
    @FXML public PasswordField senhaField;
    @FXML public Button loginButton;

    @FXML
    public void handleLoginButtonAction() {
        try {
            Sistema.getInstance().autenticarUsuario(loginField.getText(), senhaField.getText());

            Usuario usuarioLogado = Sistema.getInstance().getUsuarioLogado();

            if (usuarioLogado instanceof Administrador) {
                SceneSwitcher.switchScene(loginButton, "admin-dashboard-view.fxml");
            } else {
                SceneSwitcher.switchScene(loginButton, "catalogo-view.fxml");
            }

        } catch (LoginInvalidoException e) {
            showAlert(Alert.AlertType.ERROR, "Erro de Login", e.getMessage());
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erro de Sistema", "Não foi possível carregar a próxima tela.");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCadastrar() {
        try {
            SceneSwitcher.switchScene(loginButton, "cadastro-view.fxml");
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