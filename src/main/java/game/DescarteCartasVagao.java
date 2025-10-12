package game;

import model.CartasVagao;

import java.util.ArrayList;
import java.util.List;

public class DescarteCartasVagao {
    private List<CartasVagao> cartas;

    public void adicionar(CartasVagao carta){
        this.cartas.add(carta);
    }

    public List<CartasVagao> pegar(){
        List<CartasVagao> todas = new ArrayList<>(cartas);
        cartas.clear();
        return todas;
    }
}
