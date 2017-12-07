package server;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import objects.*;

/**
 * @author Emanuele Pagotto
 */
class ManageClient implements Runnable {

    private Socket s; //socket del client
    private ObjectInputStream in; //stream in input
    private ObjectOutputStream out; //stream in output
    private boolean isLoggedIn;
    private int clientId, lastMessageSent;
    private String clientUsername;

    //costruttore
    public ManageClient(Socket sck) {
        s = sck;
        isLoggedIn = false;
        lastMessageSent = 0;
        System.out.println("Connesso al client sul socket " + s.toString());
        try { //preparo gli stream
            out = new ObjectOutputStream(s.getOutputStream());
            out.flush();
            in = new ObjectInputStream(s.getInputStream());
        } catch (IOException e) { //maaaaa...potrebbero esserci problemini qua e la, non si sa mai
            System.err.println("Client Error: " + e.getMessage());
            System.err.println("Localized: " + e.getLocalizedMessage());
            System.err.print("Stack Trace: ");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message m = (Message) in.readObject();
                String op = m.getOp();
                if (op.equals("login")) {
                    login(m.getSrc(), m.getMessage());
                } else if (op.equals("register")) {
                    register(m.getSrc(), m.getMessage());
                } else {
                    registerMessage(m);
                }
                if (isLoggedIn) {
                    sendConnUsers();
                }
            }
        } catch (ClassNotFoundException | IOException | SQLException e) { //altri errori, altri ty catch
            System.err.println("Client Error: " + e.getMessage());
            System.err.println("Localized: " + e.getLocalizedMessage());
            System.err.print("Stack Trace: ");
            e.printStackTrace();
        }
    }

    public void register(String usrName, String pw) throws SQLException, ClassNotFoundException, IOException { //mi registro sul database
        if (SQLhelper.register(usrName, pw)) {
            out.writeObject(new Message("Registrazione effettuata", "server", usrName, "msg"));
            System.out.println("Utente " + usrName + " Collegato");
        } else {
            out.writeObject(new Message("Nome utente già esistente", "server", usrName, "msg"));
        }
    }

    public void login(String usrName, String pw) throws SQLException, ClassNotFoundException, IOException { //faccio il login confrontando il database
        if (!isLoggedIn) { //se il client su questo socket non ha ancora fatto il login
            if (SQLhelper.login(usrName, pw)) { //faccio il login
                System.out.println("aaaa");
                this.clientId = Server.addConnectedUser(usrName, this.s);
                this.clientUsername = usrName;
                out.writeObject(new Message("Login effettuato come " + usrName + "(#" + clientId + ")", "server", usrName, "msg"));
                isLoggedIn = true;
                sendChat();
            } else {
                out.writeObject(new Message("Dati errati", "server", usrName, "msg")); //ma i dati potrebbero non essere giusti...
            }
        } else { //altrimenti
            out.writeObject(new Message("Login già effettuato", "server", usrName, "msg")); //è mona
        }
    }

    public void sendChat() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<Message> chat = SQLhelper.getMessages(clientId);
        int messagesToSend = chat.size() - lastMessageSent;
        out.writeObject(new Message(messagesToSend + "", "server", clientUsername, "chat"));
        while (lastMessageSent < messagesToSend) {
            Message m = chat.get(lastMessageSent);
            out.writeObject(new Message(m.getMessage(), m.getSrc(), m.getDst(), ""));
            lastMessageSent++;
        }
    }

    private void registerMessage(Message m) throws ClassNotFoundException, SQLException { //inoltro il messaggio
        SQLhelper.inserisciMessaggio(m);
    }

    private void sendConnUsers() throws IOException, ClassNotFoundException, SQLException {
        out.writeObject(new Message(Server.connectedClients.size() + "", "server", clientUsername, "conn"));
        for (String u : Server.connectedClients) {
            out.writeObject(new Message(u, "server", clientUsername, ""));
        }
    }
}
