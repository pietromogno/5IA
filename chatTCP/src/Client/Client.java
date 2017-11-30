package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.Message;

/**
 * @author Emanuele Pagotto
 */
public class Client {

    private static ObjectOutputStream out;
    private static Socket server;
    messageReceiver inp;
    String userName;
            
    public Client(String address, int port) {
        try {
            server = new Socket(address, port);
            out = new ObjectOutputStream(server.getOutputStream());
            out.flush();
            inp = new messageReceiver();
        } catch (IOException ex) {
            System.out.println("ops");
        }
    }

    public void register(String userName, String password) throws IOException, ClassNotFoundException {
        Message m = new Message(userName, password, 1);
        out.writeObject(m);
    }

    public void login(String userName, String password) throws IOException, ClassNotFoundException {
        Message m = new Message(userName, password, 0);
        out.writeObject(m);   
    }
    
    public void startMessageReceiver(){
        inp.start();
    }

    public void sendMessage(String message, String dest) {
        try {
            out.writeObject(new Message(message, dest, dest));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public class messageReceiver extends Thread {

        private ObjectInputStream inp;

        public messageReceiver() throws IOException {
            inp = new ObjectInputStream(Client.server.getInputStream());
        }

        public void run() {
            try {
                String msg = (String) inp.readObject();
                System.out.println(msg);
                String op = msg.substring(0, msg.indexOf(" "));
                if (op.equals("msg")) {
                    System.out.println(msg);
                    ClientForm.receiveMsg(msg.substring(msg.indexOf(" ")));
                } else if (op.equals("conn")) {
                    System.out.println(msg);
                    ClientForm.receiveCBoxData(msg.substring(msg.indexOf(" ")));
                } else {
                    System.out.println(msg);
                    ClientForm.addToChat(msg);
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
