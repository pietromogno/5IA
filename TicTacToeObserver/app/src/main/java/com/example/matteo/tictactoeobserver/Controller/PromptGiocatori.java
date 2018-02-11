package com.example.matteo.tictactoeobserver.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.matteo.tictactoeobserver.Controller.MainActivity;
import com.example.matteo.tictactoeobserver.R;

/**
 * Created by Matteo on 08/02/2018.
 */

public class PromptGiocatori extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prompt_players_activity);

        //gestione click sul primo bottone 1 contro 1
        Button btn=findViewById(R.id.btnGioca1Vs1);
        btn.setOnClickListener((view) -> {
            EditText nomeG1=findViewById(R.id.nomeG1);
            EditText nomeG2=findViewById(R.id.nomeG2);
            Intent i=new Intent(this, MainActivity.class);
            String strNomeG1=nomeG1.getText().toString();
            String strNomeG2=nomeG2.getText().toString();
            i.putExtra("nomeG1",strNomeG1 );
            i.putExtra("nomeG2",strNomeG2 );
            //Toast.makeText(getApplicationContext(), "nomeG1 " +strNomeG1, Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), "nomeG2 " +strNomeG2, Toast.LENGTH_LONG).show();
            startActivity(i);
        });

        //gestione click secondo bottone 1 vs Computer
        Button btn2=findViewById(R.id.btnGiocaVsComputer);
        btn2.setOnClickListener((view) -> {
            Intent i=new Intent(this, MainActivity.class);
            i.putExtra("nomeG2", "COMPUTER");
            startActivity(i);
        });
    }
}
