package model;

import java.util.List;

public class Jogador {
    private String nome;
    private Cor corMarcador;
    private int estoqueVagoes;
    private int pontuacao;
    private List<CartasVagao> maoDeCartas;
    private List<CartaDestino> maoDeDestino;

    public Jogador(String nome, Cor corMarcador){
        this.nome = nome;
        this.corMarcador = corMarcador;
        this.estoqueVagoes = 0;
        this.pontuacao = 0;
    }

    public void adicionarPontos(int pontos){
        this.pontuacao += pontos;
    }
    public void decrementarVagoes(int quanto){
        this.estoqueVagoes -= quanto;
    }
    public void adicionarCarta(CartasVagao carta){
        this.maoDeCartas.add(carta);
    }
    public void removerCartas(CartasVagao carta){
        this.maoDeCartas.remove(carta);
    }
    public void adicionarObjetivo(List<CartasVagao> destino){
        this.maoDeDestino.add(destino);
    }

    private boolean podeReinvindicar(Rota rota, List<CartasVagao> pagamento){

    }
}
