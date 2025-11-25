package org.tickettoride.ui;

import game.JogoController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;


/**
     GRASP: Controller
     Lógica de negócio da interface do Jogo. Integração com Front em alto nível.
    Atributos e métodos com o decorator @FXML interagem diretamente com o front (org/tickettoride/ui/jogo.fxml)
 */
public class JogoUIController {


    private JogoController jogoController;

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

    private JogoUIService jogoUIService;


    @FXML
    public void initialize() {
        List<String> nomes = List.of("Jogador 1", "Jogador 2");

        this.jogoController = new JogoController(nomes);

        // GoF Builder
        this.uiService = new UiService.Builder()
                                .comJogo(jogoController)
                                .comNomeJogador(labelNomeJogador)
                                .comPontuacao(labelPontuacao)
                                .comVagoes(labelVagoes)
                                .comMesa(hboxMesa)
                                .comMao(hboxMaoJogador)
                                .comCartasDestinos(vboxCartasObjetivo)
                                .build();

        this.jogoUIService = new JogoUIService(this.jogoController, this.uiService);
        jogoUIService.adicionaEventoEmRetangulosDeRota(this.painelMapa);
        uiService.atualizaUI();
    }
}