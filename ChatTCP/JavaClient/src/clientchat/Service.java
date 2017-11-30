/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import oggetti.Messaggio;

/**
 *
 * @author Musone Mattia
 */
class Service extends Observable {

    private static Socket socket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static Client c;
    private static Accesso a;
    private static Registrazione r;
    private String serverAddress = "localhost";
    protected Messaggio msg;

    public static void main(String args[]) {
        new Service();
    }

    Service() {
        doAccesso();
    }

    void startChatting(String nomeUtente, byte[] image) {
        c = new Client(this, nomeUtente, image);
        c.setVisible(true);
        try {
            this.deleteObservers();
            this.addObserver(c);
            if (socket == null || !socket.isConnected()) {
                connectionError();
            }
        } catch (NullPointerException ex) {

        }
    }

    void doRegistrazione() {
        r = new Registrazione(this);
        r.setVisible(true);
        try {
            this.deleteObservers();
            this.addObserver(r);
            if (socket == null || !socket.isConnected()) {
                connectionError();
            }
        } catch (NullPointerException ex) {

        }
    }

    void doAccesso() {
        a = new Accesso(this);
        a.setVisible(true);
        try {
            this.deleteObservers();
            this.addObserver(a);
            if (socket == null || !socket.isConnected()) {
                connectionError();;
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    void setConnection() {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(serverAddress, 9191), 2000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            msg = new Messaggio(Messaggio.CONNESSO, null, null, null, null, null, null);
            new Thread(new Reader()).start();
            refresh();

        } catch (IOException e) {
            connectionError();
        }
    }

    class Reader extends Thread {

        @Override
        public synchronized void run() {
            try {
                while (socket != null && !socket.isClosed()) {
                    msg = (Messaggio) in.readObject();
                    refresh();
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
                connectionError();
            }
        }
    }

    public void closeConnection(String nomeUtente) {
        if (connectionState()) {
            try {
                writeToServer(new Messaggio(Messaggio.INTERROMPI, null, nomeUtente, null, null, null, null));
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
            }
        }
    }
    
    void registerToDB(String nome, String cognome, String nomeUtente, String password) {
        writeToServer(new Messaggio(Messaggio.REGISTRAZIONE, null, nomeUtente, null, nome, cognome, password));
    }

    void accedi(String nomeUtente, String password) {
        writeToServer(new Messaggio(Messaggio.ACCESSO, null, nomeUtente, null, null, null, password));
    }

    protected void saveChat(String chat, String sorg, String dest) {
        writeToServer(new Messaggio(Messaggio.SALVACONVERSAZIONE, chat, sorg, dest, null, null, null));
    }

    void showChat(String nomeUtetne, String utenteDest) {
        writeToServer(new Messaggio(Messaggio.MOSTRAMESSAGGIO, null, nomeUtetne, utenteDest, null, null, null));
    }

    void getUtenti(String utente) {
        writeToServer(new Messaggio(Messaggio.OTTIENIUTENTI, null, utente, null, null, null, null));
    }

    void deleteAccount(String password, String nomeUtente) {
        writeToServer(new Messaggio(Messaggio.CANCELLAZIONE, null, nomeUtente, null, null, null, password));
    }

    private void connectionError() {
        msg = new Messaggio(Messaggio.ERRORECONNESSIONE, "Sei disconnesso dal server", null, null, null, null, null);
        refresh();
    }

    synchronized void writeToServer(Messaggio send) {
        try {
            //out.reset();
            //System.out.println(Thread.currentThread().toString()+"  writing  "+socket.toString());
            out.writeObject(send);
            out.flush();
        } catch (IOException ex) {
            connectionError();
        }
    }

    static boolean connectionState() {
        return socket != null ? !socket.isClosed() : false;
    }

    public void refresh() {  //aggiorna le viste
        this.setChanged();
        this.notifyObservers();
        //System.out.println(Thread.currentThread().toString()+"  refreshing  "+socket.toString());
    }
}
