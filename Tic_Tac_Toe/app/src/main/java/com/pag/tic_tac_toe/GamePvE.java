package com.pag.tic_tac_toe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class GamePvE extends AppCompatActivity implements Observer {

    private Board board;
    private int size = 3;
    private boolean isHeuristic;
    private PlayerAi Ai;
    private Player player;
    private ImageView lastClicked;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle bundle = getIntent().getExtras();
        isHeuristic = bundle.getBoolean("isHeuristic");

        board = new Board(size);
        Ai= new PlayerAi(board);
        Ai.setAiTurn(new Random().nextBoolean());

        player = Player.PLAYER_ONE;
    }

    @Override
    public void update(Observable observable, Object o) {
        boolean isAiTurn = Ai.isAiTurn();
        lastClicked.setImageDrawable(getDrawable(board.));
        if(board.getTurn()>4){

        }
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
        board.updateBoard(move,player.getDrawable());
    }

    public boolean check(int drawableId){
        return false;
    }
}
