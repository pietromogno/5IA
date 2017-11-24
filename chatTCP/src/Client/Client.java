package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.Message;

/**
 * @author Emanuele Pagotto
 */
public class Client {

    private static ObjectInputStream inp;
    private static ObjectOutputStream out;
    private static Socket server;

    public Client(String address, int port) {
        try {
            server = new Socket(address, port);
            out = new ObjectOutputStream(server.getOutputStream());
            out.flush();
            inp = new ObjectInputStream(server.getInputStream());
        } catch (IOException ex) {
            System.out.println("ops");
        }
    }

    public static void register(String userName, String password) throws IOException {
        Message m = new Message(userName, password, 1);
        out.writeObject(m);
    }

    public static void login(String userName, String password) {
        try {
            Message m = new Message(userName, password, 0);
            out.writeObject(m);
            System.out.println(inp.readObject());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void sendMessage(String message, int dest) {
        try {
            out.writeObject(new Message(message, dest, dest));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void receiveMessage(Message m){
        System.out.println(m.getName() +": "+m.getMessage());
    }

    public static void main(String[] args) {
        try {
            server = new Socket("localhost", 9090);
            out = new ObjectOutputStream(server.getOutputStream());
            out.flush();
            inp = new ObjectInputStream(server.getInputStream());
            login("MonkeyMan", "aaaa");
            Scanner t = new Scanner (System.in);
            System.out.println("Vuoi inviare il messaggio?");
            String c = t.next();
            if(c.equals("y")) sendMessage("Ciao",1);
            System.out.println(inp.readObject());
            server.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("ops");
        }
    }
}
