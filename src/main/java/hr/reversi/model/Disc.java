package hr.reversi.model;

import hr.reversi.util.DiscState;

import java.io.Serializable;

public class Disc implements Serializable {
    /** Position in row. */
    private Integer row;
    /** Position in column. */
    private Integer col;
    /** Disc state. */
    private DiscState state;

    /** Disc constructor. */
    public Disc() {
    }

    /**
     * Gets disc column coordinate.
     *
     * @return column value.
     */
    public Integer getCol() {
        return col;
    }

    /**
     * Sets disc column coordinate.
     *
     * @param colValue position in column.
     */
    public void setCol(final Integer colValue) {
        this.col = colValue;
    }

    /**
     * Gets disc row coordinate.
     *
     * @return position in column.
     */
    public Integer getRow() {
        return row;
    }

    /**
     * Sets disc row coordiante.
     *
     * @param rowValue position in row.
     */
    public void setRow(final Integer rowValue) {
        this.row = rowValue;
    }

    /**
     * Gets disc state.
     *
     * @return disc state.
     */
    public DiscState getState() {
        return state;
    }

    /**
     * Sets disc state.
     *
     * @param discState disc state.
     */
    public void setState(final DiscState discState) {
        this.state = discState;
    };
}
