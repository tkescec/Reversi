package hr.reversi.service;

import hr.reversi.util.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertService {

    public static Optional<ButtonType> showAlert(AlertType alertType, String content) {
        Alert alert;

        switch (alertType){
            case confirm -> {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
            }
            case error -> {
                alert = new Alert(Alert.AlertType.ERROR);
            }
            case info -> {
                alert = new Alert(Alert.AlertType.INFORMATION);
            }
            case warning -> {
                alert = new Alert(Alert.AlertType.WARNING);
            }
            default -> {
                alert = new Alert(Alert.AlertType.NONE);
            }
        }

        alert.setContentText(content);

        return alert.showAndWait();
    }
}
