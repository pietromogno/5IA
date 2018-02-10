package com.pag.tic_tac_toe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity  {

    Class gameMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (findViewById(R.id.button_start)).setOnClickListener((view)->{
            Intent i = new Intent(MainActivity.this, gameMode);
            Bundle extras = new Bundle();
            if(gameMode.equals(GamePvP.class)){
                extras.putString("p1Name", ((EditText) findViewById(R.id.txt_p1)).getText().toString());
                extras.putString("p2Name", ((EditText) findViewById(R.id.txt_p2)).getText().toString());
            }else{
                extras.putBoolean("isHeuristic",((Spinner)findViewById(R.id.spinner_modes)).getSelectedItemPosition() == 1);
            }
            i.putExtras(extras);
            startActivity(i);
        });
        getLayoutInflater().inflate(R.layout.pvp,findViewById(R.id.frame));
        ((RadioGroup)findViewById(R.id.radioGroup)).check(R.id.pvp);
        gameMode = GamePvP.class;
    }

    public void onClick(View v){
        FrameLayout frame = findViewById(R.id.frame);
        switch (v.getId()){
            case R.id.pvp:
                if(((RadioButton)v).isChecked()){
                    frame.removeAllViewsInLayout();
                    getLayoutInflater().inflate(R.layout.pvp,frame);
                    gameMode = GamePvP.class;
                }
                break;
            case R.id.pve:
                if(((RadioButton)v).isChecked()){
                    frame.removeAllViewsInLayout();
                    getLayoutInflater().inflate(R.layout.pve,frame);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            this, R.array.aiOptions, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    ((Spinner)findViewById(R.id.spinner_modes)).setAdapter(adapter);
                    gameMode = GamePvE.class;
                }
                break;
        }
    }
}
