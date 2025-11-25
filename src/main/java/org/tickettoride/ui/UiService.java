package org.tickettoride.ui;

import game.CartasAbertas;
import game.JogoService;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.CartaDestino;
import model.CartaVagao;
import model.Jogador;
import model.Jogadores;

import java.util.List;
import java.util.Objects;

/**
 * GoF : Builder
 */
// --- Métodos de UI (atualizarUI, criarImageView) mantidos da versão anterior ---
public class UiService {
    private final Label nomeJogador;
    private final Label pontuacao;
    private final Label vagoes;
    private final HBox hboxMesa;
    private final HBox hboxMao;
    private final VBox vboxCartasDestino;

    private UiService(Builder builder) {
        this.nomeJogador = builder.nomeJogador;
        this.pontuacao = builder.pontuacao;
        this.vagoes = builder.vagoes;
        this.hboxMesa = builder.hboxMesa;
        this.hboxMao = builder.hboxMao;
        this.vboxCartasDestino = builder.vboxCartasDestino;
    }

    public static class Builder {
        private Label nomeJogador;
        private Label pontuacao;
        private Label vagoes;
        private HBox hboxMesa;
        private HBox hboxMao;
        private VBox vboxCartasDestino;


        public Builder() {}


        public Builder comCartasDestinos(VBox vboxCartasDestino){
            this.vboxCartasDestino = vboxCartasDestino;
            return this;
        }

        public Builder comNomeJogador(Label nomeJogador) {
            this.nomeJogador = nomeJogador;
            return this;
        }

        public Builder comPontuacao(Label pontuacao) {
            this.pontuacao = pontuacao;
            return this;
        }

        public Builder comVagoes(Label vagoes) {
            this.vagoes = vagoes;
            return this;
        }

        public Builder comMesa(HBox mesa) {
            this.hboxMesa = mesa;
            return this;
        }

        public Builder comMao(HBox mao) {
            this.hboxMao = mao;
            return this;
        }

        public UiService build() {
            return new UiService(this);
        }
    }

    private void atualizaMesaEMao(Jogador jogador){
        renderizarMesa();
        renderizarMao(jogador);
    }

