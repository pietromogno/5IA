package com.example.jimmy.tris_jimmy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.drm.DrmStore;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by jimmy on 06/02/2018.
 */

public class Computer extends AppCompatActivity{

    public TextView testo;
    public GridLayout gridLayout;

    public int giocatore = 1, computer = 2;
    public int turno;

    public Tris tris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tris);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tris = new Tris();

        testo = (TextView) findViewById(R.id.testoComputer);
        gridLayout = (GridLayout) findViewById(R.id.glComputer);
        for (int i = 0; i < gridLayout.getRowCount(); i++) {
            for (int j = 0; j < gridLayout.getColumnCount(); j++) {
                Button b = (Button) gridLayout.getChildAt(gridLayout.getColumnCount() * i + j);
                final int finalI = i;
                final int finalJ = j;
                b.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        bottone(finalI, finalJ);
                    }
                });
            }
        }

        turno = getIntent().getIntExtra("turno", 1);
        if (turno == 1)
            testo.setText("Turno: Player1");
        else testo.setText("Turno: Computer");
        if (turno == computer)
            mossaComputer();
    }

    public void checkVittoria() {
        if (tris.hasWin()) {
            if (turno == 2)
                testo.setText("Hai vinto: Player1!");
            else
                testo.setText("Hai vinto: Computer!");
            turno = 0;
        } else if (tris.getLiberi() <= 0) {
            testo.setText("Pareggio");
            turno = 0;
        } else if (turno == 2)
            mossaComputer();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void bottone(int row, int column) {
        if (turno == 1) {
            if (tris.isLibero(row, column)) {
                tris.setTurno(row, column, giocatore);
                Button b = (Button) gridLayout.getChildAt(gridLayout.getColumnCount() * row + column);



                b.setBackground(getResources().getDrawable(R.drawable.x));
                testo.setText("Turno: Computer");
                turno = 2;
                checkVittoria();
            } else
                Toast.makeText(this, getResources().getString(R.string.nonLibera), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, getResources().getString(R.string.nonTurno), Toast.LENGTH_SHORT).show();
    }

    public void mossaComputer() {
        int[][] matrice = tris.getMatrice();
        int posRandom = (int) (Math.random() * tris.getLiberi());
        for (int i = 0, counterPos = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                if (tris.isLibero(i, j)) {
                    if (counterPos == posRandom) {
                        tris.setTurno(i, j, computer);
                        final int finalI = i;
                        final int finalJ = j;
                        new Handler().postDelayed(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            public void run() {
                                Button b = (Button) gridLayout.getChildAt(gridLayout.getColumnCount() * finalI + finalJ);
                                b.setBackground(getResources().getDrawable(R.drawable.cerchio));
                                testo.setText("Turno: Player1");
                                turno = 1;
                                checkVittoria();
                            }
                        }, 750);
                        return;
                    }
                    counterPos++;
                }
            }
        }
    }

    public void restartButton(View w) {
        Intent intent = new Intent(this, this.getClass());
        if (getIntent().getIntExtra("turno", giocatore) == giocatore)
            intent.putExtra("turno", computer);
        else intent.putExtra("turno", giocatore);
        startActivity(intent);
        finish();
    }
}
