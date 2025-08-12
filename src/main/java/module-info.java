module br.com.badcine {
    // Declara que este módulo precisa dos módulos do JavaFX
    requires javafx.controls;
    requires javafx.fxml;

    // Exporta os pacotes que contêm classes que precisam ser acessadas de fora
    exports br.com.badcine;
    exports br.com.badcine.view;
    exports br.com.badcine.model; // Adicionado para acesso direto

    // Abre os pacotes para reflexão pelas bibliotecas do JavaFX
    opens br.com.badcine.view to javafx.fxml;
    opens br.com.badcine.controller to javafx.fxml;


    // Permite que a TableView (javafx.base) acesse os getters da classe Filme
    opens br.com.badcine.model to javafx.base;
}