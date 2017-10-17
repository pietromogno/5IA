package client;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Francesco Forcellato
 */
public class Client {

    private static final String SERVER = "localhost";
    private static final int PORTA = 9999;
    private static Socket s;
    private GraficaClient g;

    public Client() {
        g = new GraficaClient();
        Dimension grandezzaSchermo = Toolkit.getDefaultToolkit().getScreenSize();
        g.setLocation((grandezzaSchermo.width / 2) - (g.getWidth() / 2), (grandezzaSchermo.height / 2) - (g.getHeight() / 2));
        g.setVisible(true);
    }

    public static boolean provaConnessione() {
        boolean ris = true;
        try {
            Socket prova = new Socket(SERVER, PORTA);
            prova.close();
        } catch (IOException ex) {
            ris = false;
        }
        return ris;
    }

    public static void invio(String messaggio) throws IOException {
        PrintWriter out = new PrintWriter(s.getOutputStream(), true); // with autoflush
        out.println(messaggio);
    }

    public void connetti() {
        try {
            s = new Socket(SERVER, PORTA);
            System.out.println("Connessione riuscita.");
            String domanda = "";
            while (!s.isClosed() && domanda != null) {
                BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                domanda = input.readLine();
                if (domanda.contains("Ordine")) {
                    //domanda += "\n" + input.readLine();
                    g.visibile(false);
                    invio("");
                }
                g.setDomanda(domanda);
                g.resetOpzioni();
                do {
                    domanda = input.readLine();
                    if (domanda.compareTo("") != 0) {
                        g.addOpzione(domanda);
                    }
                } while (domanda.compareTo("") != 0);
                if (g.hasOpzioni()) {
                    g.visibile(true);
                } else {
                    g.visibile(false);
                }
            }
            g.errore("Connessione scaduta");
            System.out.println("Connessione scaduta");
        } catch (Exception ex) {
            //System.out.println("CLIENT: Ops... " + ex.getMessage());
            g.errore("Comunicazione: conclusa" );
        }
    }

    public static void main(String[] args) {
        Client c = new Client();
        c.connetti();
    }
}
