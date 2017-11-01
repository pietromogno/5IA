/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Davide Porcu
 */
public class Server {

    private HashMap<Integer, ManageClientTask> clientAttivi;
    private int porta;

    public Server(int porta) {
        this.porta = porta;
    }

    public void start() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket server;
        clientAttivi = new HashMap<>();
        try {
            System.out.println("Avvio server...");
            server = new ServerSocket(porta);
            System.out.println("Pronto!");
            while (true) {
                try {
                    Socket connessione = server.accept();
                    ManageClientTask nuovoClient = new ManageClientTask(this, connessione);
                    executor.execute(nuovoClient);
                } catch (IOException ex) {
                    System.out.println("Errore->" + ex);
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public synchronized void aggiungiClient(ManageClientTask client) {
        clientAttivi.put(client.idClient, client);
    }

    public ManageClientTask getDestinatario(int idDestinatario) {
        if (clientAttivi.containsKey(idDestinatario)) {
           
            return clientAttivi.get(idDestinatario);
        }
        return null;//se non è attivo o non c'è come account registrato
    }

    public synchronized void rimuoviClient(ManageClientTask client) {
        clientAttivi.remove(client.idClient);
    }
    
    
    public boolean isClientGiaInEsecuzione(int idClient) {
       return clientAttivi.containsKey(idClient);
    }

    /*####################*/
    public static void main(String[] args) {
        Server server = new Server(9999);
        server.start();
    }

}
