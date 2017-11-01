/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import dbutil.SQLHelper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Enrico
 */
public class ClientHandler implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private int idUtente;
    private String user;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    }

    @Override
    public void run() {
        try {
            boolean continua = true;
            while (continua) {
                String op = in.readLine();
                if (op.equals("login")) {
                    String username = in.readLine();
                    String password = in.readLine();
                    if (SQLHelper.esistonoCredenziali(username, password) && !Server.isUtenteConnesso(SQLHelper.idUtente(username))) {
                        out.println("");
                        user = username;
                        idUtente = SQLHelper.idUtente(username);
                        gestisciVistaUtente();
                    } else {
                        out.println("Errore");
                    }
                } else if (op.equals("registrazione")) {
                    String username = in.readLine();
                    String password = in.readLine();
                    if (!SQLHelper.esisteUsername(username) && !SQLHelper.esistePassword(password)) {
                        SQLHelper.inserisciUtente(username, password);
                        out.println("");
                        user = username;
                        idUtente = SQLHelper.idUtente(username);
                        gestisciVistaUtente();
                    } else {
                        out.println("Errore");
                    }
                } else {
                    continua = false;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gestisciVistaUtente() throws IOException {
        Server.addUtente(idUtente, socket);
        boolean continua = true;
        while (continua) {
            String op = in.readLine();
            if (op.equals("Carica")) {
                out.println(user);
                ArrayList<Chat> chat = SQLHelper.getChat(idUtente);
                out.println(chat.size());
                for (Chat c : chat) {
                    out.println(c.getNomeChat());
                    out.println(c.getIdChat());
                }
            } else if (op.equals("AggiungiContatto")) {
                String username = in.readLine();
                if (SQLHelper.esisteUsername(username)) {
                    int idContatto = SQLHelper.idUtente(username);
                    if (idContatto != idUtente && !SQLHelper.esisteContatto(idUtente, idContatto)) {
                        int idChat = SQLHelper.aggiungiContatto(idUtente, idContatto);
                        out.println("AggiungiChat");
                        out.println(username);
                        out.println(idChat);
                        Server.notificaNuovaChat(idContatto, user, idChat);
                    } else {
                        out.println("Errore");
                    }
                } else {
                    out.println("Errore");
                }
            } else if (op.equals("CreaGruppo")) {
                out.println("CreaGruppo");
                gestisciVistaCreazioneGruppo();
            } else if (op.equals("Chat")) {
                out.println("Chat");
                int idChat = Integer.parseInt(in.readLine());
                out.println(idChat);
                gestisciVistaChat(idChat);
            } else {
                out.println("");
                continua = false;
            }
        }
        Server.removeUtente(idUtente);
    }

    public void gestisciVistaCreazioneGruppo() throws IOException {
        ArrayList<String> contatti = SQLHelper.getContatti(idUtente);
        out.println(contatti.size());
        for (String s : contatti) {
            out.println(s);
        }
        String op = in.readLine();
        if (op.equals("Crea")) {
            String nomeGruppo = in.readLine();
            int idChat = SQLHelper.creaGruppo(nomeGruppo);
            int nContatti = Integer.parseInt(in.readLine());
            SQLHelper.aggiungiMembro(idChat, idUtente);
            for (int i = 0; i < nContatti; i++) {
                int idContatto = SQLHelper.idUtente(in.readLine());
                SQLHelper.aggiungiMembro(idChat, idContatto);
                Server.notificaNuovaChat(idContatto, nomeGruppo, idChat);
            }
        }
        out.println("");
    }

    public void gestisciVistaChat(int idChat) throws IOException {
        boolean continua = true;
        while (continua) {
            String op = in.readLine();
            if (op.equals("Carica")) {
                if (SQLHelper.isGruppo(idChat)) {
                    out.println("Gruppo");
                    ArrayList<String> contatti = SQLHelper.getContattiNonInGruppo(idChat,idUtente);
                    out.println(contatti.size());
                    for (String s : contatti) {
                        out.println(s);
                    }
                } else {
                    out.println("");
                }
                ArrayList<String> messaggi = SQLHelper.getMessaggi(idChat, idUtente);
                out.println(messaggi.size());
                for (String s : messaggi) {
                    out.println(s);
                }
            } else if (op.equals("Invio")) {
                String testo = in.readLine();
                SQLHelper.addMessaggio(testo, idChat, idUtente);
                testo = user + ":" + testo;
                ArrayList<Integer> idDestinatari = SQLHelper.getDestinatari(idChat);
                idDestinatari.remove(new Integer(idUtente));
                for (int idContatto : idDestinatari) {
                    Server.inviaMessaggio(idContatto, testo, idChat);
                }
            } else if(op.equals("Aggiungi")){
                String utente=in.readLine();
                int idContatto=SQLHelper.idUtente(utente);
                SQLHelper.aggiungiMembro(idChat, idContatto);
                Server.notificaNuovaChat(idContatto,SQLHelper.nomeGruppo(idChat) , idChat);
                ArrayList<Integer>idMembri=SQLHelper.getDestinatari(idChat);
                idMembri.remove(new Integer(idUtente));
                for(int idMembro:idMembri){
                    if(SQLHelper.esisteContatto(idMembro, idContatto))Server.notificaNuovoMembro(idMembro,utente);
                }
            }else {
                out.println("");
                continua = false;
            }
        }
    }
}
