package chats;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class Server implements Runnable {

    ServerSocket serverSocket;

    ArrayList<ClientThread> clients;
    static int nClient = 0;

    public Server() {
        clients = new ArrayList<ClientThread>();
    }

    @Override
    public void run() {
        System.out.println("####AVVIO SERVER####");
        try {
            serverSocket = new ServerSocket(9898);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Nuovo client alla porta " + socket.getPort());

                ClientThread t = new ClientThread(socket, this);
                clients.add(t);
                nClient++;
                sendBroadcast(new Message("Server", "nuovo utente, ora siete in " + clients.size()));

            }
        } catch (Exception e) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public synchronized void sendBroadcast(Message msg) {
        for (ClientThread client : clients) {
            client.send(msg);
        }
    }
}

class ClientThread extends Thread {

    Socket socket = null;
    BufferedReader din;
    PrintStream dout;
    Server parent;
    String name;
    String connectionMessage;
    static int id = 0;

    public ClientThread(Socket s, Server p) {
        this.parent = p;
        socket = s;
        this.start();
        id++;
    }

    @Override
    public void run() {

        try {
            din = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dout = new PrintStream(socket.getOutputStream());

            while (true) {
                try {
                    if (din.ready()) {
                        connectionMessage = din.readLine();
                        name = connectionMessage;
                        break;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            while (true) {
                try {
                    if (din.ready()) {
                        parent.sendBroadcast(new Message(name + "", din.readLine()));
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void send(Message s) {
        try {
            while (true) {
                dout.println(s.getAuthor() + ": " + s.getMessage());
                break;
            }
        } catch (NullPointerException ex) {
            //Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
