
package server;

import java.util.LinkedHashMap;

/**
 * @author Davide Porcu
 */
public class Carrello {
    
    private LinkedHashMap<Categoria, LinkedHashMap<Prodotto, Integer>> carrello;//hash map che mantiene l'ordine ;)
    
    public Carrello() {
        carrello = new LinkedHashMap<>();
    }

    public void addProdotto(Prodotto p) {
        if (carrello.containsKey(p.getCategoria())) {
            carrello.get(p.getCategoria()).put(p, carrello.get(p.getCategoria()).get(p) + 1);
        } else {
            carrello.put(p.getCategoria(), new LinkedHashMap<>());
            carrello.get(p.getCategoria()).put(p, 1);
        }
    }

    public int getNumeroArticoliNelCarrello() {
        int num = 0;
        for (LinkedHashMap<Prodotto, Integer> categoria : carrello.values()) {
            num += categoria.size();
        }
        return num;
    }

    public String[] toStringArray() {
        final int righePerIntestazione=2;
        final int righePerTotale=2;
        int lung=righePerIntestazione+righePerTotale+getNumeroArticoliNelCarrello() + carrello.size();
        String[] scontrino = new String[lung];
        
        scontrino[0]="---    Dettaglio Scontrino    ---";
        scontrino[1]="---                           ---";

        int i = righePerIntestazione;
        double totale = 0;
        for (Categoria cat : carrello.keySet()) {
            scontrino[i] = "Reparto " + cat.getNomeCategoria();
            i++;
            for (Prodotto p : carrello.get(cat).keySet()) {
                int quantita = carrello.get(cat).get(p);
                double costoProdotti = (double) Math.round(quantita * p.getPrezzo() * 100) / 100;
                scontrino[i] = String.format("%s %5dpz. €%.2f",p.getNomeProdotto(),quantita,costoProdotti);
                totale += costoProdotti;
                i++;
            }
        }
        scontrino[lung-2]="---------------------------------";
        scontrino[lung-1]="Totale €"+totale;
        return scontrino;
    }
}
