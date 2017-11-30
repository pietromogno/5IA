package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Matteo Favaro
 */
public class Server {

    private HashMap<String, GestisciClient> clientAttivi;  // contiene gli utenti attivi
    private final int PORTA = 9999;

    public Server() {
    }

    public void start() {
        ServerSocket server;
        clientAttivi = new HashMap<>();
        try {
            System.out.println("Avvio server...");
            server = new ServerSocket(PORTA);
            System.out.println("Pronto!");
            while (true) {
                try {
                    Socket connessione = server.accept();
                    Thread nuovoClient = new GestisciClient(this, connessione);
                    nuovoClient.start();
                } catch (IOException ex) {
                    System.out.println("Errore->" + ex);
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public synchronized GestisciClient getClientAttivoFromUsername(String nomeClient) {
        GestisciClient destinatario = null;
        if (clientAttivi.containsKey(nomeClient)) {
            destinatario = clientAttivi.get(nomeClient);
        }
        return destinatario;
    }

    public synchronized ArrayList<String> getListaNomiClientAttivi() {
        int lung = clientAttivi.size();
        ArrayList<String> listaNomi = new ArrayList<>();
        for (String nomeClient : clientAttivi.keySet()) {
            listaNomi.add(nomeClient);
        }
        return listaNomi;
    }

    public synchronized void aggiungiClient(GestisciClient clientAttivo) {
        clientAttivi.put(clientAttivo.getNomeClient(), clientAttivo);
    }

    public synchronized void rimuoviClient(GestisciClient clientAttivo) {
        clientAttivi.remove(clientAttivo.getNomeClient());
    }

    public static void main(String[] args) {
        Server s = new Server();
        s.start();
    }

    public void notificaUpdateListaUtentiAttivi(ArrayList<String> listaUtentiDaNotificare) {      // invio a tutti gli utenti connessi chi è online 
        for (String username : listaUtentiDaNotificare) {
            GestisciClient gc = clientAttivi.get(username);
            if (gc != null) {//non si sa mai
                ArrayList<String> listaUtenti = (ArrayList<String>) listaUtentiDaNotificare.clone();
                System.out.println(listaUtenti);
                listaUtenti.remove(username);
                System.out.println("cio che invio:\n"+listaUtenti);
                String[] array = new String[listaUtenti.size()];
                array = listaUtenti.toArray(array);
                gc.scriviMsg(" ", " ", "UPDATELISTAUTENTIATTIVI", array);
            }
        }

    }

}
