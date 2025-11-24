package game;

import model.CartaDestino;
import model.CartaVagao;
import model.Cidade;
import model.Cor;
import java.util.ArrayList;
import java.util.List;


 //GRASP - Pure Fabrication

public class CarregadorDeCartas {

    private List<Cidade> cidadesCache;

    public CarregadorDeCartas() {
        this.cidadesCache = new ArrayList<>();
    }

    public List<CartaVagao> criarBaralhoCompletoVagao() {
        List<CartaVagao> baralho = new ArrayList<>();

        Cor[] coresNormais = {
                Cor.PRETO, Cor.AZUL, Cor.VERDE, Cor.VERMELHO,
                Cor.AMARELO, Cor.BRANCO, Cor.LARANJA, Cor.ROSA
        };

        for (Cor cor : coresNormais) {
            for (int i = 0; i < 12; i++) {
                baralho.add(new CartaVagao(cor));
            }
        }

        for (int i = 0; i < 14; i++) {
            baralho.add(new CartaVagao(Cor.CORINGA));
        }

        return baralho;
    }


    public List<CartaDestino> criarBaralhoDestino() {
        List<CartaDestino> baralho = new ArrayList<>();

        Cidade vancouver = getOuCriarCidade("Vancouver");
        Cidade seattle = getOuCriarCidade("Seattle");
        Cidade portland = getOuCriarCidade("Portland");
        Cidade calgary = getOuCriarCidade("Calgary");
        Cidade saltLakeCity = getOuCriarCidade("Salt Lake City");
        Cidade helena = getOuCriarCidade("Helena");
        Cidade winnipeg = getOuCriarCidade("Winnipeg");
        Cidade duluth = getOuCriarCidade("Duluth");
        Cidade omaha = getOuCriarCidade("Omaha");
        Cidade chicago = getOuCriarCidade("Chicago");
        Cidade montreal = getOuCriarCidade("Montreal");
        Cidade newYork = getOuCriarCidade("New York");


        baralho.add(new CartaDestino(vancouver, montreal, 20));
        baralho.add(new CartaDestino(seattle, newYork, 22));
        baralho.add(new CartaDestino(portland, chicago, 17));
        baralho.add(new CartaDestino(vancouver, saltLakeCity, 7));
        baralho.add(new CartaDestino(calgary, saltLakeCity, 7));
        baralho.add(new CartaDestino(helena, winnipeg, 4));
        baralho.add(new CartaDestino(seattle, portland, 1));
        baralho.add(new CartaDestino(duluth, omaha, 6));


        return baralho;
    }


    private Cidade getOuCriarCidade(String nome) {
        for (Cidade c : cidadesCache) {
            if (c.getNome().equalsIgnoreCase(nome)) {
                return c;
            }
        }
        Cidade nova = new Cidade(nome);
        cidadesCache.add(nova);
        return nova;
    }
}