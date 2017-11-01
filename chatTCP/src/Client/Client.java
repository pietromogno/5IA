package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.Message;
import objects.User;

/**
 * @author Emanuele Pagotto
 */
public class Client {

    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static Socket server;
    private static Object[] connectedUsers;

    public Client() {
        try {
            server = new Socket("localhost", 9090);
            out = new ObjectOutputStream(server.getOutputStream());
            out.flush();
            in = new ObjectInputStream(server.getInputStream());
        } catch (IOException ex) {
            System.out.println("ops");
        }
    }

    private static void register(String userName, String password) throws IOException {
        Message m = new Message(userName, password, 1);
        out.writeObject(m);
    }

    private static void login(String userName, String password) {
        try {
            Message m = new Message(userName, password, 0);
            out.writeObject(m);
            System.out.println(in.readObject());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void sendMessage(String message, User dest) {
        try {
            out.writeObject(new Message(message, dest, dest));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        try {
            server = new Socket("localhost", 9090);
            out = new ObjectOutputStream(server.getOutputStream());
            out.flush();
            in = new ObjectInputStream(server.getInputStream());
            login("MonkeyMan", "aaaa");
            Scanner t = new Scanner (System.in);
            System.out.println("Vuoi inviare il messaggio?");
            String c = t.next();
            if(c.equals("y")) sendMessage("Ciao",new User(1,"MonkeyMan",server));
            Message r = (Message) in.readObject();
            String m = r.getMessage();
            System.out.println(m);
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("ops");
        }

    }
}
