/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverudp;

/**
 *
 * @author simone.pasutti
 */
public class Messaggio {

    private String nomeutente, ris;
    private Object messaggio;

    Messaggio(String nomeUtente, Object messaggio) {
        this.messaggio = messaggio;
        this.nomeutente = nomeUtente;
        ris = String.join(",", nomeUtente, String.valueOf(messaggio));
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

    public Object getMessaggio() {
        return messaggio;
    }

  
}
