package com.pag.tic_tac_toe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class GamePvP extends AppCompatActivity implements Observer {

    Board board;
    TableLayout grid;
    int size = 3;
    private ImageView lastClicked;
    private Player current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle bundle = getIntent().getExtras();
        board = new Board(size, bundle.getString("p1Name"), bundle.getString("p2Name"));
        board.addObserver(this);

        grid = findViewById(R.id.tictactoe);

        current  = (new Random().nextBoolean()) ? board.getP1():board.getP2();
        Toast.makeText(this, "Tocca a " + current.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void update(Observable observable, Object o) {
        lastClicked.setImageDrawable(getDrawable(current.getDrawable()));
        if (board.getTurn() > 4) {
            if (check(current)) {
                Toast.makeText(this, current.getName() + " ha vinto", Toast.LENGTH_LONG).show();
                return;
            } else {
                if (board.getTurn() == 9) {
                    Toast.makeText(this, "Pareggio", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
        current = (current.equals(board.getP1()))?board.getP2():board.getP1();
        Toast.makeText(this, "Tocca a " + current.getName(), Toast.LENGTH_SHORT).show();
    }

    public void onClick(View v) {
        lastClicked = v.findViewById(R.id.icon);
        int[] move = new int[2];
        switch (v.getId()) {
            case R.id.r0c0:
                move = new int[]{0, 0};
                break;
            case R.id.r0c1:
                move = new int[]{0, 1};
                break;
            case R.id.r0c2:
                move = new int[]{0, 2};
                break;
            case R.id.r1c0:
                move = new int[]{1, 0};
                break;
            case R.id.r1c1:
                move = new int[]{1, 1};
                break;
            case R.id.r1c2:
                move = new int[]{1, 2};
                break;
            case R.id.r2c0:
                move = new int[]{2, 0};
                break;
            case R.id.r2c1:
                move = new int[]{2, 1};
                break;
            case R.id.r2c2:
                move = new int[]{2, 2};
                break;
        }
        board.updateBoard(move,current.getDrawable());
    }

    public boolean check(Player current) {
        int idCheck = current.getDrawable();
        int[][] values = board.getBoard();
        boolean isFinished = false;
        for(int y = 0; y < size; y++){
            int x = 0;
            while(x < 3 && values[y][x]  == idCheck){ //rows
                x++;
            }
            isFinished = (x == 3);
            x = 0;
            while(x < 3 && values[x][y] == idCheck && !isFinished){
                x++;
            }
            isFinished = (x == 3);
        }
        int dAdBs = 0;
        int dAsBd = 0;
        for(int i = 0 ; i < 3 ; i ++){
            if(values[i][i] == idCheck){
                dAdBs++;
            }
            if(values[i][3-i-1] == idCheck){
                dAsBd++;
            }
        }
        return isFinished || dAdBs == 3 || dAsBd == 3;
    }
}
