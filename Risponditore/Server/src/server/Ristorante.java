/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 * @author Musone Mattia
 */
public class Ristorante {

    private String matrice[][] = {{"0", "Benvetuto nella chat di prenotazione pasto automatica, sta parlando col ristorante Zuccante, qual è il suo nome?", "1"},
    {"1", ", scegli il menù digitando il numero corrispondente:"
        + "\n1) MENU' PIZZA"
        + "\n2) MENU' RISTORANTE", "2", "3"},
    {"2", ", ecco il menù PIZZE:"
        + "\n- Capricciosa"
        + "\n- Margherita"
        + "\n- Prosciutto e funghi"
        + "\n- Fantasia dello chef", "4"},
    {"3", ", ecco il menù RISTORANTE:"
        + "\n- Lasagna"
        + "\n- Primo a base di pesce"
        + "\n- Grigliata"
        + "\n- Affettato"
        + "\n- Carne di maiale", "4"},
    {"4", ", vuoi qualcosa da bere?"
        + "\n1) Sì"
        + "\n2) No", "5", "8"},
    {"5", ", scegli il menù digitando il numero corrispondente:"
        + "\n1)BIBITE ANALCOLICHE"
        + "\n2)BIBITE ALCOLICHE", "6", "7"},
    {"6", ", ecco il menù di BIBITE ANALCOLICHE:"
        + "\n- Fanta"
        + "\n- Coca-cola"
        + "\n- Acqua", "8"},
    {"7", ", ecco il menù di BIBITE ALCOLICHE:"
        + "\n- Vino"
        + "\n- Birra", "8"},
    {"8", ", confermi l'ordine?"
        + "\n1)Sì"
        + "\n2)No"
        + "\n***RIEPILOGO:\n", "9", "0"},
    {"9", ", grazie per aver ordinato, riceverai il pasto in poco tempo!"}};

    private String risposta[][] = {
        {"Nome: ", ""},
        {"Pasto: ", ""},
        {"Bibita: ", "Nessuna"}};
    private int nodo, arco, cursore;

    Ristorante() {
        nodo = -1;
        arco = 2;
        cursore = 0;
    }

    String giveRisposta(String domanda) {
        arco = 2;
        if (nodo == 0) {
            cursore = 0;
            risposta[2][1] = "Nessuna";
        }
        //cursore = nodo == 0 ? 0 : cursore;
        if (domanda.equals("2")) {
            arco++;
            nodo = Integer.parseInt(matrice[nodo][arco]);
        } else {
            nodo = nodo != -1 ? Integer.parseInt(matrice[nodo][arco]) : 0;
        }
        if (nodo > 0 && nodo < matrice.length - 1 && !domanda.equals("1") && !domanda.equals("2")) {
            risposta[cursore++][1] = domanda;
        }
        return "\n\n" + (nodo == (matrice.length - 2) ? risposta[0][1] + matrice[nodo][1] + riepilogo(risposta) : (nodo == 0 ? "" : risposta[0][1]) + matrice[nodo][1]);
    }

    private String riepilogo(String risposta[][]) {
        String ris = "";
        for (int i = 0; i < risposta.length; i++) {
            for (int j = 0; j < risposta[i].length; j++) {
                ris += risposta[i][j];
            }
            ris += "\n";
        }
        return ris;
    }
}
