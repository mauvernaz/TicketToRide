package game;

import model.Cidade;
import model.Jogador;
import model.Rota;
import model.Cor;
import java.util.*;

public class Tabuleiro {
    private List<Cidade> cidades;
    private List<Rota> rotas;

    /**
     * GRASP - Creator
     */
    public Tabuleiro(String arquivoDoMapa) {
        this.cidades = new ArrayList<>();
        this.rotas = new ArrayList<>();
        inicializacaoHardcode();
    }

    private void inicializacaoHardcode() {
        // Cidades
        Cidade vancouver = new Cidade("Vancouver");
        Cidade atlanta = new Cidade("Atlanta");
        Cidade seattle = new Cidade("Seattle");
        Cidade calgary = new Cidade("Calgary");
        Cidade portland = new Cidade("Portland");
        Cidade boston = new Cidade("Boston");
        Cidade charleston = new Cidade("Charleston");
        Cidade miami = new Cidade("Miami");
        Cidade newOrleans = new Cidade("NewOrleans");
        Cidade newYork = new Cidade("NewYork");
        Cidade chicago = new Cidade("Chicago");
        Cidade pittsburgh = new Cidade("Pittsburgh");
        Cidade helena = new Cidade("Helena");
        Cidade winnipeg = new Cidade("Winnipeg");
        Cidade dallas = new Cidade("Dallas");
        Cidade saintLouis = new Cidade("SaintLouis");
        Cidade toronto = new Cidade("Toronto");
        Cidade houston = new Cidade("Houston");
        Cidade denver = new Cidade("Denver");
        Cidade kansasCity = new Cidade("KansasCity");
        Cidade oklahomaCity = new Cidade("OklahomaCity");
        Cidade omaha = new Cidade("Omaha");
        Cidade phoenix = new Cidade("Phoenix");
        Cidade duluth = new Cidade("Duluth");
        Cidade elPaso = new Cidade("ElPaso");
        Cidade santaFe = new Cidade("SantaFe");
        Cidade saultStMarie = new Cidade("SaultStMarie");
        Cidade littleRock = new Cidade("LittleRock");
        Cidade saltLakeCity = new Cidade("SaltLakeCity");
        Cidade losAngeles = new Cidade("LosAngeles");
        Cidade montreal = new Cidade("Montreal");
        Cidade nashville = new Cidade("Nashville");
        Cidade washington = new Cidade("Washington");
        Cidade lasVegas = new Cidade("LasVegas");
        Cidade raleigh = new Cidade("Raleigh");
        Cidade sanFrancisco = new Cidade("SanFrancisco");

        // Cidades
        cidades.addAll(List.of(oklahomaCity, omaha, phoenix, duluth, elPaso,
                santaFe, saultStMarie, littleRock, saltLakeCity,
                losAngeles, montreal, nashville, washington, lasVegas, raleigh, sanFrancisco));

        // Rotas
        rotas.add(new Rota(atlanta, charleston, 2, Cor.CINZA));
        rotas.add(new Rota(atlanta, miami, 5, Cor.AZUL));
        rotas.add(new Rota(atlanta, newOrleans, 4, Cor.AMARELO));
        rotas.add(new Rota(atlanta, newOrleans, 4, Cor.LARANJA));
        rotas.add(new Rota(boston, newYork, 2, Cor.AMARELO));
        rotas.add(new Rota(boston, newYork, 2, Cor.VERMELHO));
        rotas.add(new Rota(calgary, helena, 4, Cor.CINZA));
        rotas.add(new Rota(calgary, winnipeg, 6, Cor.BRANCO));
        rotas.add(new Rota(charleston, miami, 4, Cor.ROSA));
        rotas.add(new Rota(chicago, pittsburgh, 3, Cor.LARANJA));
        rotas.add(new Rota(chicago, pittsburgh, 3, Cor.PRETO));
        rotas.add(new Rota(chicago, saintLouis, 2, Cor.VERDE));
        rotas.add(new Rota(chicago, saintLouis, 2, Cor.BRANCO));
        rotas.add(new Rota(chicago, toronto, 4, Cor.BRANCO));
        rotas.add(new Rota(dallas, houston, 1, Cor.CINZA));
        rotas.add(new Rota(dallas, houston, 1, Cor.CINZA));
        rotas.add(new Rota(denver, kansasCity, 4, Cor.PRETO));
        rotas.add(new Rota(denver, kansasCity, 4, Cor.LARANJA));
        rotas.add(new Rota(denver, oklahomaCity, 4, Cor.VERMELHO));
        rotas.add(new Rota(denver, omaha, 4, Cor.ROSA));
        rotas.add(new Rota(denver, phoenix, 5, Cor.BRANCO));
        rotas.add(new Rota(denver, santaFe, 2, Cor.CINZA));
        rotas.add(new Rota(duluth, chicago, 3, Cor.VERMELHO));
        rotas.add(new Rota(duluth, omaha, 2, Cor.CINZA));
        rotas.add(new Rota(duluth, omaha, 2, Cor.CINZA));
        rotas.add(new Rota(duluth, saultStMarie, 3, Cor.CINZA));
        rotas.add(new Rota(duluth, toronto, 6, Cor.ROSA));
        rotas.add(new Rota(elPaso, dallas, 4, Cor.VERMELHO));
        rotas.add(new Rota(elPaso, houston, 6, Cor.VERDE));
        rotas.add(new Rota(helena, denver, 4, Cor.VERDE));
        rotas.add(new Rota(helena, duluth, 6, Cor.LARANJA));
        rotas.add(new Rota(helena, omaha, 5, Cor.VERMELHO));
        rotas.add(new Rota(helena, saltLakeCity, 3, Cor.ROSA));
        rotas.add(new Rota(houston, newOrleans, 2, Cor.CINZA));
        rotas.add(new Rota(kansasCity, oklahomaCity, 2, Cor.CINZA));
        rotas.add(new Rota(kansasCity, oklahomaCity, 2, Cor.CINZA));
        rotas.add(new Rota(kansasCity, saintLouis, 2, Cor.AZUL));
        rotas.add(new Rota(kansasCity, saintLouis, 2, Cor.ROSA));
        rotas.add(new Rota(littleRock, dallas, 2, Cor.CINZA));
        rotas.add(new Rota(littleRock, nashville, 3, Cor.BRANCO));
        rotas.add(new Rota(littleRock, newOrleans, 3, Cor.VERDE));
        rotas.add(new Rota(losAngeles, elPaso, 6, Cor.PRETO));
        rotas.add(new Rota(losAngeles, lasVegas, 2, Cor.CINZA));
        rotas.add(new Rota(losAngeles, phoenix, 3, Cor.CINZA));
        rotas.add(new Rota(montreal, boston, 2, Cor.CINZA));
        rotas.add(new Rota(montreal, boston, 2, Cor.CINZA));
        rotas.add(new Rota(montreal, newYork, 3, Cor.AZUL));
        rotas.add(new Rota(montreal, toronto, 3, Cor.CINZA));
        rotas.add(new Rota(nashville, atlanta, 1, Cor.CINZA));
        rotas.add(new Rota(nashville, raleigh, 3, Cor.PRETO));
        rotas.add(new Rota(newOrleans, miami, 6, Cor.VERMELHO));
        rotas.add(new Rota(newYork, pittsburgh, 2, Cor.BRANCO));
        rotas.add(new Rota(newYork, pittsburgh, 2, Cor.VERDE));
        rotas.add(new Rota(newYork, washington, 2, Cor.LARANJA));
        rotas.add(new Rota(newYork, washington, 2, Cor.PRETO));
        rotas.add(new Rota(oklahomaCity, dallas, 2, Cor.CINZA));
        rotas.add(new Rota(oklahomaCity, dallas, 2, Cor.CINZA));
        rotas.add(new Rota(oklahomaCity, elPaso, 5, Cor.AMARELO));
        rotas.add(new Rota(oklahomaCity, littleRock, 2, Cor.CINZA));
        rotas.add(new Rota(omaha, chicago, 4, Cor.AZUL));
        rotas.add(new Rota(omaha, kansasCity, 2, Cor.CINZA));
        rotas.add(new Rota(omaha, kansasCity, 2, Cor.CINZA));
        rotas.add(new Rota(phoenix, elPaso, 3, Cor.CINZA));
        rotas.add(new Rota(pittsburgh, nashville, 4, Cor.AMARELO));
        rotas.add(new Rota(pittsburgh, raleigh, 2, Cor.CINZA));
        rotas.add(new Rota(pittsburgh, saintLouis, 5, Cor.VERDE));
        rotas.add(new Rota(pittsburgh, washington, 2, Cor.CINZA));
        rotas.add(new Rota(portland, saltLakeCity, 6, Cor.AZUL));
        rotas.add(new Rota(portland, sanFrancisco, 5, Cor.VERDE));
        rotas.add(new Rota(portland, sanFrancisco, 5, Cor.ROSA));
        rotas.add(new Rota(raleigh, atlanta, 2, Cor.CINZA));
        rotas.add(new Rota(raleigh, atlanta, 2, Cor.CINZA));
        rotas.add(new Rota(raleigh, charleston, 2, Cor.CINZA));
        rotas.add(new Rota(saintLouis, littleRock, 2, Cor.CINZA));
        rotas.add(new Rota(saintLouis, nashville, 2, Cor.CINZA));
        rotas.add(new Rota(saltLakeCity, denver, 3, Cor.VERMELHO));
        rotas.add(new Rota(saltLakeCity, denver, 3, Cor.AMARELO));
        rotas.add(new Rota(saltLakeCity, lasVegas, 3, Cor.LARANJA));
        rotas.add(new Rota(sanFrancisco, losAngeles, 3, Cor.ROSA));
        rotas.add(new Rota(sanFrancisco, losAngeles, 3, Cor.AMARELO));
        rotas.add(new Rota(sanFrancisco, saltLakeCity, 5, Cor.LARANJA));
        rotas.add(new Rota(sanFrancisco, saltLakeCity, 5, Cor.BRANCO));
        rotas.add(new Rota(santaFe, elPaso, 2, Cor.CINZA));
        rotas.add(new Rota(santaFe, oklahomaCity, 3, Cor.AZUL));
        rotas.add(new Rota(santaFe, phoenix, 3, Cor.CINZA));
        rotas.add(new Rota(saultStMarie, montreal, 5, Cor.PRETO));
        rotas.add(new Rota(saultStMarie, toronto, 2, Cor.CINZA));
        rotas.add(new Rota(seattle, calgary, 4, Cor.CINZA));
        rotas.add(new Rota(seattle, helena, 6, Cor.AMARELO));
        rotas.add(new Rota(seattle, portland, 1, Cor.CINZA));
        rotas.add(new Rota(seattle, portland, 1, Cor.CINZA));
        rotas.add(new Rota(toronto, pittsburgh, 2, Cor.CINZA));
        rotas.add(new Rota(vancouver, calgary, 3, Cor.CINZA));
        rotas.add(new Rota(vancouver, seattle, 1, Cor.CINZA));
        rotas.add(new Rota(vancouver, seattle, 1, Cor.CINZA));
        rotas.add(new Rota(washington, raleigh, 2, Cor.CINZA));
        rotas.add(new Rota(washington, raleigh, 2, Cor.CINZA));
        rotas.add(new Rota(winnipeg, duluth, 4, Cor.PRETO));
        rotas.add(new Rota(winnipeg, helena, 4, Cor.AZUL));
        rotas.add(new Rota(winnipeg, saultStMarie, 6, Cor.CINZA));
    }


