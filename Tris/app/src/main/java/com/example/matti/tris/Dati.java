package com.example.matti.tris;


import java.util.Observable;
import java.util.Observer;

/**
 * Created by MATTI on 04/02/2018.
 */

public class Dati implements Observer {

    private String[][] mosse;
    private boolean turnoX;
    private int click,corX,corY;
    private String playerX, playerO,playMode;

    public Dati(String player1, String player2, String playMode) {
        mosse = new String[][]{{"n", "n", "n"}, {"n", "n", "n"}, {"n", "n", "n"}};
        turnoX = true;
        click = 9;
        playerX = player1;
        playerO = player2;
        this.playMode=playMode;
    }

    @Override
    public void update(Observable observable, Object o) {
        MainActivity.Osservatore my = (MainActivity.Osservatore) (observable);
        click--;
        corX= my.pos.charAt(0) - 48;
        corY = my.pos.charAt(1) - 48;
        MainActivity.table[corX][corY].setClickable(false);
        switch(playMode){
            case "manual":
                if(turnoX){
                    MainActivity.table[corX][corY].setBackgroundResource(R.drawable.ex);
                    mosse[corX][corY] = "x";
                }else{
                    MainActivity.table[corX][corY].setBackgroundResource(R.drawable.circle);
                     mosse[corX][corY] = "o";
                }
                if(playerVictory("x")){
                    MainActivity.dialogVictory(playerX);
                }else if(playerVictory("o")){
                    MainActivity.dialogVictory(playerO);
                }
                break;
            case "easy":
                MainActivity.table[corX][corY].setBackgroundResource(R.drawable.ex);
                mosse[corX][corY] = "x";
                if(playerVictory("x")){
                    MainActivity.dialogVictory(playerX);
                }else if(playerVictory("o")){
                    MainActivity.dialogVictory(playerO);
                }else {
                    dumbPlay();
                    if (playerVictory("o")) {
                        MainActivity.dialogVictory(playerO);
                    }
                }
                break;
            case "difficult":
                MainActivity.dialogVictory("\nModalità non ancora implementata");
                break;
        }
        turnoX = !turnoX;
    }

    private void dumbPlay() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mosse[i][j].equals("n")) {
                    mosse[i][j] = "o";
                    click--;
                    MainActivity.table[i][j].setClickable(false);
                    MainActivity.table[i][j].setBackgroundResource(R.drawable.circle);
                    return;
                }
            }
        }
    }

    private boolean playerVictory(String player) {
        if (click == 0) {
            MainActivity.dialogVictory("");
        } else {
            if (mosse[0][0].equals(player) && mosse[0][1].equals(player) && mosse[0][2].equals(player)) {
                return true;
            }
            if (mosse[1][0].equals(player) && mosse[1][1].equals(player) && mosse[1][2].equals(player)) {
                return true;
            }
            if (mosse[2][0].equals(player) && mosse[2][1].equals(player) && mosse[2][2].equals(player)) {
                return true;
            }
            if (mosse[0][0].equals(player) && mosse[1][0].equals(player) && mosse[2][0].equals(player)) {
                return true;
            }
            if (mosse[0][1].equals(player) && mosse[1][1].equals(player) && mosse[2][1].equals(player)) {
                return true;
            }
            if (mosse[0][2].equals(player) && mosse[1][2].equals(player) && mosse[2][2].equals(player)) {
                return true;
            }
            if (mosse[0][0].equals(player) && mosse[1][1].equals(player) && mosse[2][2].equals(player)) {
                return true;
            }
            if (mosse[0][2].equals(player) && mosse[1][1].equals(player) && mosse[2][0].equals(player)) {
                return true;
            }
        }
        return false;
    }
}
