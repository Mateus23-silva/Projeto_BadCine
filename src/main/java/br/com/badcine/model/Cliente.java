package br.com.badcine.model;

public class Cliente extends Usuario {
    private String email; // Trocado de cpf para email

    public Cliente(int id, String nome, String login, String senha, String email) {
        super(id, nome, login, senha);
        this.email = email; // Trocado de cpf para email
    }

    public String getEmail() { // Trocado de getCpf para getEmail
        return email;
    }
}