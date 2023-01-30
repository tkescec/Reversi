package hr.reversi.controller;

import hr.reversi.Main;
import hr.reversi.model.Player;
import hr.reversi.util.DiscState;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartGameController implements Initializable {
    @FXML
    private TextField playerTwoNameTextField;

    private static Player playerTwo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerTwo = new Player();
    }

    public void startGame(){
        initPlayers();
        showBoardView();
    }

    private void initPlayers() {
        String playerTwoName = playerTwoNameTextField.getText();

        playerTwo.setDiscState(DiscState.black);
        playerTwo.setName(playerTwoName);
        playerTwo.setPoints(0);
    }

    public static void showBoardView() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/hr/reversi/view/board-view.fxml"));
        Scene scene = null;

        try {
            scene = new Scene(fxmlLoader.load(), 800, 800);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage = Main.getMainStage();

        stage.setTitle("Reversi");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static Player getPlayerTwo() {
        return playerTwo;
    }
}
