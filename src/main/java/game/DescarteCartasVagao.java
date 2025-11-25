package game;

import model.CartaVagao;

import java.util.ArrayList;
import java.util.List;

/**
 * GoF: Singleton
 */
public class DescarteCartasVagao {
    private static DescarteCartasVagao instance;
    private List<CartaVagao> cartas;

    private DescarteCartasVagao() {
        this.cartas = new ArrayList<>();
    }

    public static DescarteCartasVagao getInstance() {
        if(instance == null){
            instance = new DescarteCartasVagao();
        }
        return instance;
    }

    public void adicionar(CartaVagao carta) {
        instance.cartas.add(carta);
    }

    public void adicionar(List<CartaVagao> cartas) {
        instance.cartas.addAll(cartas);
    }

    public List<CartaVagao> pegarTodasAsCartas() {
        List<CartaVagao> todas = new ArrayList<>(cartas);
        cartas.clear();
        return todas;
    }
}