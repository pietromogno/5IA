package risponditore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Francesco Forcellato
 */
public class Server {
    
    private static final int PORTA = 9999;
    
    public static void main(String[] args) throws Exception {
        System.out.println("Server lanciato.");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(PORTA);
        try {
            while (true) {
                // crea il thread e lo lancia
                new Gestore(listener.accept(), clientNumber++).start();
            }
        } finally {
            listener.close();
        }
    }
    
    private static class Gestore extends Thread {
        
        private Socket socket;
        private int clientNumber;
        private String nome;
        
        public Gestore(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            myLog("Nuova connessione con il client #" + clientNumber + " su " + socket);
        }
        
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                
                String risposta = "";
                String domanda = Pizzeria.getPrimaDomanda();
                out.println(domanda);
                out.println("");
                ArrayList<String> ordine = new ArrayList<>();
                ArrayList<String> domandeFatte = new ArrayList<>();
                while (true) {
                    risposta = in.readLine();
                    if (socket.isClosed() || !socket.isConnected() || risposta == null) {
                        out.println("Connessione chiusa per il client #" + clientNumber + ".");
                        socket.close();
                        break;
                    }
                    if (nome == null) {
                        nome = risposta;
                    }
                    String h = Pizzeria.getProssimaDomanda(domanda, risposta);
                    if (h != null && h.compareTo(domanda) != 0) {
                        if (domandeFatte.contains(domanda)) {
                            ordine = new ArrayList<>();
                            ordine.add(nome);
                            domandeFatte = new ArrayList<>();
                        }
                        ordine.add(risposta);
                        domandeFatte.add(domanda);
                    }
                    if (h == null) {
                        ordine.add(risposta);
                        String[] noOrdine = Pizzeria.nonFaParteDiOrdine();
                        for (int i = 0; i < noOrdine.length; i++) {
                            for (int j = 0; j < ordine.size(); j++) {
                                if (ordine.get(j).compareTo(noOrdine[i]) == 0) {
                                    ordine.remove(j);
                                    j--;
                                }
                            }
                        }
                        out.println("<HTML>Ordine confermato: " + ordine.toString() + "<BR>CODICE ORDINE: " + ordine.hashCode() + "</HTML >");
                        //out.println("CODICE ORDINE: " + ordine.hashCode());
                        in.readLine();
                        break;
                    }
                    domanda = h;
                    out.println(nome + ", " + domanda);
                    String[] risp = Pizzeria.getRisposte(domanda);
                    for (int i = 0; i < risp.length; i++) {
                        out.println(risp[i]);
                    }
                    out.println("");
                }
            } catch (IOException e) {
                myLog("Errore gestione client # " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    // log("Couldn't close a socket, what's going on?");
                }
                myLog("Connessione con il client # " + clientNumber + " chiusa");
            }
        }
        
        private void myLog(String message) {
            System.out.println(message + "\n");
        }
    }
    
}
