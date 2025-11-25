package game;
import model.CartaDestino;

import java.util.List;

public class DeckCartasDestino extends Deck<CartaDestino> {
    private static CarregadorDeCartas carregador;
    private static DeckCartasDestino instance;

    public static DeckCartasDestino getDeckEmbaralhado() {
        if (instance == null) {
            CarregadorDeCartas carregador = new CarregadorDeCartas();

            List<CartaDestino> cartasDestino = carregador.criarBaralhoDestino();

            instance = new DeckCartasDestino(cartasDestino);
            instance.embaralhar();
        }
        return instance;
    }
    private DeckCartasDestino(List<CartaDestino> cartas) {
        super(cartas);
    }

    public static DeckCartasDestino inicializarCartasEmbaralhadas(){
        return getDeckEmbaralhado();
    }

    public void devolverAoFundo(List<CartaDestino> cartas) {
        this.cartas.addAll(cartas);
    }
}