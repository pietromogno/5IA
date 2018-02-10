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

public class Scelta extends AppCompatActivity {

    Button giocatori, cpu;
    Intent cambiaFase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipopartita);
        giocatori = (Button) findViewById(R.id.giocatori);
        cpu = (Button) findViewById(R.id.cpu);
        cpu.setOnClickListener(View -> {
            cambiaFase = new Intent(this, MainCpu.class);
            startActivity(cambiaFase);
        });
        giocatori.setOnClickListener(View -> {
            cambiaFase = new Intent(this, MainActivity.class);
            startActivity(cambiaFase);
        });
    }
}
