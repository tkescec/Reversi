package hr.reversi.model;

import hr.reversi.util.DiscState;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    /** Player name. */
    private String name;
    /** Number of player points. */
    private Integer points;
    /** Player disc state which translates to disc color. */
    private DiscState discState;
    /**List of all played moves for current player.
     * List of arrays with coordinates [row, col]. */
    private ArrayList<Integer[]> allPlayedMoves = new ArrayList<Integer[]>();
    /** Player winner. */
    private Boolean winner = false;

    /** Player constructor. */
    public Player() {

    }

    /**
     * Gets player name.
     *
     * @return player name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets player name.
     *
     * @param playerName player name.
     */
    public void setName(final String playerName) {
        this.name = playerName;
    }

    /**
     * Gets player points.
     *
     * @return player points.
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * Sets player points.
     *
     * @param playerPoints player points.
     */
    public void setPoints(final Integer playerPoints) {
        this.points = playerPoints;
    }

    /**
     * Gets disc state.
     *
     * @return disc state.
     */
    public DiscState getDiscState() {
        return discState;
    }

    /**
     * Sets disc state translated to color of disc on the board.
     *
     * @param state disc state.
     */
    public void setDiscState(final DiscState state) {
        this.discState = state;
    }

    /**
     * Gets all played moves.
     *
     * @return played moves.
     */
    public ArrayList<Integer[]> getAllPlayedMoves() {
        return allPlayedMoves;
    }

    /**
     * Sets all played moves.
     *
     * @param allPlayedMoves played moves.
     */
    public void setAllPlayedMoves(ArrayList<Integer[]> allPlayedMoves) {
        this.allPlayedMoves = allPlayedMoves;
    }

    /**
     * Add player played move.
     *
     * @param move played move.
     */
    public void addPlayedMove(Integer[] move) {
        this.allPlayedMoves.add(move);
    }

    public Boolean isWinner() {
        return winner;
    }

    public void setWinner(Boolean winner) {
        this.winner = winner;
    }
}


