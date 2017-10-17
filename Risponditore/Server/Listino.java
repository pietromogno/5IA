package server;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author Davide Porcu
 */
public class Listino {
    private LinkedHashMap<Categoria, LinkedHashMap<String, Prodotto>> listinoProdotti;

    public Listino() {
        this.listinoProdotti = new LinkedHashMap<>();
    }

    public void addProdotto(Prodotto p) {//aggiungo prodotto al listino
        if (listinoProdotti.containsKey(p.getCategoria())) {
            listinoProdotti.get(p.getCategoria()).put(p.getNomeProdotto().toLowerCase(), p);
        } else {
            listinoProdotti.put(p.getCategoria(), new LinkedHashMap<>());
            listinoProdotti.get(p.getCategoria()).put(p.getNomeProdotto().toLowerCase(), p);
        }
    }

    public Categoria[] getArrayDiCategorie(){
        Categoria[] elencoCategorie=new Categoria[listinoProdotti.size()];
        int i=0;
        for(Categoria c:listinoProdotti.keySet()){
            elencoCategorie[i]=c;
            i++;
        }
        return elencoCategorie;
    }
    
    public Prodotto[] getArrayProdottiDiCategoria(Categoria categoria) {//get array di prodotti in base alla categoria data-> utile quando richiedo 
        Prodotto[] lista = new Prodotto[0];
        if (listinoProdotti.containsKey(categoria)) {
            HashMap<String, Prodotto> hashMapProdotti = listinoProdotti.get(categoria);
            lista = new Prodotto[hashMapProdotti.size()];
            int i = 0;
            for (Prodotto p : hashMapProdotti.values()) {
                lista[i] = p;
                i++;
            }
        }
        return lista;
    }

    public Prodotto getProdotto(String nomeProdotto) {
        for (HashMap<String, Prodotto> map : listinoProdotti.values()) {
            if (map.containsKey(nomeProdotto.toLowerCase())) {
                return map.get(nomeProdotto.toLowerCase());
            }
        }
        return null;//inutile perchè c'è tutta una serie di controlli che evitano 
        //che l'utente inserisca nomi di prodotti che non sono nel listino
    }
}
