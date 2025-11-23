package game;

import java.util.*;

import model.*;


/**
 *(GRASP - Controller)
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
        if (this.cartasCompradasNoTurno >= 2) {
            System.out.println("Você já comprou o limite de cartas neste turno!");
            return;
        }

        if (deckVagao.estaVazio()) {
            deckVagao.reabastecer(descarteVagao.pegarTodasAsCartas());
        }

        if (deckVagao.estaVazio()) {
            System.out.println("Não há cartas no baralho nem no descarte!");
            return;
        }

        CartaVagao carta = deckVagao.comprar();
        if (carta != null) {
            getJogadorAtual().adicionarCartaNaMao(carta);
            this.cartasCompradasNoTurno++;

            System.out.println("Comprou carta do baralho (" + carta.getCor() + "). Total compradas: " + cartasCompradasNoTurno);

            if (this.cartasCompradasNoTurno == 2) {
                iniciarProximoTurno();
            }
        }
    }

    public void executarAcaoComprarCartaAberta(int indice) {
        if (this.cartasCompradasNoTurno >= 2) {
            System.out.println("Você já comprou o limite de cartas neste turno!");
            return;
        }

        CartaVagao cartaAlvo = cartasAbertas.pegar(indice);

        List<CartaVagao> visiveis = cartasAbertas.getCartas();
        if (indice < 0 || indice >= visiveis.size() || visiveis.get(indice) == null) {
            System.out.println("Índice inválido ou carta vazia.");
            return;
        }

        CartaVagao cartaSelecionada = visiveis.get(indice);
        boolean isLocomotiva = (cartaSelecionada.getCor() == Cor.CORINGA);


        if (isLocomotiva && this.cartasCompradasNoTurno > 0) {
            System.out.println("Você não pode pegar uma Locomotiva aberta se já comprou uma carta neste turno!");
            return;
        }

        CartaVagao cartaComprada = cartasAbertas.pegar(indice);

        if (cartaComprada != null) {
            getJogadorAtual().adicionarCartaNaMao(cartaComprada);
            cartasAbertas.repor(indice, deckVagao);

            if (isLocomotiva) {
                this.cartasCompradasNoTurno = 2;
                iniciarProximoTurno();
            } else {
                this.cartasCompradasNoTurno++;
                if (this.cartasCompradasNoTurno == 2) {
                    iniciarProximoTurno();
                }
            }
        }
    }

    public void executarAcaoReivindicar(Rota rota, List<CartaVagao> pagamento) {
        Jogador jogador = getJogadorAtual();

        if (this.cartasCompradasNoTurno > 0) {
            System.out.println("Você já comprou uma carta. Deve comprar outra para encerrar o turno.");
            return;
        }

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

    public void executarAcaoComprarDestinos(Scanner scanner) {

        if (this.cartasCompradasNoTurno > 0) {
            System.out.println("JOGADA INVÁLIDA: Você já iniciou uma compra de cartas de vagão.");
            System.out.println("Você deve comprar uma segunda carta de vagão para encerrar seu turno.");
            return;
        }

        System.out.println("\n--- COMPRANDO BILHETES DE DESTINO ---");
        Jogador jogador = getJogadorAtual();


        List<CartaDestino> novosObjetivos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (!deckDestino.estaVazio()) {
                novosObjetivos.add(deckDestino.comprar());
            }
        }

        if (novosObjetivos.isEmpty()) {
            System.out.println("Não há mais bilhetes de destino para comprar!");
            return;
        }


        System.out.println(jogador.getNome() + ", você comprou os seguintes objetivos:");
        for (int i = 0; i < novosObjetivos.size(); i++) {
            CartaDestino cd = novosObjetivos.get(i);
            System.out.printf("  [%d] De %s para %s (%d pontos)\n", i, cd.getOrigem().getNome(), cd.getDestino().getNome(), cd.getValor());
        }


        List<CartaDestino> cartasParaManter = new ArrayList<>();
        List<CartaDestino> cartasParaDevolver = new ArrayList<>();

        boolean escolhaValida = false;
        while (!escolhaValida) {
            System.out.printf("Digite os índices que deseja manter separados por vírgula (ex: 0,2). Deve manter pelo menos 1: ");
            String input = scanner.nextLine();

            try {
                Set<Integer> indicesEscolhidos = new HashSet<>();
                String[] indicesStr = input.split(",");

                for (String s : indicesStr) {
                    if(!s.trim().isEmpty()){
                        int index = Integer.parseInt(s.trim());
                        if (index >= 0 && index < novosObjetivos.size()) {
                            indicesEscolhidos.add(index);
                        }
                    }
                }

                if (!indicesEscolhidos.isEmpty()) {
                    escolhaValida = true;

                    for (int i = 0; i < novosObjetivos.size(); i++) {
                        if (indicesEscolhidos.contains(i)) {
                            cartasParaManter.add(novosObjetivos.get(i));
                        } else {
                            cartasParaDevolver.add(novosObjetivos.get(i));
                        }
                    }
                } else {
                    System.out.println("Escolha inválida. Você deve manter pelo menos um objetivo.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida.");
            }
        }

        jogador.adicionarObjetivos(cartasParaManter);
        System.out.println("Você manteve " + cartasParaManter.size() + " objetivo(s).");

        if (!cartasParaDevolver.isEmpty()) {
            deckDestino.devolverAoFundo(cartasParaDevolver);
            System.out.println(cartasParaDevolver.size() + " objetivo(s) devolvidos ao fundo do baralho.");
        }

        iniciarProximoTurno();
    }

    private void iniciarProximoTurno() {
        this.cartasCompradasNoTurno = 0;
        this.jogadorAtualIndex = (this.jogadorAtualIndex + 1) % this.jogadores.size();
        System.out.println("Agora é a vez de: " + getJogadorAtual().getNome());
    }

    public boolean checarCondicaoFim() {
        for (Jogador jogador : jogadores) {
            if (jogador.getEstoqueVagoes() <= 2) {
                return true;
            }
        }
        return false;
    }

    public void calcularPontuacaoFinal() {
        for(Jogador jogador: jogadores){
            int pontos = jogador.getPontuacao();
            for(Rota rota : tabuleiro.getRotas()){
                if (rota.getDono() == jogador){
                    pontos += rota.getPontos(); //SOMATÓRIO DE ROTAS
                }
            }
        
        System.out.println(jogador.getNome() + " - " + pontos + " pontos");
        }}

    public void exibirVencedor(Jogo jogo){
        List<Jogador> vencedores = new ArrayList<>();
        int maiorPonto = Integer.MIN_VALUE;
        for(Jogador jogador : jogadores){
            if(jogador.getPontuacao() > maiorPonto){
                vencedores.clear();
                vencedores.add(jogador);
                maiorPonto = jogador.getPontuacao();
                
            } else if(jogador.getPontuacao() == maiorPonto){
                vencedores.add(jogador);
            }
        }

        if(vencedores.size() == 1){
            System.out.println("O vencedor é: " + vencedores.get(0).getNome() + " com " + maiorPonto);
        } else {
            System.out.println("Houve um empate entre: ");
            for(Jogador j: vencedores){
                System.out.println(j.getNome() + ",");

            } 
            System.out.println("Com " + maiorPonto + " pontos!");
        }
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