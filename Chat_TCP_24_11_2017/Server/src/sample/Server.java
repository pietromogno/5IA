package sample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private int port, clientId;
    protected int serverId;
    private HashMap<Integer, ClientHandler> activeClient;
    private ExecutorService executor;
    private ServerSocket server;

    public Server(int port) {
        this.port = port;
        clientId = 0;
        serverId = -1;
        activeClient = new HashMap<>();
    }

    public void start() {
        executor = Executors.newCachedThreadPool();
        try {
            System.out.println("Starting server...");
            server = new ServerSocket(port);
            System.out.println("Server is ready!");
            while (true) {
                try {
                    Socket connection = server.accept();
                    ClientHandler newClient = new ClientHandler(this, connection, clientId++);
                    executor.execute(newClient);//threadpool
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized HashMap<Integer, ClientHandler> getActiveClient() {
        return activeClient;
    }

    public synchronized void addClient(ClientHandler client) {
        activeClient.put(client.clientId, client);
        System.out.println("Client: " + client.getClientId() + ": "
                + client.getClientUsername()
                + " added to the active clients list.");
    }


    public synchronized void removeClient(ClientHandler client) {
        activeClient.remove(client.getClientId());
        System.out.println("Client: " + client.getClientId() + ": "
                + client.getClientUsername()
                + " removed from the active clients list.");
    }

    public synchronized void sendMessage(ClientHandler client, Message message) throws IOException {
        try {
            activeClient.get(client.getDestinationId()).sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server serverProva = new Server(9898);
        serverProva.start();
    }
}
