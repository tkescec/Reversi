package hr.reversi.model;

import hr.reversi.util.DiscState;

import java.io.Serializable;
import java.util.ArrayList;

public class BoardState implements Serializable {
    private Disc[][] boardGrid;
    private Player playerWhite;
    private Player playerBlack;
    private DiscState playerTurn;

    public BoardState() {

    }

    public Disc[][] getBoardGrid() {
        return boardGrid;
    }

    public void setBoardGrid(Disc[][] boardGrid) {
        this.boardGrid = boardGrid;
    }

    public Player getPlayerWhite() {
        return playerWhite;
    }

    public void setPlayerWhite(Player playerWhite) {
        this.playerWhite = playerWhite;
    }

    public Player getPlayerBlack() {
        return playerBlack;
    }

    public void setPlayerBlack(Player playerBlack) {
        this.playerBlack = playerBlack;
    }

    public DiscState getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(DiscState playerTurn) {
        this.playerTurn = playerTurn;
    }
}
