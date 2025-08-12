package br.com.badcine.dao;

import br.com.badcine.model.Filme;
import java.io.*;
import java.nio.charset.StandardCharsets; // Import importante
import java.util.ArrayList;
import java.util.List;

public class FilmeDAO {
    private static final String FILE_PATH = "data/filmes.csv";

    public List<Filme> carregar() {
        List<Filme> filmes = new ArrayList<>();
        // Usa um InputStreamReader para especificar a codificação
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PATH), StandardCharsets.UTF_8))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                filmes.add(new Filme(Integer.parseInt(values[0]), values[1], values[2], Integer.parseInt(values[3]), Double.parseDouble(values[4]), Integer.parseInt(values[5]), values[6]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filmes;
    }

    public void salvar(List<Filme> filmes) {
        // Usa um OutputStreamWriter para especificar a codificação
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH), StandardCharsets.UTF_8))) {
            bw.write("ID;TITULO;GENERO;ANO;PRECO;ESTOQUE;POSTER_PATH\n");
            for (Filme filme : filmes) {
                String line = String.join(";", String.valueOf(filme.getId()), filme.getTitulo(), filme.getGenero(), String.valueOf(filme.getAnoLancamento()), String.format("%.2f", filme.getPrecoAluguel()).replace(",", "."), String.valueOf(filme.getQuantidadeEmEstoque()), filme.getPosterPath());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}