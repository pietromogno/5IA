package com.example.piergiorgio.trisconobserver;

import android.widget.ImageView;
import java.util.Observable;
import java.util.Random;

/**
 * Created by Piergiorgio Favaretto
 */

public class Tris extends Observable {

    int turno, piene, gturnoiniz;
    int punti1, punti2;
    int riga, colonna;
    int giocate[][];
    boolean conCpu;
    Random r;

    Tris(boolean conCpu){
        gturnoiniz = turno = 1;
        punti1 = punti2 = 0;
        giocate = new int [3][3];
        this.conCpu = conCpu;
        if(conCpu) r = new Random();
    }

    void azioneListener(ImageView img, int riga, int colonna){
        if(giocate[riga][colonna] == 0){
            //inserisco nella mia griglia di controllo il
            //giocatore che ha giocato quella posizione
            giocate[riga][colonna] = turno;
            setChanged(); 
            notifyObservers(new MioOggetto(turno, img));
            int n = controlla();
            piene++;
            if(n == 1){ //ha vinto il giocatore 1
                punti1++;
                setChanged();
                notifyObservers(new MioOggetto(1));
                resetta(0);
            } else if(n == 2){ //ha vinto il giocatore 2
                punti2++;
                setChanged();
                notifyObservers(new MioOggetto(2));
                resetta(0);
            }
            if(piene == 9){ //nessun vincitore, tabella piena
                setChanged();
                notifyObservers(new MioOggetto(3));
                resetta(0);
            } else { //nessun vincitore, cambia il turno
                if(turno == 1){
                    turno = 2;
                    if(conCpu)azioneRandom();
                } else {
                    turno = 1;
                }
            }
        }
    }

    private void azioneRandom(){ //formulo una mossa a caso
        boolean trovato = false;
        while(!trovato){
            riga = r.nextInt(3);
            colonna = r.nextInt(3);
            if(giocate[riga][colonna] == 0){
                trovato = !trovato;
                setChanged();
                notifyObservers(new MioOggetto(4));
            }
        }
    }

    int getRiga(){
        return riga;
    }

    int getColonna(){
        return colonna;
    }

    private int controlla(){
        int cnt;
        for(int k = 1; k < 3; k++) { //ciclo due volte per controllare entrambi i giocatori
            //controllo le righe
            for (int i = 0; i < 3; i++) {
                cnt = 0;
                for (int j = 0; j < 3; j++) {
                    if (giocate[i][j] == k) cnt++;
                }
                if (cnt == 3) {
                    return k;
                }
            }
            //controllo le colonne
            for (int i = 0; i < 3; i++) {
                cnt = 0;
                for (int j = 0; j < 3; j++) {
                    if (giocate[j][i] == k) cnt++;
                }
                if (cnt == 3) {
                    return k;
                }
            }
            //controllo la diagonale 1
            cnt = 0;
            if (giocate[0][0] == k) cnt++;
            if (giocate[1][1] == k) cnt++;
            if (giocate[2][2] == k) cnt++;
            if (cnt == 3) {
                return k;
            }
            //controllo la diagonale 2
            cnt = 0;
            if (giocate[0][2] == k) cnt++;
            if (giocate[1][1] == k) cnt++;
            if (giocate[2][0] == k) cnt++;
            if (cnt == 3) {
                return k;
            }
        }
        return 0; //nessun vincitore
    }

    int getPunti1(){
        return punti1;
    }

    int getPunti2(){
        return punti2;
    }

    void resetta(int mode){ //resetta tutte le variabili
        if(mode == 1){ //se si schiaccia resetta turno, fa ripartire dal giocatore che ha iniziato a giocarlo
            turno = gturnoiniz;
            if(conCpu) { //se si gioca contro la CPU e deve iniziare lei la faccio partire
                setChanged();
                notifyObservers(new MioOggetto(4));
            }
        }
        piene = 0;
        giocate = new int [3][3];
    }

}

class MioOggetto{

    private int turno, vincitore;
    private int riga, colonna;
    private ImageView img;

    MioOggetto(int vincitore){ //oggetto invocato per il vincitore
        this.vincitore = vincitore;
    }


    MioOggetto(int turno, ImageView img){ //oggetto invocato per inserire la mossa
        vincitore = 0;
        this.turno = turno;
        this.img = img;
    }

    ImageView getImg(){
        return img;
    }

    int getTurno(){
        return turno;
    }

    int getVincitore(){
        return vincitore;
    }

}
