package com.example.simone.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Color.*;

public class MainActivity extends AppCompatActivity {
    private boolean turno = true;
    private int contatore=0;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, btnricomincia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.button_00);
        b2 = (Button) findViewById(R.id.button_01);
        b3 = (Button) findViewById(R.id.button_02);
        b4 = (Button) findViewById(R.id.button_10);
        b5 = (Button) findViewById(R.id.button_11);
        b6 = (Button) findViewById(R.id.button_12);
        b7 = (Button) findViewById(R.id.button_20);
        b8 = (Button) findViewById(R.id.button_21);
        b9 = (Button) findViewById(R.id.button_22);
        btnricomincia = findViewById(R.id.button_ricomincia);

        btnricomincia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openPage = new Intent(MainActivity.this, MainActivity.class);
                startActivity(openPage);
                finish();
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contatore++;
                if (turno) {
                    turno = false;
                    b1.setText("X");
                    b1.setTextColor(Color.GREEN);
                    b1.setClickable(false);
                } else {
                    turno = true;
                    b1.setText("O");
                    b1.setTextColor(Color.RED);
                    b1.setClickable(false);


                }
                endGame();

            }

        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contatore++;
                if (turno) {
                    turno = false;
                    b2.setText("X");
                    b2.setTextColor(Color.GREEN);
                    b2.setClickable(false);
                } else {
                    turno = true;
                    b2.setText("O");
                    b2.setTextColor(Color.RED);
                    b2.setClickable(false);
                }
                endGame();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contatore++;
                if (turno) {
                    turno = false;
                    b3.setText("X");
                    b3.setTextColor(Color.GREEN);
                    b3.setClickable(false);
                } else {
                    turno = true;
                    b3.setText("O");
                    b3.setTextColor(Color.RED);
                    b3.setClickable(false);

                }
                endGame();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contatore++;
                if (turno) {
                    turno = false;
                    b4.setText("X");
                    b4.setTextColor(Color.GREEN);
                    b4.setClickable(false);
                } else {
                    turno = true;
                    b4.setText("O");
                    b4.setTextColor(Color.RED);
                    b4.setClickable(false);
                }
                endGame();
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contatore++;
                if (turno) {
                    turno = false;
                    b5.setText("X");
                    b5.setTextColor(Color.GREEN);
                    b5.setClickable(false);
                } else {
                    turno = true;
                    b5.setText("O");
                    b5.setTextColor(Color.RED);
                    b5.setClickable(false);
                }
                endGame();
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contatore++;
                if (turno) {
                    turno = false;
                    b6.setText("X");
                    b6.setTextColor(Color.GREEN);
                    b6.setClickable(false);
                } else {
                    turno = true;
                    b6.setText("O");
                    b6.setTextColor(Color.RED);
                    b6.setClickable(false);
                }
                endGame();
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contatore++;
                if (turno) {
                    turno = false;
                    b7.setText("X");
                    b7.setTextColor(Color.GREEN);
                    b7.setClickable(false);
                } else {
                    turno = true;
                    b7.setText("O");
                    b7.setTextColor(Color.RED);
                    b7.setClickable(false);
                }
                endGame();
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contatore++;
                if (turno) {
                    turno = false;
                    b8.setText("X");
                    b8.setTextColor(Color.GREEN);
                    b8.setClickable(false);
                } else {
                    turno = true;
                    b8.setText("O");
                    b8.setTextColor(Color.RED);
                    b8.setClickable(false);
                }
                endGame();
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contatore++;
                if (turno) {
                    turno = false;
                    b9.setText("X");
                    b9.setTextColor(Color.GREEN);
                    b9.setClickable(false);
                } else {
                    turno = true;
                    b9.setText("O");
                    b9.setTextColor(Color.RED);
                    b9.setClickable(false);
                }
                endGame();
            }
        });

    }

    public void endGame() {
        String a, b, c, d, e, f, g, h, i;
        a = b1.getText().toString();
        b = b2.getText().toString();
        c = b3.getText().toString();
        d = b4.getText().toString();
        e = b5.getText().toString();
        f = b6.getText().toString();
        g = b7.getText().toString();
        h = b8.getText().toString();
        i = b9.getText().toString();
        if (a.equals(b) && a.equals(c) && a.equals("X")) {
            Toast.makeText(MainActivity.this, "ha vinto il giocatore 1", Toast.LENGTH_LONG).show();
        }
        if (a.equals(b) && a.equals(c) && a.equals("O")) {
            Toast.makeText(MainActivity.this, "ha vinto il giocatore 2", Toast.LENGTH_LONG).show();
        }
        if (a.equals(d) && a.equals(g) && a.equals("X")) {
            Toast.makeText(MainActivity.this, "ha vinto il giocatore 1", Toast.LENGTH_LONG).show();
        }
        if (a.equals(d) && a.equals(g) && a.equals("O")) {
            Toast.makeText(MainActivity.this, "ha vinto il giocatore 2", Toast.LENGTH_LONG).show();
        }
        if (d.equals(e) && d.equals(f) && d.equals("X")) {
            Toast.makeText(MainActivity.this, "ha vinto il giocatore 1", Toast.LENGTH_LONG).show();
        }
        if (d.equals(e) && d.equals(f) && d.equals("O")) {
            Toast.makeText(MainActivity.this, "ha vinto il giocatore 2", Toast.LENGTH_LONG).show();
        }
        if (b.equals(e) && b.equals(h) && b.equals("X")) {
            Toast.makeText(MainActivity.this, "ha vinto il giocatore 1", Toast.LENGTH_LONG).show();
        }
        if (b.equals(e) && b.equals(h) && b.equals("O")) {
            Toast.makeText(MainActivity.this, "ha vinto il giocatore 2", Toast.LENGTH_LONG).show();
        }
        if (g.equals(h) && g.equals(i) && g.equals("X")) {
            Toast.makeText(MainActivity.this, "ha vinto il giocatore 1", Toast.LENGTH_LONG).show();
        }
        if (g.equals(h) && g.equals(i) && g.equals("O")) {
            Toast.makeText(MainActivity.this, "ha vinto il giocatore 2", Toast.LENGTH_LONG).show();
        }
        if (c.equals(f) && c.equals(i) && c.equals("X")) {
            Toast.makeText(MainActivity.this, "ha vinto il giocatore 1", Toast.LENGTH_LONG).show();
        }
        if (c.equals(f) && c.equals(i) && c.equals("O")) {
            Toast.makeText(MainActivity.this, "ha vinto il giocatore 2", Toast.LENGTH_LONG).show();
        }
        if (a.equals(e) && a.equals(i) && a.equals("X")) {
            Toast.makeText(MainActivity.this, "ha vinto il giocatore 1", Toast.LENGTH_LONG).show();
        }
        if (a.equals(e) && a.equals(i) && a.equals("O")) {
            Toast.makeText(MainActivity.this, "ha vinto il giocatore 2", Toast.LENGTH_LONG).show();
        }
        if (c.equals(e) && c.equals(g) && c.equals("X")) {
            Toast.makeText(MainActivity.this, "ha vinto il giocatore 1", Toast.LENGTH_LONG).show();
        }
        if (c.equals(e) && c.equals(g) && c.equals("O")) {
            Toast.makeText(MainActivity.this, "ha vinto il giocatore 2", Toast.LENGTH_LONG).show();
        }
        if (b.equals(e) && b.equals(h) && b.equals("X")) {
            Toast.makeText(MainActivity.this, "ha vinto il giocatore 1", Toast.LENGTH_LONG).show();
        }
        if (b.equals(e) && b.equals(h) && b.equals("O")) {
            Toast.makeText(MainActivity.this, "ha vinto il giocatore 2", Toast.LENGTH_LONG).show();
        }
        if(contatore==9){
            Toast.makeText(MainActivity.this, "non ha vinto nessuno", Toast.LENGTH_LONG).show();
        }


    }
}
