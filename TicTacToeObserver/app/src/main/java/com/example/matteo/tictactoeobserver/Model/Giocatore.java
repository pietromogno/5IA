package com.example.matteo.tictactoeobserver.Model;

import com.example.matteo.tictactoeobserver.Model.Gioco;

/**
 * Created by Matteo on 08/02/2018.
 */

public class Giocatore {
    private Gioco.StatoCasellaTris simbolo;
    private String nomeGiocatore;
    private int punteggio;

    public Giocatore(Gioco.StatoCasellaTris simbolo, String nomeGiocatore) {
        this.simbolo = simbolo;
        this.nomeGiocatore = nomeGiocatore;
        punteggio = 0;
    }

    public int getPunteggio() {
        return punteggio;
    }
    public String getNomeGiocatore(){
        return nomeGiocatore;
    }
    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }
    public Gioco.StatoCasellaTris getSimbolo(){ return simbolo; }
}