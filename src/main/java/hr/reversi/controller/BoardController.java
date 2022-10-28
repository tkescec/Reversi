package hr.reversi.controller;

import hr.reversi.model.Board;
import hr.reversi.model.Player;
import hr.reversi.ui.DiscUI;
import hr.reversi.util.DiscState;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements Initializable {
    @FXML
    private Circle blackDiscPoints;
    @FXML
    private Circle whiteDiscPoints;
    @FXML
    private Label lbPlayerWhiteName;
    @FXML
    private Label lbPlayerBlackName;
    @FXML
    private Label lbPlayerWhitePoints;
    @FXML
    private Label lbPlayerBlackPoints;
    @FXML
    private GridPane boardGrid;

    private Board board;
    private DiscUI discUi;
    private Player playerWhite;
    private Player playerBlack;
    private DiscState playerTurn;
    private final DiscState initPlayerTurn = DiscState.white;
    private final Boolean moveMarker = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        board = new Board();
        discUi = new DiscUI();

        initPlayers();
        initBoard();
    }

    private void initPlayers() {
        Player playerOne = StartGameController.getPlayerOne();
        Player playerTwo = StartGameController.getPlayerTwo();

        playerWhite = playerOne.getDiscState() == DiscState.white ? playerOne : playerTwo;
        playerBlack = playerOne.getDiscState() == DiscState.black ? playerOne : playerTwo;

        showPlayerName();
        showPlayerPoints();
    }

    private void showPlayerName() {
        lbPlayerWhiteName.setText(playerWhite.getName());
        lbPlayerBlackName.setText(playerBlack.getName());
    }

    private void showPlayerPoints() {
        lbPlayerWhitePoints.setText(playerWhite.getPoints().toString());
        lbPlayerBlackPoints.setText(playerBlack.getPoints().toString());
    }

    private void initBoard() {
        board.initBoard();
        setPlayerTurn(initPlayerTurn);
        board.getValidMoves(playerTurn);
        updateBoardView();
    }

    // ************** HELPER FUNCTIONS **************

    /** Sets player turn.
     *
     * @param value
     */
    private void setPlayerTurn(final DiscState value) {
        this.playerTurn = value;
    }

    /** Changes player turn. */
    private void changePlayerTurn() {
        if (playerTurn == DiscState.white) {
            setPlayerTurn(DiscState.black);
        } else if (playerTurn == DiscState.black) {
            setPlayerTurn(DiscState.white);
        }
    }

    /** Counts points for given player.
     *
     * @param player Player class object.
     */
    private void countPlayerPoints(final Player player) {
        DiscState discState = player.getDiscState();
        player.setPoints(board.getAllPlayerDiscs(discState).size());
    }

    /** Adds highlight effect to points counter disc.
     *
     * @param playerTurn current player.
     */
    public void highLightPoints(final DiscState playerTurn) {
        final double opacity = 1.0;
        if (playerTurn == DiscState.white) {
            whiteDiscPoints.setStrokeWidth(5);
            whiteDiscPoints.setStroke(Color.web("#ed7753", opacity));
        } else if (playerTurn == DiscState.black) {
            blackDiscPoints.setStrokeWidth(5);
            blackDiscPoints.setStroke(Color.web("#ed7753", opacity));
        }
    }

    // ************** VIEW UPDATE **************

    /** Updates view of all elements in main window.
     *
     */
    private void updateBoardView() {

        //switchOnNoValidMoves();
        var test = boardGrid.getChildren();
        for (Node square : boardGrid.getChildren()) {

            Integer col = boardGrid.getColumnIndex(square);
            Integer row = boardGrid.getRowIndex(square);
            DiscState discState = board.getDiscFromBoard(row, col).getState();
            StackPane sp = (StackPane) square;

            if (sp.getChildren().size() == 2) {
                sp.getChildren().remove(1);
            }
            sp.getChildren().add(discUi.makeDisc(discState));

//            if (debugMarker.equals(true)) {
//                View.DebugMarkers dm = view.new DebugMarkers();
//                sp.getChildren().add(dv.makeDisc(discState));
//                for (Disc disc : board.getFlipedDiscsToMark()) {
//                    int discRow = disc.getRow();
//                    int discCol = disc.getCol();
//                    if (discRow == row && discCol == col) {
//                        StackPane spWithMarker = new StackPane();
//                        sp.getChildren().remove(1);
//                        spWithMarker.getChildren().addAll(
//                                dv.makeDisc(discState),
//                                dm.flipDebugMarker());
//                        sp.getChildren().add(spWithMarker);
//                    }
//                }
//            } else if (debugMarker.equals(false)) {
//                sp.getChildren().add(dv.makeDisc(discState));
//            }
//
//            if (moveMarker.equals(true)) {
//                for (Integer[] move : board.getAllValidMoves()) {
//                    int validMoveRow = move[0];
//                    int validMoveCol = move[1];
//                    if (row == validMoveRow && col == validMoveCol) {
//                        sp.getChildren().add(bg.validMoveMarker());
//                    }
//                }
//            }

            if (sp.getChildren().size() > 2) {
                sp.getChildren().remove(1, sp.getChildren().size() - 1);
            }
        }
        //updatePointsCounters();

        //updatePlayerTurnIndicators();

//        if (isTimerOn.equals(true)) {
//            setGameTimer();
//        }

        if (board.getAllValidMoves().isEmpty()) {
            //addSummary(playerOne, playerTwo);
        }

        board.clearFlipedDiscsToMark();
    }

    /** Switches player if there is no valid move. */
//    private void switchOnNoValidMoves() {
//        // switch player if there are no valid moves
//        if (board.getAllValidMoves().isEmpty()) {
//            changePlayerTurn();
//            updatePointsCounters();
//            updatePlayerTurnIndicators();
//            board.getValidMoves(playerTurn);
//        }
//    }
}
