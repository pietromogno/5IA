/**
 * @author Alvise Busetto
 * @date 01.11.2017
 */

package serverchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private static String[] usernameClient = new String[8]; //array per username (se c'è un nome user vuol dire che è connesso)

    public static void main(String[] args) throws Exception {
        System.out.println("ServerChat is running.");
        String clientName = "";
        ServerSocket listener = new ServerSocket(9898); //inizializzazione dell'ascoltatore del socket
        try {
            while (true) {
                // creazione e inizio del thread
                new ServerChat(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }

    private static class ServerChat extends Thread {

        private final Socket socket;
        private int numClient = 0;

        public ServerChat(Socket socket) {
            this.socket = socket; 
            numClient++;
        }

        @Override
        public void run() {  //thread start
            try {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                try {
                    SQLHelper database = new SQLHelper(); //inizializzazione dell'oggetto per la gestione del databse

                    String inputInfoAccesso = in.readLine();  // accesso o registrazione
                    if (inputInfoAccesso.charAt(0) == '§') {
                        usernameClient[numClient] = getInfoClient(inputInfoAccesso, 5, '§');
                        database.add(inputInfoAccesso);
                    } else {
                        String user = getInfoClient(inputInfoAccesso, 1, '$');
                        String psw = getInfoClient(inputInfoAccesso, 2, '$');
                    }
                    out.println(getClientConnessi());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException e) {
                myLog("Error handling client# " + numClient + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    myLog("Couldn't close a socket, what's going on?");
                }
                myLog("Connection with client# " + numClient + " closed");
            }
        }

        /*
        *@brief metodo che returna un campo tra Nome, cognome, data di nascita, numero di telefono, username, password
        */
        
        public static String getInfoClient(String segmento, int indice, char spaziatore) { //da scegliere da 1 a 6 o
            String ris = "null";
            byte cnt = 0;
            char temp;
            for (int i = 0; i < segmento.length(); i++) {
                if (segmento.charAt(i) == spaziatore) {
                    cnt++;
                    if (cnt == indice) {
                        ris = "";
                        for (i = i + 1; i < segmento.length(); i++) {
                            temp = segmento.charAt(i);
                            if (temp == spaziatore) {
                                return ris;
                            } else {
                                ris += "" + temp;
                            }
                        }
                    }
                }
            }

            return ris;
        }
        
        /*
        *@brief metodo che restituisce una stringa che compatta tutti gli username dei client connessi separati da "§"
        */

        public String getClientConnessi() {
            String ris = "";
            for (int i = 0; i < usernameClient.length; i++) {
                ris += usernameClient[i] + "§";
            }
            return ris;
        }

        private void myLog(String message) {
            System.out.println(message);
        }
    }
}
