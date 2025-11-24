package org.tickettoride.ui;

import game.Jogo;
import model.*;
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
    @FXML private ListView<String> listaDestinos;

    @FXML
    public void initialize() {
        List<String> nomes = List.of("Jogador 1", "Jogador 2");
        this.jogo = new Jogo(nomes);

        configurarInteracaoMapa();
        atualizarUI();
    }


    private void configurarInteracaoMapa() {
        for (Node node : painelMapa.getChildren()) {
            if (node instanceof Rectangle && node.getId() != null && node.getId().startsWith("rota_")) {
                node.setOnMouseClicked(this::handleCliqueRota);
                node.setStyle("-fx-cursor: hand;");
                ((Rectangle) node).setOpacity(0.3);
            }
        }
    }

    private void handleCliqueRota(MouseEvent event) {
        Rectangle visual = (Rectangle) event.getSource();
        String id = visual.getId();

        String[] partes = id.split("_");
        if (partes.length < 3) return;

        Rota rota = jogo.getTabuleiro().buscarRota(partes[1], partes[2]);
        if (rota == null) {
            mostrarAlerta("Erro", "Rota não configurada no backend: " + id);
            return;
        }

        if (rota.getDono() != null) {
            mostrarAlerta("Ocupada", "Esta rota já tem dono!");
            return;
        }

        tentarReivindicarRota(rota, visual);
    }

    private void tentarReivindicarRota(Rota rota, Rectangle visual) {
        Jogador jogador = jogo.getJogadorAtual();

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

    private void atualizarVisualRota(Rectangle visual, Cor corJogador) {
        switch (corJogador) {
            case AZUL -> visual.setFill(Color.BLUE);
            case VERMELHO -> visual.setFill(Color.RED);
            case VERDE -> visual.setFill(Color.GREEN);
            case AMARELO -> visual.setFill(Color.YELLOW);
            case PRETO -> visual.setFill(Color.BLACK);
            default -> visual.setFill(Color.GRAY);
        }
        visual.setOpacity(1.0);
    }


    private List<CartaVagao> calcularPagamentoAutomatico(Jogador j, Rota r) {
        List<CartaVagao> mao = j.getMaoDeCartas();
        int custo = r.getComprimento();
        Cor corRota = r.getCor();

        if (corRota != Cor.CINZA) {
            return tentarPagarComCor(mao, corRota, custo);
        }

        for (Cor corTeste : Cor.values()) {
            if (corTeste != Cor.CINZA && corTeste != Cor.CORINGA) {
                List<CartaVagao> tentativa = tentarPagarComCor(mao, corTeste, custo);
                if (tentativa != null) {
                    return tentativa; // Achou uma cor que serve!
                }
            }
        }

        return null;
    }

    private List<CartaVagao> tentarPagarComCor(List<CartaVagao> mao, Cor corAlvo, int custo) {
        List<CartaVagao> selecionadas = new ArrayList<>();


        for (CartaVagao c : mao) {
            if (c.getCor() == corAlvo && selecionadas.size() < custo) {
                selecionadas.add(c);
            }
        }


        if (selecionadas.size() < custo) {
            for (CartaVagao c : mao) {
                if (c.getCor() == Cor.CORINGA && !selecionadas.contains(c)) {
                    selecionadas.add(c);
                    if (selecionadas.size() == custo) break;
                }
            }
        }


        return (selecionadas.size() == custo) ? selecionadas : null;
    }


    private void atualizarUI() {
        Jogador atual = jogo.getJogadorAtual();
        labelNomeJogador.setText(atual.getNome());
        labelPontuacao.setText(String.valueOf(atual.getPontuacao()));
        labelVagoes.setText(String.valueOf(atual.getEstoqueVagoes()));

        renderizarMesa();
        renderizarMao(atual);

        renderizarObjetivos(atual);

        if (jogo.isFimDeJogo()) {
            encerrarPartida();
        }

        if (jogo.isFimDeJogo()) {
            mostrarTelaVencedor();
        }
    }

    private void renderizarObjetivos(Jogador jogador) {
        listaDestinos.getItems().clear();

        if (jogador.getMaoDeDestino().isEmpty()) {
            listaDestinos.getItems().add("Sem bilhetes!");
            return;
        }

        for (model.CartaDestino carta : jogador.getMaoDeDestino()) {
            String texto = String.format("%s -> %s\n(%d pontos)",
                    carta.getOrigem().getNome(),
                    carta.getDestino().getNome(),
                    carta.getValor());

            listaDestinos.getItems().add(texto);
        }
    }

    private void encerrarPartida() {

        jogo.calcularPontuacoesFinais();

        List<Jogador> vencedores = jogo.getVencedores();

        StringBuilder msg = new StringBuilder();
        msg.append("O jogo acabou!\n\nRanking Final:\n");


        if (vencedores.size() == 1) {
            msg.append("VENCEDOR: ").append(vencedores.get(0).getNome());
            msg.append(" com ").append(vencedores.get(0).getPontuacao()).append(" pontos!");
        } else {
            msg.append("EMPATE entre: ");
            for(Jogador j : vencedores) msg.append(j.getNome()).append(", ");
            msg.append("com ").append(vencedores.get(0).getPontuacao()).append(" pontos!");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fim de Jogo");
        alert.setHeaderText("Resultado Final");
        alert.setContentText(msg.toString());
        alert.showAndWait();

        System.exit(0);
    }

    private void renderizarMesa() {

        hboxMesa.getChildren().clear();


        Button btnDeckVagoes = new Button("Vagões");
        btnDeckVagoes.setOnAction(e -> {
            jogo.executarAcaoComprarCartaDeck();
            atualizarUI();
        });


        Button btnDeckDestinos = new Button("Novos Destinos");
        btnDeckDestinos.setOnAction(e -> onComprarDestinosClick());


        hboxMesa.getChildren().addAll(btnDeckVagoes, btnDeckDestinos);


        for(int i = 0; i < jogo.getCartasAbertas().size(); i++) {
            final int idx = i; // Necessário para o lambda
            ImageView img = criarImageView(jogo.getCartasAbertas().get(i));


            img.setOnMouseClicked(e -> {
                jogo.executarAcaoComprarCartaAberta(idx);
                atualizarUI();
            });

            hboxMesa.getChildren().add(img);
        }
    }

    private void onComprarDestinosClick() {

        List<CartaDestino> candidatos = jogo.comprarNovosObjetivosCandidatos();

        if (candidatos == null) {
            mostrarAlerta("Ação Inválida", "Você não pode comprar objetivos agora (já jogou ou baralho vazio).");
            return;
        }


        StringBuilder opcoes = new StringBuilder("Digite os números dos bilhetes que deseja MANTER (separados por vírgula):\n");
        for (int i = 0; i < candidatos.size(); i++) {
            CartaDestino c = candidatos.get(i);
            opcoes.append(i).append(": ").append(c.getOrigem().getNome())
                    .append(" -> ").append(c.getDestino().getNome())
                    .append(" (").append(c.getValor()).append(" pts)\n");
        }
        opcoes.append("\nRegra: Você deve manter pelo menos 1.");

        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setTitle("Novos Objetivos");
        dialog.setHeaderText("Escolha seus Bilhetes");
        dialog.setContentText(opcoes.toString());

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            try {

                String[] escolhas = result.get().split(",");
                List<CartaDestino> manter = new ArrayList<>();
                List<CartaDestino> devolver = new ArrayList<>(candidatos);

                for (String s : escolhas) {
                    int idx = Integer.parseInt(s.trim());
                    if (idx >= 0 && idx < candidatos.size()) {
                        manter.add(candidatos.get(idx));
                    }
                }

                if (manter.isEmpty()) {
                    mostrarAlerta("Erro", "Você deve manter pelo menos um bilhete!");

                    jogo.confirmarEscolhaObjetivos(List.of(candidatos.get(0)), candidatos.subList(1, candidatos.size()));
                } else {
                    devolver.removeAll(manter);
                    jogo.confirmarEscolhaObjetivos(manter, devolver);
                    atualizarUI();
                }

            } catch (Exception e) {
                mostrarAlerta("Erro", "Entrada inválida. Tente novamente.");
            }
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
        } catch (Exception e) { return new ImageView(); }
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

        msg.append("Pontuação: ").append(vencedores.get(0).getPontuacao());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fim de Jogo");
        alert.setHeaderText("Temos um magnata ferroviário!");
        alert.setContentText(msg.toString());

        alert.showAndWait();

        System.exit(0);
    }

}