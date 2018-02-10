package com.example.matti.tris;


import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * Created by MATTI on 04/02/2018.
 */

public class Dati implements Observer {

    private String[][] mosse;
    private boolean turnoX;
    private int click, corX, corY;
    private String playerX, playerO, playMode;

    public Dati(String player1, String player2, String playMode) {
        mosse = new String[][]{{"n", "n", "n"}, {"n", "n", "n"}, {"n", "n", "n"}};
        turnoX = playMode.equals("manual") || Math.random() * 10 > 5;
        click = 9;
        if (!turnoX) {
            int x = new Random().nextInt(3);
            int y = new Random().nextInt(3);
            setCircle(x, y);
            click--;
        }
        playerX = player1;
        playerO = player2;
        this.playMode = playMode;
    }

    @Override
    public void update(Observable observable, Object o) {
        MainActivity.Osservatore my = (MainActivity.Osservatore) (observable);
        click--;
        corX = my.pos.charAt(0) - 48;
        corY = my.pos.charAt(1) - 48;
        MainActivity.table[corX][corY].setClickable(false);
        switch (playMode) {
            case "manual":
                if (turnoX) {
                    MainActivity.table[corX][corY].setBackgroundResource(R.drawable.ex);
                    mosse[corX][corY] = "x";
                } else {
                    MainActivity.table[corX][corY].setBackgroundResource(R.drawable.circle);
                    mosse[corX][corY] = "o";
                }
                if (playerVictory("x")) {
                    MainActivity.dialogVictory(playerX);
                } else if (playerVictory("o")) {
                    MainActivity.dialogVictory(playerO);
                }
                break;
            case "easy":
                click--;
                MainActivity.table[corX][corY].setBackgroundResource(R.drawable.ex);
                mosse[corX][corY] = "x";
                if (playerVictory("x")) {
                    MainActivity.dialogVictory(playerX);
                } else if (playerVictory("o")) {
                    MainActivity.dialogVictory(playerO);
                } else {
                    dumbPlay();
                    if (playerVictory("o")) {
                        MainActivity.dialogVictory(playerO);
                    }
                }
                break;
            case "medium":
                click--;
                MainActivity.table[corX][corY].setBackgroundResource(R.drawable.ex);
                mosse[corX][corY] = "x";
                if (playerVictory("x")) {
                    MainActivity.dialogVictory(playerX);
                } else if (playerVictory("o")) {
                    MainActivity.dialogVictory(playerO);
                } else {
                    mediumPlay("x");
                    if (playerVictory("o")) {
                        MainActivity.dialogVictory(playerO);
                    }
                }
                break;
            case "difficult":
                click--;
                MainActivity.table[corX][corY].setBackgroundResource(R.drawable.ex);
                mosse[corX][corY] = "x";
                if (playerVictory("x")) {
                    MainActivity.dialogVictory(playerX);
                } else if (playerVictory("o")) {
                    MainActivity.dialogVictory(playerO);
                } else {
                    aiPlay();
                    if (playerVictory("o")) {
                        MainActivity.dialogVictory(playerO);
                    }
                }
                break;
            case "extreme":
                click--;
                MainActivity.table[corX][corY].setBackgroundResource(R.drawable.ex);
                mosse[corX][corY] = "x";
                if (playerVictory("x")) {
                    MainActivity.dialogVictory(playerX);
                } else if (playerVictory("o")) {
                    MainActivity.dialogVictory(playerO);
                } else {
                    aiPlayExtreme();
                    if (playerVictory("o")) {
                        MainActivity.dialogVictory(playerO);
                    }
                }
                break;
        }
        turnoX = !turnoX;
    }

