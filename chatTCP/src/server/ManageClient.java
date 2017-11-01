package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import objects.*;

/**
 * @author Emanuele Pagotto
 */
class ManageClient implements Runnable {

    private Socket s; //socket del client
    private ObjectInputStream in; //stream in input
    private ObjectOutputStream out; //stream in output
    private boolean isLoggedIn;

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
                    routeMessage(m);
                }
                if (isLoggedIn) {
                    //
                }
            }
        } catch (ClassNotFoundException | IOException e) { //altri errori, altri ty catch
            System.err.println("Client Error: " + e.getMessage());
            System.err.println("Localized: " + e.getLocalizedMessage());
            System.err.print("Stack Trace: ");
            e.printStackTrace();
        }
    }

    public void register(Message m) { //mi registro sul database
        SQLhelper.register(m.getName(), m.getPw());
    }

    public void login(Message m) { //faccio il login confrontando il database
        try {
            if (!isLoggedIn) { //se il client su questo socket non ha ancora fatto il login
                if (SQLhelper.login(m.getName(), m.getPw())) { //faccio il login
                    Server.addConnectedUser(new User(1, m.getName(), this.s));
                    out.writeObject("Login effettuato");
                    isLoggedIn = true;
                } else {
                    out.writeObject("err_Dati non corretti"); //ma i dati potrebbero non essere giusti...
                }
            } else { //altrimenti
                out.writeObject("err_Hai già effettuato il login"); //è mona
            }
        } catch (IOException e) { //altri errori....
            System.err.println("Client Error: " + e.getMessage());
            System.err.println("Localized: " + e.getLocalizedMessage());
            System.err.print("Stack Trace: ");
            e.printStackTrace();
        }
    }

    private void routeMessage(Message m) { //inoltro il messaggio
        Socket dest = m.getDest();
        try {
            ObjectOutputStream d = (ObjectOutputStream) dest.getOutputStream();
            d.writeObject(m);
        } catch (IOException e) { //altri errori....
            System.err.println("Client Error: " + e.getMessage());
            System.err.println("Localized: " + e.getLocalizedMessage());
            System.err.print("Stack Trace: ");
            e.printStackTrace();
        }
    }
}
