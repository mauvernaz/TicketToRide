package org.tickettoride.ui;

import game.Jogo;
import model.CartaVagao;
import model.Jogador;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import java.util.List;

public class JogoController {

    private Jogo jogo;

    @FXML private Label labelNomeJogador;
    @FXML private Label labelPontuacao;
    @FXML private Label labelVagoes;
    @FXML private HBox hboxMesa;
    @FXML private HBox hboxMaoJogador;


    @FXML
    public void initialize() {
        List<String> nomes = new ArrayList<>(List.of("Jogador 1", "Jogador 2")); // Nomes de exemplo
        this.jogo = new Jogo(nomes);

        atualizarUI();
    }

    private void atualizarUI() {
        Jogador jogadorAtual = jogo.getJogadorAtual();

        labelNomeJogador.setText(jogadorAtual.getNome());
        labelPontuacao.setText(String.valueOf(jogadorAtual.getPontuacao()));
        labelVagoes.setText(String.valueOf(jogadorAtual.getEstoqueVagoes()));

        hboxMesa.getChildren().clear();

        Button botaoDeck = new Button("Comprar do Baralho");
        botaoDeck.setOnAction(event -> onComprarDoDeckClick());
        hboxMesa.getChildren().add(botaoDeck);

        List<CartaVagao> cartasAbertas = jogo.getCartasAbertas();
        for (int i = 0; i < cartasAbertas.size(); i++) {
            CartaVagao carta = cartasAbertas.get(i);
            ImageView imageView = criarImageViewParaCarta(carta);

            final int indice = i;
            imageView.setOnMouseClicked(event -> onCartaAbertaClick(indice));

            hboxMesa.getChildren().add(imageView);
        }

        hboxMaoJogador.getChildren().clear();
        for (CartaVagao carta : jogadorAtual.getMaoDeCartas()) {
            hboxMaoJogador.getChildren().add(criarImageViewParaCarta(carta));
        }
    }

    private ImageView criarImageViewParaCarta(CartaVagao carta) {
        String caminhoImagem = "/images/verso_vagao.png";
        if (carta != null) {
            caminhoImagem = "/images/" + carta.getCor().name() + ".png";
        }

        Image image = new Image(getClass().getResourceAsStream(caminhoImagem));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(90);
        imageView.setPreserveRatio(true);
        imageView.getStyleClass().add("carta");
        return imageView;
    }

    private void onComprarDoDeckClick() {
        System.out.println("Clicou em 'Comprar do Baralho'");
        jogo.executarAcaoComprarCartaDeck();
        atualizarUI();
    }

    private void onCartaAbertaClick(int indice) {
        System.out.println("Clicou na carta aberta de índice: " + indice);
        jogo.executarAcaoComprarCartaAberta(indice);
        atualizarUI();
    }
}