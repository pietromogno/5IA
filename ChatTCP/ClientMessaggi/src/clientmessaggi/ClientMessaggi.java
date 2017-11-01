package clientmessaggi;

import Messaggio.Messaggio;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author Francesco Forcellato
 */
public class ClientMessaggi {

    private static final String SERVER = "localhost";
    private static final int PORTA = 9999;
    private static Socket s;
    private Login login;
    private ClientG clientg;
    private Ascoltatore a;
    private String username;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public static void main(String[] args) {
        ClientMessaggi c = new ClientMessaggi();
        c.connetti();
    }

    private void myLog(String messaggio) {
        System.out.println(messaggio);
        login.errore(messaggio);
        clientg.errore(messaggio);
    }

    public ClientMessaggi() {
        System.out.println("Client inizializzato");
    }

    public String getUsername() {
        return username;
    }

    public void invio(Object messaggio) throws IOException {
        output.reset();
        output.writeObject(messaggio);
        output.flush();
    }

    public void connetti() {
        try {
            login = new Login(this);
            Dimension grandezzaSchermo = Toolkit.getDefaultToolkit().getScreenSize();
            login.setLocation((grandezzaSchermo.width / 2) - (login.getWidth() / 2), (grandezzaSchermo.height / 2) - (login.getHeight() / 2));
            login.setVisible(true);
            s = new Socket(SERVER, PORTA);
            output = new ObjectOutputStream(s.getOutputStream());
            input = new ObjectInputStream(s.getInputStream());
            a = new Ascoltatore();
            String domanda = "";
        } catch (Exception ex) {
            myLog("Ops... " + ex.getMessage());
        }
    }

    public void sign(String username, String password, Boolean sign) throws ClassNotFoundException {
        try {
            invio(username);
            invio(password);
            invio(sign);
            String risposta = (String) input.readObject();
            if (risposta.contains("Errore")) {
                login.errore(risposta);
            } else {
                this.username = username;
                DefaultListModel<String> account = (DefaultListModel) input.readObject();
                account.removeElement(username);
                clientg = new ClientG(this, (HashMap<String, DefaultListModel>) input.readObject());
                Dimension grandezzaSchermo = Toolkit.getDefaultToolkit().getScreenSize();
                clientg.setLocation((grandezzaSchermo.width / 2) - (clientg.getWidth() / 2), (grandezzaSchermo.height / 2) - (clientg.getHeight() / 2));
                login.dispose();
                clientg.setAccount(account);
                clientg.setVisible(true);
                a.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientMessaggi.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ooooooooooops " + ex);
        }
    }

    class Ascoltatore extends Thread {

        @Override
        public void run() {
            try {
                Object messaggio;
                while (!s.isClosed() && s.isConnected()) {
                    messaggio = input.readObject();
                    if (messaggio instanceof Messaggio) {
                        clientg.scriviMessaggio((Messaggio) messaggio);
                    } else {
                        DefaultListModel<String> h = (DefaultListModel<String>) messaggio;
                        h.removeElement(username);
                        clientg.setAccount((DefaultListModel<String>) messaggio);
                        clientg.setConversazioni((HashMap<String, DefaultListModel>) input.readObject());
                    }
                }
            } catch (Exception ex) {
                myLog("Connessione conclusa");
                Logger.getLogger(ClientMessaggi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
