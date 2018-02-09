package com.example.farhan.tictactoe;

import java.util.ArrayList;

public class Message {

    public static final int validMove = 0, invalidMove = 1, gameOver = 2, gameWon = 3, gameDraw = 4;
    private int messageType;
    private String message, winner, actualPlayer;
    private int[] playerScore, cellMoved;
    private ArrayList<int[]> winningPth;

    public Message(int messageType, String message) {//used for invalidMove, gameOver and gameDraw
        this.messageType = messageType;
        this.message = message;
    }

    public Message(int messageType, String actualPlayer, int[] cellMoved) {//used for validMove
        this.messageType = messageType;
        this.actualPlayer = actualPlayer;
        this.cellMoved = cellMoved;
    }

    public Message(int messageType, String winner, int[] playerScore, ArrayList<int[]> winningPth) {//used for gameWon
        this.messageType = messageType;
        this.winner = winner;
        this.playerScore = playerScore;
        this.winningPth = winningPth;
    }

    public int getMessageType() {
        return messageType;
    }

    public String getMessage() {
        return message;
    }

    public String getWinner() {
        return winner;
    }

    public String getActualPlayer() {
        return actualPlayer;
    }

    public int[] getPlayerScore() {
        return playerScore;
    }

    public int[] getCellMoved() {
        return cellMoved;
    }

    public ArrayList<int[]> getWinningPth() {
        return winningPth;
    }
}