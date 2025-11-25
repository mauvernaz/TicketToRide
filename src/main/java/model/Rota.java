package model;

public class Rota {
    private final Cidade cidadeA;
    private final Cidade cidadeB;
    private final int comprimento;
    private final Cor cor;
    private Jogador dono;

    public Rota(Cidade cidadeA, Cidade cidadeB, int comprimento, Cor cor) {
        this.cidadeA = cidadeA;
        this.cidadeB = cidadeB;
        this.comprimento = comprimento;
        this.cor = cor;
        this.dono = null;
    }


    /**
     *GRASP - Information Expert
     */
    public int getPontos() {
        switch (comprimento) {
            case 1: return 1;
            case 2: return 2;
            case 3: return 4;
            case 4: return 7;
            case 5: return 10;
            case 6: return 15;
            default: return 0;
        }
    }

    public void setDono(Jogador jogador) {
        this.dono = jogador;
    }

    public Cidade getCidadeA() { return cidadeA; }
    public Cidade getCidadeB() { return cidadeB; }
    public int getComprimento() { return comprimento; }
    public Cor getCor() { return cor; }
    public Jogador getDono() { return dono; }
}