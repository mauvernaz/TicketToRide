package game;

import model.CartaVagao;

import java.util.ArrayList;
import java.util.List;

public class DescarteCartasVagao {
    private List<CartaVagao> cartas;

    public DescarteCartasVagao() {
        this.cartas = new ArrayList<>();
    }

    public void adicionar(CartaVagao carta) {
        this.cartas.add(carta);
    }

    public void adicionar(List<CartaVagao> cartas) {
        this.cartas.addAll(cartas);
    }

    public List<CartaVagao> pegarTodasAsCartas() {
        List<CartaVagao> todas = new ArrayList<>(cartas);
        cartas.clear();
        return todas;
    }
}