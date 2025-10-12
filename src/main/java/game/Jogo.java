package game;

import model.*;

import java.util.ArrayList;
import java.util.List;


/**
 *(GRASP - Controller)
 */
public class Jogo {
    private Tabuleiro tabuleiro;
    private List<Jogador> jogadores;
    private int jogadorAtualIndex;

    private DeckCartasVagao deckVagao;
    private DescarteCartasVagao descarteVagao;
    private CartasAbertas cartasAbertas;
    private DeckCartasDestino deckDestino;

    public Jogo(List<String> nomesJogadores) {
        this.jogadores = new ArrayList<>();
        for (String nome : nomesJogadores) {
            this.jogadores.add(new Jogador(nome, Cor.VERMELHO));
        }
        setupInicial();
    }

    private void setupInicial() {

        this.tabuleiro = new Tabuleiro("mapa/america.txt");


        List<CartaVagao> todasCartasVagao = CarregadorDeCartas.criarBaralhoCompletoVagao();
        this.deckVagao = new DeckCartasVagao(todasCartasVagao);
        this.deckVagao.embaralhar();

        this.descarteVagao = new DescarteCartasVagao();

        this.cartasAbertas = new CartasAbertas(this.deckVagao);

        for (Jogador jogador : jogadores) {
            for (int i = 0; i < 4; i++) {
                jogador.adicionarCartaNaMao(deckVagao.comprar());
            }

        }

        this.jogadorAtualIndex = 0;
    }

    public void executarAcaoComprarCartaDeck() {

        Jogador jogador = getJogadorAtual();

        if(deckVagao.estaVazio()){
            deckVagao.reabastecer(descarteVagao.pegarTodasAsCartas());
        }

        CartaVagao cartaComprada = deckVagao.comprar();
        if (cartaComprada != null) {
            jogador.adicionarCartaNaMao(cartaComprada);
        }

        iniciarProximoTurno();
    }

    public void executarAcaoComprarCartaAberta(int indice) {
        Jogador jogador = getJogadorAtual();
        CartaVagao cartaComprada = cartasAbertas.pegar(indice);

        if (cartaComprada != null) {
            jogador.adicionarCartaNaMao(cartaComprada);
            cartasAbertas.repor(indice, deckVagao);
        }

        iniciarProximoTurno();
    }

    public void executarAcaoReivindicar(Rota rota, List<CartaVagao> pagamento) {
        Jogador jogador = getJogadorAtual();

        if (jogador.podeReivindicarRota(rota, pagamento)) {
            jogador.removerCartasDaMao(pagamento);
            descarteVagao.adicionar(pagamento);

            rota.setDono(jogador);
            jogador.adicionarPontos(rota.getPontos());
            jogador.decrementarVagoes(rota.getComprimento());

            iniciarProximoTurno();
        } else {
            System.out.println("Jogada inválida!");
        }
    }

    private void iniciarProximoTurno() {
        this.jogadorAtualIndex = (this.jogadorAtualIndex + 1) % this.jogadores.size();
        System.out.println("Próximo turno: " + getJogadorAtual().getNome());
    }

    public boolean checarCondicaoFim() {
        for (Jogador jogador : jogadores) {
            if (jogador.getEstoqueVagoes() <= 2) {
                return true;
            }
        }
        return false;
    }

    private void calcularPontuacaoFinal() {

    }

    public Jogador getJogadorAtual() {
        return this.jogadores.get(this.jogadorAtualIndex);
    }

    public List<CartaVagao> getCartasAbertas() {
        return this.cartasAbertas.getCartas();
    }

    public Tabuleiro getTabuleiro() {
        return this.tabuleiro;
    }
}