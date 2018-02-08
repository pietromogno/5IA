package com.pag.tic_tac_toe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

public class Game extends AppCompatActivity implements Observer {

    Board board;
    TableLayout grid;
    int size = 3;
    private ImageView lastClicked;
    private int lastMoveX, lastMoveY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        lastMoveX = 0;
        lastMoveY = 0;
        Bundle bundle = getIntent().getExtras();
        board = new Board(size, bundle.getString("p1Name"), bundle.getString("p2Name"));
        board.addObserver(this);
        grid = findViewById(R.id.tictactoe);
        Toast.makeText(this, "Tocca a " + board.getCurrent().getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void update(Observable observable, Object o) {
        Player current = board.getCurrent();
        lastClicked.setImageDrawable(getDrawable(current.getDrawable()));
        if (board.getTurn() >= 5) {
            if (check(current)) {
                Toast.makeText(this, ((current.equals(Player.PLAYER_ONE)) ? board.getP2() : board.getP1()).getName() + " ha vinto", Toast.LENGTH_LONG).show();
                return;
            }else{
                if(board.getTurn() == 9){
                    Toast.makeText(this, "Pareggio",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
        board.updateCurrent();
        Toast.makeText(this, "Tocca a " + current.getName(), Toast.LENGTH_SHORT).show();
    }

    public void onClick(View v) {
        lastClicked = v.findViewById(R.id.icon);
        switch (v.getId()) {
            case R.id.r0c0:
                lastMoveY = 0;
                lastMoveX = 0;
                break;
            case R.id.r0c1:
                lastMoveY = 0;
                lastMoveX = 1;
                break;
            case R.id.r0c2:
                lastMoveY = 0;
                lastMoveX = 2;
                break;
            case R.id.r1c0:
                lastMoveY = 1;
                lastMoveX = 0;
                break;
            case R.id.r1c1:
                lastMoveY = 1;
                lastMoveX = 1;
                break;
            case R.id.r1c2:
                lastMoveY = 1;
                lastMoveX = 2;
                break;
            case R.id.r2c0:
                lastMoveY = 2;
                lastMoveX = 0;
                break;
            case R.id.r2c1:
                lastMoveY = 2;
                lastMoveX = 1;
                break;
            case R.id.r2c2:
                lastMoveY = 2;
                lastMoveX = 2;
                break;
        }
        board.updateBoard(lastMoveX, lastMoveY);
    }

    public boolean check(Player current) {
        int idCheck = current.getDrawable();
        int[][] values = board.getBoard();
        //check rows
        for (int i = 0; i < size; i++) {
            if (values[i][0] == idCheck)
                return values[i][0] == values[i][1] && values[i][1] == values[i][2];
        }
        //check columns
        for (int i = 0; i < size; i++) {
            if (values[0][i] == idCheck)
                return values[0][i] == values[1][i] && values[1][i] == values[2][i];
        }
        //check diagonals
        if (values[0][0] == idCheck)
            return values[0][0] == values[1][1] && values[1][1] == values[2][2];
        if (values[0][2] == idCheck)
            return values[0][2] == values[1][1] && values[1][1] == values[2][0];
        return false;
    }
}
