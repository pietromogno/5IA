package com.pag.tic_tac_toe;
import java.util.Observable;
import java.util.Random;

public class Board extends Observable {

    private final int size;
    private Player p1, p2, current;
    private int[][] board;

    public Board(int size,String p1Name, String p2Name) {
        this.size = size;
        p1 = Player.PLAYER_ONE;
        p1.setName(p1Name);
        p2 = Player.PLAYER_TWO;
        p2.setName(p2Name);
        current = (new Random().nextDouble() > 0.5) ? p1 : p2;
        board = new int[this.size][this.size];
    }

    public void updateBoard(int x, int y) {
        if (board[y][x] == 0) {
            board[y][x] = current.getDrawable();
            current = (current.equals(p1)) ? p2 : p1;
            setChanged();
            notifyObservers();
            clearChanged();
        }
    }

    public int getDrawableId(int x, int y){
        return board[y][x];
    }
}
