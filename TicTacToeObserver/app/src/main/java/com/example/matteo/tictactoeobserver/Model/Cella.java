package com.example.matteo.tictactoeobserver.Model;

import com.example.matteo.tictactoeobserver.Model.Gioco;

/**
 * Created by Matteo on 06/02/2018.
 */
public class Cella {
    public int riga;
    public int colonna;
    public Gioco.StatoCasellaTris giocatore;

    public Cella(int riga, int colonna, Gioco.StatoCasellaTris giocatore) {
        this.riga = riga;
        this.colonna = colonna;
        this.giocatore = giocatore;
    }
}