package game;
import model.CartaDestino;
import java.util.List;

public class DeckCartasDestino extends Deck<CartaDestino> {

    public DeckCartasDestino(List<CartaDestino> todasCartas) {
        super(todasCartas);
    }

    public void devolverAoFundo(List<CartaDestino> cartas) {
        // Adicionar no final da lista simula colocar no "fundo" do baralho.
        this.cartas.addAll(cartas);
    }
}