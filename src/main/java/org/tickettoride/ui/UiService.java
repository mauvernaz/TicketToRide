package org.tickettoride.ui;

import game.Jogo;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.CartaVagao;
import model.Cor;
import model.Jogador;

import java.util.List;
import java.util.Objects;

// --- Métodos de UI (atualizarUI, criarImageView) mantidos da versão anterior ---
public class UiService {
    private final Jogo jogo;
    private final Label nomeJogador;
    private final Label pontuacao;
    private final Label vagoes;
    private final HBox hboxMesa;
    private final HBox hboxMao;

    public UiService(Jogo jogo, Label nomeJogador, Label pontuacao, Label vagoes, HBox mesa, HBox hboxMao, JogoService jogoService){
        this.jogo = jogo;
        this.nomeJogador = nomeJogador;
        this.pontuacao = pontuacao;
        this.vagoes = vagoes;
        this.hboxMesa = mesa;
        this.hboxMao = hboxMao;
    }

    private void atualizaMesaEMao(Jogador jogador){
        renderizarMesa();
        renderizarMao(jogador);
    }

    /**
     * Helper que converte a Cor do modelo para a cor do JavaFX
     */
    public void atualizarVisualRota(Rectangle visual) {
        switch (this.jogo.getJogadorAtual().getCorMarcador()) {
            case AZUL -> visual.setFill(Color.BLUE);
            case VERMELHO -> visual.setFill(Color.RED);
            case VERDE -> visual.setFill(Color.GREEN);
            case AMARELO -> visual.setFill(Color.YELLOW);
            case PRETO -> visual.setFill(Color.BLACK);
            default -> visual.setFill(Color.GRAY);
        }
        visual.setOpacity(1.0); // Torna sólida para indicar posse
    }

    public void atualizaUI() {
        Jogador jogadorAtual = jogo.getJogadorAtual();
        this.nomeJogador.setText(jogadorAtual.getNome());
        this.pontuacao.setText(String.valueOf(jogadorAtual.getPontuacao()));
        this.vagoes.setText(String.valueOf(jogadorAtual.getEstoqueVagoes()));

        atualizaMesaEMao(jogadorAtual);

        if (jogo.isFimDeJogo()) {
            mostrarTelaVencedor();
        }
    }

    private void renderizarMesa() {
        this.hboxMesa.getChildren().clear();
        Button btnDeck = new Button("Deck");
        btnDeck.setOnAction(e -> { jogo.executarAcaoComprarCartaDeck(); atualizaUI(); });
        this.hboxMesa.getChildren().add(btnDeck);

        for(int i=0; i<jogo.getCartasAbertas().size(); i++) {
            final int idx = i;
            ImageView img = criarImageView(jogo.getCartasAbertas().get(i));
            img.setOnMouseClicked(e -> { jogo.executarAcaoComprarCartaAberta(idx); atualizaUI(); });
            this.hboxMesa.getChildren().add(img);
        }
    }

    private void renderizarMao(Jogador jogador) {
        this.hboxMao.getChildren().clear();
        for(CartaVagao carta : jogador.getMaoDeCartas()) {
            this.hboxMao.getChildren().add(criarImageView(carta));
        }
    }

    private ImageView criarImageView(CartaVagao c) {
        String path = (c == null) ? "/images/verso_vagao.png" : "/images/" + c.getCor().name() + ".png";
        try {
            ImageView img = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))));
            img.setFitHeight(80); img.setPreserveRatio(true);
            return img;
        } catch (Exception e) { return new ImageView(); } // Fallback
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
