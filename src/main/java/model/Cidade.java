package model;

public class Cidade {
    private final String nome;

    public Cidade(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cidade cidade = (Cidade) o;
        return nome.equals(cidade.nome);
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }
}