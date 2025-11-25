package game;

import java.util.*;
import model.*;

import static game.JogoService.inicializarJogadores;

/**
 * GRASP - Controller: Orquestra a lógica de negócio de Jogo.
 * GRASP - Information Expert: Possui todas as informações relacionadas ao Jogo
 */
public class JogoController {
    private Tabuleiro tabuleiro;


    private DeckCartasVagao deckVagao;
    private CartasAbertas cartasAbertas;


    public JogoController(List<String> nomesJogadores) {
        this.tabuleiro = Tabuleiro.getInstance("mapa/america.txt");
        this.deckVagao = DeckCartasVagao.inicializarCartasEmbaralhadas();
        this.cartasAbertas = CartasAbertas.getInstance();

        inicializarJogadores(nomesJogadores);

        JogoService.setJogadorAtual(0);
    }










    // --- GETTERS NECESSÁRIOS PARA UI ---

    public List<CartaVagao> getCartasAbertas() { return this.cartasAbertas.getCartas(); }
    public Tabuleiro getTabuleiro() { return this.tabuleiro; }
}