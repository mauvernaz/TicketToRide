package game;
import model.Carta;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * classe abstrata que representa um baralho genérico de cartas
 * usa Generics (<T>) para poder ser um baralho de qualquer tipo de Carta
 * @param <T> o tipo de carta que este baralho irá conter (CartaVagao, CartaDestino)
 */
public abstract class Deck<T extends Carta> {
    protected List<T> cartas;

    public Deck(List<T> cartasIniciais) {
        this.cartas = new ArrayList<>(cartasIniciais);
    }

    public void embaralhar() {
        Collections.shuffle(this.cartas);
    }

    public T comprar() {
        if (!estaVazio()) {
            return this.cartas.remove(0); // Remove o primeiro elemento da lista (o "topo")
        }
        return null;
    }

    public boolean estaVazio() {
        return this.cartas.isEmpty();
    }

    public int tamanho() {
        return this.cartas.size();
    }
}