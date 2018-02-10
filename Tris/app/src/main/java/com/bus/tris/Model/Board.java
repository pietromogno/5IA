package com.bus.tris.Model;

/**
 * Created by BUS on 07/02/2018.
 */
import static com.bus.tris.Model.Player.O;
import static com.bus.tris.Model.Player.X;

public class Board {

    private Cell[][] cells = new Cell[3][3];

    private Player winner;
    private GameState state;
    private Player currentTurn;

    private int freeCells;
    private enum GameState { IN_PROGRESS, FINISHED };

    public Board() {
        restart();
    }

    /**
     *  Restart o start un nuovo gioco farà pulire il campo e lo stato di gioco.
     */
    public void restart() {
        clearCells();
        winner = null;
        currentTurn = Player.X;
        state = GameState.IN_PROGRESS;
        freeCells = 9;
    }

    /*public void computerMoove(){
        int posRandom = (int)(Math.random()*freeCells);
        for(int i=0, counterPos = 0; i<cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if(!cells[i][j].equals(Player.O) && !cells[i][j].equals(Player.X)){
                    if(counterPos == posRandom) {
                        cells[i][j].setValue(currentTurn);
                        freeCells--;
                        if(isWinningMoveByPlayer(currentTurn, i, j)) {
                            state = GameState.FINISHED;
                            winner = currentTurn;
                        }
                    }
                }
            }
        }
    }*/

    /**
     * segna la riga correntre per il giocatore corrente.
     *
     *
     * @param row 0..2
     * @param col 0..2
     * @return il giocatore che ha fatto la mossa o null se non ha fatto niente
     *
     */
    public Player mark( int row, int col ) {

        Player playerThatMoved = null;

        if(isValid(row, col)) {

            cells[row][col].setValue(currentTurn);
            freeCells--;
            playerThatMoved = currentTurn;

            if(isWinningMoveByPlayer(currentTurn, row, col)) {
                state = GameState.FINISHED;
                winner = currentTurn;

            } else {
                // flip the current turn and continue
                flipCurrentTurn();
            }
        }

        return playerThatMoved;
    }

    public Player getWinner() {
        return winner;
    }

    private void clearCells() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    private boolean isValid(int row, int col ) {
        if( state == GameState.FINISHED ) {
            return false;
        } else if( isOutOfBounds(row) || isOutOfBounds(col) ) {
            return false;
        } else if( isCellValueAlreadySet(row, col) ) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isOutOfBounds(int idx) {
        return idx < 0 || idx > 2;
    }

    private boolean isCellValueAlreadySet(int row, int col) {
        return cells[row][col].getValue() != null;
    }


    private boolean isWinningMoveByPlayer(Player player, int currentRow, int currentCol) {

        return (cells[currentRow][0].getValue() == player         // 3-in-the-row
                && cells[currentRow][1].getValue() == player
                && cells[currentRow][2].getValue() == player
                || cells[0][currentCol].getValue() == player      // 3-in-the-column
                && cells[1][currentCol].getValue() == player
                && cells[2][currentCol].getValue() == player
                || currentRow == currentCol            // 3-in-the-diagonal
                && cells[0][0].getValue() == player
                && cells[1][1].getValue() == player
                && cells[2][2].getValue() == player
                || currentRow + currentCol == 2    // 3-in-the-opposite-diagonal
                && cells[0][2].getValue() == player
                && cells[1][1].getValue() == player
                && cells[2][0].getValue() == player);
    }

    private void flipCurrentTurn() {
        currentTurn = currentTurn == X ? O : X;
    }

}
