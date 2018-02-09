package com.example.farhan.tictactoe;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class TrisGame extends Observable {

    enum cellElement {
        X, O, E
    }

    private final String gameOverMessage = "Press RESET to play again.";
    private final String invalidMoveMessage = "This cell is already occupied.";
    private final String drawGame = "Match ended in a draw.";
    private final String turnIndication = "The first move expect to: X.";
    private final int possibleCombs = 8;

    private cellElement[][] mg;
    private cellElement turn;

    private boolean gameOver, won;

    private int scP1, scP2;
    private ArrayList<int[]> winningPth;

    private ArrayList<OpponentStatus> statuses;

    public TrisGame() {
        setGamePoint();
        startGame();
    }

    public void startGame() {
        mg = new cellElement[3][3];
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                mg[row][column] = cellElement.E;
            }
        }
        turn = cellElement.X;
        gameOver = false;
        won = false;
        winningPth = new ArrayList<>();
        statuses = new ArrayList<>();
        setChanged();
        if (turn == cellElement.X)
            notifyObservers(new Message(Message.invalidMove, turnIndication));
    }

    private void setGamePoint() {
        this.scP1 = 0;
        this.scP2 = 0;
    }

    private boolean isGameOver() {
        if (gameOver) {
            setChanged();
            notifyObservers(new Message(Message.gameOver, gameOverMessage));
        }
        return gameOver;
    }

    private boolean isMgFull() {
        int cnt = 0;
        for (int i = 0; i < mg.length; i++) {
            for (int j = 0; j < mg[i].length; j++) {
                if (mg[i][j] == cellElement.E) cnt++;
            }
        }
        return cnt == 0;
    }

    private boolean validateMove(int row, int column) {
        boolean ris = mg[row][column] == cellElement.E;
        if (!ris) {
            setChanged();
            if (turn == cellElement.X)
                notifyObservers(new Message(Message.invalidMove, invalidMoveMessage));
        } else {
            setChanged();
            notifyObservers(new Message(Message.validMove, getActualPlayer(), new int[]{row, column}));
        }
        return ris;
    }

    public void setMove(int row, int column) {
        if (!isGameOver() && validateMove(row, column)) {
            mg[row][column] = turn;
            hasWon();
        }
    }

    private String getActualPlayer() {
        return (turn == cellElement.X) ? "X" : "O";
    }

    private void hasWon() {
        statuses.clear();
        verticalControl();
        orizontalControl();
        firtDiagonalControl();
        secondDiagonalControl();
        if (isWon()) {
            gameOver = true;
            setMatchScore();
            isGameOver();
            setChanged();
            notifyObservers(new Message(Message.gameWon, getActualPlayer(), getMatchScore(), getWinningPth()));
        } else if (isMgFull()) {
            gameOver = true;
            isGameOver();
            setChanged();
            notifyObservers(new Message(Message.gameDraw, drawGame));
        } else {
            changeTurn();
            if (turn == cellElement.O) {
                int px, py;
                do {
                    int[] pos = getMachineMove();
                    px = pos[0];
                    py = pos[1];
                } while (!validateMove(px, py));
                setMove(px, py);
            }
        }
    }

    private int[] getMachineMove() {
        int temp = 0;
        for (int i = 0; i < statuses.size(); i++) {
            if (statuses.get(i).getSeedNumber() > temp && statuses.get(i).emptyPos.size() > 0) {
                temp = i;
            }
        }
        return statuses.get(temp).getEmptyPos();
    }

    private void setMatchScore() {
        if (getTurn() == cellElement.X) {
            scP1++;
        } else {
            scP2++;
        }
    }

    private cellElement getTurn() {
        return turn;
    }

    private void changeTurn() {
        turn = (getTurn() == cellElement.X) ? cellElement.O : cellElement.X;
    }

    private int[] getMatchScore() {
        return new int[]{scP1, scP2};
    }

    private ArrayList<int[]> getWinningPth() {
        return winningPth;
    }

    private boolean isWon() {
        return won;
    }

    private void verticalControl() {
        if (!isWon()) {
            for (int i = 0; !isGameOver() && i < 3; i++) {
                int cnt = 0;
                int seeds = 0;
                ArrayList<int[]> emptyPos = new ArrayList<>();
                winningPth.clear();
                for (int j = 0; j < 3; j++) {
                    if (mg[j][i] == turn) {
                        cnt++;
                        winningPth.add(new int[]{j, i});
                    } else if (mg[j][i] == cellElement.E) {
                        emptyPos.add(new int[]{j, i});
                    }
                    if (mg[j][i] == cellElement.X) {
                        seeds++;
                    }
                }
                statuses.add(new OpponentStatus(seeds, emptyPos));
                won = gameOver = cnt == 3;
            }
        }
    }

    private void orizontalControl() {
        if (!isWon()) {
            for (int i = 0; !isGameOver() && i < 3; i++) {
                int cnt = 0;
                int seeds = 0;
                ArrayList<int[]> emptyPos = new ArrayList<>();
                winningPth.clear();
                for (int j = 0; j < 3; j++) {
                    if (mg[i][j] == turn) {
                        cnt++;
                        winningPth.add(new int[]{i, j});
                    } else if (mg[i][j] == cellElement.E) {
                        emptyPos.add(new int[]{i, j});
                    }
                    if (mg[i][j] == cellElement.X) {
                        seeds++;
                    }
                }
                statuses.add(new OpponentStatus(seeds, emptyPos));
                won = gameOver = cnt == 3;
            }
        }
    }


    private void firtDiagonalControl() {
        if (!isWon()) {
            winningPth.clear();
            int cnt = 0;
            int seeds = 0;
            ArrayList<int[]> emptyPos = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                if (mg[i][i] == turn) {
                    cnt++;
                    winningPth.add(new int[]{i, i});
                } else if (mg[i][i] == cellElement.E) {
                    emptyPos.add(new int[]{i, 2 - i});
                }
                if (mg[i][i] == cellElement.X) {
                    seeds++;
                }
            }
            statuses.add(new OpponentStatus(seeds, emptyPos));
            won = gameOver = cnt == 3;
        }
    }

    private void secondDiagonalControl() {
        if (!isWon()) {
            winningPth.clear();
            int cnt = 0;
            int seeds = 0;
            ArrayList<int[]> emptyPos = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                if (mg[i][2 - i] == turn) {
                    cnt++;
                    winningPth.add(new int[]{i, 2 - i});
                } else if (mg[i][2 - i] == cellElement.E) {
                    emptyPos.add(new int[]{i, 2 - i});
                }
                if (mg[i][2 - i] == cellElement.X) {
                    seeds++;
                }
            }
            statuses.add(new OpponentStatus(seeds, emptyPos));
            won = gameOver = cnt == 3;
        }
    }

    private class OpponentStatus {
        private int seeds;
        private ArrayList<int[]> emptyPos;

        public OpponentStatus(int seeds, ArrayList<int[]> emptyPos) {
            this.seeds = seeds;
            this.emptyPos = emptyPos;
        }

        public int getSeedNumber() {
            return seeds;
        }

        public int[] getEmptyPos() {
            return emptyPos.get(0);
        }

    }
}
