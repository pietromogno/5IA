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

    private static ObjectOutputStream out;
    private static Socket server;
    protected static MessageReceiver inp;
    protected static String userName;
    protected static ArrayList<Message> chat;
    protected static ArrayList<String> users;
    private static ClientForm form;

    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                server = new Socket(args[0], Integer.parseInt(args[1]));
            } else {
                server = new Socket("localhost", 9090);
            }
            out = new ObjectOutputStream(server.getOutputStream());
            out.flush();
            inp = new MessageReceiver(server);
            form = new ClientForm();
            chat = new ArrayList<>();
            users = new ArrayList<>();
            inp.start();
        } catch (IOException ex) {
            System.out.println("ops");
        }
    }

    public static void sendMessage(String message, String src, String dest, String op) {
        try {
            out.writeObject(new Message(message, src, dest, op));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void getChat(String otherPerson) {
        ArrayList<Message> tmp = new ArrayList<>();
        for (Message m : chat) {
            if (m.getDst().equals(otherPerson) || m.getSrc().equals(otherPerson)) {
                tmp.add(m);
            }
        }
        form.updateChat(new Message[tmp.size()]);
    }

    public static void close() throws IOException {
        server.close();
    }

    private static class MessageReceiver extends Thread {

        private final ObjectInputStream inp;
        private final Socket server;

        public MessageReceiver(Socket server) throws IOException {
            this.server = server;
            inp = new ObjectInputStream(server.getInputStream());
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Message msg = (Message) inp.readObject();
                    String op = msg.getOp();
                    if (op.equals("msg")) {
                        Client.form.updateNotification(msg.getMessage());
                    } else if (op.equals("conn")) {
                        int max = Integer.parseInt(msg.getMessage());
                        String[] data;
                        if (max >= 0) {
                            for (int i = 0; i < max; i++) {
                                String usr = ((Message) inp.readObject()).getMessage();
                                Client.users.add(usr);
                            }
                            data = users.toArray(new String[users.size()]);
                        } else {
                            data = new String[]{"Nessun altro utente online"};
                        }
                        Client.form.updateCbox(data);
                    } else if (op.equals("chat")) {
                        int max = Integer.parseInt(msg.getMessage());
                        for (int i = 0; i < max; i++) {
                            Message m = (Message) inp.readObject();
                            Client.chat.add(m);
                        };
                        Client.chat.toArray(new Message[Client.chat.size()]);
                    }
                }
            } catch (IOException | ClassNotFoundException e) { //maaaaa...potrebbero esserci problemini qua e la, non si sa mai
                System.err.println("Client Error: " + e.getMessage());
                System.err.println("Localized: " + e.getLocalizedMessage());
                System.err.print("Stack Trace: ");
                e.printStackTrace();
            }
        }
    }
}
