package game;

import model.Cidade;
import model.Jogador;
import model.Rota;
import model.Cor;
import java.util.*;
import java.util.stream.Collectors;

public class Tabuleiro {
    private List<Cidade> cidades;
    private List<Rota> rotas;

    /**
     * GRASP - Creator
     */
    public Tabuleiro(String arquivoDoMapa) {
        this.cidades = new ArrayList<>();
        this.rotas = new ArrayList<>();
        inicializarMapaHardcoded();
    }

    private void inicializarMapaHardcoded() {
        Cidade vancouver = new Cidade("Vancouver");
        Cidade seattle = new Cidade("Seattle");
        Cidade portland = new Cidade("Portland");
        Cidade calgary = new Cidade("Calgary");
        // add + cidades

        cidades.addAll(List.of(vancouver, seattle, portland, calgary));

        //arestas grafo
        rotas.add(new Rota(vancouver, seattle, 1, Cor.CINZA));
        rotas.add(new Rota(vancouver, seattle, 1, Cor.CINZA));

        rotas.add(new Rota(seattle, portland, 1, Cor.CINZA));
        rotas.add(new Rota(seattle, portland, 1, Cor.CINZA));

        rotas.add(new Rota(seattle, calgary, 4, Cor.CINZA));

        // add + rotas
    }


    public boolean checarConectividade(Jogador jogador, Cidade origem, Cidade destino) {
        if (origem.equals(destino)) return true;

        Set<Cidade> visitados = new HashSet<>();
        Queue<Cidade> fila = new LinkedList<>();

        fila.add(origem);
        visitados.add(origem);

        while (!fila.isEmpty()) {
            Cidade atual = fila.poll();

            if (atual.equals(destino)) return true;

            for (Rota rota : rotas) {
                if (jogador.equals(rota.getDono())) {
                    Cidade vizinho = null;
                    // Lógica de grafo não-direcionado
                    if (rota.getCidadeA().equals(atual)) {
                        vizinho = rota.getCidadeB();
                    } else if (rota.getCidadeB().equals(atual)) {
                        vizinho = rota.getCidadeA();
                    }

                    if (vizinho != null && !visitados.contains(vizinho)) {
                        visitados.add(vizinho);
                        fila.add(vizinho);
                    }
                }
            }
        }
        return false;
    }

    public Rota buscarRota(String nomeA, String nomeB) {
        for (Rota r : rotas) {
            boolean matchA = r.getCidadeA().getNome().equalsIgnoreCase(nomeA);
            boolean matchB = r.getCidadeB().getNome().equalsIgnoreCase(nomeB);

            if (((matchA && r.getCidadeB().getNome().equalsIgnoreCase(nomeB)) ||
                    (r.getCidadeA().getNome().equalsIgnoreCase(nomeB) && matchB)) &&
                    r.getDono() == null) {
                return r;
            }
        }
        return null;
    }

    // Getters
    public List<Rota> getRotas() { return rotas; }
    public List<Cidade> getCidades() { return cidades; }
}