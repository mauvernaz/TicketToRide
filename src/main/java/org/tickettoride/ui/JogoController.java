package org.tickettoride.ui;

import game.Jogo;
import model.CartaVagao;
import model.Cor;
import model.Jogador;
import model.Rota;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JogoController {

    private Jogo jogo;

    @FXML private AnchorPane painelMapa; // Onde ficam o Mapa e os Retângulos
    @FXML private Label labelNomeJogador;
    @FXML private Label labelPontuacao;
    @FXML private Label labelVagoes;
    @FXML private HBox hboxMesa;
    @FXML private HBox hboxMaoJogador;

    @FXML
    public void initialize() {
        List<String> nomes = List.of("Jogador 1", "Jogador 2");
        this.jogo = new Jogo(nomes);

        configurarInteracaoMapa(); // Configuração dinâmica das rotas
        atualizarUI();
    }

    /**
     * MAGIA DO FRONTEND: Varre todos os componentes do mapa.
     * Se achar um retângulo com ID começando com "rota_", adiciona o evento de clique.
     */
    private void configurarInteracaoMapa() {
        for (Node node : painelMapa.getChildren()) {
            if (node instanceof Rectangle && node.getId() != null && node.getId().startsWith("rota_")) {
                node.setOnMouseClicked(this::handleCliqueRota);
                node.setStyle("-fx-cursor: hand;");
                ((Rectangle) node).setOpacity(0.3); // Semitransparente para ver o mapa
            }
        }
    }

    private void handleCliqueRota(MouseEvent event) {
        Rectangle visual = (Rectangle) event.getSource();
        String id = visual.getId(); // ex: "rota_Vancouver_Seattle"

        // 1. Parse do ID para obter cidades
        String[] partes = id.split("_");
        if (partes.length < 3) return;

        // 2. Busca Rota no Backend
        Rota rota = jogo.getTabuleiro().buscarRota(partes[1], partes[2]);
        if (rota == null) {
            mostrarAlerta("Erro", "Rota não configurada no backend: " + id);
            return;
        }

        if (rota.getDono() != null) {
            mostrarAlerta("Ocupada", "Esta rota já tem dono!");
            return;
        }

        // 3. Tenta Reivindicar
        tentarReivindicarRota(rota, visual);
    }

    private void tentarReivindicarRota(Rota rota, Rectangle visual) {
        Jogador jogador = jogo.getJogadorAtual();

        // Algoritmo Guloso para selecionar cartas automaticamente da mão
        List<CartaVagao> pagamento = calcularPagamentoAutomatico(jogador, rota);

        if (pagamento != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmar Jogada");
            confirm.setHeaderText("Reivindicar rota " + rota.getCidadeA().getNome() + " - " + rota.getCidadeB().getNome());
            confirm.setContentText("Custo: " + rota.getComprimento() + " cartas. Pagar?");

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean sucesso = jogo.executarAcaoReivindicar(rota, pagamento);
                if (sucesso) {
                    atualizarVisualRota(visual, jogador.getCorMarcador());
                    atualizarUI();
                }
            }
        } else {
            mostrarAlerta("Cartas Insuficientes", "Você precisa de " + rota.getComprimento() + " cartas da cor " + rota.getCor());
        }
    }

    /**
     * Helper que converte a Cor do modelo para a cor do JavaFX
     */
    private void atualizarVisualRota(Rectangle visual, Cor corJogador) {
        switch (corJogador) {
            case AZUL -> visual.setFill(Color.BLUE);
            case VERMELHO -> visual.setFill(Color.RED);
            case VERDE -> visual.setFill(Color.GREEN);
            case AMARELO -> visual.setFill(Color.YELLOW);
            case PRETO -> visual.setFill(Color.BLACK);
            default -> visual.setFill(Color.GRAY);
        }
        visual.setOpacity(1.0); // Torna sólida para indicar posse
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

    // --- Métodos de UI (atualizarUI, criarImageView) mantidos da versão anterior ---

    private void atualizarUI() {
        Jogador atual = jogo.getJogadorAtual();
        labelNomeJogador.setText(atual.getNome());
        labelPontuacao.setText(String.valueOf(atual.getPontuacao()));
        labelVagoes.setText(String.valueOf(atual.getEstoqueVagoes()));

        // Atualiza mesa e mão (código igual ao anterior)
        renderizarMesa();
        renderizarMao(atual);

        if (jogo.isFimDeJogo()) {
            mostrarTelaVencedor();
        }
    }

    private void renderizarMesa() {
        hboxMesa.getChildren().clear();
        Button btnDeck = new Button("Deck");
        btnDeck.setOnAction(e -> { jogo.executarAcaoComprarCartaDeck(); atualizarUI(); });
        hboxMesa.getChildren().add(btnDeck);

        for(int i=0; i<jogo.getCartasAbertas().size(); i++) {
            final int idx = i;
            ImageView img = criarImageView(jogo.getCartasAbertas().get(i));
            img.setOnMouseClicked(e -> { jogo.executarAcaoComprarCartaAberta(idx); atualizarUI(); });
            hboxMesa.getChildren().add(img);
        }
    }

    private void renderizarMao(Jogador j) {
        hboxMaoJogador.getChildren().clear();
        for(CartaVagao c : j.getMaoDeCartas()) {
            hboxMaoJogador.getChildren().add(criarImageView(c));
        }
    }

    private ImageView criarImageView(CartaVagao c) {
        String path = (c == null) ? "/images/verso_vagao.png" : "/images/" + c.getCor().name() + ".png";
        try {
            ImageView img = new ImageView(new Image(getClass().getResourceAsStream(path)));
            img.setFitHeight(80); img.setPreserveRatio(true);
            return img;
        } catch (Exception e) { return new ImageView(); } // Fallback
    }

    private void mostrarAlerta(String titulo, String conteudo) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo); a.setContentText(conteudo);
        a.showAndWait();
    }


    private void mostrarTelaVencedor() {
        List<Jogador> vencedores = jogo.getVencedores();

        StringBuilder msg = new StringBuilder();
        if (vencedores.size() == 1) {
            msg.append("O Vencedor é ").append(vencedores.get(0).getNome());
        } else {
            msg.append("Empate entre: ");
            for (Jogador j : vencedores) msg.append(j.getNome()).append(" ");
        }

        msg.append("\n\nPlacar Final:\n");
        // Precisamos de acesso à lista de todos os jogadores para mostrar o ranking
        // (Você pode adicionar um getJogadores() na classe Jogo se não tiver)
        // Aqui assumo que vamos apenas parabenizar o vencedor.
        msg.append("Pontuação: ").append(vencedores.get(0).getPontuacao());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fim de Jogo");
        alert.setHeaderText("Temos um magnata ferroviário!");
        alert.setContentText(msg.toString());

        // Bloqueia a interação até fechar o alerta
        alert.showAndWait();

        // Fecha o jogo ou reinicia (System.exit(0) fecha tudo)
        System.exit(0);
    }

}