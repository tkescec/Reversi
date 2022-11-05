package hr.reversi.controller;

import hr.reversi.Main;
import hr.reversi.model.Player;
import hr.reversi.service.DocumentationService;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class ResultController implements Initializable {
    @FXML
    private ListView listWhiteMoves;
    @FXML
    private ListView listBlackMoves;
    @FXML
    private Label lbPlayerWhiteName;
    @FXML
    private Label lbPlayerBlackName;
    @FXML
    private Label lbPlayerWhitePoints;
    @FXML
    private Label lbPlayerBlackPoints;

    private Player playerWhite;
    private Player playerBlack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerWhite = BoardController.getPlayerWhite();
        playerBlack = BoardController.getPlayerBlack();

        initData();
    }

    private void initData() {
        lbPlayerWhiteName.setText(playerWhite.getName());
        lbPlayerWhitePoints.setText(playerWhite.getPoints().toString());

        lbPlayerBlackName.setText(playerBlack.getName());
        lbPlayerBlackPoints.setText(playerBlack.getPoints().toString());

        showPlayerMoves(playerWhite, listWhiteMoves);
        showPlayerMoves(playerBlack, listBlackMoves);
    }

    private void showPlayerMoves(Player player, ListView list) {

        ArrayList<Integer[]> playerMoves = player.getAllPlayedMoves();
        ObservableList<String> items = FXCollections.observableArrayList();

        Integer moveNumber = 1;

        for (Integer[] move : playerMoves) {
            items.add("#" + moveNumber + " [ Row " + move[0] + " | Col " + move[1] + " ]");
            moveNumber++;
        }

        list.setItems(items);
    }

    @FXML
    /** New game button click handler. */
    private void onNewGameButtonClick() {
        StartGameController.showBoardView();
    }

    @FXML
    /** High score button click handler. */
    private void onHighScoreButtonClick() {
        BoardController.showHighScoreView();
    }

    @FXML
    /** Documentation button click handler. */
    private void onDocumentationButtonClick() {
        DocumentationService.generateDocumentation();
    }
}
