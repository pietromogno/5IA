package risponditore;

/**
 *
 * @author Francesco Forcellato
 */
public class Pizzeria {

    private static String[][][] grafo;
    private static String[] domande;
    private static String[] noOrdine;
    private static final int STATI = 10;

    private static void inizializza() {
        grafo = new String[STATI][STATI][];
        domande = new String[STATI];
        noOrdine = new String[]{"Si", "No"};//Contiene tutte le conferme che non fanno parte dell'ordine
        domande[0] = "Qual'è il tuo nome?";
        domande[1] = "che pizza vuoi?";
        domande[2] = "vuoi anche da bere?";
        domande[3] = "che cosa vuoi da bere?";
        domande[4] = "confermi l'ordine?";
        domande[5] = null;

        grafo[0][1] = new String[]{""};//sarebbe il nome, può essere qualsiasi cosa

        grafo[1][2] = new String[]{
            "NORD Olio di Oliva, Grana Padano a scaglie",
            "EST Olio di Oliva, Aglio",
            "SUD Olio di Oliva, Salamino Piccante, olive nere",
            "OVEST Olio di Oliva, Rosmarino, Grana Padano",
            "CENTRO Olio di Oliva all’Aglio, Grana Padano, Pomodorini Cirietto, Origano",
            "ROSA DEI VENTI Olio di Oliva all\'Aglio, Monte Veronese",
            "PIZZOTELLA Pizza Fritta",
            "CALZONE pomodoro, mozzarella, prosciutto, funghi champignon, ricotta, grana in cottura",
            "CAPRICCIOSA pomodoro, mozzarella, prosciutto, funghi champignon, carciofi, capperi, olive nere (con nocciolo), origano",
            "CLASSICA pomodoro, mozzarella, prosciutto, funghi champignon, salamino piccante",
            "DIAVOLA pomodoro, mozzarella, salamino piccante",
            "FUNGHI pomodoro, mozzarella, funghi champignon",
            "MARGHERITA pomodoro, mozzarella",
            "MARINARA pomodoro, olio all'aglio, origano",
            "NAPOLETANA pomodoro, mozzarella, capperi, acciughe, origano pomodorini cirietto, grana in cottura",
            "ORTOLANA pomodoro, mozzarella, zucchine e melanzane grigliate, patate e peperoni al forno, spinaci, pomodorini ciliegino, grana in cottura",
            "PARMA pomodoro, mozzarella, crudo di Parma",
            "PARMIGIANA pomodoro, mozzarella, melanzane grigliate, grana in cottura",
            "PROSCIUTTO pomodoro, mozzarella, prosciutto cotto",
            "PROSCIUTTO E FUNGHI pomodoro, mozzarella, prosciutto, funghi champignon",
            "4 FORMAGGI pomodoro, mozzarella, ricotta, gorgonzola dolce, emmenthal",
            "4 STAGIONI pomodoro, mozzarella, prosciutto, funghi champignon, carciofi",
            "ROMANA pomodoro, mozzarella, acciughe, origano",
            "SICILIANA pomodoro, origano, capperi, olive nere (con nocciolo), acciughe, melanzane grigliate",
            "SPECK E MASCARPONE pomodoro, mozzarella, mascarpone, speck",
            "TONNO E CIPOLLA pomodoro, mozzarella, tonno, cipolla",
            "VERDE E CRUDO pomodoro, mozzarella, gorgonzola dolce, crudo di Parma",
            "VIENNESE pomodoro, mozzarella, würstel"
        };

        grafo[2][3] = new String[]{"Si"};
        grafo[2][4] = new String[]{"No"};

        grafo[3][4] = new String[]{"CocaCola", "Pepsi", "Sprite", "Birra", "Fanta"};

        grafo[4][5] = new String[]{"Si"};
        grafo[4][1] = new String[]{"No"};
        grafo[5][0] = null;
    }

    public static String[] nonFaParteDiOrdine() {
        return noOrdine;
    }

    public static String getProssimaDomanda(String domanda, String risposta) {
        int dom = indiceDomanda(domanda);
        int risp = indiceRisposta(dom, risposta);
        if (risp == -1) {
            return domanda;
        }
        return domande[risp];
    }

    public static String[] getRisposte(String domanda) {
        int k = indiceDomanda(domanda);
        String[] ris = null;
        for (int i = 0; i < grafo[k].length; i++) {
            if (grafo[k][i] != null) {
                String[] h = grafo[k][i];
                if (ris == null && h != null) {
                    ris = h;
                } else if (h != null) {
                    String[] aux = ris.clone();
                    ris = new String[ris.length + h.length];
                    for (int p = 0; p < aux.length; p++) {
                        ris[p] = aux[p];
                    }
                    for (int p = ris.length - 1; p < aux.length + h.length; p++) {
                        ris[p] = h[p - aux.length];
                    }
                }
            }
        }
        return ris;
    }

    private static int indiceDomanda(String domanda) {
        int i = 0;
        for (i = 0; i < STATI; i++) {
            if (domande[i].compareTo(domanda) == 0) {
                return i;
            }
        }
        return -1;
    }

    private static int indiceRisposta(int indiceDomanda, String risposta) {
        int i = 0;
        for (i = 0; i < grafo[indiceDomanda].length; i++) {
            for (int j = 0; grafo[indiceDomanda][i] != null && j < grafo[indiceDomanda][i].length; j++) {
                if (grafo[indiceDomanda][i][j].compareTo(risposta) == 0 || grafo[indiceDomanda][i][j].compareTo("") == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static String getPrimaDomanda() {
        if (domande == null) {
            inizializza();
        }
        return domande[0];
    }

}
