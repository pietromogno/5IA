/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat;

import java.io.Serializable;

/**
 *
 * @author MATTI
 */
public class Messaggio implements Serializable{

    private int idUtente, funzione;
    private Object messaggio;
    public static final int ISREGISTRATO = 0, REGISTRAZIONE = 1, ACCESSO = 2, SALVACONVERSAZIONE = 3, MOSTRAMESSAGGIO = 4, OTTIENIUTENTI = 5, CANCELLAZIONE = 6, ERRORECONNESSIONE = 7;

    public int getIdUtente() {
        return idUtente;
    }

    public int getFunzione() {
        return funzione;
    }

    public void setFunzione(int funzione) {
        this.funzione = funzione;
    }

    public Object getMessaggio() {
        return messaggio;
    }

    public Messaggio(int idUtente, int funzione, Object messaggio) {
        this.idUtente = idUtente;
        this.funzione = funzione;
        this.messaggio = messaggio;
    }

}
