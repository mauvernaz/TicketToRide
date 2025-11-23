package game;

import model.CartaDestino;
import model.CartaVagao;
import model.Cidade;
import model.Cor;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitária (GRASP - Pure Fabrication) responsável por criar os dados iniciais do jogo.
 * Isola a complexidade de instanciar todas as cartas e cidades da classe Jogo.
 */
public class CarregadorDeCartas {

    // Mantemos as cidades como atributos para garantir que sejam as mesmas instâncias
    // usadas tanto nas rotas quanto nos bilhetes de destino.
    private List<Cidade> cidadesCache;

    public CarregadorDeCartas() {
        this.cidadesCache = new ArrayList<>();
    }

    /**
     * Cria a lista completa de cartas de vagão.
     */
    public List<CartaVagao> criarBaralhoCompletoVagao() {
        List<CartaVagao> baralho = new ArrayList<>();

        // Todas as 8 cores do jogo padrão
        Cor[] coresNormais = {
                Cor.PRETO, Cor.AZUL, Cor.VERDE, Cor.VERMELHO,
                Cor.AMARELO, Cor.BRANCO, Cor.LARANJA, Cor.ROSA
        };

        for (Cor cor : coresNormais) {
            for (int i = 0; i < 12; i++) {
                baralho.add(new CartaVagao(cor));
            }
        }

        // 14 Locomotivas (Coringas)
        for (int i = 0; i < 14; i++) {
            baralho.add(new CartaVagao(Cor.CORINGA));
        }

        return baralho;
    }

    /**
     * Cria a lista completa de bilhetes de destino.
     * Também inicializa as cidades se necessário.
     */
    public List<CartaDestino> criarBaralhoDestino() {
        List<CartaDestino> baralho = new ArrayList<>();

        // Criação ou recuperação das cidades (para garantir consistência)
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
        // ... adicione outras cidades conforme necessário

        // Criação dos Bilhetes (Exemplos baseados no jogo real)
        baralho.add(new CartaDestino(vancouver, montreal, 20));
        baralho.add(new CartaDestino(seattle, newYork, 22));
        baralho.add(new CartaDestino(portland, chicago, 17));
        baralho.add(new CartaDestino(vancouver, saltLakeCity, 7));
        baralho.add(new CartaDestino(calgary, saltLakeCity, 7));
        baralho.add(new CartaDestino(helena, winnipeg, 4));
        baralho.add(new CartaDestino(seattle, portland, 1)); // Rota curta para teste fácil
        baralho.add(new CartaDestino(duluth, omaha, 6));

        // Adicione mais bilhetes para ter variedade

        return baralho;
    }

    /**
     * Helper para garantir que não criamos duas cidades com o mesmo nome.
     */
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