 package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Emanuele Pagotto
 */
public class Server {
    
    static ArrayList<String> connectedClients;
    static ArrayList<Socket> clientSockets;
    static ExecutorService threadExe;
    static ServerSocket mainSck;
    static boolean connectedClientsChanged;
    
    public static void main(String[] args) {
        try {
            connectedClients = new ArrayList<>();
            clientSockets = new ArrayList<>();
            System.out.println("The server has started");
            threadExe = Executors.newCachedThreadPool();
            mainSck = new ServerSocket(9090);
            connectedClientsChanged = false;
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
    
    protected static int addConnectedUser(String n, Socket s) throws IOException{
        connectedClients.add(n);
        clientSockets.add(s);
        connectedClientsChanged = true;
        return connectedClients.size();
    }
    
    protected static void disconnect(String n,Socket s){
        connectedClients.remove(n);
        clientSockets.remove(s);
        connectedClientsChanged = true;
    }
}
