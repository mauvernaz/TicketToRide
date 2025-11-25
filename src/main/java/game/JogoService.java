package game;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class JogoService {
    static int NUMERO_CARTAS_INICIAL = 4;
    private static int cartasCompradasNoTurno = 0;
    private static DescarteCartasVagao descarteVagao = DescarteCartasVagao.getInstance();
    private static int jogadorAtual;


    public static void inicializarJogadores(List<String> nomes) {
        Jogadores.getOrCreateInstance(nomes);

        for (Jogador jogador : Jogadores.getJogadores().getListaJogadores()) {
            adicionarCartasNaMaoDoJogador(jogador);
            adicionaCartasDestinoNaMaoDoJogador(jogador);
        }
    }
    private static void adicionaCartasDestinoNaMaoDoJogador(Jogador jogador) {
        DeckCartasDestino deckDestino = DeckCartasDestino.getDeckEmbaralhado();

        for(int i=0; i<3; i++) if(!deckDestino.estaVazio()) jogador.adicionarObjetivos(List.of(deckDestino.comprar()));
    }

    private static void adicionarCartasNaMaoDoJogador(Jogador jogador) {
        DeckCartasVagao deckVagao = DeckCartasVagao.getInstance();
        for (int i = 0; i < NUMERO_CARTAS_INICIAL; i++) {
            if(!deckVagao.estaVazio()) jogador.adicionarCartaNaMao(deckVagao.comprar());
        }
    }

    public static void executarAcaoComprarCartaDeck() {
        DeckCartasVagao deckVagao = DeckCartasVagao.getInstance();

        if (cartasCompradasNoTurno >= 2) return;
        if (deckVagao.estaVazio()) deckVagao.reabastecer(descarteVagao.pegarTodasAsCartas());
        if (deckVagao.estaVazio()) return;

        getJogadorAtual().adicionarCartaNaMao(deckVagao.comprar());
        cartasCompradasNoTurno++;
        if (cartasCompradasNoTurno == 2) iniciarProximoTurno();
    }

    public static void executarAcaoComprarCartaAberta(int indice) {
        CartasAbertas cartasAbertasInstance = CartasAbertas.getInstance();
        DeckCartasVagao deckVagao = DeckCartasVagao.getInstance();

        if (cartasCompradasNoTurno >= 2) return;

        CartaVagao cartaAlvo = cartasAbertasInstance.getCartas().get(indice);
        if (cartaAlvo == null) return;

        boolean isLocomotiva = (cartaAlvo.getCor() == Cor.CORINGA);
        if (isLocomotiva && cartasCompradasNoTurno > 0) return; // Regra da Locomotiva

        CartaVagao cartaComprada = cartasAbertasInstance.pegar(indice);
        if (cartaComprada != null) {
            getJogadorAtual().adicionarCartaNaMao(cartaComprada);
            cartasAbertasInstance.repor(indice, deckVagao);

            if (isLocomotiva) {
                cartasCompradasNoTurno = 2; // Locomotiva vale por 2
                iniciarProximoTurno();
            } else {
                cartasCompradasNoTurno++;
                if (cartasCompradasNoTurno == 2) iniciarProximoTurno();
            }
        }
    }

    public static boolean executarAcaoReivindicar(Rota rota, List<CartaVagao> pagamento) {
        if (cartasCompradasNoTurno > 0) return false;

        Jogador jogador = getJogadorAtual();
        if (jogador.podeReivindicarRota(rota, pagamento)) {
            jogador.removerCartasDaMao(pagamento);
            descarteVagao.adicionar(pagamento);

            rota.setDono(jogador);
            jogador.adicionarPontos(rota.getPontos());
            jogador.decrementarVagoes(rota.getComprimento());

            iniciarProximoTurno();
            return true;
        }
        return false;
    }

    public static void setJogadorAtual(int index){
        jogadorAtual = index;
    }

    private static void iniciarProximoTurno() {
        cartasCompradasNoTurno = 0;
        setJogadorAtual((jogadorAtual + 1) % Jogadores.getJogadores().getListaJogadores().size());
    }

    public boolean checarCondicaoFim() {
        for (Jogador j : Jogadores.getJogadores().getListaJogadores()) if (j.getEstoqueVagoes() <= 2) return true;
        return false;
    }

    public static void calcularPontuacoesFinais() {
        for (Jogador jogador : Jogadores.getJogadores().getListaJogadores()) {
            // Para cada objetivo do jogador...
            for (CartaDestino bilhete : jogador.getMaoDeDestino()) {
                // Usa o algoritmo BFS do Tabuleiro para checar se completou
                boolean completou = Tabuleiro.getInstance().checarConectividade(
                        jogador,
                        bilhete.getOrigem(),
                        bilhete.getDestino()
                );

                if (completou) {
                    jogador.adicionarPontos(bilhete.getValor());
                } else {
                    jogador.adicionarPontos(-bilhete.getValor()); // Penalidade
                }
            }
        }
    }

    /**
     * Retorna uma lista com o(s) vencedor(es) (em caso de empate).
     * A UI usará isso para mostrar a mensagem.
     */
    public static List<Jogador> getVencedores() {
        calcularPontuacoesFinais(); // Garante que os pontos de destino foram somados

        List<Jogador> vencedores = new ArrayList<>();
        int maiorPontuacao = Integer.MIN_VALUE;

        for (Jogador j : Jogadores.getJogadores().getListaJogadores()) {
            if (j.getPontuacao() > maiorPontuacao) {
                maiorPontuacao = j.getPontuacao();
                vencedores.clear();
                vencedores.add(j);
            } else if (j.getPontuacao() == maiorPontuacao) {
                vencedores.add(j);
            }
        }
        return vencedores;
    }


    public static Jogador getJogadorAtual() {
        Jogadores jogadores = Jogadores.getJogadores();
        return jogadores.getJogadorAtIndex(jogadorAtual);
    }
}
