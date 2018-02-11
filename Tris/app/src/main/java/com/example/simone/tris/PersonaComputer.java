package com.example.simone.tris;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PersonaComputer extends AppCompatActivity {

    Button b1, b2, b3, b4, b5, b6 ,b7, b8, b9;

    int turno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.persona_computer);

        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        b4 = (Button) findViewById(R.id.b4);
        b5 = (Button) findViewById(R.id.b5);
        b6 = (Button) findViewById(R.id.b6);
        b7 = (Button) findViewById(R.id.b7);
        b8 = (Button) findViewById(R.id.b8);
        b9 = (Button) findViewById(R.id.b9);

        turno = 1;

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b1.getText().toString().equals("")){
                    if (turno == 1) {
                        turno = 2;
                        b1.setText("X");
                    } else if (turno == 2) {
                        turno = 1;
                        b1.setText("O");
                    }
                }
                vittoria();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b2.getText().toString().equals("")){
                    if (turno == 1) {
                        turno = 2;
                        b2.setText("X");
                    } else if (turno == 2) {
                        turno = 1;
                        b2.setText("O");
                    }
                }
                vittoria();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b3.getText().toString().equals("")){
                    if (turno == 1) {
                        turno = 2;
                        b3.setText("X");
                    } else if (turno == 2) {
                        turno = 1;
                        b3.setText("O");
                    }
                }
                vittoria();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b4.getText().toString().equals("")){
                    if (turno == 1) {
                        turno = 2;
                        b4.setText("X");
                    } else if (turno == 2) {
                        turno = 1;
                        b4.setText("O");
                    }
                }
                vittoria();
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b5.getText().toString().equals("")){
                    if (turno == 1) {
                        turno = 2;
                        b5.setText("X");
                    } else if (turno == 2) {
                        turno = 1;
                        b5.setText("O");
                    }
                }
                vittoria();
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b6.getText().toString().equals("")){
                    if (turno == 1) {
                        turno = 2;
                        b6.setText("X");
                    } else if (turno == 2) {
                        turno = 1;
                        b6.setText("O");
                    }
                }
                vittoria();
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b7.getText().toString().equals("")){
                    if (turno == 1) {
                        turno = 2;
                        b7.setText("X");
                    } else if (turno == 2) {
                        turno = 1;
                        b7.setText("O");
                    }
                }
                vittoria();
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b8.getText().toString().equals("")){
                    if (turno == 1) {
                        turno = 2;
                        b8.setText("X");
                    } else if (turno == 2) {
                        turno = 1;
                        b8.setText("O");
                    }
                }
                vittoria();
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b9.getText().toString().equals("")){
                    if (turno == 1) {
                        turno = 2;
                        b9.setText("X");
                    } else if (turno == 2) {
                        turno = 1;
                        b9.setText("O");
                    }
                }
                vittoria();
            }
        });
    }

    public void vittoria (){
        String a, b, c, d, e, f, g, h, i;
        boolean fine = false;

        a=b1.getText().toString();
        b=b2.getText().toString();
        c=b3.getText().toString();

        d=b4.getText().toString();
        e=b5.getText().toString();
        f=b6.getText().toString();

        g=b7.getText().toString();
        h=b8.getText().toString();
        i=b9.getText().toString();

        //VINCITE X

        if(a.equals("X") && b.equals("X") && c.equals("X")){
            Toast.makeText(this, "Vince Giocatore X", Toast.LENGTH_LONG).show();
            fine = true;
        }
        if(d.equals("X") && e.equals("X") && f.equals("X")){
            Toast.makeText(this, "Vince Giocatore X", Toast.LENGTH_LONG).show();
            fine = true;
        }
        if(g.equals("X") && h.equals("X") && i.equals("X")){
            Toast.makeText(this, "Vince Giocatore X", Toast.LENGTH_LONG).show();
            fine = true;
        }
        if(a.equals("X") && d.equals("X") && g.equals("X")){
            Toast.makeText(this, "Vince Giocatore X", Toast.LENGTH_LONG).show();
            fine = true;
        }
        if(b.equals("X") && e.equals("X") && h.equals("X")){
            Toast.makeText(this, "Vince Giocatore X", Toast.LENGTH_LONG).show();
            fine = true;
        }
        if(c.equals("X") && f.equals("X") && i.equals("X")){
            Toast.makeText(this, "Vince Giocatore X", Toast.LENGTH_LONG).show();
            fine = true;
        }
        if(a.equals("X") && e.equals("X") && i.equals("X")){
            Toast.makeText(this, "Vince Giocatore X", Toast.LENGTH_LONG).show();
            fine = true;
        }
        if(c.equals("X") && e.equals("X") && g.equals("X")){
            Toast.makeText(this, "Vince Giocatore X", Toast.LENGTH_LONG).show();
            fine = true;
        }

        //VINCITE O

        if(a.equals("O") && b.equals("O") && c.equals("O")){
            Toast.makeText(this, "Vince Giocatore O", Toast.LENGTH_LONG).show();
            fine = true;
        }
        if(d.equals("O") && e.equals("O") && f.equals("O")){
            Toast.makeText(this, "Vince Giocatore O", Toast.LENGTH_LONG).show();
            fine = true;
        }
        if(g.equals("O") && h.equals("O") && i.equals("O")){
            Toast.makeText(this, "Vince Giocatore O", Toast.LENGTH_LONG).show();
            fine = true;
        }
        if(a.equals("O") && d.equals("O") && g.equals("O")){
            Toast.makeText(this, "Vince Giocatore O", Toast.LENGTH_LONG).show();
            fine = true;
        }
        if(b.equals("O") && e.equals("O") && h.equals("O")){
            Toast.makeText(this, "Vince Giocatore O", Toast.LENGTH_LONG).show();
            fine = true;
        }
        if(c.equals("O") && f.equals("O") && i.equals("O")){
            Toast.makeText(this, "Vince Giocatore O", Toast.LENGTH_LONG).show();
            fine = true;
        }
        if(a.equals("O") && e.equals("O") && i.equals("O")){
            Toast.makeText(this, "Vince Giocatore O", Toast.LENGTH_LONG).show();
            fine = true;
        }
        if(c.equals("O") && e.equals("O") && g.equals("O")){
            Toast.makeText(this, "Vince Giocatore O", Toast.LENGTH_LONG).show();
            fine = true;
        }

        if(fine){
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
