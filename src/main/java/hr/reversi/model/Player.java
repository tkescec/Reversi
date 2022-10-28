package hr.reversi.model;

import hr.reversi.util.DiscState;

public class Player {
    /** Player name. */
    private String name;
    /** Number of player points. */
    private Integer points;
    /** Player disc state which translates to disc color. */
    private DiscState discState;

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
}


