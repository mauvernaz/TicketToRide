package game;

import java.util.*;
import model.*;

/**
 * GRASP - Controller: Orquestra a lógica do jogo.
 */
public class Jogo {
    private Tabuleiro tabuleiro;
    private List<Jogador> jogadores;
    private int jogadorAtualIndex;
    private int cartasCompradasNoTurno = 0;

    private DeckCartasVagao deckVagao;
    private DescarteCartasVagao descarteVagao;
    private CartasAbertas cartasAbertas;
    private DeckCartasDestino deckDestino;

    public Jogo(List<String> nomesJogadores) {
        this.jogadores = new ArrayList<>();
        // Define cores diferentes para cada jogador
        Cor[] coresDisponiveis = {Cor.VERMELHO, Cor.AZUL, Cor.AMARELO, Cor.VERDE, Cor.PRETO};
        for (int i = 0; i < nomesJogadores.size(); i++) {
            this.jogadores.add(new Jogador(nomesJogadores.get(i), coresDisponiveis[i % coresDisponiveis.length]));
        }
        setupInicial();
    }

    private void setupInicial() {
        CarregadorDeCartas carregador = new CarregadorDeCartas();
        this.tabuleiro = new Tabuleiro("mapa/america.txt");

        List<CartaVagao> todasCartasVagao = carregador.criarBaralhoCompletoVagao();
        this.deckVagao = new DeckCartasVagao(todasCartasVagao);
        this.deckVagao.embaralhar();

        this.descarteVagao = new DescarteCartasVagao();
        this.cartasAbertas = new CartasAbertas(this.deckVagao);

        List<CartaDestino> todasCartasDestino = carregador.criarBaralhoDestino();
        this.deckDestino = new DeckCartasDestino(todasCartasDestino);
        this.deckDestino.embaralhar();

        // Distribuição inicial
        for (Jogador jogador : jogadores) {
            for (int i = 0; i < 4; i++) {
                if(!deckVagao.estaVazio()) jogador.adicionarCartaNaMao(deckVagao.comprar());
            }
            // Simulação: Jogador pega 3 objetivos iniciais
            for(int i=0; i<3; i++) if(!deckDestino.estaVazio()) jogador.adicionarObjetivos(List.of(deckDestino.comprar()));
        }
        this.jogadorAtualIndex = 0;
    }

    // --- Lógica de Ações ---

    public void executarAcaoComprarCartaDeck() {
        if (this.cartasCompradasNoTurno >= 2) return;
        if (deckVagao.estaVazio()) deckVagao.reabastecer(descarteVagao.pegarTodasAsCartas());
        if (deckVagao.estaVazio()) return;

        getJogadorAtual().adicionarCartaNaMao(deckVagao.comprar());
        this.cartasCompradasNoTurno++;
        if (this.cartasCompradasNoTurno == 2) iniciarProximoTurno();
    }

    public void executarAcaoComprarCartaAberta(int indice) {
        if (this.cartasCompradasNoTurno >= 2) return;

        CartaVagao cartaAlvo = cartasAbertas.getCartas().get(indice);
        if (cartaAlvo == null) return;

        boolean isLocomotiva = (cartaAlvo.getCor() == Cor.CORINGA);
        if (isLocomotiva && this.cartasCompradasNoTurno > 0) return; // Regra da Locomotiva

        CartaVagao cartaComprada = cartasAbertas.pegar(indice);
        if (cartaComprada != null) {
            getJogadorAtual().adicionarCartaNaMao(cartaComprada);
            cartasAbertas.repor(indice, deckVagao);

            if (isLocomotiva) {
                this.cartasCompradasNoTurno = 2; // Locomotiva vale por 2
                iniciarProximoTurno();
            } else {
                this.cartasCompradasNoTurno++;
                if (this.cartasCompradasNoTurno == 2) iniciarProximoTurno();
            }
        }
    }

    public boolean executarAcaoReivindicar(Rota rota, List<CartaVagao> pagamento) {
        if (this.cartasCompradasNoTurno > 0) return false;

        Jogador jogador = getJogadorAtual();
        if (jogador.podeReivindicarRota(rota, pagamento)) {
            jogador.removerCartasDaMao(pagamento);
            descarteVagao.adicionar(pagamento);

            rota.setDono(jogador);
            jogador.adicionarPontos(rota.getPontos());
            jogador.decrementarVagoes(rota.getComprimento());

            iniciarProximoTurno();
            return true; // Sucesso
        }
        return false;
    }

    private void iniciarProximoTurno() {
        this.cartasCompradasNoTurno = 0;
        this.jogadorAtualIndex = (this.jogadorAtualIndex + 1) % this.jogadores.size();
    }

