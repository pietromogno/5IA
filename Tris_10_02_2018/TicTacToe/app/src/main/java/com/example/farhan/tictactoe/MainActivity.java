package com.example.farhan.tictactoe;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    Button[][] mg;
    TextView p1, p2;
    Button reset;
    TrisGame tGame;
    String actualPlayer;
    Context context;
    CharSequence text;
    Toast toast;
    int duration;
    ArrayList<int[]> winningPth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        captureElements();

        tGame = new TrisGame();
        tGame.addObserver(this);
    }

    private void captureElements() {
        //setting the buttons in the matrix
        mg = new Button[3][3];
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                mg[row][column] = findViewById(getResources()
                        .getIdentifier("c" + row + "" + column, "id",
                                getPackageName()));
                mg[row][column].setText("");
                int finalRow = row;
                int finalColumn = column;
                mg[row][column].setOnClickListener((View view) -> {
                    tGame.setMove(finalRow, finalColumn);
                });
            }
        }

        //getting the text views
        p1 = findViewById(R.id.txtP1);
        p2 = findViewById(R.id.txtP2);

        //setting up the reset button
        reset = findViewById(R.id.btnReset);
        reset.setOnClickListener((view) -> {
            tGame.startGame();
            for (Button[] buttons : mg) {
                for (Button button : buttons) {
                    button.setText("");
                    button.setBackgroundColor(Color.rgb(224, 224, 224));
                }
            }
        });
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof TrisGame && o instanceof Message) {
            Message msgTemp = (Message) o;
            switch (msgTemp.getMessageType()) {
                case Message.validMove:
                    this.actualPlayer = msgTemp.getActualPlayer();
                    int[] cell = msgTemp.getCellMoved();
                    mg[cell[0]][cell[1]].setText(actualPlayer);
                    break;
                case Message.invalidMove:
                    context = getApplicationContext();
                    text = msgTemp.getMessage();
                    duration = Toast.LENGTH_SHORT;
                    toast = Toast.makeText(context, text, duration);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                    break;
                case Message.gameWon:
                    this.actualPlayer = msgTemp.getWinner();
                    p1.setText("X -> " + msgTemp.getPlayerScore()[0]);
                    p2.setText("O -> " + msgTemp.getPlayerScore()[1]);
                    this.winningPth = msgTemp.getWinningPth();
                    for (int i = 0; i < winningPth.size(); i++) {
                        mg[winningPth.get(i)[0]][winningPth.get(i)[1]].setBackgroundColor(Color.rgb(204, 255, 204));
                    }
                    context = getApplicationContext();
                    duration = Toast.LENGTH_SHORT;
                    toast = Toast.makeText(context, actualPlayer + " has won !", duration);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                    break;
                case Message.gameOver:
                    context = getApplicationContext();
                    text = msgTemp.getMessage();
                    duration = Toast.LENGTH_SHORT;
                    toast = Toast.makeText(context, text, duration);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                    break;
                case Message.gameDraw:
                    context = getApplicationContext();
                    text = msgTemp.getMessage();
                    duration = Toast.LENGTH_SHORT;
                    toast = Toast.makeText(context, text, duration);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                    break;
            }
        }
    }
}
