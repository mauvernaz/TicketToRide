package org.tickettoride.ui;

import game.Jogo;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;


/**
    Lógica de negócio da interface. Integração com Front em alto nível.
    Atributos e métodos com o decorator @FXML interagem diretamente com o front (org/tickettoride/ui/jogo.fxml)
 */
public class JogoController {


    private Jogo jogo;

    @FXML
    private VBox vboxCartasObjetivo;

    @FXML
    private AnchorPane painelMapa; // Onde ficam o Mapa e os Retângulos

    @FXML
    private Label labelNomeJogador;

    @FXML
    private Label labelPontuacao;

    @FXML
    private Label labelVagoes;

    @FXML
    private HBox hboxMesa;

    @FXML
    private HBox hboxMaoJogador;

    private UiService uiService;

    private JogoService jogoService;


    @FXML
    public void initialize() {
        List<String> nomes = List.of("Jogador 1", "Jogador 2");

        this.jogo = new Jogo(nomes);

        // GoF Builder
        this.uiService = new UiService.Builder()
                                .comJogo(jogo)
                                .comNomeJogador(labelNomeJogador)
                                .comPontuacao(labelPontuacao)
                                .comVagoes(labelVagoes)
                                .comMesa(hboxMesa)
                                .comMao(hboxMaoJogador)
                                .comCartasDestinos(vboxCartasObjetivo)
                                .build();

        this.jogoService = new JogoService(this.jogo, this.uiService);
        jogoService.adicionaEventoEmRetangulosDeRota(this.painelMapa);
        uiService.atualizaUI();
    }
}