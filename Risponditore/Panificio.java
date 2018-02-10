package server;

public class Panificio {

    String[][] grafoRisposte;
    double costo = 0; 
    String[] domande = {"Quale tipo di pane desidera? (morbido croccante)", "Quale forma di pane preferisce? (bocconcino tartaruga rosetta spiga spaccatina pagnotta)", "Desidera altro pane? Costo attuale: "+costo()};
    byte cnt; // ricorda a quale stato della conversazone siamo

    public Panificio() { 
        cnt = 0;
        grafoRisposte = new String[3][8];
        initGrafo(grafoRisposte);
        //popolameno del grafo con le risposte
        grafoRisposte[0][1] = "morbido";
        grafoRisposte[0][2] = "croccante";
        grafoRisposte[1][2] = "bocconcino";   
        grafoRisposte[1][3] = "rosetta";   
        grafoRisposte[1][4] = "tartaruga";
        grafoRisposte[1][5] = "spiga"; 
        grafoRisposte[1][6] = "spaccatina";   
        grafoRisposte[1][7] = "pagnotta";  
        grafoRisposte[2][1] = "si";  
        grafoRisposte[2][2] = "no";  
    }
    
    private String[][] initGrafo(String[][] s) {
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++) {
                s[i][j] = "";
            }
        }
        return s;
    }
    
    private double costo(){
        costo += Math.random();
        return costo;
    }   
    
    public String getDomanda() {
        return domande[cnt];
    }

    public String getRisposta(String s) {
        for (byte i = 0; i < grafoRisposte[cnt].length; i++) { //ciclo per individuare la risposta e la relativa domanda successiva
            if (grafoRisposte[cnt][i].equals(s)) { 
                if(cnt == 2 && s.equals("si")) {
                    cnt = 0; return ""; 
                }
                cnt++;
                return "";
            } 
        } return "";
    }

    private boolean fineConversazione(byte k) {
        for (byte i = 0; i < grafoRisposte[cnt].length; i++) { //se tutti i nodi del grafo sono vuoti non ci sono risposte da dare
            if (!grafoRisposte[cnt][i].equals("")) {
                return false;
            }
        }
        return true;
    }
}