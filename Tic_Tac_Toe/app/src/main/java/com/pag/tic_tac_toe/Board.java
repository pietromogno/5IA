package com.pag.tic_tac_toe;
import android.graphics.drawable.Drawable;

import java.util.Observable;
import java.util.Random;

public class Board extends Observable {

    private final int size;
    private int turn;
    private Player p1, p2;
    private int[][] board;

    //constructor for pvp
    Board(int size,String p1Name, String p2Name) {
        this.size = size;
        p1 = Player.PLAYER_ONE;
        p1.setName(p1Name);
        p2 = Player.PLAYER_TWO;
        p2.setName(p2Name);
        board = new int[this.size][this.size];
        turn = 0;
    }

    //constructor for pve
    Board(int size){
        this.size = size;
        board = new int[this.size][this.size];
    }

    int[][] getBoard() {
        return board;
    }

    void updateBoard(int[] move, int drawableId){
        int y = move[0];
        int x = move[1];
        if (board[y][x] == 0) {
            board[x][y] = drawableId;
            turn++;
            setChanged();
            notifyObservers();
            clearChanged();
        }
    }

    int getSize(){return size;}

    int getTurn() {
        return turn;
    }

    Player getP1() {
        return p1;
    }

    Player getP2() {
        return p2;
    }
}