    /**
     * Helper que converte a Cor do modelo para a cor do JavaFX
     */
    public void atualizarVisualRota(Rectangle visual) {
        switch (JogoService.getJogadorAtual().getCorMarcador()) {
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
        Jogador jogadorAtual = JogoService.getJogadorAtual();
        this.nomeJogador.setText(jogadorAtual.getNome());
        this.pontuacao.setText(String.valueOf(jogadorAtual.getPontuacao()));
        this.vagoes.setText(String.valueOf(jogadorAtual.getEstoqueVagoes()));

        this.carregarCartasDestinoIniciaisNaUI();

        atualizaMesaEMao(jogadorAtual);

        if (isFimDeJogo()) {
            mostrarTelaVencedor();
        }
    }

    public boolean isFimDeJogo() {
        // A regra oficial é: quando alguém tem 2 ou menos vagões,
        // todos jogam mais um turno. Para simplificar a versão acadêmica:
        // Acaba assim que alguém tiver 2 ou menos.
        for (Jogador j : Jogadores.getJogadores().getListaJogadores()) {
            if (j.getEstoqueVagoes() <= 2) return true;
        }
        return false;
    }
    private void renderizarMesa() {
        this.hboxMesa.getChildren().clear();
        Button btnDeck = new Button("Deck");
        btnDeck.setOnAction(e -> { JogoService.executarAcaoComprarCartaDeck(); atualizaUI(); });
        this.hboxMesa.getChildren().add(btnDeck);

        for(int i = 0; i< CartasAbertas.getInstance().getCartas().size(); i++) {
            final int idx = i;
            ImageView img = criarImageView(CartasAbertas.getInstance().getCartas().get(i));
            img.setOnMouseClicked(e -> { JogoService.executarAcaoComprarCartaAberta(idx); atualizaUI(); });
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
        List<Jogador> vencedores = JogoService.getVencedores();

        StringBuilder msg = new StringBuilder();
        if (vencedores.size() == 1) {
            msg.append("O Vencedor é ").append(vencedores.get(0).getNome());
        } else {
            msg.append("Empate entre: ");
            for (Jogador j : vencedores) msg.append(j.getNome()).append(" ");
        }

        msg.append("\n\nPlacar Final:\n");

        msg.append("Pontuação: ").append(vencedores.get(0).getPontuacao());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fim de Jogo");
        alert.setHeaderText("Temos um magnata ferroviário!");
        alert.setContentText(msg.toString());

        alert.showAndWait();

        System.exit(0);
    }

    public void adicionarCartaObjetivo(String origem, String destino, int pontos) {
        // 1. Cria o Container da Carta (AnchorPane)
        AnchorPane card = new AnchorPane();
        card.setPrefHeight(80.0);
        card.setStyle("-fx-background-color: #fff8dc; -fx-background-radius: 8; " +
                "-fx-border-color: #d4c4a1; -fx-border-radius: 8; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 2);");

        // 2. Cria a parte das Cidades (VBox Esquerda)
        VBox boxCidades = new VBox();
        boxCidades.setAlignment(Pos.CENTER_LEFT);
        boxCidades.setSpacing(2.0);

        Label lblOrigem = new Label(origem);
        lblOrigem.setTextFill(Color.web("#4a4a4a"));
        lblOrigem.setFont(Font.font("System", FontWeight.BOLD, 14));

        Label lblSeta = new Label("▼");
        lblSeta.setTextFill(Color.web("#888888"));
        lblSeta.setFont(Font.font("System", 10));

        Label lblDestino = new Label(destino);
        lblDestino.setTextFill(Color.web("#4a4a4a"));
        lblDestino.setFont(Font.font("System", FontWeight.BOLD, 14));

        boxCidades.getChildren().addAll(lblOrigem, lblSeta, lblDestino);

        // Posiciona as cidades no AnchorPane
        AnchorPane.setTopAnchor(boxCidades, 10.0);
        AnchorPane.setLeftAnchor(boxCidades, 10.0);
        AnchorPane.setBottomAnchor(boxCidades, 10.0);

        // 3. Cria a "Bolinha" de Pontos (VBox Direita)
        VBox boxPontos = new VBox();
        boxPontos.setAlignment(Pos.CENTER);
        boxPontos.setPrefSize(35, 35);
        boxPontos.setStyle("-fx-background-color: #e6b800; -fx-background-radius: 20;");

        Label lblPontos = new Label(String.valueOf(pontos));
        lblPontos.setTextFill(Color.WHITE);
        // Ajusta tamanho da fonte se o número for grande (ex: 20 vs 4)
        double fontSize = pontos > 9 ? 15.0 : 18.0;
        lblPontos.setFont(Font.font("System", FontWeight.BOLD, fontSize));

        boxPontos.getChildren().add(lblPontos);

        // Posiciona os pontos no AnchorPane
        AnchorPane.setBottomAnchor(boxPontos, 10.0);
        AnchorPane.setRightAnchor(boxPontos, 10.0);

        // 4. Adiciona tudo ao card e o card ao Painel Principal
        card.getChildren().addAll(boxCidades, boxPontos);
        vboxCartasDestino.getChildren().add(card);
    }

    private void limparCartasObjetivo() {
        vboxCartasDestino.getChildren().clear();
    }

    public void carregarCartasDestinoIniciaisNaUI() {
        List<CartaDestino> listaDeCartasDestino = JogoService.getJogadorAtual().getMaoDeDestino();

        limparCartasObjetivo();

        for(CartaDestino carta: listaDeCartasDestino){
            this.adicionarCartaObjetivo(
                    carta.getOrigem().getNome(),
                    carta.getDestino().getNome(),
                    carta.getValor()
            );
        }
    }
}
