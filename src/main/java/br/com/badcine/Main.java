package br.com.badcine;

import br.com.badcine.controller.Sistema;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Sistema.getInstance().iniciar();

        // Procura o FXML na raiz de 'resources'
        URL fxmlUrl = getClass().getResource("/login-view.fxml");
        if (fxmlUrl == null) {
            System.err.println("ERRO CRÍTICO: Não foi possível encontrar 'login-view.fxml' na raiz de resources.");
            return;
        }

        Parent root = FXMLLoader.load(fxmlUrl);
        Scene scene = new Scene(root);

        // Procura o CSS na raiz de 'resources'
        URL cssUrl = getClass().getResource("/style.css");
        if(cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }

        primaryStage.setTitle("BadCine");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}