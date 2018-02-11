package tris.manuel.it.tris;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by manuel on 05/01/2018.
 */

public class Locale extends AppCompatActivity {

    public TextView testo;
    public GridLayout grid;

    public int giocatore1 = 1;
    public int giocatore2 = 2;
    public int turno;

    public Tris tris;


    //definisce la vincita al termine della partita
    public void checkVittoria(){
        if(tris.hasWin()){
            if(turno == 2)
                testo.setText(getResources().getString(R.string.haVinto)+" Giocatore1!");
            else
                testo.setText(getResources().getString(R.string.haVinto)+" Giocatore2!");
            turno = 0;
        } else if(tris.getLiberi() <= 0){
            testo.setText(getResources().getString(R.string.pareggio));
            turno = 0;
        }
    }

    //definisce l'azione che antecede la mossa
    public void bottone(int riga, int colonna){
        if (tris.isLibero(riga, colonna)) {
            if(turno == giocatore1) {
                tris.setTurno(riga, colonna, giocatore1);
                Button b = (Button) grid.getChildAt(grid.getColumnCount() * riga + colonna);
                b.setBackground(getResources().getDrawable(R.drawable.croce));
                testo.setText(getResources().getString(R.string.Turno) + " Giocatore2");
                turno = 2;
                checkVittoria();
            } else if(turno == giocatore2) {
                tris.setTurno(riga, colonna, giocatore2);
                Button b = (Button) grid.getChildAt(grid.getColumnCount() * riga + colonna);
                b.setBackground(getResources().getDrawable(R.drawable.cerchio));
                testo.setText(getResources().getString(R.string.Turno) + " Giocatore1");
                turno = 1;
                checkVittoria();
            }
        } else
            Toast.makeText(this, getResources().getString(R.string.nonLibera), Toast.LENGTH_SHORT).show();
    }

    //procedimento per il tasto di nuova partita
    public void restartButton(View w){
        Intent intent = new Intent(this, this.getClass());
        if(getIntent().getIntExtra("turno", giocatore1) == giocatore1)
            intent.putExtra("turno", giocatore2);
        else intent.putExtra("turno", giocatore1);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tris);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tris = new Tris();
        testo = (TextView) findViewById(R.id.testoComputer);
        grid = (GridLayout) findViewById(R.id.glComputer);
        for(int i=0; i<grid.getRowCount(); i++){
            for(int j=0; j<grid.getColumnCount(); j++){
                Button b = (Button)grid.getChildAt(grid.getColumnCount()*i+j);
                final int finalI = i;
                final int finalJ = j;
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottone(finalI, finalJ);
                    }
                });
            }
        }

        turno = getIntent().getIntExtra("turno", 1);
        if(turno == 1)
            testo.setText(getResources().getString(R.string.Turno) + " Giocatore1");
        else testo.setText(getResources().getString(R.string.Turno) + " Giocatore2");
    }
}