    public boolean checarCondicaoFim() {
        for (Jogador j : jogadores) if (j.getEstoqueVagoes() <= 2) return true;
        return false;
    }

    public void calcularPontuacoesFinais() {
        System.out.println("\n--- CÁLCULO FINAL DE PONTOS ---");

        for (Jogador jogador : jogadores) {
            System.out.println("Calculando para: " + jogador.getNome());

            for (CartaDestino bilhete : jogador.getMaoDeDestino()) {
                // 1. Chama o BFS do Tabuleiro
                boolean completou = tabuleiro.checarConectividade(
                        jogador,
                        bilhete.getOrigem(),
                        bilhete.getDestino()
                );

                // 2. Aplica a pontuação
                if (completou) {
                    System.out.println("  [SUCESSO] " + bilhete.getOrigem().getNome() + " -> " + bilhete.getDestino().getNome() + " (+" + bilhete.getValor() + ")");
                    jogador.adicionarPontos(bilhete.getValor());
                } else {
                    System.out.println("  [FALHA]   " + bilhete.getOrigem().getNome() + " -> " + bilhete.getDestino().getNome() + " (-" + bilhete.getValor() + ")");
                    jogador.adicionarPontos(-bilhete.getValor()); // Penalidade!
                }
            }
        }
    }

    /**
     * Retorna uma lista com o(s) vencedor(es) (em caso de empate).
     * A UI usará isso para mostrar a mensagem.
     */
    public List<Jogador> getVencedores() {
        calcularPontuacoesFinais(); // Bilhetes

        // --- CÁLCULO DO BÔNUS DE MAIOR ROTA (+10) ---
        int maxRotaGlobal = 0;
        List<Jogador> ganhadoresBonus = new ArrayList<>();

        for (Jogador j : jogadores) {
            int maiorCaminho = tabuleiro.calcularMaiorCaminho(j);
            System.out.println("Maior caminho de " + j.getNome() + ": " + maiorCaminho);

            if (maiorCaminho > maxRotaGlobal) {
                maxRotaGlobal = maiorCaminho;
                ganhadoresBonus.clear();
                ganhadoresBonus.add(j);
            } else if (maiorCaminho == maxRotaGlobal && maxRotaGlobal > 0) {
                ganhadoresBonus.add(j);
            }
        }

        // Aplica os 10 pontos
        for (Jogador j : ganhadoresBonus) {
            System.out.println("BÔNUS! " + j.getNome() + " ganhou +10 pontos pela maior rota.");
            j.adicionarPontos(10);
        }
        // ---------------------------------------------

        List<Jogador> vencedores = new ArrayList<>();
        // ... (resto da lógica de achar o maior score que já existia) ...
        int maiorPontuacao = Integer.MIN_VALUE;
        for (Jogador j : jogadores) {
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

    // Método auxiliar para saber se o jogo acabou oficialmente
    public boolean isFimDeJogo() {
        // A regra oficial é: quando alguém tem 2 ou menos vagões,
        // todos jogam mais um turno. Para simplificar a versão acadêmica:
        // Acaba assim que alguém tiver 2 ou menos.
        for (Jogador j : jogadores) {
            if (j.getEstoqueVagoes() <= 2) return true;
        }
        return false;
    }

    // Em Jogo.java

    /**
     * Passo 1: Pega 3 cartas do baralho para mostrar ao jogador.
     */
    public List<CartaDestino> comprarNovosObjetivosCandidatos() {
        // Validação de turno
        if (this.cartasCompradasNoTurno > 0) return null;

        List<CartaDestino> candidatos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (!deckDestino.estaVazio()) {
                candidatos.add(deckDestino.comprar());
            }
        }
        return candidatos;
    }

    /**
     * Passo 2: Recebe a decisão do jogador e finaliza o turno.
     */
    public void confirmarEscolhaObjetivos(List<CartaDestino> manter, List<CartaDestino> devolver) {
        getJogadorAtual().adicionarObjetivos(manter);
        deckDestino.devolverAoFundo(devolver);

        // Essa ação consome o turno inteiro
        iniciarProximoTurno();
    }

    // --- GETTERS NECESSÁRIOS PARA UI ---
    public Jogador getJogadorAtual() { return this.jogadores.get(this.jogadorAtualIndex); }
    public List<CartaVagao> getCartasAbertas() { return this.cartasAbertas.getCartas(); }
    public Tabuleiro getTabuleiro() { return this.tabuleiro; }
    public int getCartasCompradasNoTurno() { return cartasCompradasNoTurno; }
}