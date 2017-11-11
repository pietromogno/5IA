/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chattcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Piergiorgio Favaretto
 * @date 10/11/2017
 * @version 1.0
 */
public class Server {

    static int porta = 1234;
    static HashMap<String, Socket> h = new HashMap<>();
    static ArrayList<String> connessi = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket srvSocket = new ServerSocket(porta);
        ExecutorService esecutore = Executors.newCachedThreadPool();
        try {
            while (true) {
                Socket sock = srvSocket.accept();
                esecutore.submit(new NuovoClient(sock));
            }
        } finally {
            esecutore.shutdown();
            srvSocket.close();
        }
    }
}

class NuovoClient implements Runnable {

    private PrintWriter writer;
    private BufferedReader reader;
    private Socket sock;
    private String username = "", notifica, password;
    private PrintWriter writer2, wrAggiorna;
    private static SQLHelper s;

    public NuovoClient(Socket sock) {
        this.sock = sock;
        try {
            writer = new PrintWriter(sock.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            s = new SQLHelper();
        } catch (IOException ex) {
        }
    }

    @Override
    public void run() {
        try {
            boolean ris = false;
            while (!ris) {
                notifica = reader.readLine();
                username = reader.readLine();
                password = reader.readLine();
                String avvisi = "Account non presente";
                if (notifica.equals("login")) {
                    if (s.esiste(username, password)) {
                        if (Server.h.containsKey(username)) {
                            avvisi = "Con questi dati si è gia loggato un qualcun altro";
                        } else {
                            ris = true;
                        }
                    }
                } else if (notifica.toLowerCase().equals("registrazione")) {
                    if (!s.presente(username)) {
                        ris = true;
                        s.esiste(username, password);
                    } else {
                        avvisi = "Username già presente nel DataBase";
                    }
                }
                if (ris) {
                    avvisi = "#registrazioneok";
                    Server.h.put(username, sock);
                    Server.connessi.add(username);
                }
                writer.println(avvisi);
            }
            aggiornaListe();
            String[] p;
            String u = "", mes = "";
            while (true) {
                try {
                    u = reader.readLine();
                    p = u.split(" ");
                    if (u != null && !p[0].equals("#fine") && Server.h.containsKey(u)) {
                        mes = reader.readLine();
                        writer2 = new PrintWriter(Server.h.get(u).getOutputStream(), true);
                        writer2.println(username + ":  " + mes);
                    } else {
                        Server.h.remove(p[1]);
                        Server.connessi.remove(p[1]);
                        aggiornaListe();
                    }
                } catch (Exception e) {
                }
            }
        } catch (IOException | SQLException ex) {
            Logger.getLogger(NuovoClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void aggiornaListe() throws IOException {
        for (String x : Server.h.keySet()) {
            wrAggiorna = new PrintWriter(Server.h.get(x).getOutputStream(), true);
            wrAggiorna.println("#aggiornalalista");
            for (int j = 0; j < Server.connessi.size(); j++) {
                wrAggiorna.println(Server.connessi.get(j));
            }
            wrAggiorna.println("#fineinviolistconnessi");
        }
    }

}
