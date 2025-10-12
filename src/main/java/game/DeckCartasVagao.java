package game;
import model.CartaVagao;
import java.util.List;

public class DeckCartasVagao extends Deck<CartaVagao> {

    public DeckCartasVagao(List<CartaVagao> todasCartas) {
        super(todasCartas);
    }

    public void reabastecer(List<CartaVagao> cartasDoDescarte) {
        this.cartas.addAll(cartasDoDescarte);
        this.embaralhar();
    }
}