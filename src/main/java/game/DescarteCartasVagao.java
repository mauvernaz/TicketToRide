package game;

import model.CartaVagao;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a pilha de descarte de cartas de vagão.
 */
public class DescarteCartasVagao {
    private List<CartaVagao> cartas;

    public DescarteCartasVagao() {
        // Sempre inicialize as listas para evitar NullPointerException!
        this.cartas = new ArrayList<>();
    }

    /**
     * Adiciona uma carta à pilha de descarte.
     * @param carta A carta a ser descartada.
     */
    public void adicionar(CartaVagao carta) {
        this.cartas.add(carta);
    }

    /**
     * Adiciona uma lista de cartas à pilha de descarte.
     * @param cartas A lista de cartas a serem descartadas.
     */
    public void adicionar(List<CartaVagao> cartas) {
        this.cartas.addAll(cartas);
    }

    /**
     * Remove e retorna todas as cartas da pilha de descarte.
     * Usado para reabastecer o Deck de vagões.
     * @return Uma lista com todas as cartas que estavam no descarte.
     */
    public List<CartaVagao> pegarTodasAsCartas() {
        // Cria uma cópia, limpa a original e retorna a cópia. É uma abordagem segura.
        List<CartaVagao> todas = new ArrayList<>(cartas);
        cartas.clear();
        return todas;
    }
}