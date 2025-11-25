package model;

public class CartaVagao implements Carta {
    private final Cor cor;

    public CartaVagao(Cor cor) {
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }
}
