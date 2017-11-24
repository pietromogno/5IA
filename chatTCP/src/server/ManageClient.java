package server;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import objects.*;

/**
 * @author Emanuele Pagotto
 */
class ManageClient implements Runnable {

    private Socket s; //socket del client
    private ObjectInputStream in; //stream in input
    private ObjectOutputStream out; //stream in output
    private boolean isLoggedIn;
    private int clientId;

    //costruttore
    public ManageClient(Socket sck) {
        s = sck;
        isLoggedIn = false;
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
                if (isLoggedIn) {
                    //
                }
            }
        } catch (ClassNotFoundException | IOException | SQLException e) { //altri errori, altri ty catch
            System.err.println("Client Error: " + e.getMessage());
            System.err.println("Localized: " + e.getLocalizedMessage());
            System.err.print("Stack Trace: ");
            e.printStackTrace();
        }
    }

    public void register(Message m) throws SQLException, ClassNotFoundException { //mi registro sul database
        SQLhelper.register(m.getName(), m.getPw());
    }

    public void login(Message m) throws SQLException, ClassNotFoundException, IOException { //faccio il login confrontando il database
        if (!isLoggedIn) { //se il client su questo socket non ha ancora fatto il login
            if (SQLhelper.login(m.getName(), m.getPw())) { //faccio il login
                this.clientId = Server.addConnectedUser(m.getName(), this.s);
                out.writeObject("Login effettuato come " + m.getName() + "(#" + clientId + ")");
                isLoggedIn = true;
            } else {
                out.writeObject("err_Dati non corretti"); //ma i dati potrebbero non essere giusti...
            }
        } else { //altrimenti
            out.writeObject("err_Hai già effettuato il login"); //è mona
        }
    }

    private void registerMessage(Message m) throws ClassNotFoundException, SQLException { //inoltro il messaggio
        SQLhelper.inserisciMessaggio(m);
    }
}
