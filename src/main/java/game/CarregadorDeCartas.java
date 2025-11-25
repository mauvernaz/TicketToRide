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
    public List<CartaVagao> criarBaralhoCartasVagao() {
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

        Cidade vancouver = getOuCriarCidade(String.valueOf(Cidades.VANCOUVER));
        Cidade seattle = getOuCriarCidade(String.valueOf(Cidades.SEATTLE));
        Cidade portland = getOuCriarCidade(String.valueOf(Cidades.PORTLAND));
        Cidade calgary = getOuCriarCidade(String.valueOf(Cidades.CALGARY));
        Cidade saltLakeCity = getOuCriarCidade(String.valueOf(Cidades.SALT_LAKE_CITY));
        Cidade helena = getOuCriarCidade(String.valueOf(Cidades.HELENA));
        Cidade winnipeg = getOuCriarCidade(String.valueOf(Cidades.WINNIPEG));
        Cidade duluth = getOuCriarCidade(String.valueOf(Cidades.DULUTH));
        Cidade omaha = getOuCriarCidade(String.valueOf(Cidades.OMAHA));
        Cidade chicago = getOuCriarCidade(String.valueOf(Cidades.CHICAGO));
        Cidade montreal = getOuCriarCidade(String.valueOf(Cidades.MONTREAL));
        Cidade newYork = getOuCriarCidade(String.valueOf(Cidades.NEW_YORK));
        Cidade boston = getOuCriarCidade(String.valueOf(Cidades.BOSTON));
        Cidade dallas = getOuCriarCidade(String.valueOf(Cidades.DALLAS));
        Cidade kansasCity = getOuCriarCidade(String.valueOf(Cidades.KANSAS_CITY));
        Cidade miami = getOuCriarCidade(String.valueOf(Cidades.MIAMI));
        Cidade phoenix = getOuCriarCidade(String.valueOf(Cidades.PHOENIX));
        Cidade newOrleans = getOuCriarCidade(String.valueOf(Cidades.NEW_ORLEANS));
        Cidade santaFe = getOuCriarCidade(String.valueOf(Cidades.SANTA_FE));
        Cidade elPaso = getOuCriarCidade(String.valueOf(Cidades.EL_PASO));
        Cidade pittsburgh = getOuCriarCidade(String.valueOf(Cidades.PITTSBURGH));
        Cidade houston = getOuCriarCidade(String.valueOf(Cidades.HOUSTON));
        Cidade losAngeles = getOuCriarCidade(String.valueOf(Cidades.LOS_ANGELES));
        Cidade atlanta = getOuCriarCidade(String.valueOf(Cidades.ATLANTA));
        Cidade nashville = getOuCriarCidade(String.valueOf(Cidades.NASHVILLE));
        Cidade sanFrancisco = getOuCriarCidade(String.valueOf(Cidades.SAN_FRANCISCO));
        Cidade saultStMarie = getOuCriarCidade(String.valueOf(Cidades.SAULT_ST_MARIE));
        Cidade oklahomaCity = getOuCriarCidade(String.valueOf(Cidades.OKLAHOMA_CITY));
        Cidade toronto = getOuCriarCidade(String.valueOf(Cidades.TORONTO));
        Cidade littleRock = getOuCriarCidade(String.valueOf(Cidades.LITTLE_ROCK));
        Cidade denver = getOuCriarCidade(String.valueOf(Cidades.DENVER));



        baralho.add(new CartaDestino(boston, miami, 12));
        baralho.add(new CartaDestino(calgary, phoenix, 13));
        baralho.add(new CartaDestino(calgary, saltLakeCity, 7));
        baralho.add(new CartaDestino(chicago, newOrleans, 7));
        baralho.add(new CartaDestino(chicago, santaFe, 9));
        baralho.add(new CartaDestino(dallas, newYork, 11));
        baralho.add(new CartaDestino(denver, elPaso, 4));
        baralho.add(new CartaDestino(denver, pittsburgh, 11));
        baralho.add(new CartaDestino(duluth, elPaso, 10));
        baralho.add(new CartaDestino(duluth, houston, 8));
        baralho.add(new CartaDestino(helena, losAngeles, 8));
        baralho.add(new CartaDestino(kansasCity, houston, 5));
        baralho.add(new CartaDestino(losAngeles, chicago, 16));
        baralho.add(new CartaDestino(losAngeles, miami, 20));
        baralho.add(new CartaDestino(losAngeles, newYork, 21));
        baralho.add(new CartaDestino(montreal, atlanta, 9));
        baralho.add(new CartaDestino(montreal, newOrleans, 13));
        baralho.add(new CartaDestino(newYork, atlanta, 6));
        baralho.add(new CartaDestino(portland, nashville, 17));
        baralho.add(new CartaDestino(portland, phoenix, 11));
        baralho.add(new CartaDestino(sanFrancisco, atlanta, 17));
        baralho.add(new CartaDestino(saultStMarie, nashville, 8));
        baralho.add(new CartaDestino(saultStMarie, oklahomaCity, 9));
        baralho.add(new CartaDestino(seattle, losAngeles, 9));
        baralho.add(new CartaDestino(seattle, newYork, 22));
        baralho.add(new CartaDestino(toronto, miami, 10));
        baralho.add(new CartaDestino(vancouver, montreal, 20));
        baralho.add(new CartaDestino(vancouver, santaFe, 13));
        baralho.add(new CartaDestino(winnipeg, houston, 12));
        baralho.add(new CartaDestino(winnipeg, littleRock, 11));

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