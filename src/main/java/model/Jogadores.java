package model;

import java.util.ArrayList;
import java.util.List;

public class Jogadores {
    private static Jogadores instance;
    private final List<Jogador> listaJogadores;

    private Jogadores(List<Jogador> jogadores){
        this.listaJogadores = jogadores;
    }

    public static Jogadores getOrCreateInstance(List<String> nomesJogadores) {
        if (instance == null) {
            List<Jogador> novosJogadores = new ArrayList<>();

            Cor[] coresDisponiveis = {Cor.VERMELHO, Cor.AZUL, Cor.AMARELO, Cor.VERDE, Cor.PRETO};

            for (int i = 0; i < nomesJogadores.size(); i++) {
                String nome = nomesJogadores.get(i);
                Cor cor = coresDisponiveis[i % coresDisponiveis.length];

                novosJogadores.add(new Jogador(nome, cor));
            }

            instance = new Jogadores(novosJogadores);
        }
        return instance;
    }

    public static Jogadores getJogadores(){
        return instance;
    }

    public List<Jogador> getListaJogadores() {
        return listaJogadores;
    }

    public Jogador getJogadorAtIndex(int indexJogador){
        return this.getListaJogadores().get(indexJogador);
    }
}
