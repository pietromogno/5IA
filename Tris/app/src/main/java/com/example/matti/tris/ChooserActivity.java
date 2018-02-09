package com.example.matti.tris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ChooserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);
        final LinearLayout startGame = findViewById(R.id.startLayout);
        startGame.setVisibility(View.INVISIBLE);
        final Button manual = findViewById(R.id.manualBtn);
        final EditText giocatore2 = findViewById(R.id.player2);
        Button easy = findViewById(R.id.easyBtn);
        Button difficult = findViewById(R.id.difficultBtn);
        ImageView logo= findViewById(R.id.logo);

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
        logo.startAnimation(anim);

        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(final View mainView) {
                startGame.setVisibility(View.VISIBLE);
                giocatore2.setVisibility(View.INVISIBLE);
                final EditText giocatore = findViewById(R.id.player1);
                Button start = findViewById(R.id.startBtn);
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (giocatore.getText().length() > 0) {
                            Intent i = new Intent(ChooserActivity.this, MainActivity.class);
                            i.putExtra("modalita", String.valueOf(mainView.getTag()));
                            i.putExtra("giocatore", giocatore.getText().toString());
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(ChooserActivity.this, getResources().getString(R.string.no_player_name), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        easy.setOnClickListener(click);
        difficult.setOnClickListener(click);
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View mainView) {
                startGame.setVisibility(View.VISIBLE);
                final EditText giocatore = findViewById(R.id.player1);
                giocatore2.setVisibility(View.VISIBLE);
                Button start = findViewById(R.id.startBtn);
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (giocatore.getText().length() > 0 && giocatore2.getText().length() > 0) {
                            Intent i = new Intent(ChooserActivity.this, MainActivity.class);
                            i.putExtra("modalita", String.valueOf(mainView.getTag()));
                            i.putExtra("giocatore", giocatore.getText().toString());
                            i.putExtra("giocatore2", giocatore2.getText().toString());
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(ChooserActivity.this, getResources().getString(R.string.no_player_name), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
