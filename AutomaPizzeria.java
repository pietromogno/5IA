package server;
/**
 * @brief è la struttura statica a grafo rappresentato trammite una matrice
 * che stabilisce le domande che la Pizzeria dovrà fare al cliente
 * @author Matteo Mognato
 * @date 10/10/2017
 */
class AutomaPizzeria {
    
    private static final int N=7;
    
    private static final String[] DOMANDE = {
        "Buongiorno pizzeria Mognato mi può dire nome e cognome",
        "Quante pizze vuole?",
        "Quali pizze vuole?",
        "Viene a prenderle o portiamo noi? vengo/portiamo",
        "Via?",
        "Telefono",
        "Per che ora?",
        "Conferma? si/no/inizio"
    };
    
    /**
     * @brief passando la Striga della risposta crea la matrice indicante se in
     * in base alla risposta l'arco esiste
     * @param s
     * @return risposte ovvero la matrice di booleani
     */
    private static boolean [][] creaMatriceRisposte(String s){
        boolean[][] risposte = {{false, s instanceof String, false, false, false, false, false, false},
                                {false, false, s instanceof String, false, false, false, false, false},
                                {false, false, false, s instanceof String, false, false, false, false},
                                {false, false, false, false, s.equals("portiamo"), false, s.equals("vengo"), false},
                                {false, false, false, false, false, s instanceof String, false, false},
                                {false, false, false, false, false, false, s instanceof String, false},
                                {false, false, false, false, false, false, false, s instanceof String},
                                {s.equals("inizio"), false, false, false, false, false, false, false}};
        return risposte;
    }
    
    /**
     * @brief posta una domanda e la sua risposta dice la domanda successiva
     * @param risposta
     * @param domanda
     * @return String domanda succesiva
     */
    public static String domanda(String risposta, String domanda) {
        
        if (domanda == null) {
            return DOMANDE[0];
        } else {
            if(domanda.equals(DOMANDE[7])& (risposta.equals("si")||risposta.equals("no"))) return "FINE";
            boolean [][] risposte=creaMatriceRisposte(risposta);
            int i=0; 
            for(;i<=N;i++) if(DOMANDE[i].equals(domanda)) break; //i indica il numero di domanda
            for(int j=0; j<=N; j++) {
                if(risposte[i][j]) {
                    return DOMANDE[j];
                }
            }
            return domanda;
        }
    }
}
