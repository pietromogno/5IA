package com.example.davide.tris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {


    char Player = 'X';//viene impostato "X" come giocatore
    boolean isX = true; // booleano che indica se il giocatore coerrente è X o meno, e gestisce i turno
    Tabella t = new Tabella();
    int[] lastMove = new int[2];
    Button lastClicked;
    boolean playable = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.msg)).setText("Tocca a " + Player);
        t.addObserver(this);
    }


    public void changeTurn() {
        Player = (Player == 'X') ? 'O' : 'X';
        isX = !isX;
    }


    @Override
    public void update(Observable o, Object arg) {
        lastClicked.setText(Player + "");
        playable = t.checkTable();
        if (playable) {
            ((TextView) findViewById(R.id.msg)).setText("Tocca a " + Player);
        } else {
            ((TextView) findViewById(R.id.msg)).setText("Ha vinto " + Player);
        }
        this.changeTurn();

    }

    public void click(View v) {
        lastClicked = (Button) v;
        switch (v.getId()) {
            case R.id.btnTL:
                lastMove[0] = 0;
                lastMove[1] = 0;
                t.update(Player, lastMove, playable);
                break;
            case R.id.btnTC:
                lastMove[0] = 0;
                lastMove[1] = 1;
                t.update(Player, lastMove, playable);
                break;
            case R.id.btnTR:
                lastMove[0] = 0;
                lastMove[1] = 2;
                t.update(Player, lastMove, playable);
                break;
            case R.id.btnML:
                lastMove[0] = 1;
                lastMove[1] = 0;
                t.update(Player, lastMove, playable);
                break;
            case R.id.btnMC:
                lastMove[0] = 1;
                lastMove[1] = 1;
                t.update(Player, lastMove, playable);
                break;
            case R.id.btnMR:
                lastMove[0] = 1;
                lastMove[1] = 2;
                t.update(Player, lastMove, playable);
                break;
            case R.id.btnBL:
                lastMove[0] = 2;
                lastMove[1] = 0;
                t.update(Player, lastMove, playable);
                break;
            case R.id.btnBC:
                lastMove[0] = 2;
                lastMove[1] = 1;
                t.update(Player, lastMove, playable);
                break;
            case R.id.btnBR:
                lastMove[0] = 2;
                lastMove[1] = 2;
                t.update(Player, lastMove, playable);
                break;
            case R.id.btnRestart:
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                break;
        }
    }

}