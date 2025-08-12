package br.com.badcine.model;
public abstract class Usuario {
    protected int id;
    protected String nome;
    protected String login;
    protected String senha;
    public Usuario(int id, String nome, String login, String senha) { this.id = id; this.nome = nome; this.login = login; this.senha = senha; }
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getLogin() { return login; }
    public String getSenha() { return senha; }
}
