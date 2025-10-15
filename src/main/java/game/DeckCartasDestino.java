package game;
import model.CartaDestino;
import java.util.List;

public class DeckCartasDestino extends Deck<CartaDestino> {

    public DeckCartasDestino(List<CartaDestino> todasCartas) {
        super(todasCartas);
    }

    public void devolverAoFundo(List<CartaDestino> cartas) {
        this.cartas.addAll(cartas);
    }
}