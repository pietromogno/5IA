package com.pag.tic_tac_toe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Observable;
import java.util.Observer;

public class Game extends AppCompatActivity implements Observer {

    Board board;
    TableLayout grid;
    int size = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle bundle = getIntent().getExtras();
        board = new Board(size, bundle.getString("p1Name"), bundle.getString("p2Name"));
        board.addObserver(this);
        grid = findViewById(R.id.tictactoe);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof Board) {
            for (int y = 0; y < size; y++) {
                TableRow tr = (TableRow) grid.getChildAt(y);
                for (int x = 0; x < size; x++) {
                    View gc = tr.getChildAt(x);
                    int drawableID = ((Board) observable).getDrawableId(x, y);
                    if (drawableID != 0)
                        ((ImageView) gc.findViewById(R.id.icon)).setImageDrawable(getDrawable(drawableID));
                }
            }
        }
    }

    public void onClick(View v) {
        int x = 0;
        int y = 0;
        switch (v.getId()) {
            case R.id.r0c0:
                y = 0;
                x = 0;
                break;
            case R.id.r0c1:
                y = 0;
                x = 1;
                break;
            case R.id.r0c2:
                y = 0;
                x = 2;
                break;
            case R.id.r1c0:
                y = 1;
                x = 0;
                break;
            case R.id.r1c1:
                y = 1;
                x = 1;
                break;
            case R.id.r1c2:
                y = 1;
                x = 2;
                break;
            case R.id.r2c0:
                y = 2;
                x = 0;
                break;
            case R.id.r2c1:
                y = 2;
                x = 1;
                break;
            case R.id.r2c2:
                y = 2;
                x = 2;
                break;
        }
        board.updateBoard(x, y);
    }
}
