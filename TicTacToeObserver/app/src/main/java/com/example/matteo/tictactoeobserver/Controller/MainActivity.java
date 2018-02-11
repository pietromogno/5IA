package com.example.matteo.tictactoeobserver.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.matteo.tictactoeobserver.Model.Cella;
import com.example.matteo.tictactoeobserver.Model.Gioco;
import com.example.matteo.tictactoeobserver.R;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer, View.OnClickListener {

    private int NCELLE = 3;
    private Button[][] mbtn; //matrice dei bottoni
    Gioco g;
    boolean campoValido;
    TextView txt_turno, txt_g1, txt_g2;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String nomeG1, nomeG2;
        nomeG2 = intent.getStringExtra("nomeG2");
        //gestiso il caso di essere stato chiamato per 1 contro 1 o 1 vs Computer
        if (!nomeG2.equals("COMPUTER")) {
            nomeG1 = intent.getStringExtra("nomeG1");
            g = new Gioco(nomeG1, nomeG2);
        } else {
            g = new Gioco();
        }
        //Toast.makeText(getApplicationContext(), "nomeG1 " +nomeG1, Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(), "nomeG2 " +nomeG2, Toast.LENGTH_LONG).show();
        txt_g1 = findViewById(R.id.risultatiG1);
        txt_g2 = findViewById(R.id.risultatiG2);
        txt_turno = findViewById(R.id.turno);

        g.addObserver(this);
        campoValido = true;
        txt_turno.setText("E' il turno di: " + g.g1.getNomeGiocatore());
        findViewById(R.id.btnReset).setOnClickListener(view -> {
            pulisciCampo();
            g.reset();
            campoValido = true;
            txt_turno.setText("E' il turno di: " + ((g.g1.getPunteggio() + g.g2.getPunteggio()) % 2 == 0 ? g.g1.getNomeGiocatore() : g.g2.getNomeGiocatore()));
            g.computerFaiMossa();
        });
    }

    @Override
    public void onClick(View v) {
        if (campoValido) {
            switch (v.getId()) {
                case R.id.btn00:
                    g.clickCella(0, 0);
                    break;
                case R.id.btn01:
                    g.clickCella(0, 1);
                    break;
                case R.id.btn02:
                    g.clickCella(0, 2);
                    break;
                case R.id.btn10:
                    g.clickCella(1, 0);
                    break;
                case R.id.btn11:
                    g.clickCella(1, 1);
                    break;
                case R.id.btn12:
                    g.clickCella(1, 2);
                    break;
                case R.id.btn20:
                    g.clickCella(2, 0);
                    break;
                case R.id.btn21:
                    g.clickCella(2, 1);
                    break;
                case R.id.btn22:
                    g.clickCella(2, 2);
                    break;
            }

        }
    }

    public void pulisciCampo() {
        for (int i = 0; i < NCELLE; i++) {
            for (int j = 0; j < NCELLE; j++) {
                Button btn = findViewById(getResources().getIdentifier("btn" + i + "" + j, "id", getPackageName()));
                btn.setText("");
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) { //dice i risultati gestisce l'aggiornamento
        Cella c = (Cella) arg;
        Button btn = findViewById(getResources().getIdentifier("btn" + c.riga + "" + c.colonna, "id", getPackageName()));
        if (c.giocatore == Gioco.StatoCasellaTris.X) {
            btn.setText("X");
            if (!g.partitaFinita) txt_turno.setText("E il turno di: " + g.g2.getNomeGiocatore());
        } else {
            btn.setText("O");
            if (!g.partitaFinita) txt_turno.setText("E il turno di: " + g.g1.getNomeGiocatore());
        }
        if (g.partitaFinita) {
            campoValido = false;
            Gioco.StatoCasellaTris ris = g.vincitore;
            if (ris == Gioco.StatoCasellaTris.O) {
                Toast.makeText(getApplicationContext(), "Ha vinto " + g.g2.getNomeGiocatore(), Toast.LENGTH_LONG).show();
                g.g2.setPunteggio(g.g2.getPunteggio() + 1);
            } else if (ris == Gioco.StatoCasellaTris.X) {
                Toast.makeText(getApplicationContext(), "Ha vinto " + g.g1.getNomeGiocatore(), Toast.LENGTH_LONG).show();
                g.g1.setPunteggio(g.g1.getPunteggio() + 1);
            } else {
                Toast.makeText(getApplicationContext(), "Pareggio", Toast.LENGTH_LONG).show();
            }
            txt_g1.setText(g.g1.getNomeGiocatore() + "    punteggio = " + g.g1.getPunteggio());
            txt_g2.setText(g.g2.getNomeGiocatore() + "    punteggio = " + g.g2.getPunteggio());
        }
    }


}
