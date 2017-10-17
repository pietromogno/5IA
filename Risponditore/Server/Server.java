package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static server.Ferramenta.*;

/**
 * @author Davide Porcu
 */
public class Server {

    static final int PORTA=9999; 
    
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();//crea thread tanti quanti a seconda della necessità e riusa quelli già creati
        ServerSocket server;
        int numeroCliente=0;
        try {
            System.out.println("Avvio server...");
            apriNegozio();
            server = new ServerSocket(PORTA);
            System.out.println("Pronto!");
            while(true){
                try {
                    Socket connessione = server.accept();
                    executor.execute(new ManageClientTask(connessione,numeroCliente++));
                } catch (IOException ex) {
                    System.out.println("Errore->" + ex);
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
