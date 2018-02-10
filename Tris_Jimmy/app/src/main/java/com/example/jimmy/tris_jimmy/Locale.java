package com.example.jimmy.tris_jimmy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jimmy on 06/02/2018.
 */

public class Locale extends AppCompatActivity {
    public TextView testo;
    public GridLayout gridLayout;

    public int giocatore1 = 1, giocatore2 = 2;
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
        for(int i=0; i<gridLayout.getRowCount(); i++){
            for(int j=0; j<gridLayout.getColumnCount(); j++){
                Button b = (Button)gridLayout.getChildAt(gridLayout.getColumnCount()*i+j);
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
        if(turno == 1)
            testo.setText("Turno: Giocatore 1");
        else testo.setText("Turno: Giocatore 2");
    }

    public void checkVittoria(){
        if(tris.hasWin()){
            if(turno == 2)
                testo.setText("Hai vinto: Giocatore 1!");
            else
                testo.setText("Hai vinto: Giocatore 2!");
            turno = 0;
        } else if(tris.getLiberi() <= 0){
            testo.setText("Pareggio");
            turno = 0;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void bottone(int row, int column){
        if (tris.isLibero(row, column)) {
            if(turno == giocatore1) {
                tris.setTurno(row, column, giocatore1);
                Button b = (Button) gridLayout.getChildAt(gridLayout.getColumnCount() * row + column);
                b.setBackground(getResources().getDrawable(R.drawable.x));
                testo.setText("Turno: Giocatore 2");
                turno = 2;
                checkVittoria();
            } else if(turno == giocatore2) {
                tris.setTurno(row, column, giocatore2);
                Button b = (Button) gridLayout.getChildAt(gridLayout.getColumnCount() * row + column);
                b.setBackground(getResources().getDrawable(R.drawable.cerchio));
                testo.setText("Turno: Giocatore 2");
                turno = 1;
                checkVittoria();
            }
        } else
            Toast.makeText(this, getResources().getString(R.string.nonLibera), Toast.LENGTH_SHORT).show();
    }

    public void restartButton(View w){
        Intent intent = new Intent(this, this.getClass());
        if(getIntent().getIntExtra("turno", giocatore1) == giocatore1)
            intent.putExtra("turno", giocatore2);
        else intent.putExtra("turno", giocatore1);
        startActivity(intent);
        finish();
    }
}
