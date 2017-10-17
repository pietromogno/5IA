/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author Enrico
 */
public class Prenotazione {
    private String orario;
    private String nominativo;
    private int nPersone;

    public Prenotazione(String orario, String nominativo, int nPersone) {
        this.orario = orario;
        this.nominativo = nominativo;
        this.nPersone = nPersone;
    }
    
    public Prenotazione(){
        orario=null;
        nominativo=null;
        nPersone=-1;
    }
    
    public void inizializza(){
        orario=null;
        nominativo=null;
        nPersone=-1;
    }

    @Override
    public String toString() {
        return "Prenotazione{" + "orario=" + orario + ", nominativo=" + nominativo + ", nPersone=" + nPersone + '}';
    }

    public String getOrario() {
        return orario;
    }

    public void setOrario(String orario) {
        this.orario = orario;
    }

    public String getNominativo() {
        return nominativo;
    }

    public void setNominativo(String nominativo) {
        this.nominativo = nominativo;
    }

    public int getnPersone() {
        return nPersone;
    }

    public void setnPersone(int nPersone) {
        this.nPersone = nPersone;
    }
}
