package br.com.badcine.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class SceneSwitcher {

    public static void switchScene(Node node, String fxmlFile) throws IOException {
        // Procura o FXML na raiz de 'resources'
        URL fxmlUrl = SceneSwitcher.class.getResource("/" + fxmlFile);
        if (fxmlUrl == null) {
            System.err.println("Erro ao trocar de cena: Não foi possível encontrar o arquivo FXML '/" + fxmlFile + "'");
            return;
        }
        Parent root = FXMLLoader.load(fxmlUrl);
        Scene newScene = new Scene(root);

        // Procura o CSS na raiz de 'resources'
        URL cssUrl = SceneSwitcher.class.getResource("/style.css");
        if (cssUrl != null) {
            newScene.getStylesheets().add(cssUrl.toExternalForm());
        }

        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(newScene);
        stage.centerOnScreen();
    }
}