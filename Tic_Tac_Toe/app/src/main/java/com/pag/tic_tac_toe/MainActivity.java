package com.pag.tic_tac_toe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
public class MainActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (findViewById(R.id.button_start)).setOnClickListener((view)->{
            Intent i = new Intent(MainActivity.this, Game.class);
            Bundle extras = new Bundle();
            extras.putString("p1Name", ((EditText) findViewById(R.id.txt_p1)).getText().toString());
            extras.putString("p2Name", ((EditText) findViewById(R.id.txt_p2)).getText().toString());
            i.putExtras(extras);
            startActivity(i);
        });
    }

}
