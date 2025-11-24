package model;

import java.util.List;

import java.util.ArrayList;
import java.util.Collections;

public class Jogador {
    private final String nome;
    private final Cor corMarcador;
    private int estoqueVagoes;
    private int pontuacao;
    private List<CartaVagao> maoDeCartas;
    private List<CartaDestino> maoDeDestino;

    public Jogador(String nome, Cor corMarcador){
        this.nome = nome;
        this.corMarcador = corMarcador;
        this.estoqueVagoes = 45;
        this.pontuacao = 0;
        this.maoDeCartas = new ArrayList<>();
        this.maoDeDestino = new ArrayList<>();
    }

    public void adicionarPontos(int pontos) {
        this.pontuacao += pontos;
    }

    public void decrementarVagoes(int quantidade) {
        this.estoqueVagoes -= quantidade;
    }

    public void adicionarCartaNaMao(CartaVagao carta) {
        this.maoDeCartas.add(carta);
    }

    public void removerCartasDaMao(List<CartaVagao> cartas) {
        this.maoDeCartas.removeAll(cartas);
    }

    public void adicionarObjetivos(List<CartaDestino> objetivos) {
        this.maoDeDestino.addAll(objetivos);
    }

    /**
     * GRASP - Information Expert
     */
    public boolean podeReivindicarRota(Rota rota, List<CartaVagao> pagamento) {
        if (this.estoqueVagoes < rota.getComprimento()) {
            return false;
        }
        if (rota.getDono() != null) {
            return false;
        }
        if (pagamento.size() != rota.getComprimento()) {
            return false;
        }
        if (!this.maoDeCartas.containsAll(pagamento)) {
            return false;
        }

        Cor corDaRota = rota.getCor();
        if (corDaRota == Cor.CINZA) {
            Cor corBasePagamento = null;
            for (CartaVagao carta : pagamento) {
                if (carta.getCor() != Cor.CORINGA) {
                    if (corBasePagamento == null) {
                        corBasePagamento = carta.getCor();
                    } else if (corBasePagamento != carta.getCor()) {
                        return false;
                    }
                }
            }
        } else {
            for (CartaVagao carta : pagamento) {
                if (carta.getCor() != corDaRota && carta.getCor() != Cor.CORINGA) {
                    return false;
                }
            }
        }

        return true;
    }

    public String getNome() { return nome; }
    public int getPontuacao() { return pontuacao; }
    public int getEstoqueVagoes() { return estoqueVagoes; }
    public List<CartaVagao> getMaoDeCartas() { return Collections.unmodifiableList(maoDeCartas); }
    public List<CartaDestino> getMaoDeDestino() { return Collections.unmodifiableList(maoDeDestino); }

    public Cor getCorMarcador() {
        return this.corMarcador;
    }
}