package com.example.simone.tris;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    Button PvP, PvPc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PvP = (Button) findViewById(R.id.PvP);
        PvPc = (Button) findViewById(R.id.PvPc);

        PvP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, PersonaPersona.class);
                startActivity(in);
            }
        });

        PvPc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, PersonaComputer.class);
                startActivity(in);
            }
        });
    }
}
