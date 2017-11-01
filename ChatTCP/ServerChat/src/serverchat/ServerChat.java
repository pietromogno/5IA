/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MATTI
 */
public class ServerChat {

    /**
     * @param args the command line arguments
     */
    String ip = "localhost";
    int portaD = 9090;
    private static SQLHelper db;
    //private static Connections notify;

    public static void main(String[] args) {
        //  notify = new Connections();
        try {
            db = new SQLHelper();
            int porta = 9090;
            ServerSocket listener = new ServerSocket(porta);
            while (true) {
                Socket socket = listener.accept();
                new Connection(socket);
            }
        } catch (Exception ex) {
            Logger.getLogger(ServerChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static class Connection extends Thread {

        Socket socket;

        public Connection(Socket soc) {
            socket = soc;
            this.start();
        }
/**
 * Questa funzione si occupa di porre un arraylist all'interno di un oggetto
 * @param utente utente che riceverà il messaggio
 * @param nomeUtente utente che invia il messaggio
 * @return un oggetto contenente un ArrayList di array di stringe aventi al suo interno le chat suddivise
 */
        Object getChat(String utente, String nomeUtente) throws SQLException {
            ArrayList<String[]> a = db.showChat(utente, nomeUtente);
            return a;
        }
/**
 * Questa funzione si occupa di porre un arraylist all'interno di un oggetto
 * @param utente
 * @return un arraylist di stringe contenenti tutti gli utenti registrati nel DB
 */
        Object getUtenti(String utente) throws SQLException {
            ArrayList<String> a = db.getUtenti(utente);
            return a;
        }
/**
 * Questa procedura resta in ascolto di un interno che identifica l'operazione da eseguire successivamente nel database, 
 * successivamente vengono lette le informazioni da passare all'interno delle varie funzioni,
 * nelle funzioni dove viene ricevuta la chat ci sarà un ulteriore inputStreamer in lettura
 * @return sempre un oggetto
 */
        @Override
        public void run() {
            try {
                ObjectOutputStream out
                        = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream input
                        = new ObjectInputStream(socket.getInputStream());
                while (socket.isConnected()) {
                    if (input.available() > 0) {
                        int function = input.readInt();
                        String in = input.readUTF();
                        switch (function) {
                            case 0:
                                out.writeObject(db.isRegistered(in));
                                out.flush();
                                break;
                            case 1:
                                System.out.println("1");
                                String temp[] = in.split(",");
                                System.out.println(temp[0] + temp[1] + temp[2] + temp[3]);
                                out.writeObject(db.resisterToDB(temp[0], temp[1], temp[2], temp[3]));
                                out.flush();
                                break;
                            case 3:
                                String temp1[] = in.split(",");
                                out.writeObject(db.accedi(temp1[0], temp1[1]));
                                out.flush();
                                //notify.setNewConnection(socket, temp1[0]);
                                break;
                            case 4:
                                temp = in.split(",");
                                out.writeObject(getChat(temp[0], temp[1]));
                                out.flush();
                                break;
                            case 5:
                                out.writeObject(getUtenti(in));
                                out.flush();
                                break;
                            case 6:
                                temp = in.split(",");
                                out.writeObject(db.saveChat(input.readUTF(), temp[0], temp[1], temp[2]));
                                out.flush();
                                //notify.start();
                                //notify.update(temp3[0], temp3[1]);
                                break;
                            case 7:
                                temp = in.split(",");
                                out.writeObject(db.deleteAccount(temp[0], temp[1]));
                                out.flush();
                                break;
                        }
                    }
                }
                socket.close();
                out.close();
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
/**
 * Classe che avrebbe dovuto gestire la notifica di avvenuta scrittura, da parte di un utente, nella chat e quindi aggiornare la sua a sua volta
 */
    /* static class Connections extends Thread{

        private ArrayList<Socket> sok;
        private ArrayList<String> users;
        private int connNumb;
        Socket s;
        ObjectOutputStrean output;

        Connections() {
            sok = new ArrayList<>();
            users = new ArrayList<>();
            connNumb = 0;
        }
    @Override
        public void run(Socket socketDest, String userName) {
            s=socketDest;
            output=new OutputObjectStream(s.getOutputStream());
        }

        public void setNewConnection() {
            sok.add(socket);
            users.add(userName);
            System.out.println(users.get(connNumb));
            connNumb++;
        }

        public void update(String userNameSorg, String userNameDest) {
            int i = users.indexOf(userNameDest);
            if (i >= 0) {
                try {
                    output.writeInt(8);
                    output.writeObject(db.showChat(userNameSorg, userNameDest));
                    output.flush();
                    output.close();
                    s.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }*/
}
