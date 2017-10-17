package server;

import javaOSD.strutture.nodi.NodoConc;

public class ServizioTaxi {

    String[][] grafoRisposte; //struttura di grafo a amatrice di adiacenza, occero l'automa
    final String[] domande = {"vuole un passaggio?[si/no]", "dove vuole andare? [casa/casa dell'autista]", "sono 25$", "arrivederci", "il viaggio è gratuito"}; //array contenente le domande di base
    int cont; // indice che tiene conto delle domande da porre e segue il flusso della conversazione

    public ServizioTaxi() {
        cont = 0; 
        grafoRisposte = new String[5][5];
        creaGrafo(grafoRisposte);
        //popolameno del grafo con le risposte
        grafoRisposte[0][1] = "si";
        grafoRisposte[0][3] = "no";
        grafoRisposte[1][2] = "casa";
        grafoRisposte[1][4] = "casa dell'autista";
    }
    
    /*
    *@brief procedura che popola il garfo di stringhe vuote per favorire i confronti al suo interno
    */
    private String[][] creaGrafo(String[][] s) {
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++) {
                s[i][j] = "";
            }
        }
        return s;
    }

    /*
    *@brief metodo che estrae la risposta da dare all'affermazione del cliente
    *@param s è la stringa data in input dal client
    */
    public String getRisposta(String s) {
        for (int i = 0; i < grafoRisposte[cont].length; i++) { //ciclo per individuare la risposta e la relativa domanda successiva
            if (grafoRisposte[cont][i].equals(s)) { 
                cont = i;
                return "ok";
            }
        }
        if (fineDiscorso(cont)) { //se la domanda a cui si è arrivati non possiede risposte significa che il discorso è finito o errato
            return "deve smetterla di importunarmi..."; //discorso finito
        } else {
            return "non ho capito"; //risposta sbagliata
        }
    }

    /*
    *@brief metodo per controllare se la domanda a cui si è arrivati possiede o meno risposte possibili
    */
    private boolean fineDiscorso(int k) {
        for (int i = 0; i < grafoRisposte[cont].length; i++) { //se tutti i nodi del grafo sono vuoti non ci sono risposte da dare
            if (!grafoRisposte[cont][i].equals("")) {
                return false;
            }
        }
        return true;
    }

    /*
    *@brief metodo per la comunicazione al client della domanda 
    */
    public String getDomanda() {
        return domande[cont];
    }
}
