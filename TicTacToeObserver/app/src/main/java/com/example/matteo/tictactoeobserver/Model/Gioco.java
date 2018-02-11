package com.example.matteo.tictactoeobserver.Model;

import java.util.Observable;
import java.util.Random;

/**
 * Created by Matteo on 06/02/18.
 */

public class Gioco extends Observable {

    StatoCasellaTris[][] ac;//array caselle
    final int NCELLE = 3;
    public Giocatore gAttuale;
    public StatoCasellaTris vincitore;
    public boolean partitaFinita;
    public Giocatore g1, g2;
    public boolean isControPC;

    public enum StatoCasellaTris {
        O, X, Vuoto
    }

    public Gioco(String nomeG1, String nomeG2) {
        ac = new StatoCasellaTris[NCELLE][NCELLE];
        g1 = new Giocatore(StatoCasellaTris.X, nomeG1);
        g2 = new Giocatore(StatoCasellaTris.O, nomeG2);
        isControPC=false;
        reset();
    }

    public Gioco(){
        ac = new StatoCasellaTris[NCELLE][NCELLE];
        g1=new Giocatore(StatoCasellaTris.X, "te");
        g2=new Giocatore(StatoCasellaTris.O, "computer");
        isControPC=true;
        reset();
    }

    //gestisco il caso in cui sia uomo contro computer con questo fa la mossa
    public void computerFaiMossa(){
        if(isControPC&&gAttuale==g2){
            int[] punti=cercaMossaComputer();
            clickCella(punti[0], punti[1]);
        }
    }

    //dice le coordinate di dove deve fare la mossa il computer
    public int[] cercaMossaComputer(){
        final StatoCasellaTris VUOTO=StatoCasellaTris.Vuoto;
        final StatoCasellaTris S_PC=g2.getSimbolo();
        final StatoCasellaTris S_G1=g1.getSimbolo();
        //controllo possibili vittorie pc
        for(int i=0; i<NCELLE; i++){
            for(int j=0; j<NCELLE; j++){
                if(ac[i][j]==VUOTO) {
                    ac[i][j] = S_PC;
                    boolean vinto = controlloVittoria(gAttuale);
                    ac[i][j] = StatoCasellaTris.Vuoto;
                    if (vinto) {
                        return new int[]{i, j};
                    }
                }
            }
        }
        //controllo possibili vittorie altro giocatore per bloccarlo
        for(int i=0; i<NCELLE; i++){
            for (int j=0; j<NCELLE; j++){
                if(ac[i][j]==VUOTO) {
                    ac[i][j] = S_G1;
                    boolean vintoAltro = controlloVittoria(g1);
                    ac[i][j] = StatoCasellaTris.Vuoto;
                    if(vintoAltro) return new int[]{i,j};
                }
            }
        }
        //metto a caso
        int r, c;
        do{
            Random rand=new Random();
            r=rand.nextInt()%3;
            c=rand.nextInt()%3;
            if(r<0) r*=-1;
            if(c<0) c*=-1;
        }while(ac[r][c]!=VUOTO);
        return new int[]{r,c};
    }

    //resetta il campo di gioco e azzero tutti i valori
    public void reset() {
        //pulisco l'array
        for (int i = 0; i < NCELLE; i++) {
            for (int j = 0; j < NCELLE; j++) {
                ac[i][j] = StatoCasellaTris.Vuoto;
            }
        }
        vincitore = StatoCasellaTris.Vuoto;
        partitaFinita = false;
        gAttuale = (g1.getPunteggio()+g2.getPunteggio())%2==0? g1: g2; //faccio lo switch dei giocatori decidendo chi deve partire
    }

    //gestione del click su una cella
    public void clickCella(int r, int c) {
        if (ac[r][c] == StatoCasellaTris.Vuoto&&!partitaFinita) {
            ac[r][c] = gAttuale.getSimbolo();
            boolean vinto=controlloVittoria(gAttuale); //controlla se il giocatore attuale con la mossa appena fatta ha vinto
            if(vinto) vincitore=gAttuale.getSimbolo(); //se vinto allora è il vincitore
            partitaFinita = (isGiocoFinito() || vinto); //è vero se il giocatore attuale ha vinto o è un pareggio
            StatoCasellaTris s_gAttuale=gAttuale.getSimbolo();
            setChanged(); //setta le modifiche per observer
            notifyObservers(new Cella(r, c, s_gAttuale)); //notifica l'observer
            if(!partitaFinita){
                gAttuale = gAttuale.getSimbolo() == StatoCasellaTris.O ? g1 : g2; //se la partita non è finita switch giocatori
                computerFaiMossa(); //controllo che se 1vsComputer ed è il turno del computer->metterà il proprio simbolo
            }
            else reset(); //se partita finita resetta campo di gioco
        }
    }


    //controlla se ha vinto
    public boolean controlloVittoria(Giocatore g) {
        StatoCasellaTris s=g.getSimbolo();
        for (int i = 0; i < NCELLE; i++) {
            if (ac[0][i] == s && ac[1][i] == s && ac[2][i] == s) return true;
            if (ac[i][0] == s && ac[i][1] == s && ac[i][2] == s) return true;
        }
        if (ac[0][0] == s && ac[1][1] == s && ac[2][2] == s) return true;
        if (ac[0][2] == s && ac[1][1] == s && ac[2][0] == s) return true;
        return false;
    }

    //controlla se gioca è finito
    public boolean isGiocoFinito() {
        boolean ris = true;
        for (int i = 0; i < NCELLE; i++) {
            for (int j = 0; j < NCELLE; j++) {
                if (ac[i][j] == StatoCasellaTris.Vuoto) return false;
            }
        }
        return true;
    }
}