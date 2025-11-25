package game;

import model.Carta;
import model.CartaVagao;
import java.util.List;

/**
 * GoF Singleton
 */
public class DeckCartasVagao extends DeckCartas<CartaVagao> {
    private static CarregadorDeCartas carregador;
    private static DeckCartasVagao instance;

    private DeckCartasVagao(List<CartaVagao> cartas) {
        super(cartas);
    }

    public static DeckCartasVagao getInstance() {
        if (instance == null) {
            CarregadorDeCartas carregador = new CarregadorDeCartas();

            List<CartaVagao> cartasVagao = carregador.criarBaralhoCartasVagao();

            instance = new DeckCartasVagao(cartasVagao);
            instance.embaralhar();
        }
        return instance;
    }

    public void reabastecer(List<CartaVagao> cartasDoDescarte) {
        this.cartas.addAll(cartasDoDescarte);
        this.embaralhar();
    }

    // Opcional: Método para reiniciar o singleton se começar um novo jogo
    public static void reset() {
        instance = null;
    }

    public static DeckCartasVagao inicializarCartasEmbaralhadas(){
        if(instance == null){
            carregador = new CarregadorDeCartas();
            instance = new DeckCartasVagao(carregador.criarBaralhoCartasVagao());
            instance.embaralhar();
        }
        return instance;
    }
}