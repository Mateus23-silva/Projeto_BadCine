package br.com.badcine.controller;

import br.com.badcine.dao.FilmeDAO;
import br.com.badcine.dao.UsuarioDAO;
import br.com.badcine.exception.EstoqueInsuficienteException;
import br.com.badcine.exception.LoginInvalidoException;
import br.com.badcine.model.Administrador;
import br.com.badcine.model.Cliente;
import br.com.badcine.model.Filme;
import br.com.badcine.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Sistema {
    private static Sistema instance;
    private List<Usuario> usuarios;
    private List<Filme> filmes;
    private List<Filme> carrinho = new ArrayList<>();
    private Usuario usuarioLogado;
    private FilmeDAO filmeDAO;
    private UsuarioDAO usuarioDAO;

    private Sistema() {
        this.filmeDAO = new FilmeDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    public static Sistema getInstance() {
        if (instance == null) instance = new Sistema();
        return instance;
    }

    public void iniciar() {
        this.usuarios = this.usuarioDAO.carregar();
        this.filmes = this.filmeDAO.carregar();
        System.out.println("Sistema iniciado. " + usuarios.size() + " usuários e " + filmes.size() + " filmes carregados.");
    }

    public void autenticarUsuario(String login, String senha) throws LoginInvalidoException {
        Optional<Usuario> usuario = usuarios.stream().filter(u -> u.getLogin().equals(login) && u.getSenha().equals(senha)).findFirst();
        if (usuario.isPresent()) {
            this.usuarioLogado = usuario.get();
        } else {
            throw new LoginInvalidoException("Login ou senha inválidos.");
        }
    }

    public void processarAluguel() throws EstoqueInsuficienteException {
        for (Filme filmeAlugado : carrinho) {
            Filme filmeOriginal = filmes.stream().filter(f -> f.getId() == filmeAlugado.getId()).findFirst().orElse(null);
            if (filmeOriginal != null) {
                filmeOriginal.decrementarEstoque();
            }
        }
        salvarDadosFilmes();
    }

    
    public int getProximoIdFilme() {
        return filmes.stream().mapToInt(Filme::getId).max().orElse(0) + 1;
    }

    public void adicionarFilme(String titulo, String genero, int ano, double preco, int estoque, String posterPath) {
        int novoId = getProximoIdFilme();
        Filme novoFilme = new Filme(novoId, titulo, genero, ano, preco, estoque, posterPath);
        this.filmes.add(novoFilme);
        salvarDadosFilmes();
    }

    public void removerFilme(Filme filmeParaRemover) {
        this.filmes.remove(filmeParaRemover);
        salvarDadosFilmes();
    }

    public void atualizarFilme(Filme filmeAtualizado) {
        salvarDadosFilmes();
    }

    public void cadastrarNovoCliente(String nome, String email, String login, String senha) throws Exception {
        boolean loginJaExiste = usuarios.stream().anyMatch(u -> u.getLogin().equalsIgnoreCase(login));
        if (loginJaExiste) {
            throw new Exception("Este login já está em uso. Por favor, escolha outro.");
        }

        int novoId = usuarios.stream().mapToInt(Usuario::getId).max().orElse(0) + 1;
        Cliente novoCliente = new Cliente(novoId, nome, login, senha, email);

        this.usuarios.add(novoCliente);

        this.usuarioDAO.salvar(this.usuarios);
        System.out.println("Novo usuário salvo. Total de usuários: " + this.usuarios.size());
    }

    private void salvarDadosFilmes() {
        filmeDAO.salvar(this.filmes);
        System.out.println("Dados dos filmes salvos com sucesso no arquivo.");
    }

    public List<Filme> getFilmes() { return filmes; }
    public Usuario getUsuarioLogado() { return usuarioLogado; }
    public void adicionarAoCarrinho(Filme filme) { carrinho.add(filme); }
    public List<Filme> getItensDoCarrinho() { return carrinho; }
    public void limparCarrinho() { carrinho.clear(); }
    public void removerDoCarrinho(Filme filme) { carrinho.remove(filme); }
}