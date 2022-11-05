package hr.reversi.model;

import hr.reversi.util.DiscState;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {
    /** Number of rows. */
    private final int rows = 8;
    /** Number of columns. */
    private final int cols = 8;
    /** Representation of boardGrid in 2d array. */
    private Disc[][] boardGrid = new Disc[rows][cols];
    /**List of all valid moves for current player.
     * List of arrays with coordinates [row, col]. */
    private ArrayList<Integer[]> allValidMoves = new ArrayList<Integer[]>();
    /** List of all opponent discs captured by player.*/
    private ArrayList<Disc> flipedDiscsToMark = new ArrayList<Disc>();

    /**
     * Board constructor.
     */
    public Board() {
        //initBoard();
    }

    /**
     * Inits boardGrid with two discs for each player.
     * All discs are initially set to DiscState.empty which translates
     * in view to empty square on boardGrid.
     */
    public void initBoard() {
        DiscState state = DiscState.empty;
        for (int row = 0; row < boardGrid.length; row++) {
            for (int col = 0; col < boardGrid[row].length; col++) {
                addDisc(row, col, state);
            }
        }
        initDiscs();
    }

    /**
     * Inits two discs for each player.
     */
    private void initDiscs() {
        final int firstWhiteDiscRow = 3;
        final int firstWhiteDiscCol = 3;
        final DiscState whiteDiscState = DiscState.white;
        modifyDiscState(firstWhiteDiscRow, firstWhiteDiscCol, whiteDiscState);

        final int secWhiteDiscRow = 4;
        final int secWhiteDiscCol = 4;
        modifyDiscState(secWhiteDiscRow, secWhiteDiscCol, whiteDiscState);

        final int firstBlackDiscRow = 3;
        final int firstBlackDiscCol = 4;
        final DiscState blackDiscState = DiscState.black;
        modifyDiscState(firstBlackDiscRow, firstBlackDiscCol, blackDiscState);

        final int secBlackDiscRow = 4;
        final int secBlackDiscCol = 3;
        modifyDiscState(secBlackDiscRow, secBlackDiscCol, blackDiscState);
    }

    /**
     * Adds disc object to boardGrid array.
     *
     * @param row       position in row.
     * @param col       position in column.
     * @param discState disc state.
     */
    private void addDisc(final Integer row, final Integer col, final DiscState discState) {
        Disc disc = new Disc();
        disc.setRow(row);
        disc.setCol(col);
        disc.setState(discState);
        boardGrid[row][col] = disc;
    }

    /**
     * Gets disc object from boardGrid for given coordinates.
     *
     * @param row position in row.
     * @param col position in column.
     * @return Disc object.
     */
    public Disc getDiscFromBoard(final Integer row, final Integer col) {
        Disc disc = boardGrid[row][col];
        return disc;
    }

    /**
     *
     * @return Returns all valid moves for current player.
     */
    public ArrayList<Integer[]> getAllValidMoves() {
        return allValidMoves;
    }

    /**
     *
     * @return Returns list of disk to mark.
     */
    public ArrayList<Disc> getFlipedDiscsToMark() {
        return flipedDiscsToMark;
    }

    /**
     * Clear list of disks to flip.
     */
    public void clearFlipedDiscsToMark() {
        flipedDiscsToMark.clear();
    }

    /**
     * Flip all captured opponent discs.
     * @param row row value.
     * @param col column value.
     * @param playerTurn current player turn (0 - white, 1 - black).
     */
    public void flipAllDiscs(final Integer row, final Integer col,
                             final DiscState playerTurn) {
        this.flipHorizontalDiscs(row, col, playerTurn);
        this.flipVerticalDiscs(row, col, playerTurn);
        this.flipDiagonalDiscs(row, col, playerTurn);
    }

    /**
     * Modifies state of disc.
     *
     * @param row       position in row.
     * @param col       position in column.
     * @param discState disc state.
     */
    public void modifyDiscState(final Integer row, final Integer col,
                                final DiscState discState) {
        Disc disc = getDiscFromBoard(row, col);
        disc.setState(discState);
    }

    /**
     * Gets all discs of particular player.
     *
     * @param currentPlayer player disc state (or playerTurn).
     * @return list of Disc objects.
     */
    public ArrayList<Disc> getAllPlayerDiscs(final DiscState currentPlayer) {
        ArrayList<Disc> list = new ArrayList<Disc>();
        for (int row = 0; row < boardGrid.length; row++) {
            for (int col = 0; col < boardGrid[row].length; col++) {
                Disc disc = getDiscFromBoard(row, col);
                DiscState discState = disc.getState();
                if (discState == currentPlayer) {
                    list.add(disc);
                }
            }
        }
        return list;
    }

    // ************** SEARCH AND VALIDATE MOVES **************

    /** Gets all horizontal moves.
     *
     * @param disc Disc object.
     * @return list of arrays with moves coordinates.
     */
    private ArrayList<Integer[]> getHorizontalMoves(final Disc disc) {
        Integer discRow = disc.getRow();
        Integer discCol = disc.getCol();
        int colRight = discCol + 1;
        DiscState opponentDiscState = DiscState.white;
        DiscState nextDiscState = DiscState.empty;
        ArrayList<Integer[]> result = new ArrayList<Integer[]>();

        if (disc.getState() == DiscState.white) {
            opponentDiscState = DiscState.black;
        } else if (disc.getState() == DiscState.black) {
            opponentDiscState = DiscState.white;
        }

        // search right
        if (colRight <= boardGrid.length - 1) {
            nextDiscState = this.getDiscFromBoard(discRow, colRight)
                    .getState();
        }

        while (nextDiscState == opponentDiscState) {
            colRight++;

            if (colRight > boardGrid.length - 1) {
                break;
            }

            nextDiscState = this.getDiscFromBoard(discRow, colRight)
                    .getState();

            if (nextDiscState == DiscState.empty) {
                Integer[] move = new Integer[2];
                move[0] = discRow;
                move[1] = colRight;
                result.add(move);
                break;
            }
        }

        // search left
        int colLeft = discCol - 1;
        DiscState nextDiscStateLeft = DiscState.empty;

        if (colLeft >= 0) {
            nextDiscStateLeft = this.getDiscFromBoard(discRow, colLeft)
                    .getState();
        }

        while (nextDiscStateLeft == opponentDiscState) {
            colLeft--;

            if (colLeft < 0) {
                break;
            }

            nextDiscStateLeft = this.getDiscFromBoard(discRow, colLeft)
                    .getState();

            if (nextDiscStateLeft == DiscState.empty) {
                Integer[] move = new Integer[2];
                move[0] = discRow;
                move[1] = colLeft;
                result.add(move);
                break;
            }
        }
        return result;
    }

    /** Gets all vertical moves.
     *
     * @param disc Disc object.
     * @return list of arrays with moves coordinates.
     */
    private ArrayList<Integer[]> getVerticalMoves(final Disc disc) {
        Integer discRow = disc.getRow();
        Integer discCol = disc.getCol();
        int rowUp = discRow - 1;
        DiscState opponentDiscState = DiscState.white;
        DiscState nextDiscStateUp = DiscState.empty;
        ArrayList<Integer[]> result = new ArrayList<Integer[]>();

        if (disc.getState() == DiscState.white) {
            opponentDiscState = DiscState.black;
        } else if (disc.getState() == DiscState.black) {
            opponentDiscState = DiscState.white;
        }

        // search up
        if (rowUp >= 0) {
            nextDiscStateUp = this.getDiscFromBoard(rowUp, discCol).getState();
        }

        while (nextDiscStateUp == opponentDiscState) {
            rowUp--;
            if (rowUp < 0) {
                break;
            }

            nextDiscStateUp = this.getDiscFromBoard(rowUp, discCol).getState();

            if (nextDiscStateUp == DiscState.empty) {
                Integer[] move = new Integer[2];
                move[0] = rowUp;
                move[1] = discCol;
                result.add(move);
                break;
            }
        }

        int rowDown = discRow + 1;
        DiscState nextDiscStateDown = DiscState.empty;

        // search down
        if (rowDown <= boardGrid.length - 1) {
            nextDiscStateDown = this.getDiscFromBoard(rowDown, discCol)
                    .getState();
        }

        while (nextDiscStateDown == opponentDiscState) {
            rowDown++;
            if (rowDown > boardGrid.length - 1) {
                break;
            }

            nextDiscStateDown = this.getDiscFromBoard(rowDown, discCol)
                    .getState();

            if (nextDiscStateDown == DiscState.empty) {
                Integer[] move = new Integer[2];
                move[0] = rowDown;
                move[1] = discCol;
                result.add(move);
                break;
            }
        }
        return result;
    }

    /** Gets all diagonal moves.
     *
     * @param disc Disc object.
     * @return list of arrays with moves coordinates.
     */
    private ArrayList<Integer[]> getDiagonalMoves(final Disc disc) {
        ArrayList<Integer[]> result = new ArrayList<Integer[]>();
        Integer row = disc.getRow();
        Integer col = disc.getCol();
        DiscState discState = disc.getState();
        DiscState nextDiscState = DiscState.empty;
        DiscState opponentDiscState = DiscState.white;
        DiscState prevDiscState = discState;

        if (disc.getState() == DiscState.white) {
            opponentDiscState = DiscState.black;
        } else if (disc.getState() == DiscState.black) {
            opponentDiscState = DiscState.white;
        }

        // diagonal up right
        for (int i = row - 1; i >= 0; i--) {
            col++;

            if (col > boardGrid.length - 1) {
                break;
            }

            nextDiscState = this.getDiscFromBoard(i, col).getState();

            if (nextDiscState == discState) {
                break;
            }

            if (nextDiscState == opponentDiscState) {
                prevDiscState = opponentDiscState;
                continue;
            }

            if (nextDiscState == DiscState.empty && prevDiscState != discState) {
                Integer[] move = new Integer[2];
                move[0] = i;
                move[1] = col;
                result.add(move);
                break;
            } else if (nextDiscState == DiscState.empty && prevDiscState == discState) {
                break;
            }
        }

        row = disc.getRow();
        col = disc.getCol();
        discState = disc.getState();
        nextDiscState = DiscState.empty;
        opponentDiscState = DiscState.white;
        prevDiscState = discState;

        if (disc.getState() == DiscState.white) {
            opponentDiscState = DiscState.black;
        } else if (disc.getState() == DiscState.black) {
            opponentDiscState = DiscState.white;
        }

        // diagonal down left
        for (int i = row + 1; i < boardGrid.length; i++) {
            col--;

            if (col < 0) {
                break;
            }

            nextDiscState = this.getDiscFromBoard(i, col).getState();

            if (nextDiscState == discState) {
                break;
            }

            if (nextDiscState == opponentDiscState) {
                prevDiscState = opponentDiscState;
                continue;
            }

            if (nextDiscState == DiscState.empty && prevDiscState != discState) {
                Integer[] move = new Integer[2];
                move[0] = i;
                move[1] = col;
                result.add(move);
                break;
            } else if (nextDiscState == DiscState.empty && prevDiscState == discState) {
                break;
            }
        }

        row = disc.getRow();
        col = disc.getCol();
        discState = disc.getState();
        nextDiscState = DiscState.empty;
        opponentDiscState = DiscState.white;
        prevDiscState = discState;

        if (disc.getState() == DiscState.white) {
            opponentDiscState = DiscState.black;
        } else if (disc.getState() == DiscState.black) {
            opponentDiscState = DiscState.white;
        }

        // diagonal up left
        for (int i = col - 1; i >= 0; i--) {
            row--;

            if (row < 0) {
                break;
            }

            nextDiscState = this.getDiscFromBoard(row, i).getState();

            if (nextDiscState == discState) {
                break;
            }

            if (nextDiscState == opponentDiscState) {
                prevDiscState = opponentDiscState;
                continue;
            }

            if (nextDiscState == DiscState.empty && prevDiscState != discState) {
                Integer[] move = new Integer[2];
                move[0] = row;
                move[1] = i;
                result.add(move);
                break;
            } else if (nextDiscState == DiscState.empty && prevDiscState == discState) {
                break;
            }
        }

        row = disc.getRow();
        col = disc.getCol();
        discState = disc.getState();
        nextDiscState = DiscState.empty;
        opponentDiscState = DiscState.white;
        prevDiscState = discState;

        if (disc.getState() == DiscState.white) {
            opponentDiscState = DiscState.black;
        } else if (disc.getState() == DiscState.black) {
            opponentDiscState = DiscState.white;
        }

        // diagonal down right
        for (int i = col + 1; i < boardGrid.length; i++) {
            row++;

            if (row > boardGrid.length - 1) {
                break;
            }

            nextDiscState = this.getDiscFromBoard(row, i).getState();

            if (nextDiscState == discState) {
                break;
            }

            if (nextDiscState == opponentDiscState) {
                prevDiscState = opponentDiscState;
                continue;
            }

            if (nextDiscState == DiscState.empty && prevDiscState != discState) {
                Integer[] move = new Integer[2];
                move[0] = row;
                move[1] = i;
                result.add(move);
                break;
            } else if (nextDiscState == DiscState.empty && prevDiscState == discState) {
                break;
            }
        }
        return result;
    }

    /** Collects all valid moves for current player.
     *
     * @param newPlayerTurn current player.
     */
    public void getValidMoves(final DiscState newPlayerTurn) {
        allValidMoves.clear();
        // generate posible moves for player
        ArrayList<Disc> list = this.getAllPlayerDiscs(newPlayerTurn);
        for (Disc disc : list) {
            ArrayList<Integer[]> hMoves = getHorizontalMoves(disc);
            for (Integer[] move : hMoves) {
                allValidMoves.add(move);
            }

            ArrayList<Integer[]> vMoves = getVerticalMoves(disc);
            for (Integer[] move : vMoves) {
                allValidMoves.add(move);
            }

            ArrayList<Integer[]> dMoves = getDiagonalMoves(disc);
            for (Integer[] move : dMoves) {
                allValidMoves.add(move);
            }
        }
        this.removeDuplicatedValidMoves();
    }

    /** Removes duplicated valid moves. */
    private void removeDuplicatedValidMoves() {
        for (int i = 0; i < allValidMoves.size(); i++) {
            Integer[] iMove = allValidMoves.get(i);
            int iRow = iMove[0];
            int iCol = iMove[1];
            for (int j = 0; j < allValidMoves.size(); j++) {
                Integer[] jMove = allValidMoves.get(j);
                int jRow = jMove[0];
                int jCol = jMove[1];
                if (i != j) {
                    if (iRow == jRow && iCol == jCol) {
                        allValidMoves.remove(j);
                    }
                }
            }
        }
    }

    // ************** FLIP OPPONENT DISCS **************

    /** Changes state of opponent disc captured by player.
     *
     * @param row row coordinates.
     * @param col column coordinates.
     * @param currentPlayer current player turn (0 - white, 1 - black).
     */
    public void flipHorizontalDiscs(final Integer row, final Integer col,
                                    final DiscState currentPlayer) {
        DiscState nextDiscState = DiscState.empty;
        DiscState primaryDiscState = currentPlayer;
        ArrayList<Disc> discsToFlip = new ArrayList<Disc>();

        // add loop to check if placed move "close" opponent discs on right
        for (int i = col + 1; i <= boardGrid.length - 1; i++) {
            nextDiscState = this.getDiscFromBoard(row, i).getState();
            if (nextDiscState != primaryDiscState) {
                Disc opponentDisc = this.getDiscFromBoard(row, i);
                discsToFlip.add(opponentDisc);
            }

            if (nextDiscState == DiscState.empty) {
                discsToFlip.clear();
                break;
            } else if (nextDiscState == primaryDiscState) {
                for (Disc disc : discsToFlip) {
                    this.getDiscFromBoard(disc.getRow(), disc.getCol())
                            .setState(primaryDiscState);
                    flipedDiscsToMark.add(disc);
                }
                break;
            } else if (i + 1 > boardGrid.length - 1) {
                discsToFlip.clear();
                break;
            }
        }

        // add loop to check if placed move "close" opponent discs on left
        for (int i = col - 1; i >= 0; i--) {
            nextDiscState = this.getDiscFromBoard(row, i).getState();
            if (nextDiscState != primaryDiscState) {
                Disc opponentDisc = this.getDiscFromBoard(row, i);
                discsToFlip.add(opponentDisc);
            }

            if (nextDiscState == DiscState.empty) {
                discsToFlip.clear();
                break;
            } else if (nextDiscState == primaryDiscState) {
                for (Disc disc : discsToFlip) {
                    this.getDiscFromBoard(disc.getRow(), disc.getCol())
                            .setState(primaryDiscState);
                    flipedDiscsToMark.add(disc);
                }
                break;
            } else if (i - 1 < 0) {
                discsToFlip.clear();
                break;
            }
        }
    }

    /** Changes state of opponent disc captured by player.
     *
     * @param row row coordinates.
     * @param col column coordinates.
     * @param currentPlayer current player turn .
     */
    public void flipVerticalDiscs(final Integer row, final Integer col,
                                  final DiscState currentPlayer) {
        DiscState nextDiscState = DiscState.empty;
        DiscState primaryDiscState = currentPlayer;
        ArrayList<Disc> discsToFlip = new ArrayList<Disc>();

        // add loop to check if placed move "close" opponent discs up
        for (int i = row - 1; i >= 0; i--) {
            nextDiscState = this.getDiscFromBoard(i, col).getState();
            if (nextDiscState != primaryDiscState) {
                Disc opponentDisc = this.getDiscFromBoard(i, col);
                discsToFlip.add(opponentDisc);
            }

            if (nextDiscState == DiscState.empty) {
                discsToFlip.clear();
                break;
            } else if (nextDiscState == primaryDiscState) {
                for (Disc disc : discsToFlip) {
                    this.getDiscFromBoard(disc.getRow(), disc.getCol())
                            .setState(primaryDiscState);
                    flipedDiscsToMark.add(disc);
                }
                break;
            } else if (i - 1 < 0) {
                discsToFlip.clear();
                break;
            }
        }

        // add loop to check if placed move "close" opponent discs down
        for (int i = row + 1; i <= boardGrid.length - 1; i++) {
            nextDiscState = this.getDiscFromBoard(i, col).getState();
            if (nextDiscState != primaryDiscState) {
                Disc opponentDisc = this.getDiscFromBoard(i, col);
                discsToFlip.add(opponentDisc);
            }

            if (nextDiscState == DiscState.empty) {
                discsToFlip.clear();
                break;
            } else if (nextDiscState == primaryDiscState) {
                for (Disc disc : discsToFlip) {
                    this.getDiscFromBoard(disc.getRow(), disc.getCol())
                            .setState(primaryDiscState);
                    flipedDiscsToMark.add(disc);
                }
                break;
            } else if (i + 1 > boardGrid.length - 1) {
                discsToFlip.clear();
                break;
            }
        }
    }

    /** Changes state of opponent disc captured by player.
     *
     * @param rowValue row coordinates.
     * @param colValue column coordinates.
     * @param currentPlayer current player turn .
     */
    public void flipDiagonalDiscs(final Integer rowValue,
                                  final Integer colValue, final DiscState currentPlayer) {
        int row = rowValue;
        int col = colValue;
        DiscState nextDiscState = DiscState.empty;
        DiscState primaryDiscState = currentPlayer;
        int tmpRow = row;
        int tmpCol = col;
        ArrayList<Disc> discsToFlip = new ArrayList<Disc>();

        /** add loop to check if placed move "close"
         *  opponent discs  (diagonal up right)
         */
        for (int i = row - 1; i >= 0; i--) {
            col++;

            if (col > boardGrid.length - 1) {
                break;
            }

            nextDiscState = this.getDiscFromBoard(i, col).getState();
            if (nextDiscState != primaryDiscState) {
                Disc opponentDisc = this.getDiscFromBoard(i, col);
                discsToFlip.add(opponentDisc);
            }

            if (nextDiscState == DiscState.empty) {
                discsToFlip.clear();
                break;
            } else if (nextDiscState == primaryDiscState) {
                for (Disc disc : discsToFlip) {
                    this.getDiscFromBoard(disc.getRow(), disc.getCol())
                            .setState(primaryDiscState);
                    flipedDiscsToMark.add(disc);
                }
                break;
            } else if (i - 1 < 0 || col + 1 > boardGrid.length - 1) {
                discsToFlip.clear();
                break;
            }
        }

        row = tmpRow;
        col = tmpCol;

        /** add loop to check if placed move "close"
         * opponent discs (diagonal down left)
         */
        for (int i = row + 1; i < boardGrid.length; i++) {
            col--;

            if (col < 0) {
                break;
            }

            nextDiscState = this.getDiscFromBoard(i, col).getState();
            if (nextDiscState != primaryDiscState) {
                Disc opponentDisc = this.getDiscFromBoard(i, col);
                discsToFlip.add(opponentDisc);
            }

            if (nextDiscState == DiscState.empty) {
                discsToFlip.clear();
                break;
            } else if (nextDiscState == primaryDiscState) {
                for (Disc disc : discsToFlip) {
                    this.getDiscFromBoard(disc.getRow(), disc.getCol())
                            .setState(primaryDiscState);
                    flipedDiscsToMark.add(disc);
                }
                break;
            } else if (i + 1 > boardGrid.length - 1 || col - 1 < 0) {
                discsToFlip.clear();
                break;
            }
        }

        row = tmpRow;
        col = tmpCol;

        /** add loop to check if placed move "close"
         *  opponent discs (diagonal up left)
         */
        for (int i = row - 1; i >= 0; i--) {
            col--;

            if (col < 0) {
                break;
            }

            nextDiscState = this.getDiscFromBoard(i, col).getState();
            if (nextDiscState != primaryDiscState) {
                Disc opponentDisc = this.getDiscFromBoard(i, col);
                discsToFlip.add(opponentDisc);
            }

            if (nextDiscState == DiscState.empty) {
                discsToFlip.clear();
                break;
            } else if (nextDiscState == primaryDiscState) {
                for (Disc disc : discsToFlip) {
                    this.getDiscFromBoard(disc.getRow(), disc.getCol())
                            .setState(primaryDiscState);
                    flipedDiscsToMark.add(disc);
                }
                break;
            } else if (i - 1 < 0 || col - 1 < 0) {
                discsToFlip.clear();
                break;
            }
        }

        row = tmpRow;
        col = tmpCol;

        /** add loop to check if placed move "close"
         * opponent discs (diagonal down right)
         */
        for (int i = row + 1; i < boardGrid.length; i++) {
            col++;

            if (col > boardGrid.length - 1) {
                break;
            }

            nextDiscState = this.getDiscFromBoard(i, col).getState();
            if (nextDiscState != primaryDiscState) {
                Disc opponentDisc = this.getDiscFromBoard(i, col);
                discsToFlip.add(opponentDisc);
            }

            if (nextDiscState == DiscState.empty) {
                discsToFlip.clear();
                break;
            } else if (nextDiscState == primaryDiscState) {
                for (Disc disc : discsToFlip) {
                    this.getDiscFromBoard(disc.getRow(), disc.getCol())
                            .setState(primaryDiscState);
                    flipedDiscsToMark.add(disc);
                }
                break;
            } else if (i + 1 > boardGrid.length - 1
                    || col + 1 > boardGrid.length - 1) {
                discsToFlip.clear();
                break;
            }
        }
    }


    /** Prints current state of the boardGrid to console. */
    public void printBoard() {
        for (int row = 0; row < boardGrid.length; row++) {
            for (int col = 0; col < boardGrid[row].length; col++) {
                Disc disc = getDiscFromBoard(row, col);
                System.out.print(disc.getState() + "\t");
            }
            System.out.println();
        }
    }

    /**
     * Gets boardGrid.
     *
     * @return 2d array of Disc objects.
     */
    public Disc[][] getBoardGrid() {
        return boardGrid;
    }

    public void setBoardGrid(Disc[][] boardGrid) {
        this.boardGrid = boardGrid;
    }
}
