package com.example.piergiorgio.trisconobserver;

import android.util.Log;
import android.widget.ImageView;

import java.util.Observable;

/**
 * Created by Piergiorgio Favaretto
 */

public class Tris extends Observable {

    boolean vinto;
    int turno, piene;
    int punti1, punti2;
    int giocate[][]; //matrice che tiene conto delle caselle giocate e da che giocatore

    Tris(){
        vinto = false;
        turno = 1;
        punti1 = punti2 = 0;
        giocate = new int [3][3];
    }

    void azioneListener(ImageView img, int riga, int colonna){ //serie di azioni che compie il Listner di ogni mage view
        if(giocate[riga][colonna] == 0){ // cella vnon giocata
            giocate[riga][colonna] = turno;
            setChanged(); 
            notifyObservers(new MioOggetto(turno, img)); //notivica la mossa per l'aggiunta del segno
            int n = controlla(); // controlla se qualche giocatore ha vinto
            piene++;
            if(n == 1){ //giocatore 1
                punti1++;
                setChanged();
                notifyObservers(new MioOggetto(1));
                resetta(0);
            } else if(n == 2){  //giocatore 2
                punti2++;
                setChanged();
                notifyObservers(new MioOggetto(2));
                resetta(0);
            }
            if(piene == 9){ //nessun vincitore ma tabella piena
                setChanged();
                notifyObservers(new MioOggetto(3));
                resetta(0);
            } else {
                if(turno == 1){
                    turno = 2;
                } else {
                    turno = 1;
                }
            }
        }
    }

    private int controlla(){
        int cnt;
        for(int k = 1; k < 3 && !vinto; k++)  { //righe
            for (int i = 0; i < 3; i++) {
                cnt = 0;
                for (int j = 0; j < 3; j++) {
                    if (giocate[i][j] == k) cnt++;
                }
                if (cnt == 3) {
                    vinto = true;
                    return k;
                }
            }
            if (!vinto) { //colonne
                for (int i = 0; i < 3; i++) {
                    cnt = 0;
                    for (int j = 0; j < 3; j++) {
                        if (giocate[j][i] == k) cnt++;
                    }
                    if (cnt == 3) {
                        vinto = true;
                        return k;
                    }
                }
            }
            if (!vinto) {
                cnt = 0;
                for (int i = 0; i < 3; i++) { //diagonale da sinistra a destra
                    if (giocate[i][i] == k) cnt++;
                }
                if (cnt == 3) {
                    return k;
                } else {
                    cnt = 0;
                    for (int j = 0; j < 3; j++) {  //diagonale da destra a sinistra 
                        for (int i = 2; i >= 0; i--) {
                            if (giocate[j][i] == k) cnt++;
                            Log.d("String", cnt + "");
                        }
                        if (cnt == 3) {
                            return k;
                        }
                    }
                }
            }
        }
        return 0;  //nessun vincitore
    }

    int getPunti1(){
        return punti1;
    }

    int getPunti2(){
        return punti2;
    }

    void resetta(int mode){ //resetta parametri
        if(mode == 1){ //maniene il turno quando è schiacciato il bottone resetta
            if(turno == 1)turno = 2;
            else turno = 1;
        }
        vinto = false;
        piene = 0;
        giocate = new int [3][3];
    }

}

class MioOggetto{

    int turno, vincitore;
    ImageView img;

    MioOggetto(int vincitore){ //oggetto invocato per il vincitore
        this.vincitore = vincitore;
    }


    MioOggetto(int turno, ImageView img){ //oggetto invocato per mostrare la mossa
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
