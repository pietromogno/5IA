package com.example.davide.tris;

import java.util.Observable;
import java.util.Observer;

public class Tabella extends Observable {
    char [ ] [ ] gioco = new char [3] [3];

    public Tabella() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gioco[i][j] = 'a'; //nel caso in cui il valore interno sia 'a' la casella è segnata come vuota
            }
        }

    }

    public void update(char Player, int [] coord, boolean playable){
        if( gioco [coord[0]] [coord[1]] == 'a'&& playable) {
            gioco[coord[0]][coord[1]] = Player;
            setChanged();
            notifyObservers();
            clearChanged();
        }
    }

    public boolean checkTable(){
        for (int i = 0; i <3; i++){
            if (gioco [0] [i] == gioco [1] [i]&& gioco [1] [i] == gioco [2] [i]){
                if (gioco [0] [0] == 'X' || gioco [0] [0] == 'O'){
                    return false;
                }
            }
        }
        for (int i = 0; i <3; i++){
            if (gioco [i] [0] == gioco [i] [1] && gioco [i] [1] == gioco [i] [2]){
                if (gioco [0] [0] == 'X' || gioco [0] [0] == 'O') {
                    return false;
                }
            }
        }

        if (gioco [0] [0] == gioco [1] [1] && gioco [1] [1] == gioco [2] [2]){
            if (gioco [0] [0] == 'X' || gioco [0] [0] == 'O') {
                return false;
            }
        }
        if (gioco [2] [0] == gioco [1] [1] && gioco [1] [1] == gioco [0] [2]){
            if (gioco [2] [0] == 'X' || gioco [0] [0] == 'O') {
                return false;
            }
        }
        return true;
    }



}


