package model;

public class CartaDestino extends Carta {
    private final Cidade origem;
    private final Cidade destino;
    private final int valor;

    public CartaDestino(Cidade origem, Cidade destino, int valor) {
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
    }

    public Cidade getOrigem() {
        return origem;
    }

    public Cidade getDestino() {
        return destino;
    }

    public int getValor() {
        return valor;
    }
}