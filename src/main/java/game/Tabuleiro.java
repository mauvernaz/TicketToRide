package game;

import model.Cidade;
import model.Jogador;
import model.Rota;
import model.Cor;
import java.util.*;
import java.util.stream.Collectors;

public class Tabuleiro {
    private List<Cidade> cidades;
    private List<Rota> rotas;

    /**
     * GRASP - Creator
     */
    public Tabuleiro(String arquivoDoMapa) {
        this.cidades = new ArrayList<>();
        this.rotas = new ArrayList<>();
        inicializarMapaHardcoded();
    }

    private void inicializarMapaHardcoded() {
        // 1. Helper para garantir instância única das cidades
        Map<String, Cidade> cacheCidades = new HashMap<>();

        // Função local para pegar ou criar cidade
        java.util.function.Function<String, Cidade> getCidade = (nome) -> {
            return cacheCidades.computeIfAbsent(nome, Cidade::new);
        };

        // 2. Criação das Rotas (Mapeado do seu FXML + Regras Oficiais)

        // --- COSTA OESTE / NORTE ---
        // Vancouver <-> Seattle (Dupla: Cinza/Cinza)
        rotas.add(new Rota(getCidade.apply("Vancouver"), getCidade.apply("Seattle"), 1, Cor.CINZA));
        rotas.add(new Rota(getCidade.apply("Vancouver"), getCidade.apply("Seattle"), 1, Cor.CINZA));

        // Seattle <-> Portland (Dupla: Cinza/Cinza)
        rotas.add(new Rota(getCidade.apply("Seattle"), getCidade.apply("Portland"), 1, Cor.CINZA));
        rotas.add(new Rota(getCidade.apply("Seattle"), getCidade.apply("Portland"), 1, Cor.CINZA));

        // Seattle <-> Helena (Amarelo)
        rotas.add(new Rota(getCidade.apply("Seattle"), getCidade.apply("Helena"), 6, Cor.AMARELO));

        // Vancouver <-> Calgary (Cinza)
        rotas.add(new Rota(getCidade.apply("Vancouver"), getCidade.apply("Calgary"), 3, Cor.CINZA));

        // Calgary <-> Helena (Cinza)
        rotas.add(new Rota(getCidade.apply("Calgary"), getCidade.apply("Helena"), 4, Cor.CINZA));

        // Winnipeg <-> Helena (Azul)
        rotas.add(new Rota(getCidade.apply("Winnipeg"), getCidade.apply("Helena"), 4, Cor.AZUL));

        // Winnipeg <-> Duluth (Preto)
        rotas.add(new Rota(getCidade.apply("Winnipeg"), getCidade.apply("Duluth"), 4, Cor.PRETO));

        // Helena <-> Duluth (Laranja)
        rotas.add(new Rota(getCidade.apply("Helena"), getCidade.apply("Duluth"), 6, Cor.LARANJA));

        // Duluth <-> Toronto (Rosa)
        rotas.add(new Rota(getCidade.apply("Duluth"), getCidade.apply("Toronto"), 6, Cor.ROSA));

        // --- MIDWEST / LESTE ---
        // Chicago <-> Pittsburgh (Dupla: Laranja/Preto)
        rotas.add(new Rota(getCidade.apply("Chicago"), getCidade.apply("Pittsburgh"), 3, Cor.LARANJA));
        rotas.add(new Rota(getCidade.apply("Chicago"), getCidade.apply("Pittsburgh"), 3, Cor.PRETO));

        // Denver <-> Kansas City (Dupla: Preto/Laranja)
        rotas.add(new Rota(getCidade.apply("Denver"), getCidade.apply("Kansas City"), 4, Cor.PRETO));
        rotas.add(new Rota(getCidade.apply("Denver"), getCidade.apply("Kansas City"), 4, Cor.LARANJA));

        // Salt Lake City <-> Denver (Dupla: Vermelho/Amarelo)
        rotas.add(new Rota(getCidade.apply("Salt Lake City"), getCidade.apply("Denver"), 3, Cor.VERMELHO));
        rotas.add(new Rota(getCidade.apply("Salt Lake City"), getCidade.apply("Denver"), 3, Cor.AMARELO));

        // Helena <-> Denver (Verde)
        rotas.add(new Rota(getCidade.apply("Helena"), getCidade.apply("Denver"), 4, Cor.VERDE));

        // Helena <-> Omaha (Vermelho)
        rotas.add(new Rota(getCidade.apply("Helena"), getCidade.apply("Omaha"), 5, Cor.VERMELHO));

        // Helena <-> Salt Lake City (Rosa)
        rotas.add(new Rota(getCidade.apply("Helena"), getCidade.apply("Salt Lake City"), 3, Cor.ROSA));

        // San Francisco <-> Salt Lake City (Dupla: Laranja/Branco)
        rotas.add(new Rota(getCidade.apply("San Francisco"), getCidade.apply("Salt Lake City"), 5, Cor.LARANJA));
        rotas.add(new Rota(getCidade.apply("San Francisco"), getCidade.apply("Salt Lake City"), 5, Cor.BRANCO));

        // --- SUL ---
        // El Paso <-> Dallas (Vermelho)
        rotas.add(new Rota(getCidade.apply("El Paso"), getCidade.apply("Dallas"), 4, Cor.VERMELHO));

        // Santa Fe <-> Oklahoma City (Azul)
        rotas.add(new Rota(getCidade.apply("Santa Fe"), getCidade.apply("Oklahoma City"), 3, Cor.AZUL));

        // Atlanta <-> Miami (Azul)
        rotas.add(new Rota(getCidade.apply("Atlanta"), getCidade.apply("Miami"), 5, Cor.AZUL));

        // Pittsburgh <-> Saint Louis (Verde)
        rotas.add(new Rota(getCidade.apply("Pittsburgh"), getCidade.apply("Saint Louis"), 5, Cor.VERDE));

        // Chicago <-> Saint Louis (Dupla: Verde/Branco)
        rotas.add(new Rota(getCidade.apply("Chicago"), getCidade.apply("Saint Louis"), 2, Cor.VERDE));
        rotas.add(new Rota(getCidade.apply("Chicago"), getCidade.apply("Saint Louis"), 2, Cor.BRANCO));

        // Omaha <-> Chicago (Azul)
        rotas.add(new Rota(getCidade.apply("Omaha"), getCidade.apply("Chicago"), 4, Cor.AZUL));

        // Kansas City <-> Saint Louis (Dupla: Azul/Rosa)
        rotas.add(new Rota(getCidade.apply("Kansas City"), getCidade.apply("Saint Louis"), 2, Cor.AZUL));
        rotas.add(new Rota(getCidade.apply("Kansas City"), getCidade.apply("Saint Louis"), 2, Cor.ROSA));

        // Little Rock <-> New Orleans (Verde)
        rotas.add(new Rota(getCidade.apply("Little Rock"), getCidade.apply("New Orleans"), 3, Cor.VERDE));

        // Atlanta <-> New Orleans (Dupla: Amarelo/Laranja)
        rotas.add(new Rota(getCidade.apply("Atlanta"), getCidade.apply("New Orleans"), 4, Cor.AMARELO));
        rotas.add(new Rota(getCidade.apply("Atlanta"), getCidade.apply("New Orleans"), 4, Cor.LARANJA));

        // Charleston <-> Miami (Rosa)
        rotas.add(new Rota(getCidade.apply("Charleston"), getCidade.apply("Miami"), 4, Cor.ROSA));

        // Salt Lake City <-> Las Vegas (Laranja)
        rotas.add(new Rota(getCidade.apply("Salt Lake City"), getCidade.apply("Las Vegas"), 3, Cor.LARANJA));

        // Portland <-> San Francisco (Dupla: Verde/Rosa)
        rotas.add(new Rota(getCidade.apply("Portland"), getCidade.apply("San Francisco"), 5, Cor.VERDE));
        rotas.add(new Rota(getCidade.apply("Portland"), getCidade.apply("San Francisco"), 5, Cor.ROSA));

        // Winnipeg <-> Sault St. Marie (Cinza)
        rotas.add(new Rota(getCidade.apply("Winnipeg"), getCidade.apply("Sault St Marie"), 6, Cor.CINZA));

        // Duluth <-> Sault St. Marie (Cinza)
        rotas.add(new Rota(getCidade.apply("Duluth"), getCidade.apply("Sault St Marie"), 3, Cor.CINZA));

        // Sault St. Marie <-> Toronto (Cinza)
        rotas.add(new Rota(getCidade.apply("Sault St Marie"), getCidade.apply("Toronto"), 2, Cor.CINZA));

        // Toronto <-> Pittsburgh (Cinza)
        rotas.add(new Rota(getCidade.apply("Toronto"), getCidade.apply("Pittsburgh"), 2, Cor.CINZA));

        // Montreal <-> Toronto (Cinza)
        rotas.add(new Rota(getCidade.apply("Montreal"), getCidade.apply("Toronto"), 3, Cor.CINZA));

        // Montreal <-> New York (Azul)
        rotas.add(new Rota(getCidade.apply("Montreal"), getCidade.apply("New York"), 3, Cor.AZUL));

        // New York <-> Pittsburgh (Dupla: Branco/Verde)
        rotas.add(new Rota(getCidade.apply("New York"), getCidade.apply("Pittsburgh"), 2, Cor.BRANCO));
        rotas.add(new Rota(getCidade.apply("New York"), getCidade.apply("Pittsburgh"), 2, Cor.VERDE));

        // Montreal <-> Boston (Dupla: Cinza/Cinza)
        rotas.add(new Rota(getCidade.apply("Montreal"), getCidade.apply("Boston"), 2, Cor.CINZA));
        rotas.add(new Rota(getCidade.apply("Montreal"), getCidade.apply("Boston"), 2, Cor.CINZA));

        // Boston <-> New York (Dupla: Amarelo/Vermelho)
        rotas.add(new Rota(getCidade.apply("Boston"), getCidade.apply("New York"), 2, Cor.AMARELO));
        rotas.add(new Rota(getCidade.apply("Boston"), getCidade.apply("New York"), 2, Cor.VERMELHO));

        // New York <-> Washington (Dupla: Laranja/Preto)
        rotas.add(new Rota(getCidade.apply("New York"), getCidade.apply("Washington"), 2, Cor.LARANJA));
        rotas.add(new Rota(getCidade.apply("New York"), getCidade.apply("Washington"), 2, Cor.PRETO));

        // Pittsburgh <-> Nashville (Amarelo)
        rotas.add(new Rota(getCidade.apply("Pittsburgh"), getCidade.apply("Nashville"), 4, Cor.AMARELO));

        // Nashville <-> Raleigh (Preto)
        rotas.add(new Rota(getCidade.apply("Nashville"), getCidade.apply("Raleigh"), 3, Cor.PRETO));

        // San Francisco <-> Los Angeles (Dupla: Amarelo/Rosa)
        rotas.add(new Rota(getCidade.apply("San Francisco"), getCidade.apply("Los Angeles"), 3, Cor.AMARELO));
        rotas.add(new Rota(getCidade.apply("San Francisco"), getCidade.apply("Los Angeles"), 3, Cor.ROSA));

        // Oklahoma City <-> El Paso (Amarelo)
        rotas.add(new Rota(getCidade.apply("Oklahoma City"), getCidade.apply("El Paso"), 5, Cor.AMARELO));

        // Duluth <-> Omaha (Dupla: Cinza/Cinza)
        rotas.add(new Rota(getCidade.apply("Duluth"), getCidade.apply("Omaha"), 2, Cor.CINZA));
        rotas.add(new Rota(getCidade.apply("Duluth"), getCidade.apply("Omaha"), 2, Cor.CINZA));

        // Omaha <-> Kansas City (Dupla: Cinza/Cinza)
        rotas.add(new Rota(getCidade.apply("Omaha"), getCidade.apply("Kansas City"), 1, Cor.CINZA));
        rotas.add(new Rota(getCidade.apply("Omaha"), getCidade.apply("Kansas City"), 1, Cor.CINZA));

        // Denver <-> Santa Fe (Cinza)
        rotas.add(new Rota(getCidade.apply("Denver"), getCidade.apply("Santa Fe"), 2, Cor.CINZA));

        // Kansas City <-> Oklahoma City (Dupla: Cinza/Cinza)
        rotas.add(new Rota(getCidade.apply("Kansas City"), getCidade.apply("Oklahoma City"), 2, Cor.CINZA));
        rotas.add(new Rota(getCidade.apply("Kansas City"), getCidade.apply("Oklahoma City"), 2, Cor.CINZA));

        // Oklahoma City <-> Little Rock (Cinza)
        rotas.add(new Rota(getCidade.apply("Oklahoma City"), getCidade.apply("Little Rock"), 2, Cor.CINZA));

        // Saint Louis <-> Little Rock (Cinza)
        rotas.add(new Rota(getCidade.apply("Saint Louis"), getCidade.apply("Little Rock"), 2, Cor.CINZA));

        // Oklahoma City <-> Dallas (Dupla: Cinza/Cinza)
        rotas.add(new Rota(getCidade.apply("Oklahoma City"), getCidade.apply("Dallas"), 2, Cor.CINZA));
        rotas.add(new Rota(getCidade.apply("Oklahoma City"), getCidade.apply("Dallas"), 2, Cor.CINZA));

        // Santa Fe <-> El Paso (Cinza)
        rotas.add(new Rota(getCidade.apply("Santa Fe"), getCidade.apply("El Paso"), 2, Cor.CINZA));

        // Santa Fe <-> Phoenix (Cinza)
        rotas.add(new Rota(getCidade.apply("Santa Fe"), getCidade.apply("Phoenix"), 3, Cor.CINZA));

        // Phoenix <-> El Paso (Cinza)
        rotas.add(new Rota(getCidade.apply("Phoenix"), getCidade.apply("El Paso"), 3, Cor.CINZA));

        // Los Angeles <-> Phoenix (Cinza)
        rotas.add(new Rota(getCidade.apply("Los Angeles"), getCidade.apply("Phoenix"), 3, Cor.CINZA));

        // Los Angeles <-> Las Vegas (Cinza)
        rotas.add(new Rota(getCidade.apply("Los Angeles"), getCidade.apply("Las Vegas"), 2, Cor.CINZA));

        // Denver <-> Phoenix (Branco)
        rotas.add(new Rota(getCidade.apply("Denver"), getCidade.apply("Phoenix"), 5, Cor.BRANCO));

        // Little Rock <-> Dallas (Cinza)
        rotas.add(new Rota(getCidade.apply("Little Rock"), getCidade.apply("Dallas"), 2, Cor.CINZA));

        // Dallas <-> Houston (Dupla: Cinza/Cinza)
        rotas.add(new Rota(getCidade.apply("Dallas"), getCidade.apply("Houston"), 1, Cor.CINZA));
        rotas.add(new Rota(getCidade.apply("Dallas"), getCidade.apply("Houston"), 1, Cor.CINZA));

        // Houston <-> New Orleans (Cinza)
        rotas.add(new Rota(getCidade.apply("Houston"), getCidade.apply("New Orleans"), 2, Cor.CINZA));

        // Saint Louis <-> Nashville (Cinza)
        rotas.add(new Rota(getCidade.apply("Saint Louis"), getCidade.apply("Nashville"), 2, Cor.CINZA));

        // Raleigh <-> Atlanta (Dupla: Cinza/Cinza)
        rotas.add(new Rota(getCidade.apply("Raleigh"), getCidade.apply("Atlanta"), 2, Cor.CINZA));
        rotas.add(new Rota(getCidade.apply("Raleigh"), getCidade.apply("Atlanta"), 2, Cor.CINZA));

        // Nashville <-> Atlanta (Cinza)
        rotas.add(new Rota(getCidade.apply("Nashville"), getCidade.apply("Atlanta"), 1, Cor.CINZA));

        // Pittsburgh <-> Raleigh (Cinza)
        rotas.add(new Rota(getCidade.apply("Pittsburgh"), getCidade.apply("Raleigh"), 2, Cor.CINZA));

        // Pittsburgh <-> Washington (Cinza)
        rotas.add(new Rota(getCidade.apply("Pittsburgh"), getCidade.apply("Washington"), 2, Cor.CINZA));

        // Washington <-> Raleigh (Dupla: Cinza/Cinza)
        rotas.add(new Rota(getCidade.apply("Washington"), getCidade.apply("Raleigh"), 2, Cor.CINZA));
        rotas.add(new Rota(getCidade.apply("Washington"), getCidade.apply("Raleigh"), 2, Cor.CINZA));

        // Raleigh <-> Charleston (Cinza)
        rotas.add(new Rota(getCidade.apply("Raleigh"), getCidade.apply("Charleston"), 2, Cor.CINZA));

        // Atlanta <-> Charleston (Cinza)
        rotas.add(new Rota(getCidade.apply("Atlanta"), getCidade.apply("Charleston"), 2, Cor.CINZA));

        // Portland <-> Salt Lake City (Azul)
        rotas.add(new Rota(getCidade.apply("Portland"), getCidade.apply("Salt Lake City"), 6, Cor.AZUL));

        // Los Angeles <-> El Paso (Preto)
        rotas.add(new Rota(getCidade.apply("Los Angeles"), getCidade.apply("El Paso"), 6, Cor.PRETO));

        // Little Rock <-> Nashville (Branco)
        rotas.add(new Rota(getCidade.apply("Little Rock"), getCidade.apply("Nashville"), 3, Cor.BRANCO));

        // Chicago <-> Toronto (Branco)
        rotas.add(new Rota(getCidade.apply("Chicago"), getCidade.apply("Toronto"), 4, Cor.BRANCO));

        // Sault St. Marie <-> Montreal (Preto)
        rotas.add(new Rota(getCidade.apply("Sault St Marie"), getCidade.apply("Montreal"), 5, Cor.PRETO));

        // Duluth <-> Chicago (Vermelho)
        rotas.add(new Rota(getCidade.apply("Duluth"), getCidade.apply("Chicago"), 3, Cor.VERMELHO));

        // Denver <-> Omaha (Rosa)
        rotas.add(new Rota(getCidade.apply("Denver"), getCidade.apply("Omaha"), 4, Cor.ROSA));

        // Denver <-> Oklahoma City (Vermelho)
        rotas.add(new Rota(getCidade.apply("Denver"), getCidade.apply("Oklahoma City"), 4, Cor.VERMELHO));

        // El Paso <-> Houston (Verde)
        rotas.add(new Rota(getCidade.apply("El Paso"), getCidade.apply("Houston"), 6, Cor.VERDE));

        // Seattle <-> Calgary (Cinza)
        rotas.add(new Rota(getCidade.apply("Seattle"), getCidade.apply("Calgary"), 4, Cor.CINZA));

        // Calgary <-> Winnipeg (Branco)
        rotas.add(new Rota(getCidade.apply("Calgary"), getCidade.apply("Winnipeg"), 6, Cor.BRANCO));

        // New Orleans <-> Miami (Vermelho)
        rotas.add(new Rota(getCidade.apply("New Orleans"), getCidade.apply("Miami"), 6, Cor.VERMELHO));

        // 3. Adicionar todas as cidades ao atributo da classe
        this.cidades.addAll(cacheCidades.values());
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

    // Em Tabuleiro.java

    /**
     * Calcula o tamanho do maior caminho contínuo de um jogador.
     * Problema NP-Hard (Longest Path), mas tratável aqui pelo tamanho pequeno do grafo.
     */
    public int calcularMaiorCaminho(Jogador jogador) {
        int maxCaminho = 0;
        // Filtra apenas as rotas desse jogador
        List<Rota> rotasDoJogador = rotas.stream()
                .filter(r -> jogador.equals(r.getDono()))
                .collect(Collectors.toList());

        // Tenta iniciar um caminho a partir de cada rota que o jogador possui
        for (Rota inicio : rotasDoJogador) {
            // Precisamos trackear as rotas visitadas para não repetir a MESMA aresta
            Set<Rota> visitadas = new HashSet<>();
            visitadas.add(inicio);

            // Tenta ir para um lado (Cidade A) e para o outro (Cidade B)
            int caminhoA = dfsMaiorCaminho(inicio.getCidadeA(), visitadas, rotasDoJogador, inicio.getComprimento());
            int caminhoB = dfsMaiorCaminho(inicio.getCidadeB(), visitadas, rotasDoJogador, inicio.getComprimento());

            maxCaminho = Math.max(maxCaminho, Math.max(caminhoA, caminhoB));
        }
        return maxCaminho;
    }

    private int dfsMaiorCaminho(Cidade atual, Set<Rota> visitadas, List<Rota> todasRotas, int comprimentoAtual) {
        int maxLocal = comprimentoAtual;

        for (Rota r : todasRotas) {
            if (!visitadas.contains(r)) {
                // Verifica se a rota está conectada à cidade atual
                Cidade proxima = null;
                if (r.getCidadeA().equals(atual)) proxima = r.getCidadeB();
                else if (r.getCidadeB().equals(atual)) proxima = r.getCidadeA();

                if (proxima != null) {
                    // Cria um novo set para o branch da recursão (backtracking implícito)
                    Set<Rota> novasVisitadas = new HashSet<>(visitadas);
                    novasVisitadas.add(r);

                    int profundidade = dfsMaiorCaminho(proxima, novasVisitadas, todasRotas, comprimentoAtual + r.getComprimento());
                    maxLocal = Math.max(maxLocal, profundidade);
                }
            }
        }
        return maxLocal;
    }

    /**
     * Busca uma rota de forma robusta, ignorando espaços, case e caracteres especiais.
     * Isso resolve o problema de IDs como "SaultStMarie" vs Cidade "Sault St. Marie".
     */
    public Rota buscarRota(String nomeA_UI, String nomeB_UI) {
        // Normaliza as strings da UI (remove espaços, pontos e põe em minúsculo)
        String chaveA = normalizarNome(nomeA_UI);
        String chaveB = normalizarNome(nomeB_UI);

        for (Rota r : rotas) {
            // Normaliza os nomes reais do Backend para comparar
            String backendA = normalizarNome(r.getCidadeA().getNome());
            String backendB = normalizarNome(r.getCidadeB().getNome());

            // Verifica a conexão (A-B ou B-A)
            boolean sentidoDireto = backendA.equals(chaveA) && backendB.equals(chaveB);
            boolean sentidoInverso = backendA.equals(chaveB) && backendB.equals(chaveA);

            if ((sentidoDireto || sentidoInverso) && r.getDono() == null) {
                return r;
            }
        }
        return null;
    }

    /**
     * Remove espaços, pontos e transforma em minúsculas para comparação segura.
     */
    private String normalizarNome(String input) {
        if (input == null) return "";
        return input.toLowerCase()
                .replace(" ", "")
                .replace(".", "")
                .replace("_", "");
    }

    // Getters
    public List<Rota> getRotas() { return rotas; }
    public List<Cidade> getCidades() { return cidades; }
}