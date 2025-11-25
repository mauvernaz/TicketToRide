package org.tickettoride.ui;

import game.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

import static game.JogoService.inicializarJogadores;


/**
     GRASP: Controller
     Lógica de negócio da interface do Jogo. Integração Back-Front em alto nível.
     Atributos e métodos com o decorator @FXML interagem diretamente com o front (org/tickettoride/ui/jogo.fxml)
 */
public class JogoUIController {



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
        Tabuleiro.getInstance("mapa/america.txt");
        DeckCartasVagao.inicializarCartasEmbaralhadas();
        CartasAbertas.getInstance();

        List<String> nomes = List.of("Jogador 1", "Jogador 2");
        inicializarJogadores(nomes);

        JogoService.setJogadorAtual(0);

        this.uiService = new UiService.Builder()
                                .comNomeJogador(labelNomeJogador)
                                .comPontuacao(labelPontuacao)
                                .comVagoes(labelVagoes)
                                .comMesa(hboxMesa)
                                .comMao(hboxMaoJogador)
                                .comCartasDestinos(vboxCartasObjetivo)
                                .build();

        this.jogoUIService = new JogoUIService(this.uiService);
        jogoUIService.adicionaEventoEmRetangulosDeRota(this.painelMapa);
        uiService.atualizaUI();
    }
}