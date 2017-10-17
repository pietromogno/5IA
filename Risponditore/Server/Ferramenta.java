package server;

import java.util.ArrayList;

/**
 * @author Davide Porcu
 */
public class Ferramenta {

    private static final String ANYSTRINGALLOWED = "<any>";
    private static final int STATIFISSI = 6;
    
    private static String[] domandeStati;
    private static Funzione[] funzioniPerStato;
    private static Object[][][] matriceDiAdiacenza;
   
    public static Listino listino;
    
    private final static Funzione getScontrino = (rispostaUtente, cliente) -> {
        return cliente.getScontrinoOrdine();
    };

    private final static Funzione addProdotto = (rispostaUtente, cliente) -> {
        cliente.addProdotto(listino.getProdotto(rispostaUtente));
        return null;
    };

    private final static Funzione setNomeCliente = (rispostaUtente, cliente) -> {
        cliente.setNome(rispostaUtente);
        return null;
    };

    public static void apriNegozio() {
        loadListino();
        
        Categoria[] listaCategoria = listino.getArrayDiCategorie();
        int statiTotali = STATIFISSI + listaCategoria.length;

        domandeStati = new String[statiTotali];
        //domande fisse per gli stati 
        domandeStati[0] = "";//serve solo per ottenere la prima domanda
        domandeStati[1] = "Benvenuto nella Ferramenta \"Rotelle Fuori Posto\", come ti chiami?";
        domandeStati[2] = "Cosa vuoi ordinare?";
        domandeStati[3] = "Vuoi ordinare un altro prodotto?";
        domandeStati[4] = "Ecco il tuo ordine: (premi invio per proseguire)";
        domandeStati[5] = "Grazie e arriverderci! Digita \"exit\" per uscire!";
        
        matriceDiAdiacenza = new Object[statiTotali][statiTotali][];
        //relazioni fisse tra gli stati
        matriceDiAdiacenza[0][1] = new String[]{""};//stato iniziale
        matriceDiAdiacenza[1][2] = new String[]{ANYSTRINGALLOWED};//richiesta nome
        matriceDiAdiacenza[3][2] = new String[]{"Si"};//richiesta altro prodotto
        matriceDiAdiacenza[3][4] = new String[]{"no"};//richiesta altro prodotto
        matriceDiAdiacenza[4][5] = new String[]{ANYSTRINGALLOWED};//mostra scontrino
        //matriceDiAdiacenza[5][5] = new String[]{ANYSTRINGALLOWED};//stato finale ->rimango sempre nello stesso stato

        funzioniPerStato = new Funzione[statiTotali];
        funzioniPerStato[1] = setNomeCliente;
        funzioniPerStato[3] = getScontrino;
        
        //STATI COSTRUITI DINAMICAMENTE
        int numCategorie = listaCategoria.length;
        for (int i = 0; i < numCategorie; i++) {
            matriceDiAdiacenza[2][STATIFISSI + i] = new Categoria[]{listaCategoria[i]};//aggiungo categoria
            matriceDiAdiacenza[STATIFISSI + i ][3] = listino.getArrayProdottiDiCategoria(listaCategoria[i]);//aggiungo prodotti della categoria
            funzioniPerStato[STATIFISSI+i]=addProdotto;//aggiungo lambda a array di funzioni da eseguire 
            domandeStati[STATIFISSI+i]="Scegli un prodotto della categoria: "+listaCategoria[i].getNomeCategoria();//aggiungo domanda per scegliere i prodotti della categoria
        }
    }

    private static void loadListino() {
        listino = new Listino();
        ArrayList<Categoria> listaCategoria=UtilSQLiteDB.getListaCategorie();
        for(Categoria c:listaCategoria){
            ArrayList<Prodotto> listaProdotti=UtilSQLiteDB.getListaProdottiDellaCategoria(c);
            for(Prodotto p:listaProdotti) {
               listino.addProdotto(p);
            }
        }
    }

    public static String getDomandaSuccessiva(String domandaCorrente, String userRisposta) {
        int indexDomanda = indexOf(domandeStati, domandaCorrente);//esiste per forza
        int indexRisposta = getColumnIndexRisposta(indexDomanda, userRisposta);
        String domandaSuccessiva;
        if (indexRisposta >= 0) {

            domandaSuccessiva = domandeStati[indexRisposta];
        } else {
            domandaSuccessiva = domandaCorrente;
        }
        return domandaSuccessiva;
    }

    public static String[] eseguiAzioneCorrispondente(String domandaCorrente, String userRisposta, OrdineCliente cliente) {
        int indexDomanda = indexOf(domandeStati, domandaCorrente);//esiste per forza
        int indexRisposta = getColumnIndexRisposta(indexDomanda, userRisposta);
        String[] altriMessaggiPerClient = null;
        if (indexRisposta >= 0&& funzioniPerStato[indexDomanda]!=null) {
            altriMessaggiPerClient = funzioniPerStato[indexDomanda].getAndDoSomething(userRisposta, cliente);
        }
        return altriMessaggiPerClient == null ? new String[0] : altriMessaggiPerClient;
    }

    public static ArrayList<String> getElencoRispostePossibili(String domandaCorrente) {
        int indexDomanda = indexOf(domandeStati, domandaCorrente);//esiste per forza
        ArrayList<String> elencoRisposte = new ArrayList<>();
        for (int i = 0; i < matriceDiAdiacenza[indexDomanda].length; i++) {
            Object[] risposteTransizione = matriceDiAdiacenza[indexDomanda][i];
            if (risposteTransizione != null) {
                for (Object risposta : risposteTransizione) {
                    elencoRisposte.add(risposta.toString());
                }
            }
        }
        elencoRisposte.remove(ANYSTRINGALLOWED);
        return elencoRisposte;
    }

    private static int getColumnIndexRisposta(int indexDomanda, String userRisposta) {
        int indexElencoRisposte = 0;
        while (indexElencoRisposte < matriceDiAdiacenza[indexDomanda].length) {
            Object[] elencoRisposte = matriceDiAdiacenza[indexDomanda][indexElencoRisposte];
            if (elencoRisposte != null && indexOf(elencoRisposte, userRisposta) >= 0) {
                return indexElencoRisposte;
            }
            indexElencoRisposte++;
        }
        return -1;
    }

    private static int indexOf(Object[] a, String x) {
        int i = 0;
        while (i < a.length) {
            if (a[i].toString().equalsIgnoreCase(x) || a[i].equals(ANYSTRINGALLOWED)) {//a[i].equals(ANYSTRINGALLOWED) indica che non c'è una risposta precisa -> utile per stato con un unica direzione percorribile
                return i;
            }
            i++;
        }
        return -1;
    }
    
}

@FunctionalInterface
interface Funzione {

    public String[] getAndDoSomething(String rispostaUtente, OrdineCliente cliente);

}
