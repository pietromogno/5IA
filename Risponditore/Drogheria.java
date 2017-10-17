/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drogheria;

import java.util.ArrayList;

/**
 *
 * @author Piergiorgio
 */
public class Drogheria {

    public ArrayList<String[]> messaggi;
    String[] accoglienza = {"Buongiorno, come posso esserle utile?", "Buonasera", "Salve", "Prego, mi dica", "Posso aiutarla?"};
    String[] qualita = {"La vuole sfusa o inbustata?"};
    String[] richiestaSfusa = {"Ok, in che quantita?", "Quanta gliene serve?", "Perfetto, quanta ne vuole?"};
    String[] richiestaBusta = {"Ok, quante buste?", "Quante buste vuole?", "Quante gliene servono?"};
    String[] altro = {"Ok, le serve altro? (Se si dire si + cosa si vuole)"};
    String[] porgi = {"Ecco a lei", "Ecco qua", "Tenga", "Eccoci"};
    String[] congedo = {"Buonagiornata!", "Buonaserata!", "Arrivederci!", "Prego, a presto!"};

    public Drogheria() {
        messaggi = new ArrayList<>();
        setBaseMessaggi();
    }

    void setBaseMessaggi() {
        messaggi.add(accoglienza);
        messaggi.add(qualita);
        messaggi.add(richiestaBusta);
        messaggi.add(richiestaSfusa);
        messaggi.add(altro);
        messaggi.add(porgi);
        messaggi.add(congedo);
    }

    public int bustaOFresca(String richiesta) {
        String[] spezMex = richiesta.split(" ");
        int i = 0;
        while (i < spezMex.length) {
            if (spezMex[i].compareTo("inbustata") == 0 || spezMex[i].compareTo("Inbustata") == 0) {
                return 2;
            } else if (spezMex[i].compareTo("sfusa") == 0 || spezMex[i].compareTo("Sfusa") == 0) {
                return 3;
            }
            i++;
        }
        return 1;
    }

    public int ripeti(String risposta) {
        String[] spezMex = risposta.split(" ");
        int i = 0;
        while (i < spezMex.length) {
            if (spezMex[i].compareTo("si") == 0 || spezMex[i].compareTo("Si") == 0 || spezMex[i].compareTo("SI") == 0) {
                return 1;
            } else if (spezMex[i].compareTo("no") == 0 || spezMex[i].compareTo("No") == 0 || spezMex[i].compareTo("NO") == 0) {
                return 6;
            }
            i++;
        }
        return 4;
    }

}
