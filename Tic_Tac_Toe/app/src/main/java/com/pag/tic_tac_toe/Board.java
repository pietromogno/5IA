package com.pag.tic_tac_toe;

import java.util.Arrays;
import java.util.Observable;
import java.util.Random;

public class Board extends Observable {

    private final int size = 3;
    Player p1, p2, current;
    int[][] board;

    public Board(String p1Name, String p2Name) {
        p1 = Player.PLAYER_ONE;
        p1.setName(p1Name);
        p2 = Player.PLAYER_TWO;
        p2.setName(p2Name);
        current = (new Random().nextDouble() > 0.5) ? p1 : p2;
        board = new int[this.size][this.size];
    }

    public boolean update(int x, int y) {
        if (board[x][y] == 0) {
            board[x][y] = current.getDrawable();
            current = (current.equals(p1)) ? p2 : p1;
            return true;
        } else {
            return false; //ou mona hai già cliccato la dio*
        }
    }
}
