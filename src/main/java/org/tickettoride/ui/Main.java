package org.tickettoride.ui;

import game.Jogo;
import model.CartaVagao;
import model.Jogador;
import model.Cor;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *interagir com o usuário e delegar as ações para a classe Jogo (Controlador)
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=========================================");
        System.out.println(" BEM-VINDO AO TICKET TO RIDE - VERSÃO 0.1 ");
        System.out.println("=========================================");

        //1: configurar os jogadores
        List<String> nomesJogadores = configurarJogadores(scanner);

        //2: inicializar o jogo
        Jogo jogo = new Jogo(nomesJogadores);
        System.out.println("\nO jogo começou! Boa sorte a todos.");

        //3: loop principal do jogo
        while (!jogo.checarCondicaoFim()) {
            Jogador jogadorAtual = jogo.getJogadorAtual();

            exibirStatusCompleto(jogo);

            int escolha = apresentarOpcoes(jogadorAtual, scanner);

            processarEscolha(escolha, jogo, scanner);
        }

        //4: fim de jogo
        System.out.println("=========================================");
        System.out.println("          FIM DE JOGO!          ");
        System.out.println("=========================================");
        // a lógica de cálculo final e exibição do vencedor seria chamada aqui
        // jogo.calcularPontuacaoFinal();
        // exibirVencedor(jogo);

        scanner.close();
    }

    private static List<String> configurarJogadores(Scanner scanner) {
        int numJogadores = 0;
        while (numJogadores < 2 || numJogadores > 5) {
            System.out.print("\nDigite o número de jogadores (2-5): ");
            try {
                numJogadores = Integer.parseInt(scanner.nextLine());
                if (numJogadores < 2 || numJogadores > 5) {
                    System.out.println("Número inválido. Por favor, insira um valor entre 2 e 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
            }
        }

        List<String> nomes = new ArrayList<>();
        for (int i = 0; i < numJogadores; i++) {
            System.out.print("Digite o nome do Jogador " + (i + 1) + ": ");
            nomes.add(scanner.nextLine());
        }
        return nomes;
    }

    private static void exibirStatusCompleto(Jogo jogo) {
        Jogador jogador = jogo.getJogadorAtual();
        System.out.println("\n-------------------------------------------------");
        System.out.println(">>> TURNO DE: " + jogador.getNome().toUpperCase() + " <<<");
        System.out.println("-------------------------------------------------");
        System.out.println("Pontuação: " + jogador.getPontuacao() + " | Vagões restantes: " + jogador.getEstoqueVagoes());

        if (jogador.getMaoDeCartas() == null) {
            System.out.println("\nSua mão de cartas: [VAZIO]");
        } else {
            String mao = jogador.getMaoDeCartas().stream()
                    .filter(c -> c != null && c.getCor() != null)
                    .map(c -> c.getCor().name())
                    .collect(Collectors.joining(", "));
            System.out.println("\nSua mão de cartas: [" + mao + "]");
        }

        System.out.println("\nCartas Abertas:");
        List<CartaVagao> abertas = jogo.getCartasAbertas();
        for (int i = 0; i < abertas.size(); i++) {
            CartaVagao carta = abertas.get(i);
            System.out.println("  [" + i + "] " + (carta != null ? carta.getCor() : "VAZIO"));
        }
    }

    private static int apresentarOpcoes(Jogador jogador, Scanner scanner) {
        System.out.println("\n" + jogador.getNome() + ", escolha sua ação:");
        System.out.println("1. Comprar carta de vagão do baralho (compra cega)");
        System.out.println("2. Comprar carta de vagão aberta");
        System.out.println("3. Reivindicar uma rota (Funcionalidade para versão final)");
        System.out.println("4. Comprar bilhetes de destino (Funcionalidade para versão final)");

        int escolha = -1;
        while (escolha < 1 || escolha > 4) {
            System.out.print("Sua escolha: ");
            try {
                escolha = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Digite um número de 1 a 4.");
            }
        }
        return escolha;
    }

    private static void processarEscolha(int escolha, Jogo jogo, Scanner scanner) {
        switch (escolha) {
            case 1:
                System.out.println("...Comprando carta do baralho...");
                jogo.executarAcaoComprarCartaDeck();
                break;
            case 2:
                System.out.print("Digite o índice da carta que deseja comprar (0-4): ");
                try {
                    int indice = Integer.parseInt(scanner.nextLine());
                    System.out.println("...Comprando carta aberta de índice " + indice + "...");
                    jogo.executarAcaoComprarCartaAberta(indice);
                } catch (NumberFormatException e) {
                    System.out.println("Índice inválido.");
                }
                break;
            case 3:
                System.out.println("Ação 'Reivindicar Rota' será totalmente implementada na próxima versão.");
                break;
            case 4:
                System.out.println("Ação 'Comprar Bilhetes de Destino' será implementada na próxima versão.");
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }
    }
}