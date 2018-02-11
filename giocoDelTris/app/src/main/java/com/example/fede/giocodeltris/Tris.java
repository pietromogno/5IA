package com.example.fede.giocodeltris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Tris extends AppCompatActivity {
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9,bNP;
    int turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tris);
        b1 = (Button) findViewById(R.id.btn00);
        b2 = (Button) findViewById(R.id.btn01);
        b3 = (Button) findViewById(R.id.btn02);
        b4 = (Button) findViewById(R.id.btn10);
        b5 = (Button) findViewById(R.id.btn11);
        b6 = (Button) findViewById(R.id.btn12);
        b7 = (Button) findViewById(R.id.btn20);
        b8 = (Button) findViewById(R.id.btn21);
        b9 = (Button) findViewById(R.id.btn22);
        bNP = (Button) findViewById(R.id.btnNuovaPartita);

        turn = 1;

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b1.getText().toString().equals("")) {
                    if (turn == 1) {
                        turn = 2;
                        b1.setText("X");
                    } else if (turn == 2) {
                        turn = 1;
                        b1.setText("O");
                    }
                }
                fineGioco();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b2.getText().toString().equals("")) {
                    if (turn == 1) {
                        turn = 2;
                        b2.setText("X");
                    } else if (turn == 2) {
                        turn = 1;
                        b2.setText("O");
                    }
                }
                fineGioco();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b3.getText().toString().equals("")) {
                    if (turn == 1) {
                        turn = 2;
                        b3.setText("X");
                    } else if (turn == 2) {
                        turn = 1;
                        b3.setText("O");
                    }
                }
                fineGioco();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b4.getText().toString().equals("")) {
                    if (turn == 1) {
                        turn = 2;
                        b4.setText("X");
                    } else if (turn == 2) {
                        turn = 1;
                        b4.setText("O");
                    }
                }
                fineGioco();
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b5.getText().toString().equals("")) {
                    if (turn == 1) {
                        turn = 2;
                        b5.setText("X");
                    } else if (turn == 2) {
                        turn = 1;
                        b5.setText("O");
                    }
                }
                fineGioco();

            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b6.getText().toString().equals("")) {
                    if (turn == 1) {
                        turn = 2;
                        b6.setText("X");
                    } else if (turn == 2) {
                        turn = 1;
                        b6.setText("O");
                    }
                }

                fineGioco();
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b7.getText().toString().equals("")) {
                    if (turn == 1) {
                        turn = 2;
                        b7.setText("X");
                    } else if (turn == 2) {
                        turn = 1;
                        b7.setText("O");
                    }
                }
                fineGioco();
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b8.getText().toString().equals("")) {
                    if (turn == 1) {
                        turn = 2;
                        b8.setText("X");
                    } else if (turn == 2) {
                        turn = 1;
                        b8.setText("O");
                    }
                }
                fineGioco();
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b9.getText().toString().equals("")) {
                    if (turn == 1) {
                        turn = 2;
                        b9.setText("X");
                    } else if (turn == 2) {
                        turn = 1;
                        b9.setText("O");
                    }
                }
                fineGioco();

            }
        });
        bNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openPage = new Intent(Tris.this, Tris.class);
                startActivity(openPage);
                finish();
            }
        });
    }


    protected void fineGioco() {
        String a, b, c, d, e, f, g, h, i;
        boolean fine = false;
        a = b1.getText().toString();
        b = b2.getText().toString();
        c = b3.getText().toString();
        d = b4.getText().toString();
        e = b5.getText().toString();
        f = b6.getText().toString();
        g = b7.getText().toString();
        h = b8.getText().toString();
        i = b9.getText().toString();

        if (a.equals("X") && b.equals("X") && c.equals("X")) {
            Toast.makeText(Tris.this, "Ha vinto X", Toast.LENGTH_LONG).show();
            fine = true;
        }
        else if (a.equals("X") && e.equals("X") && i.equals("X")) {
            Toast.makeText(Tris.this, "Ha vinto X", Toast.LENGTH_LONG).show();
            fine = true;
        }
        else if (a.equals("X") && d.equals("X") && g.equals("X")) {
            Toast.makeText(Tris.this, "Ha vinto X", Toast.LENGTH_LONG).show();
            fine = true;
        }
        else if (b.equals("X") && e.equals("X") && h.equals("X")) {
            Toast.makeText(Tris.this, "Ha vinto X", Toast.LENGTH_LONG).show();
            fine = true;
        }
        else if (c.equals("X") && f.equals("X") && i.equals("X")) {
            Toast.makeText(Tris.this, "Ha vinto X", Toast.LENGTH_LONG).show();
            fine = true;
        }
        else if (d.equals("X") && e.equals("X") && f.equals("X")) {
            Toast.makeText(Tris.this, "Ha vinto X", Toast.LENGTH_LONG).show();
            fine = true;
        }
        else if (g.equals("X") && b.equals("X") && i.equals("X")) {
            Toast.makeText(Tris.this, "Ha vinto X", Toast.LENGTH_LONG).show();
            fine = true;
        }
        else if (g.equals("X") && e.equals("X") && c.equals("X")) {
            Toast.makeText(Tris.this, "Ha vinto X", Toast.LENGTH_LONG).show();
            fine = true;
        }


        else if (a.equals("O") && b.equals("O") && c.equals("O")) {
            Toast.makeText(Tris.this, "Ha vinto O", Toast.LENGTH_LONG).show();
            fine = true;
        }
        else if (a.equals("O") && e.equals("O") && i.equals("O")) {
            Toast.makeText(Tris.this, "Ha vinto O", Toast.LENGTH_LONG).show();
            fine = true;
        }
        else if (a.equals("O") && d.equals("O") && g.equals("O")) {
            Toast.makeText(Tris.this, "Ha vinto O", Toast.LENGTH_LONG).show();
            fine = true;
        }
        else if (b.equals("O") && e.equals("O") && h.equals("O")) {
            Toast.makeText(Tris.this, "Ha vinto O", Toast.LENGTH_LONG).show();
            fine = true;
        }
        else if (c.equals("O") && f.equals("O") && i.equals("O")) {
            Toast.makeText(Tris.this, "Ha vinto O", Toast.LENGTH_LONG).show();
            fine = true;
        }
        else if (d.equals("O") && e.equals("O") && f.equals("O")) {
            Toast.makeText(Tris.this, "Ha vinto O", Toast.LENGTH_LONG).show();
            fine = true;
        }
        else if (g.equals("O") && b.equals("O") && i.equals("O")) {
            Toast.makeText(Tris.this, "Ha vinto O", Toast.LENGTH_LONG).show();
            fine = true;
        }
        else if (g.equals("O") && e.equals("O") && c.equals("O")) {
            Toast.makeText(Tris.this, "Ha vinto O", Toast.LENGTH_LONG).show();
            fine = true;
        }
        if (fine) {
            b1.setEnabled(false);
            b2.setEnabled(false);
            b3.setEnabled(false);
            b4.setEnabled(false);
            b5.setEnabled(false);
            b6.setEnabled(false);
            b7.setEnabled(false);
            b8.setEnabled(false);
            b9.setEnabled(false);
        }
    }
}