package game;

import model.CartaVagao;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * gerencia as 5 cartas de vagão que ficam abertas na mesa
 */
public class CartasAbertas {
    private final int NUMERO_DE_CARTAS = 5;
    private CartaVagao[] cartas;

    /**
     *GRASP - Creator
     */
    public CartasAbertas(DeckCartasVagao deck) {
        this.cartas = new CartaVagao[NUMERO_DE_CARTAS];
        for (int i = 0; i < NUMERO_DE_CARTAS; i++) {
            repor(i, deck);
        }
    }

    public CartaVagao pegar(int indice) {
        if (indice < 0 || indice >= NUMERO_DE_CARTAS) {
            return null;
        }
        CartaVagao cartaPega = cartas[indice];
        cartas[indice] = null;
        return cartaPega;
    }

    public void repor(int indice, DeckCartasVagao deck) {
        if (indice < 0 || indice >= NUMERO_DE_CARTAS) {
            return;
        }

        if (!deck.estaVazio()){
            cartas[indice] = deck.comprar();
        }
    }

    public List<CartaVagao> getCartas() {
        return new ArrayList<>(Arrays.asList(cartas));
    }
}
