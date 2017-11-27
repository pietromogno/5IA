package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.Message;

/**
 * @author Emanuele Pagotto
 */
public class Client {

    private ObjectInputStream inp;
    private ObjectOutputStream out;
    private Socket server;
    private ArrayList<Message> chat;

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

    public void register(String userName, String password) throws IOException {
        Message m = new Message(userName, password, 1);
        out.writeObject(m);
    }

    public void login(String userName, String password) {
        try {
            Message m = new Message(userName, password, 0);
            out.writeObject(m);
            System.out.println(inp.readObject());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sendMessage(String message, int dest) {
        try {
            out.writeObject(new Message(message, dest, dest));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void receiveMessages() throws IOException, ClassNotFoundException{
        int nNewMessages = inp.readInt();
        for(int i = 0; i < nNewMessages; i++){
            chat.add((Message) inp.readObject());
        }
    }
}
