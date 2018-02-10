package pietro.tris;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

public class GiocoVPC extends AppCompatActivity {

    Button b1, b2, b3, b4, b5, b6, b7, b8, b9;
    int s1,s2,s3,s4,s5,s6,s7,s8,s9;
    int turno;
    ArrayList<Button> lista  = new ArrayList<>();
    ArrayList<Integer> premuti=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giocopvp);

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

        premuti.add(s1); premuti.add(s2);premuti.add(s3);premuti.add(s4);premuti.add(s5);premuti.add(s6);premuti.add(s7);premuti.add(s8);premuti.add(s9);
        lista.add(b1); lista.add(b2); lista.add(b3); lista.add(b4); lista.add(b5); lista.add(b6); lista.add(b7); lista.add(b8); lista.add(b9);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b1.getText().toString().equals("")) {
                    if (turno == 1) {
                        turno = 2;
                        b1.setText("X");
                        contrattacca(b1);
                        turno = 1;
                    }
                }
                s1=1;
                fineGioco(view);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b2.getText().toString().equals("")) {
                    if (turno == 1) {
                        turno = 2;
                        b2.setText("X");
                        contrattacca(b2);
                        turno = 1;
                    }
                }
                s2=1;
                fineGioco(view);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b3.getText().toString().equals("")) {
                    if (turno == 1) {
                        turno = 2;
                        b3.setText("X");
                        contrattacca(b3);
                        turno = 1;
                    }
                }
                s3=1;
                fineGioco(view);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b4.getText().toString().equals("")) {
                    if (turno == 1) {
                        turno = 2;
                        b4.setText("X");
                        contrattacca(b4);
                        turno = 1;
                    }
                }
                s4=1;
                fineGioco(view);
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b5.getText().toString().equals("")) {
                    if (turno == 1) {
                        turno = 2;
                        b5.setText("X");
                        contrattacca(b5);
                        turno = 1;
                    }
                }
                s5=1;
                fineGioco(view);
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b6.getText().toString().equals("")) {
                    if (turno == 1) {
                        turno = 2;
                        b6.setText("X");
                        contrattacca(b6);
                        turno = 1;
                    }
                }
                s6=1;
                fineGioco(view);
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b7.getText().toString().equals("")) {
                    if (turno == 1) {
                        turno = 2;
                        b7.setText("X");
                        contrattacca(b7);
                        turno = 1;
                    }
                }
                s7=1;
                fineGioco(view);
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b8.getText().toString().equals("")) {
                    if (turno == 1) {
                        turno = 2;
                        b8.setText("X");
                        contrattacca(b8);
                        turno = 1;
                    }
                }
                s8=1;
                fineGioco(view);
            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b9.getText().toString().equals("")) {
                    if (turno == 1) {
                        turno = 2;
                        b9.setText("X");
                        contrattacca(b9);
                        turno = 1;
                    }
                }
                s9=1;
                fineGioco(view);
            }
        });


    }

    private void mostraAlert(String s , final View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle( "Fine Partita" )
                .setMessage(s+", vuoi giocare di nuovo?")
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        Intent esci = new Intent(v.getContext(),MainActivity.class);
                        startActivity(esci);
                    }})
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        b1.setText("");
                        b2.setText("");
                        b3.setText("");
                        b4.setText("");
                        b5.setText("");
                        b6.setText("");
                        b7.setText("");
                        b8.setText("");
                        b9.setText("");
                        turno=1;
                        s1=s2=s3=s4=s5=s6=s7=s8=s9=0;
                        premuti.add(s1); premuti.add(s2);premuti.add(s3);premuti.add(s4);premuti.add(s5);premuti.add(s6);premuti.add(s7);premuti.add(s8);premuti.add(s9);
                        lista.add(b1); lista.add(b2); lista.add(b3); lista.add(b4); lista.add(b5); lista.add(b6); lista.add(b7); lista.add(b8); lista.add(b9);
                    }
                }).show();
    }


    public void contrattacca(Button b){
        lista.remove(b);
        if(lista.size()>1) {
            int r = (int) Math.random() * lista.size();

            lista.get(r).setText("O");
            lista.remove(lista.get(r));
        }
    }

    private void fineGioco(View v) {
        boolean fine=false;
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

        if (a.equals("X") && b.equals("X") && c.equals("X") || d.equals("X") && e.equals("X") && f.equals("X") || g.equals("X") && h.equals("X") && i.equals("X") || a.equals("X") && d.equals("X") && g.equals("X") || b.equals("X") && e.equals("X") && h.equals("X") || c.equals("X") && f.equals("X") && i.equals("X") || a.equals("X") && e.equals("X") && i.equals("X") || c.equals("X") && e.equals("X") && g.equals("X")) {
            mostraAlert("Hai Vinto",v);
            fine=true;
        }else if (a.equals("O") && b.equals("O") && c.equals("O") || d.equals("O") && e.equals("O") && f.equals("O") || g.equals("O") && h.equals("O") && i.equals("O") || a.equals("O") && d.equals("O") && g.equals("O") || b.equals("O") && e.equals("O") && h.equals("O") || c.equals("O") && f.equals("O") && i.equals("O") || a.equals("O") && e.equals("O") && i.equals("O") || c.equals("O") && e.equals("O") && g.equals("O")){
            mostraAlert("PC ha vinto",v);
            fine=true;
        }else if((s1!=0) && (s2!=0) && (s3!=0) && (s4!=0) && (s5!=0) && (s6!=0) && (s7!=0) && (s8!=0) && (s9!=0) && (!fine)) {
            mostraAlert("Pareggio",v);
            fine = true;
        }
    }
}
