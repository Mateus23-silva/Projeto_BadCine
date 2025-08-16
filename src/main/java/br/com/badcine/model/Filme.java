package br.com.badcine.model;

import br.com.badcine.exception.EstoqueInsuficienteException;

public class Filme {
    private int id;
    private String titulo;
    private String genero;
    private int anoLancamento;
    private double precoAluguel;
    private int quantidadeEmEstoque;
    private String posterPath; // Novo campo

    public Filme(int id, String titulo, String genero, int ano, double preco, int estoque, String posterPath) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.anoLancamento = ano;
        this.precoAluguel = preco;
        this.quantidadeEmEstoque = estoque;
        this.posterPath = posterPath; // Novo campo
    }

    public void decrementarEstoque() throws EstoqueInsuficienteException {
        if (quantidadeEmEstoque <= 0) throw new EstoqueInsuficienteException("Estoque insuficiente para " + titulo);
        this.quantidadeEmEstoque--;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getGenero() { return genero; }
    public int getAnoLancamento() { return anoLancamento; }
    public double getPrecoAluguel() { return precoAluguel; }
    public int getQuantidadeEmEstoque() { return quantidadeEmEstoque; }
    public String getPosterPath() { return posterPath; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setAnoLancamento(int anoLancamento) { this.anoLancamento = anoLancamento; }
    public void setPrecoAluguel(double precoAluguel) { this.precoAluguel = precoAluguel; }
    public void setQuantidadeEmEstoque(int quantidadeEmEstoque) { this.quantidadeEmEstoque = quantidadeEmEstoque; }
    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }
}