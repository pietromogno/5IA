/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientudp;

/**
 *
 * @author simone.pasutti
 */
public class Messaggio {

    private String messaggio, ris, nomeutente;

    Messaggio(String nomeUtente, String messaggio) {
        this.messaggio = messaggio;
        this.nomeutente = nomeUtente;
        ris = String.join(",", nomeutente, messaggio);
    }

    public int length() {
        return ris.length();
    }

    public byte[] getBytes() {
        return ris.getBytes();
    }

    public String getNomeUtente() {
        return nomeutente;
    }

    public String getMessaggio() {
        return messaggio;
    }
}
