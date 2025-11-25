package org.tickettoride.ui;

import game.JogoService;
import game.Tabuleiro;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.tickettoride.ui.Utils.mostrarAlerta;

public class JogoUIService {
    private final UiService uiService;

    public JogoUIService(UiService uiService){
        this.uiService = uiService;
    }

    /**
     * Se achar um retângulo com ID começando com "rota_", adiciona o evento de clique.
     */
    public void adicionaEventoEmRetangulosDeRota(AnchorPane painel) {
        for (Node node : painel.getChildren()) {
            if (isRetanguloRota(node)) {
                node.setOnMouseClicked(this::validaCliqueRota);
                node.setStyle("-fx-cursor: hand;");
                node.setOpacity(0.3);
            }
        }
    }

    private static boolean isRetanguloRota(Node node) {
        return node instanceof Rectangle && node.getId() != null && node.getId().startsWith("rota_");
    }

    private void validaCliqueRota(MouseEvent event) {
        Rectangle visual = (Rectangle) event.getSource();
        String id = visual.getId(); // ex: "rota_vancouver_seattle"


        String[] partes = parseIdParaObterCidades(id);
        if (partes == null) return;

        List<Rota> rotas = Tabuleiro.getInstance().buscarRotaNasRotas(partes[1], partes[2]);
        if (rotas.isEmpty()) return;

        for(Rota rota : rotas) {
            if (podeReivindicarRota(rota, visual)) {
                uiService.atualizarVisualRota(visual);
                uiService.atualizaUI();
                return;
            }
        }
        if(rotas.get(0).getCor() == Cor.CINZA){
            mostrarAlerta("Cartas Insuficientes", "Você precisa de " + rotas.get(0).getComprimento() + " cartas da mesma cor!");
        } else if (rotas.size() == 2){
            mostrarAlerta("Cartas Insuficientes", "Você precisa de " + rotas.get(0).getComprimento() + " cartas das cores " + rotas.get(0).getCor() + " ou " + rotas.get(1).getCor());
        } else{
            mostrarAlerta("Cartas Insuficientes", "Você precisa de " + rotas.get(0).getComprimento() + " cartas da cor " + rotas.get(0).getCor());

        }
    }

    private static String[] parseIdParaObterCidades(String id) {
        String[] partes = id.split("_");
        if (partes.length < 3) return null;
        return partes;
    }

    private boolean podeReivindicarRota(Rota rota, Rectangle visual) {
        Jogador jogador = JogoService.getJogadorAtual();

        // Algoritmo Guloso para selecionar cartas automaticamente da mão
        List<CartaVagao> pagamento = calcularPagamentoAutomatico(jogador, rota);

        if (pagamento != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmar Jogada");
            confirm.setHeaderText("Reivindicar rota " + rota.getCidadeA().getNome() + " - " + rota.getCidadeB().getNome());
            confirm.setContentText("Custo: " + rota.getComprimento() + " cartas. Pagar?");

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                return JogoService.executarAcaoReivindicar(rota, pagamento);
            }
        }
        return false;
    }

    /**
     * Algoritmo simples para encontrar cartas válidas na mão do jogador.
     */
    /**
     * Algoritmo robusto para selecionar cartas automaticamente.
     * Resolve o problema das rotas Cinzas.
     */
    private List<CartaVagao> calcularPagamentoAutomatico(Jogador j, Rota r) {
        List<CartaVagao> mao = j.getMaoDeCartas();
        int custo = r.getComprimento();
        Cor corRota = r.getCor();

        // CASO 1: Rota com Cor Específica (Não Cinza)
        if (corRota != Cor.CINZA) {
            return tentarPagarComCor(mao, corRota, custo);
        }

        // CASO 2: Rota Cinza (Tenta achar qualquer cor que satisfaça o custo)
        // Testa todas as cores possíveis (exceto Cinza e Coringa)
        for (Cor corTeste : Cor.values()) {
            if (corTeste != Cor.CINZA && corTeste != Cor.CORINGA) {
                List<CartaVagao> tentativa = tentarPagarComCor(mao, corTeste, custo);
                if (tentativa != null) {
                    return tentativa; // Achou uma cor que serve!
                }
            }
        }

        return null; // Não tem cartas suficientes em nenhuma cor
    }

    /**
     * Helper: Tenta formar um pagamento de tamanho 'custo' usando 'corAlvo' + Coringas.
     */
    private List<CartaVagao> tentarPagarComCor(List<CartaVagao> mao, Cor corAlvo, int custo) {
        List<CartaVagao> selecionadas = new ArrayList<>();

        // 1. Pega cartas da cor exata
        for (CartaVagao c : mao) {
            if (c.getCor() == corAlvo && selecionadas.size() < custo) {
                selecionadas.add(c);
            }
        }

        // 2. Se ainda falta, completa com Coringas
        if (selecionadas.size() < custo) {
            for (CartaVagao c : mao) {
                if (c.getCor() == Cor.CORINGA && !selecionadas.contains(c)) {
                    selecionadas.add(c);
                    if (selecionadas.size() == custo) break;
                }
            }
        }

        // Retorna a lista se conseguiu o total, senão retorna null
        return (selecionadas.size() == custo) ? selecionadas : null;
    }

}
