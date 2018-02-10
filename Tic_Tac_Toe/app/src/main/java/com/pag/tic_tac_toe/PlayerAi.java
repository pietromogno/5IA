package com.pag.tic_tac_toe;

public class PlayerAi {
    private Board board;
    private boolean myTurn;
    private Player me;

    PlayerAi(Board board) {
        this.board=board;
        me = Player.PLAYER_TWO;
        me.setName("Ai-chan");
    }

    public boolean isAiTurn() {
        return myTurn;
    }

    public void setAiTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public void nextRandomMove(){
        int[][] preferredMoves = {
                {1, 1}, {0, 0}, {0, 2}, {2, 0}, {2, 2},
                {0, 1}, {1, 0}, {1, 2}, {2, 1}};
        int[][]boardCells = board.getBoard();
        for(int[]move:preferredMoves){
            if(boardCells[move[0]][move[1]] == Player.PLAYER_NONE.getDrawable()) board.updateBoard(move,me.getDrawable());
        }
    }

    //TODO implement heuristic method to calculate next best move
    public void nextBestMove(){
        throw new UnsupportedOperationException(String.valueOf(R.string.unsupportedException));
    }

    public int getDrawable(){
        return me.getDrawable();
    }
}
