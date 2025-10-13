package game;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.Cidade;
import model.Jogador;
import model.Rota;

public class Tabuleiro {
    private List<Cidade> cidades;
    private List<Rota> rotas;

    /**
     *GRASP - Creator
     */
    public Tabuleiro(String arquivoDoMapa) {
        // A lógica para ler um arquivo e popular as listas de cidades e rotas
        // seria implementada aqui. É uma parte complexa.
        // Por agora, podemos inicializar com listas vazias ou com dados de teste.
        System.out.println("Tabuleiro sendo criado a partir de: " + arquivoDoMapa);
        this.cidades = new ArrayList<>();
        this.rotas = new ArrayList<>();
    }

    public List<Rota> getRotasDisponiveis() {
        return rotas.stream()
                .filter(rota -> rota.getDono() == null)
                .collect(Collectors.toList());
    }

    public List<Rota> getRotasDoJogador(Jogador jogador) {
        return rotas.stream()
                .filter(rota -> jogador.equals(rota.getDono()))
                .collect(Collectors.toList());
    }

    // MÉTODOS AVANÇADOS PARA A VERSÃO FINAL


    public boolean checarConectividade(Jogador jogador, Cidade origem, Cidade destino) {
        System.out.println("Lógica de conectividade a ser implementada.");
        return false;
    }

    public int buscarMaiorRota(Jogador jogador) {
        System.out.println("Lógica de maior rota a ser implementada.");
        return 0;
    }

    public List<Rota> getRota(){return rotas;};
}