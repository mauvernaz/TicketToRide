package game;

import java.util.List;
import model.Carta;

public class DeckCartas<T extends Carta> extends Deck<T> {
    public DeckCartas(List<T> cartas) {
        super(cartas);
    }
}
