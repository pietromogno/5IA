 package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import objects.User;

/**
 * @author Emanuele Pagotto
 */
public class Server {
    
    private static ArrayList <User> connectedClients;

    public static void main(String[] args) {
        try {
            connectedClients = new ArrayList<>();
            System.out.println("The server has started");
            ExecutorService threadExe = Executors.newCachedThreadPool();
            ServerSocket mainSck = new ServerSocket(9090);
            while (true) {
                Socket threadSck = mainSck.accept();
                threadExe.execute(new ManageClient(threadSck));
            }
        } catch (IOException e) {
            System.err.println("Client Error: " + e.getMessage());
            System.err.println("Localized: " + e.getLocalizedMessage());
            System.err.print("Stack Trace: ");
            e.printStackTrace();
        }
    }
    
    protected static void addConnectedUser(User u){
        connectedClients.add(u);
        System.out.println("srv: Utente "+u.getUsrName()+" connesso");
    }
}
