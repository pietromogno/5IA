package favaro.matteo.tris3;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class ActivityTwo extends AppCompatActivity {
    int gameMode;
    int turn;
    Tris tris;
    String p1="x";
    String p2="o";
    String p;
    String pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        initializeGame();
    }
    private void initializeGame() {
        gameMode = getIntent().getIntExtra("gameMode", 0);
        turn=1;
        tris=new Tris();
        if(gameMode==2){
            try {
                Socket client=new Socket("192.168.56.1",8080);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void restartGame(){
        ((Button)findViewById(R.id.button1)).setText("");
        ((Button)findViewById(R.id.button2)).setText("");
        ((Button)findViewById(R.id.button3)).setText("");
        ((Button)findViewById(R.id.button4)).setText("");
        ((Button)findViewById(R.id.button5)).setText("");
        ((Button)findViewById(R.id.button6)).setText("");
        ((Button)findViewById(R.id.button7)).setText("");
        ((Button)findViewById(R.id.button8)).setText("");
        ((Button)findViewById(R.id.button9)).setText("");
        turn=1;
        tris=new Tris();
        p=p1;
    }
    public void onClick(View view) {
        if(gameMode==1){
            if(((Button)findViewById(view.getId())).getText().toString().equals("")){
                if(turn%2!=0) {
                    p =p1;
                }else{
                    p=p2;
                }
                ((Button)findViewById(view.getId())).setText(p);
                pos=""+view.getTag();
                switch(pos){
                    case "0":
                        pos="00";
                        break;
                    case "1":
                        pos="01";
                        break;
                    case "2":
                        pos="02";
                        break;
                }
                int ix=Integer.parseInt(pos.substring(0,1));
                int jx=Integer.parseInt(pos.substring(1,2));
                tris.set(ix,jx,p);
                turn++;
                if(tris.getWinner()==" VINCE IL GIOCATORE X "||tris.getWinner()==" VINCE IL GIOCATORE O "){
                    newAllert(tris.getWinner());
                }
                if(tris.isFull()==true){
                    newAllert("Pareggio");
                }
            }
        }
        if(gameMode==0){
            if(((Button)findViewById(view.getId())).getText().toString().equals("")){
                if(turn%2!=0) {
                    p =p1;
                }else{
                    p=p2;
                }
                ((Button)findViewById(view.getId())).setText(p);
                pos=""+view.getTag();
                switch(pos){
                    case "0":
                        pos="00";
                        break;
                    case "1":
                        pos="01";
                        break;
                    case "2":
                        pos="02";
                        break;
                }
                int ix=Integer.parseInt(pos.substring(0,1));
                int jx=Integer.parseInt(pos.substring(1,2));
                tris.set(ix,jx,p);
                turn++;
                if(tris.getWinner()==" VINCE IL GIOCATORE X "||tris.getWinner()==" VINCE IL GIOCATORE O "){
                    newAllert(tris.getWinner());
                }
                if(tris.isFull()==true){
                    newAllert("Pareggio");
                }
                setP2();
            }
        }
        if(gameMode==2){

        }
    }
    public void newAllert(String message){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Click per ricominciare");
        dlgAlert.setTitle(message);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
        restartGame();
    }
    public void setP2() {
        Random r = new Random();
        int b = r.nextInt(8) + 1;
        //viene scelto un bottone vuoto casuale
        Button bt=null;
        switch (b) {
            case 1:
                bt = findViewById(R.id.button1);
                break;
            case 2:
                bt = findViewById(R.id.button2);
                break;
            case 3:
                bt = findViewById(R.id.button3);
                break;
            case 4:
                bt = findViewById(R.id.button4);
                break;
            case 5:
                bt = findViewById(R.id.button5);
                break;
            case 6:
                bt = findViewById(R.id.button6);
                break;
            case 7:
                bt = findViewById(R.id.button7);
                break;
            case 8:
                bt = findViewById(R.id.button8);
                break;
            case 9:
                bt = findViewById(R.id.button9);
                break;
        }
        if(bt.getText().toString().equals("")) {
            if (turn % 2 != 0) {
                p = p1;
            } else {
                p = p2;
            }
            bt.setText(p);
            pos = "" + bt.getTag();
            switch (pos) {
                case "0":
                    pos = "00";
                    break;
                case "1":
                    pos = "01";
                    break;
                case "2":
                    pos = "02";
                    break;
            }
            int ix = Integer.parseInt(pos.substring(0, 1));
            int jx = Integer.parseInt(pos.substring(1, 2));
            tris.set(ix, jx, p);
            turn++;
            if (tris.getWinner() == " VINCE IL GIOCATORE X " || tris.getWinner() == " VINCE IL GIOCATORE O ") {
                newAllert(tris.getWinner());
            }
            if (tris.isFull() == true) {
                newAllert("Pareggio");
            }
        }else{
            setP2();
        }
    }
}