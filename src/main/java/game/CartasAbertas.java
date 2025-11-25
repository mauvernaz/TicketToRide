package game;

import model.CartaVagao;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Cartas abertas para consumo do Jogador
 */
public class CartasAbertas {
    private static CartasAbertas instance;
    private final int NUMERO_DE_CARTAS_ABERTAS_LIMITE = 5;
    private CartaVagao[] cartas;

    /**
     *GRASP - Creator
     */
    private CartasAbertas(DeckCartasVagao deck) {
        this.cartas = new CartaVagao[NUMERO_DE_CARTAS_ABERTAS_LIMITE];
        for (int i = 0; i < NUMERO_DE_CARTAS_ABERTAS_LIMITE; i++) {
            repor(i, deck);
        }
    }

    public static CartasAbertas getInstance() {
        if(instance == null){
            instance = new CartasAbertas(DeckCartasVagao.getInstance());
        }
        return instance;
    }

    public CartaVagao pegar(int indice) {
        if (indice < 0 || indice >= NUMERO_DE_CARTAS_ABERTAS_LIMITE) {
            return null;
        }
        CartaVagao cartaPega = cartas[indice];
        cartas[indice] = null;
        return cartaPega;
    }

    public void repor(int indice, DeckCartasVagao deck) {
        if (indice < 0 || indice >= NUMERO_DE_CARTAS_ABERTAS_LIMITE) {
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
