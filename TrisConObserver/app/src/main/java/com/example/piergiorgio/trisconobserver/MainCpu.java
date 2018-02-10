package com.example.piergiorgio.trisconobserver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Piergiorgio on 10/02/2018.
 */

public class MainCpu extends AppCompatActivity {

    Button inizia, indietro;
    String nome1, nome2 = "CPU";
    EditText viewnome1;
    Intent cambiaFase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincpu);
        viewnome1 = (EditText) findViewById(R.id.nome1);
        inizia = (Button) findViewById(R.id.inizia);
        indietro = (Button) findViewById(R.id.indietro);
        inizia.setOnClickListener(View -> {
            nome1 = viewnome1.getText().toString();
            if(!nome1.equals("")){
                if(!nome1.equals(nome2)){
                    Bundle dati = new Bundle();
                    cambiaFase = new Intent(this, Gioco.class);
                    dati.putString("nome1", nome1.toString());
                    dati.putString("nome2", nome2.toString());
                    cambiaFase.putExtras(dati);
                    startActivity(cambiaFase);
                } else{
                    Toast.makeText(getApplicationContext(), "Inserire un nome diveso",Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Inserire il nome del giocatore",Toast.LENGTH_SHORT).show();
            }
        });
        indietro.setOnClickListener(View -> {
            cambiaFase = new Intent(this, Scelta.class);
            startActivity(cambiaFase);
        });
    }
}