    private void dumbPlay() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mosse[i][j].equals("n")) {
                    mosse[i][j] = "o";
                    MainActivity.table[i][j].setClickable(false);
                    MainActivity.table[i][j].setBackgroundResource(R.drawable.circle);
                    return;
                }
            }
        }
    }

    private void aiPlay() {
        if (click > 0) {
            for (int i = 0; i < 3; i++) {
                if (checkRow(i, "x")) {
                    return;
                }
            }
            if (checkCol("x")) {
                return;
            } else {
                dumbPlay();
            }
        } else {
            MainActivity.dialogVictory("");
        }
    }

    private void aiPlayExtreme() {
        Log.d("click", click + "");
        if (click > 0) {
            for (int i = 0; i < 3; i++) {
                if (checkRow(i, "x")) {
                    return;
                }
            }
            if (checkCol("x")) {
                return;
            } else {
                for (int i = 0; i < 3; i++) {
                    if (checkRow(i, "o")) {
                        return;
                    }
                }
                if (checkCol("o")) {
                    return;
                } else {
                    dumbPlay();
                }
            }
        } else {
            MainActivity.dialogVictory("");
        }
    }

    private boolean checkRow(int i, String player) {
        if (mosse[i][0].equals(player) && mosse[i][1].equals(player) && mosse[i][2].equals("n")) {
            setCircle(i, 2);
            return true;
        } else if (mosse[i][0].equals(player) && mosse[i][2].equals(player) && mosse[i][1].equals("n")) {
            setCircle(i, 1);
            return true;
        } else if (mosse[i][1].equals(player) && mosse[i][2].equals(player) && mosse[i][0].equals("n")) {
            setCircle(i, 0);
            return true;
        } else if (mosse[0][i].equals(player) && mosse[1][i].equals(player) && mosse[2][i].equals("n")) {
            setCircle(2, i);
            return true;
        } else if (mosse[0][i].equals(player) && mosse[2][i].equals(player) && mosse[1][i].equals("n")) {
            setCircle(1, i);
            return true;
        } else if (mosse[1][i].equals(player) && mosse[2][i].equals(player) && mosse[0][i].equals("n")) {
            setCircle(0, i);
            return true;
        } else {
            return false;
        }
    }

    private boolean checkCol(String player) {
        if (mosse[0][0].equals(player) && mosse[1][1].equals(player) && mosse[2][2].equals("n")) {
            setCircle(2, 2);
            return true;
        } else if (mosse[0][0].equals(player) && mosse[2][2].equals(player) && mosse[1][1].equals("n")) {
            setCircle(1, 1);
            return true;
        } else if (mosse[1][1].equals(player) && mosse[2][2].equals(player) && mosse[0][0].equals("n")) {
            setCircle(0, 0);
            return true;
        } else if (mosse[0][2].equals(player) && mosse[1][1].equals(player) && mosse[2][0].equals("n")) {
            setCircle(2, 0);
            return true;
        } else if (mosse[0][2].equals(player) && mosse[2][0].equals(player) && mosse[1][1].equals("n")) {
            setCircle(1, 1);
            return true;
        } else if (mosse[1][1].equals(player) && mosse[2][0].equals(player) && mosse[0][2].equals("n")) {
            setCircle(0, 2);
            return true;
        } else {
            return false;
        }
    }

    private void setCircle(int x, int y) {
        mosse[x][y] = "o";
        MainActivity.table[x][y].setClickable(false);
        MainActivity.table[x][y].setBackgroundResource(R.drawable.circle);
    }

    private void mediumPlay(String player) {
        if (click == 0) {
            MainActivity.dialogVictory("");
        } else {
            boolean hasDoMove = false;
            if (!hasDoMove && mosse[0][0].equals(player) && mosse[0][1].equals(player) && mosse[0][2].equals("n")) {
                setCircle(0, 2);
                hasDoMove = true;
            }
            if (!hasDoMove && mosse[1][0].equals(player) && mosse[1][1].equals(player) && mosse[1][2].equals("n")) {
                setCircle(1, 2);
                hasDoMove = true;
            }
            if (!hasDoMove && mosse[2][0].equals(player) && mosse[2][1].equals(player) && mosse[2][2].equals("n")) {
                setCircle(2, 2);
                hasDoMove = true;
            }
            if (!hasDoMove && mosse[0][0].equals(player) && mosse[1][0].equals(player) && mosse[2][0].equals("n")) {
                setCircle(2, 0);
                hasDoMove = true;
            }
            if (!hasDoMove && mosse[0][1].equals(player) && mosse[1][1].equals(player) && mosse[2][1].equals("n")) {
                setCircle(2, 1);
                hasDoMove = true;
            }
            if (!hasDoMove && mosse[0][2].equals(player) && mosse[1][2].equals(player) && mosse[2][2].equals("n")) {
                setCircle(2, 2);
                hasDoMove = true;
            }
            if (!hasDoMove && mosse[0][0].equals(player) && mosse[1][1].equals(player) && mosse[2][2].equals("n")) {
                setCircle(2, 2);
                hasDoMove = true;
            }
            if (!hasDoMove && mosse[0][2].equals(player) && mosse[1][1].equals(player) && mosse[2][0].equals("n")) {
                setCircle(2, 0);
                hasDoMove = true;
            }
            if (!hasDoMove) {
                dumbPlay();
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
