/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import dbutil.UtilDB;
import java.util.function.Predicate;

/**
 *
 * @author Enrico
 */
public class Pizzeria {

    final static Object[] RISPOSTE1 = {"Ordine", 1, "Prenotazione", 5, "Disdetta", 8, "ANY", 0};
    final static Object[] RISPOSTE2 = {"FINE", 2, "ANY", 1};
    final static Object[] RISPOSTE3 = {"Asporto", 3, "Ritiro", 4, "ANY", 2};
    final static Object[] RISPOSTE4 = {"ANY", 4};
    final static Object[] RISPOSTE5 = {"ANY", 9};
    final static Object[] RISPOSTE6 = {"ANY", 6};
    final static Object[] RISPOSTE7 = {"", 6, "ANY", 7};
    final static Object[] RISPOSTE8 = {"", 7, "ANY", 9};
    final static Object[] RISPOSTE9 = {"BACK", 0, "", 8, "ANY", 9};
    final static Object[] RISPOSTE10 = {"Sì", 10, "No", 10, "ANY", 9};
    final static Object[] RISPOSTE11 = {"Sì", 0, "No", 11, "ANY", 10};

    final static Object[] DOMANDE = {
        "buongiorno! Sono l'assistente automatico della pizzeria 'Pizza Pazza'! Come posso aiutarla?(Ordine/Prenotazione/Disdetta)", RISPOSTE1,
        "cosa vuole ordinare? (Indicare la fine della lista con 'FINE')", RISPOSTE2,
        "con che modalità desidera ricevere i prodotti ordinati?(Asporto/Ritiro)", RISPOSTE3,
        "a quale indirizzo desidera ricevere i prodotti ordinati?", RISPOSTE4,
        "per che ora vuole avere i prodotti ordinati?", RISPOSTE5,
        "per quando desidera prenotare?", RISPOSTE6,
        "per quante persone desidera prenotare?", RISPOSTE7,
        "con che nominativo desidera prenotare?", RISPOSTE8,
        "a che nominativo è associata la prenotazione che desidera disdire?(Digitare 'BACK' se si desidera tornare alla domanda precedente)", RISPOSTE9,
        "conferma le richieste appena effettuate?", RISPOSTE10,
        "vuole effettuare ulteriori richieste?", RISPOSTE11,
        "grazie e arrivederci!"
    };

    private interface Elaboratore {

        String elabora(Ordine o, Prenotazione p, String risposta, int idUtente);
    }

    final static Elaboratore[] ELABORATORI = {
        (o, p, r, id) -> {
            o.inizializza();
            p.inizializza();
            return r;
        },
        (o, p, r, id) -> {
            if (!r.equals("FINE")) {
                o.aggiungiProdotto(r);
            }
            return r;
        },
        (o, p, r, id) -> {
            if (r.equals("Ritiro")) {
                o.setIndirizzo("");
            }
            return r;
        },
        (o, p, r, id) -> {
            o.setIndirizzo(r);
            return r;
        },
        (o, p, r, id) -> {
            o.setOrario(r);
            return r;
        },
        (o, p, r, id) -> {
            p.setOrario(r);
            return r;
        },
        (o, p, r, id) -> {
            String ris = r;
            try {
                p.setnPersone(Integer.parseInt(r));
            } catch (NumberFormatException ex) {
                ris = "";
            }
            return ris;
        },
        (o, p, r, id) -> {
            String ris = r;
            if (UtilDB.esisteNominativo(r) || r.equals("BACK")) {
                ris = "";
            } else {
                p.setNominativo(r);
            }
            return ris;
        },
        (o, p, r, id) -> {
            String ris = r;
            if (!ris.equals("BACK")) {
                if (UtilDB.esistonoNominativoIdUtente(r, id)) {
                    p.setNominativo(r);
                } else {
                    ris = "";
                }
            }
            return ris;
        },
        (o, p, r, id) -> {
            if (r.equals("Sì")) {
                if (o.getOrario() != null) {
                    UtilDB.inserisciOrdine(o, id);
                } else if (p.getOrario() != null) {
                    UtilDB.inserisciPrenotazione(p, id);
                } else {
                    UtilDB.cancellaPrenotazione(p.getNominativo());
                }
            }
            return r;
        },
        (o, p, r, id) -> {
            return r;
        }
    };
    
    public static int getFirstId(){
        return 0;
    }
    
    public static int getLastId(){
        return (DOMANDE.length-1)/2;
    }

    public static String getDomanda(int idDomanda) {
        return DOMANDE[2 * idDomanda] + "";
    }

    public static int nextQuestion(int idDomandaPrec, String risposta) {
        Object[] risposte = (Object[]) DOMANDE[2 * idDomandaPrec + 1];
        int ris = -1;
        for (int i = 0; i < risposte.length / 2 && ris < 0; i++) {
            if (risposte[2 * i].equals(risposta) || risposte[2 * i].equals("ANY")) {
                ris = (int) risposte[2 * i + 1];
            }
        }
        return ris;
    }

    public static String elaboraDati(Ordine o, Prenotazione p, String r, int idDomanda, int idUtente) {
        return ELABORATORI[idDomanda].elabora(o, p, r, idUtente);
    }

}
