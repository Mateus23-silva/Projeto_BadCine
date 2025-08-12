package br.com.badcine.dao;

import br.com.badcine.model.*;
import java.io.*;
import java.nio.charset.StandardCharsets; // Import importante
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private static final String FILE_PATH = "data/usuarios.csv";

    public List<Usuario> carregar() {
        List<Usuario> usuarios = new ArrayList<>();
        // Usa um InputStreamReader para especificar a codificação
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PATH), StandardCharsets.UTF_8))) {
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
                    usuarios.add(new Cliente(id, nome, login, senha, values[5]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public void salvar(List<Usuario> usuarios) {
        // Usa um OutputStreamWriter para especificar a codificação
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH), StandardCharsets.UTF_8))) {
            bw.write("TIPO;ID;NOME;LOGIN;SENHA;EMAIL\n");
            for (Usuario usuario : usuarios) {
                String tipo = (usuario instanceof Administrador) ? "ADMIN" : "CLIENTE";
                String email = (usuario instanceof Cliente) ? ((Cliente) usuario).getEmail() : "";
                String line = String.join(";", tipo, String.valueOf(usuario.getId()), usuario.getNome(), usuario.getLogin(), usuario.getSenha(), email);
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}