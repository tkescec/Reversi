package hr.reversi.controller;

import hr.reversi.Main;
import hr.reversi.service.DocumentationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HighScoreController implements Initializable {

    @FXML
    private ListView listHighScore;
    private Map<String, Integer> playerList = new HashMap<String, Integer>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        playerList.put("Pero", 60);
        playerList.put("Ana", 58);
        playerList.put("Mirko", 57);

        showScore();
    }

    private void showScore() {
        ObservableList<Map.Entry<String, Integer>> items = FXCollections.observableArrayList();

        for (Map.Entry<String, Integer> entry : playerList.entrySet()) {
            items.add(entry);
        }

        Collections.sort(items,
                new Comparator<Map.Entry<String, Integer>>() {

                    @Override
                    public int compare(Map.Entry<String, Integer> es1,
                                       Map.Entry<String, Integer> es2) {
                        return -es1.getValue().compareTo(es2.getValue());
                    }
                });

        listHighScore.setItems(items);
    }

    // ************** EVENT HANDLERS **************
    @FXML
    /** New game button click handler. */
    private void onNewGameButtonClick() {
        StartGameController.showBoardView();
    }

    @FXML
    /** Documentation button click handler. */
    private void onDocumentationButtonClick() {
        DocumentationService.generateDocumentation();
    }
}
