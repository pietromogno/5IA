package spaccino;

public class Spaccino {

    static Object[][][] spacc;
    static String[] domanda = new String[]{
        "Come ti chiami?", "Quale ganja vuoi", "Quanti grammi?"
    };

    public static void Spaccino() {
        spacc = new Object[4][4][3];
        spacc[0][1] = new String[]{""};
        spacc[1][2] = new String[]{"Californi Kush", "Lemon Haze", "Purple Haze"};
        spacc[2][3] = new String[]{"0.5 g", "1.0 g", "2.0 g"};
    }

    public static String getDomandaSuccessiva(String domandaS, String rispostaS) { //domanda e risposta dal server
        int r = 0, c = 0, i = 0;
        while (r < 3) { //ciclo che trova la domanda a cui si è arrivati
            if (domanda[r].equals(domandaS)) {
                while (c < 3) { //ciclo che trova il gruppo di risposte
                    if (spacc[r][c] != null) {
                        while (i < 3) {//ciclo che confronta la risposta ricevuta
                            if (spacc[r][c][i].equals(rispostaS)) {
                                return domanda[c]; //trovato lo stato per la domanda successiva
                            } else {
                                i++;
                            }
                        }
                    } else {
                        c++;
                    }
                }
            } else {
                r++;
            }
        }
        return domandaS; //se non ho la risposta che voglio, rifaccio la stessa domanda
    }

    public static String getRisposta(String domandaS) { //domanda dal server
        int r = 0, c = 0, i = 0;
        String risposte = "";
        while (r < 3) { //trovo la domanda
            if (domanda[r].equals(domandaS)) {
                while (c < 3) { //trovo il gruppo di risposte di quella domanda
                    if (spacc[r][c] != null) {
                        i = 0;
                        while(i<3){ //faccio la stringa per la risposta
                            risposte += spacc[r][c][i] + " ";
                            i++;
                        }
                    } else {
                        c++;
                    }
                }
            } else {
                r++;
            }
        }
        return risposte;
    }
}
