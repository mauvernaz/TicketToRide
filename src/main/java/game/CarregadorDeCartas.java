package game;

import model.CartaVagao;
import model.Cor;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitária
 */
public class CarregadorDeCartas {

    /**
     * cria a lista completa de cartas de vagão conforme as regras do jogo.
     * (12 de cada cor + 14 locomotivas/coringas).
     * @return uma lista com todas as 110 cartas de vagão.
     */
    public static List<CartaVagao> criarBaralhoCompletoVagao() {
        List<CartaVagao> baralho = new ArrayList<>();

        // cores normais: 12 de cada
        Cor[] coresNormais = {Cor.PRETO, Cor.AZUL, Cor.VERDE, Cor.VERMELHO, Cor.AMARELO, Cor.BRANCO, Cor.LARANJA, Cor.ROSA};
        for (Cor cor : coresNormais) {
            for (int i = 0; i < 12; i++) {
                baralho.add(new CartaVagao(cor));
            }
        }

        // coringas (Locomotivas): 14
        for (int i = 0; i < 14; i++) {
            baralho.add(new CartaVagao(Cor.CORINGA));
        }

        return baralho;
    }

    // criar pra cartas destino
    // public static List<CartaDestino> criarBaralhoDestino() { ... }
}