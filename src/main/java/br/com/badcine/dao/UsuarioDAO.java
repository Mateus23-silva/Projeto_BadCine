package br.com.badcine.dao;

import br.com.badcine.model.Administrador;
import br.com.badcine.model.Cliente;
import br.com.badcine.model.Usuario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private static final String FILE_PATH = "data/usuarios.csv";

    public List<Usuario> carregar() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";", -1);
                int id = Integer.parseInt(values[1]);
                String nome = values[2];
                String login = values[3];
                String senha = values[4];
                if ("ADMIN".equals(values[0])) {
                    usuarios.add(new Administrador(id, nome, login, senha));
                } else if ("CLIENTE".equals(values[0])) {
                    // Agora passa o email (coluna 5) para o construtor
                    usuarios.add(new Cliente(id, nome, login, senha, values[5]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public void salvar(List<Usuario> usuarios) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write("TIPO;ID;NOME;LOGIN;SENHA;EMAIL\n"); // Cabeçalho atualizado
            for (Usuario usuario : usuarios) {
                String tipo = (usuario instanceof Administrador) ? "ADMIN" : "CLIENTE";
                // Agora pega o email em vez do CPF
                String email = (usuario instanceof Cliente) ? ((Cliente) usuario).getEmail() : "";

                String line = String.join(";",
                        tipo,
                        String.valueOf(usuario.getId()),
                        usuario.getNome(),
                        usuario.getLogin(),
                        usuario.getSenha(),
                        email); // Salva o email
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}