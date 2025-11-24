package org.tickettoride.ui;

import game.Jogo;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

import java.util.List;


/**
    Lógica de negócio da interface. Integração com Front em alto nível.
    Atributos e métodos com o decorator @FXML interagem diretamente com o front (org/tickettoride/ui/jogo.fxml)
 */
public class JogoController {


    private Jogo jogo;

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
        // GoF Builder
        this.jogo = new Jogo(nomes);
        this.uiService = new UiService(jogo, labelNomeJogador, labelPontuacao, labelVagoes, hboxMesa, hboxMaoJogador, jogoService);
        this.jogoService = new JogoService(this.jogo, this.uiService);


        jogoService.adicionaEventoEmRetangulosDeRota(this.painelMapa); // Configuração dinâmica das rotas
        uiService.atualizaUI();
    }
}