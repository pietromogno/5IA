package servermessaggi;

import Messaggio.Messaggio;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author Francesco Forcellato
 */
public class ServerMessaggi {

    private static ArrayList<Account> dest;

    public static void main(String[] args) throws IOException {
        dest = SQLHelper.getAccount();
        System.out.println("Server in esecuzione");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(9999);
        try {
            while (true) {
                Socket h = listener.accept();
                new Destinatario(h, clientNumber++).start();
            }
        } finally {
            listener.close();
        }
    }

    private static void broadcast(Object messaggio) {
        for (Account x : dest) {
            ObjectOutputStream destinatario = x.getOutput();
            try {
                if (destinatario != null) {
                    destinatario.reset();
                    destinatario.writeObject(messaggio);
                    destinatario.flush();
                    destinatario.reset();
                    destinatario.writeObject(SQLHelper.getConversazioni(x.getUsername()));
                    destinatario.flush();
                }
            } catch (IOException ex) {
                Logger.getLogger(ServerMessaggi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static class Destinatario extends Thread {

        private static Socket socket;
        private int clientNumber;
        private Account persona;
        private ObjectInputStream input;
        private ObjectOutputStream output;

        public Destinatario(Socket socket, int clientNumber) {
            this.socket = socket;
            try {
                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(ServerMessaggi.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.clientNumber = clientNumber;
            myLog("Nuova connessione con il client #" + clientNumber + " al " + socket);
        }

        public void inviaMittente(Object messaggio) throws IOException {
            output.reset();
            output.writeObject(messaggio);
            output.flush();
        }

        private boolean sign() throws IOException, ClassNotFoundException {
            //login o registrazione
            boolean trovato = false;
            String username = (String) input.readObject();
            String password = (String) input.readObject();
            Boolean sign = (boolean) input.readObject();
            if (username != null && password != null && sign != null) {
                int i = 0;
                for (i = 0; i < dest.size() && !trovato; i++) {
                    if (dest.get(i).getUsername().compareTo(username) == 0) {
                        trovato = true;
                        persona = dest.get(i);
                        i--;
                    }
                }
                if (!trovato && sign) {
                    SQLHelper.addAccount(username, password);
                    persona = new Account(username, password, socket, output, input);
                    dest.add(persona);
                    inviaMittente("Ok");
                    DefaultListModel account = new DefaultListModel();
                    for (Account x : dest) {
                        account.addElement(x.getUsername());
                    }
                    broadcast(account);
                    //inviaMittente(SQLHelper.getConversazioni(username));
                    trovato = true;
                } else if (!trovato && !sign) {
                    inviaMittente("Errore: account inesistente.");
                } else if (persona.getPassword().compareTo(password) != 0) {
                    if (sign) {
                        inviaMittente("Errore: username non disponibile.");
                    } else {
                        inviaMittente("Errore: password non combaciano.");
                    }
                    trovato = false;
                } else {
                    inviaMittente("Ok");
                    dest.get(i).setSocket(socket, output, input);
                    DefaultListModel account = new DefaultListModel();
                    for (Account x : dest) {
                        if (x.getUsername().compareTo(username) != 0) {
                            account.addElement(x.getUsername());
                        }
                    }
                    inviaMittente(account);
                    inviaMittente(SQLHelper.getConversazioni(username));
                }
            } else {
                trovato = true;
            }
            return trovato;
        }

        public void invia(Messaggio m) throws IOException {
            SQLHelper.aggiungiMessaggio(m);
            ObjectOutputStream destinatario = null;
            for (int i = 0; i < dest.size(); i++) {
                if (m.getDestinatario().compareTo(dest.get(i).getUsername()) == 0) {
                    destinatario = dest.get(i).getOutput();
                }
            }
            if (destinatario != null) {
                destinatario.writeObject(m);
                destinatario.flush();
            }
            output.writeObject(m);
            output.flush();
        }

        public void run() {
            try {
                boolean login = false;
                while (!login && socket.isConnected() && !socket.isClosed()) {
                    login = sign();
                }
                while (login) {
                    Messaggio m = (Messaggio) input.readObject();
                    invia(m);
                }
            } catch (Exception e) {
                myLog("Connessione con client# " + clientNumber + " chiusa " +e.getStackTrace());
            }
        }

        private void myLog(String message) {
            System.out.println(message);
        }
    }
}
