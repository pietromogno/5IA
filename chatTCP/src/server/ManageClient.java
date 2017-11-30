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
                int type = m.getType();
                if (type == 0) {
                    login(m);
                } else if (type == 1) {
                    register(m);
                } else {
                    registerMessage(m);
                }
                if (isLoggedIn && Server.connectedClientsChanged) {
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

    public void register(Message m) throws SQLException, ClassNotFoundException, IOException { //mi registro sul database
        if (SQLhelper.register(m.getName(), m.getPw())) {
            out.writeObject("msg Registrazione effettuata con successo");
            System.out.println("Utente " + m.getName() + " Collegato");
        } else {
            out.writeObject("msg Il nome utente esiste già");
        }
    }

    public void login(Message m) throws SQLException, ClassNotFoundException, IOException { //faccio il login confrontando il database
        if (!isLoggedIn) { //se il client su questo socket non ha ancora fatto il login
            if (SQLhelper.login(m.getName(), m.getPw())) { //faccio il login
                this.clientId = Server.addConnectedUser(m.getName(), this.s);
                out.writeObject("msg Login effettuato come " + m.getName() + "(#" + clientId + ")");
                isLoggedIn = true;
            } else {
                out.writeObject("msg Dati non corretti"); //ma i dati potrebbero non essere giusti...
            }
        } else { //altrimenti
            out.writeObject("msg Hai già effettuato il login"); //è mona
        }
    }

    public void sendChat() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<Message> chat = SQLhelper.getMessages(clientId);
        int messagesToSend = chat.size() - lastMessageSent;
        out.writeInt(messagesToSend);
        while (lastMessageSent < messagesToSend) {
            out.writeObject(chat.get(lastMessageSent).getMessage());
            lastMessageSent++;
        }
    }

    private void registerMessage(Message m) throws ClassNotFoundException, SQLException { //inoltro il messaggio
        SQLhelper.inserisciMessaggio(m);
    }

    private void sendConnUsers() throws IOException {
        for (String u : Server.connectedClients) {
            out.writeObject(u);
        }
    }
}
