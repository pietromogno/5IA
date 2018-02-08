package com.pag.tic_tac_toe;
import java.util.Observable;
import java.util.Random;

public class Board extends Observable {

    private final int size;
    private int turn;
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
        turn = 0;
    }

    public void updateBoard(int x, int y) {
        if (board[y][x] == 0) {
            board[y][x] = current.getDrawable();
            turn++;
            setChanged();
            notifyObservers();
            clearChanged();
        }
    }

    public void updateCurrent(){
        current = (current.equals(p1)) ? p2 : p1;
    }

    public int getDrawableId(int x, int y){
        return board[y][x];
    }

    public int getTurn() {
        return turn;
    }

    public Player getCurrent(){
        return current;
    }

    public int[][] getBoard() {
        return board;
    }

    public Player getP1() {

        return p1;
    }

    public Player getP2() {
        return p2;
    }
}
