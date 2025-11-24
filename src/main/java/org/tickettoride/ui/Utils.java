package org.tickettoride.ui;

import javafx.scene.control.Alert;

public class Utils {

    public static void mostrarAlerta(String titulo, String conteudo) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo); a.setContentText(conteudo);
        a.showAndWait();
    }
}
