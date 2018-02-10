package com.example.piergiorgio.trisconobserver;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Piergiorgio Favaretto
 */

public class Gioco extends AppCompatActivity implements Observer {

    ImageView r1c1, r1c2,r1c3;
    ImageView r2c1, r2c2, r2c3;
    ImageView r3c1, r3c2, r3c3;
    TextView giocatore1, giocatore2;
    TextView punteggio1, punteggio2;
    Button indietro, resetta;
    boolean conCpu;
    String nome1, nome2;
    Tris t;
    ImageView [][] caselle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioco);
        nome1 = getIntent().getStringExtra("nome1");
        nome2 = getIntent().getStringExtra("nome2");
        if(nome2.equals("CPU")){
            conCpu = true;

        }
        this.indietro = (Button) findViewById(R.id.indietro);
        this.resetta = (Button) findViewById(R.id.resetta);
        giocatore1 = (TextView) findViewById(R.id.nome1);
        giocatore2 = (TextView) findViewById(R.id.nome2);
        punteggio1 = (TextView) findViewById(R.id.punteggio1);
        punteggio2 = (TextView) findViewById(R.id.punteggio2);
        giocatore1.setText(nome1);
        giocatore2.setText(nome2);
        punteggio1.setText(0 + "");
        punteggio2.setText(0 + "");
        this.r1c1 = (ImageView) findViewById(R.id.r1c1);
        this.r1c2 = (ImageView) findViewById(R.id.r1c2);
        this.r1c3 = (ImageView) findViewById(R.id.r1c3);
        this.r2c1 = (ImageView) findViewById(R.id.r2c1);
        this.r2c2 = (ImageView) findViewById(R.id.r2c2);
        this.r2c3 = (ImageView) findViewById(R.id.r2c3);
        this.r3c1 = (ImageView) findViewById(R.id.r3c1);
        this.r3c2 = (ImageView) findViewById(R.id.r3c2);
        this.r3c3 = (ImageView) findViewById(R.id.r3c3);
        caselle = new ImageView[][] {{r1c1, r1c2,r1c3},{r2c1, r2c2, r2c3},{r3c1, r3c2, r3c3}};
        t = new Tris(conCpu);
        t.addObserver(this);
        settaListener();
    }

    private void settaListener(){
        r1c1.setOnClickListener(View -> {
                t.azioneListener(r1c1, 0, 0);
        });
        r1c2.setOnClickListener(View -> {
                t.azioneListener(r1c2, 0, 1);
        });
        r1c3.setOnClickListener(View -> {
                t.azioneListener(r1c3, 0, 2);
        });
        r2c1.setOnClickListener(View -> {
                t.azioneListener(r2c1, 1, 0);
        });
        r2c2.setOnClickListener(View -> {
                t.azioneListener(r2c2, 1, 1);
        });
        r2c3.setOnClickListener(View -> {
                t.azioneListener(r2c3, 1, 2);
        });
        r3c1.setOnClickListener(View -> {
                t.azioneListener(r3c1, 2, 0);
        });
        r3c2.setOnClickListener(View -> {
            t.azioneListener(r3c2, 2, 1);
        });
        r3c3.setOnClickListener(View -> {
            t.azioneListener(r3c3, 2, 2);
        });
        indietro.setOnClickListener(View -> {
            Intent cambiaFase;
            if(!nome2.equals("CPU")){
                cambiaFase= new Intent(this, MainActivity.class);
            } else {
                cambiaFase = new Intent(this, MainCpu.class);
            }
            startActivity(cambiaFase);
        });
        resetta.setOnClickListener(View -> {
            resettaGrafica();
            t.resetta(1);
        });
    }

    private void settaImmagine(ImageView img, int turno){
        if(turno == 1){
            img.setBackgroundResource(R.drawable.ic_croce);
        } else if(turno == 2){
            img.setBackgroundResource(R.drawable.ic_cerchio);
        }
    }

    private void resettaGrafica(){
        r1c1.setBackgroundResource(R.drawable.vuoto);
        r1c2.setBackgroundResource(R.drawable.vuoto);
        r1c3.setBackgroundResource(R.drawable.vuoto);
        r2c1.setBackgroundResource(R.drawable.vuoto);
        r2c2.setBackgroundResource(R.drawable.vuoto);
        r2c3.setBackgroundResource(R.drawable.vuoto);
        r3c1.setBackgroundResource(R.drawable.vuoto);
        r3c2.setBackgroundResource(R.drawable.vuoto);
        r3c3.setBackgroundResource(R.drawable.vuoto);
    }

    @Override
    public void update(Observable o, Object arg) {
        MioOggetto m = (MioOggetto) arg;
        if(m.getVincitore() == 0){
                settaImmagine(m.getImg(), m.getTurno());
        }else if(m.getVincitore() == 4){ //turno conseguito dalla CPU
            new CountDownTimer(1500, 1000) {
                public void onTick(long millisUntilFinished) {}
                public void onFinish() {t.azioneListener(caselle[t.getRiga()][t.getColonna()], t.getRiga(), t.getColonna());}
            }.start();
        } else if(m.getVincitore() == 3){
            new CountDownTimer(1500, 1000) {
                public void onTick(long millisUntilFinished) {}
                public void onFinish() {resettaGrafica();}
            }.start();
            Toast.makeText(getApplicationContext(), "Nessuno dei giocatori ha vinto :(", Toast.LENGTH_SHORT).show();
        } else if(m.getVincitore() == 1){
            punteggio1.setText(t.getPunti1() + "");
            new CountDownTimer(1500, 1000) {
                public void onTick(long millisUntilFinished) {}
                public void onFinish() {resettaGrafica();}
            }.start();
            Toast.makeText(getApplicationContext(), "Ha vinto il giocatore " + nome1, Toast.LENGTH_SHORT).show();
        } else {
            punteggio2.setText(t.getPunti2() + "");
            new CountDownTimer(1500, 1000) {
                public void onTick(long millisUntilFinished) {}
                public void onFinish() {resettaGrafica();}
            }.start();
            Toast.makeText(getApplicationContext(), "Ha vinto il giocatore " + nome2, Toast.LENGTH_SHORT).show();
        }
    }

}