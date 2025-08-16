package br.com.badcine;

/**
 * Esta é uma classe "launcher" separada. Seu único propósito é chamar
 * a classe principal real do JavaFX. Esta abordagem resolve problemas
 * de classpath e module path ao executar um JAR "gordo" (fat jar).
 */
public class Main {
    public static void main(String[] args) {
        // Chama o método main da nossa classe de aplicação JavaFX real
        BadCineApp.main(args);
    }
}