    public boolean checarConectividade(Jogador jogador, Cidade origem, Cidade destino) {
        if (origem.equals(destino)) return true;

        Set<Cidade> visitados = new HashSet<>();
        Queue<Cidade> fila = new LinkedList<>();

        fila.add(origem);
        visitados.add(origem);

        while (!fila.isEmpty()) {
            Cidade atual = fila.poll();

            if (atual.equals(destino)) return true;

            for (Rota rota : rotas) {
                if (jogador.equals(rota.getDono())) {
                    Cidade vizinho = null;
                    // Lógica de grafo não-direcionado
                    if (rota.getCidadeA().equals(atual)) {
                        vizinho = rota.getCidadeB();
                    } else if (rota.getCidadeB().equals(atual)) {
                        vizinho = rota.getCidadeA();
                    }

                    if (vizinho != null && !visitados.contains(vizinho)) {
                        visitados.add(vizinho);
                        fila.add(vizinho);
                    }
                }
            }
        }
        return false;
    }

    public Rota buscarRota(String nomeA, String nomeB) {
        for (Rota r : rotas) {
            boolean matchA = r.getCidadeA().getNome().equalsIgnoreCase(nomeA);
            boolean matchB = r.getCidadeB().getNome().equalsIgnoreCase(nomeB);

            if (((matchA && r.getCidadeB().getNome().equalsIgnoreCase(nomeB)) ||
                    (r.getCidadeA().getNome().equalsIgnoreCase(nomeB) && matchB)) &&
                    r.getDono() == null) {
                return r;
            }
        }
        return null;
    }

    // Getters
    public List<Rota> getRotas() { return rotas; }
    public List<Cidade> getCidades() { return cidades; }
}