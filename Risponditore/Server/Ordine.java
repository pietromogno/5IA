/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.ArrayList;

/**
 *
 * @author Enrico
 */
public class Ordine {
    
    private ArrayList<String>prodotti;
    private String indirizzo;
    private String orario;

    public Ordine(ArrayList<String> prodotti, String indirizzo, String orario) {
        this.prodotti = prodotti;
        this.indirizzo = indirizzo;
        this.orario = orario;
    }

    @Override
    public String toString() {
        return "Ordine{" + "indirizzo=" + indirizzo + ", orario=" + orario + '}';
    }
    
    public Ordine(){
        prodotti=new ArrayList<>();
        indirizzo=null;
        orario=null;
    }
    
    public void inizializza(){
        prodotti=new ArrayList<>();
        indirizzo=null;
        orario=null;
    }

    public ArrayList<String> getProdotti() {
        return prodotti;
    }

    public void setProdotti(ArrayList<String> prodotti) {
        this.prodotti = prodotti;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getOrario() {
        return orario;
    }

    public void setOrario(String orario) {
        this.orario = orario;
    }
    
    public void aggiungiProdotto(String prodotto){
        prodotti.add(prodotto);
    }
    
}
