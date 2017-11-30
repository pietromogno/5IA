/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;
import oggetti.Messaggio;
import serverchat.ServerChat.Connection.Inoltra;

/**
 *
 * @author MATTI
 */
public class ServerChat {

    /**
     * @param args the command line arguments
     */
    private String ip = "localhost";
    private static int portaD = 9191;
    private static Inoltra conn = null;
    private static SQLHelper db;

    public static void main(String[] args) {
        conn = new Inoltra();
        ThreadPoolExecutor tp = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        tp.execute(conn);
        try {
            db = new SQLHelper();
            ServerSocket listener = new ServerSocket(portaD);
            while (true) {
                Socket socket = listener.accept();
                //System.out.println("connesso"+socket.toString());
                Connection c = new Connection(socket);
                tp.execute(c);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static class Connection extends Thread {

        private static Socket socket;
        private static ObjectOutputStream out;
        private static ObjectInputStream input;
        private String nomeUtente;

        public Connection(Socket soc) {
            try {
                nomeUtente = "";
                socket = soc;
                out = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(ServerChat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /**
         * Questa funzione si occupa di porre un arraylist all'interno di un
         * oggetto
         *
         * @param utente utente che riceverà il messaggio
         * @param nomeUtente utente che invia il messaggio
         * @return un oggetto contenente un ArrayList di array di stringe aventi
         * al suo interno le chat suddivise
         */
        Object getChat(String dest, String sorg) throws SQLException {
            ArrayList<String[]> a = db.showChat(dest, sorg);
            return a;
        }

        /**
         * Questa funzione si occupa di porre un arraylist all'interno di un
         * oggetto
         *
         * @param utente
         * @return un arraylist di stringe contenenti tutti gli utenti
         * registrati nel DB
         */
        Object getUtenti(String utente) throws SQLException {
            ArrayList<String> a = db.getUtenti(utente);
            return a;
        }

        /**
         * Questa procedura resta in ascolto di un interno che identifica
         * l'operazione da eseguire successivamente nel database,
         * successivamente vengono lette le informazioni da passare all'interno
         * delle varie funzioni, nelle funzioni dove viene ricevuta la chat ci
         * sarà un ulteriore inputStreamer in lettura
         *
         * @return sempre un oggetto
         */
        @Override
        public synchronized void run() {
            try {
                while (!socket.isClosed()) {
                    //System.out.print("Letto  -    " + Thread.currentThread().toString() + "    -    " + socket.toString());
                    Messaggio msg = (Messaggio) input.readObject();
                    switch (msg.getFunzione()) {
                        case Messaggio.REGISTRAZIONE:
                            writeToClient(new Messaggio(Messaggio.REGISTRAZIONE, db.resisterToDB(msg.getNome(), msg.getCognome(), msg.getNomeUtenteSorg(), msg.getPassword()), null, null, null, null, null));
                            break;
                        case Messaggio.ACCESSO:
                            Object[] result = db.accedi(msg.getNomeUtenteSorg(), msg.getPassword());
                            if (!conn.isLoged(msg.getNomeUtenteSorg())) {
                                nomeUtente = msg.getNomeUtenteSorg();
                                writeToClient(new Messaggio(Messaggio.ACCESSO, result, nomeUtente, null, null, null, null));
                                if (!result[0].equals("1")) {
                                    conn.setNewConnection(msg.getNomeUtenteSorg());
                                }
                            } else {
                                writeToClient(new Messaggio(Messaggio.ACCESSO, new String[]{"1", "Hai già effettuato l'accesso"}, msg.getNomeUtenteSorg(), null, null, null, null));
                            }
                            break;
                        case Messaggio.MOSTRAMESSAGGIO:
                            writeToClient(new Messaggio(Messaggio.MOSTRAMESSAGGIO, getChat(msg.getNomeUtenteDest(), msg.getNomeUtenteSorg()), null, msg.getNomeUtenteDest(), null, null, null));
                            break;
                        case Messaggio.OTTIENIUTENTI:
                            writeToClient(new Messaggio(Messaggio.OTTIENIUTENTI, getUtenti(msg.getNomeUtenteSorg()), null, null, null, null, null));
                            break;
                        case Messaggio.SALVACONVERSAZIONE:
                            writeToClient(new Messaggio(Messaggio.SALVACONVERSAZIONE, db.saveChat((String) msg.getMessaggio(), msg.getNomeUtenteSorg(), msg.getNomeUtenteDest()), null, null, null, null, null));
                            conn.update(msg.getNomeUtenteDest(), getChat(msg.getNomeUtenteDest(), msg.getNomeUtenteSorg()));
                            break;
                        case Messaggio.CANCELLAZIONE:
                            writeToClient(new Messaggio(Messaggio.CANCELLAZIONE, db.deleteAccount(msg.getNomeUtenteSorg(), msg.getPassword()), null, null, null, null, null));
                            break;
                        case Messaggio.INTERROMPI:
                            input.close();
                            out.close();
                            socket.close();
                            conn.deleteConnection(msg.getNomeUtenteSorg());
                            break;
                        case Messaggio.IMMAGINE:
                            writeToClient(new Messaggio(Messaggio.IMMAGINE, db.insertImage(msg.getNomeUtenteSorg(), (byte[]) msg.getMessaggio()), null, null, null, null, null));
                            break;
                        default:
                            System.out.println("Errore");
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    socket.close();
                    conn.deleteConnection(nomeUtente);
                    Thread.currentThread().interrupt();
                } catch (IOException ex) {
                    Logger.getLogger(ServerChat.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        synchronized void writeToClient(Messaggio send) {
            try {
                //out.reset();
                out.writeObject(send);
                out.flush();
            } catch (IOException ex) {
                Logger.getLogger(ServerChat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /**
         * Classe che avrebbe dovuto gestire la notifica di avvenuta scrittura,
         * da parte di un utente, nella chat e quindi aggiornare la sua a sua
         * volta
         */
        static class Inoltra implements Runnable {

            private HashMap<String, ObjectOutputStream> usersOutput;

            protected void Inoltra() {
                usersOutput = new HashMap<>();
            }

            /*public void d() {
                System.out.println(usersOutput.toString());
            }*/

            @Override
            public void run() {
                usersOutput = new HashMap<>();
            }

            protected void setNewConnection(String userName) {
                usersOutput.put(userName, out);
            }

            protected synchronized void deleteConnection(String userName) {
                usersOutput.remove(userName, usersOutput.get(userName));//crea classe di logout ed eliminazione utente
            }

            protected boolean isLoged(String utente) {
                System.out.println(utente + usersOutput.containsKey(utente));
                return usersOutput.containsKey(utente);
            }

            protected synchronized void update(String userNameDest, Object chat) {
                try {
                    if (usersOutput.containsKey(userNameDest)) {
                        ObjectOutputStream temp = usersOutput.get(userNameDest);
                        //temp.reset();
                        temp.writeObject(new Messaggio(Messaggio.MOSTRAMESSAGGIO, chat, null, userNameDest, null, null, null));
                        temp.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
