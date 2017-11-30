package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


/**
 * @author Matteo Favaro
 */
public class GestisciClient extends Thread {

    static final String ANY = "---";

    private String nomeClient;
    private Socket connessione;
    private Server server;

    private BufferedReader in;
    private PrintWriter out;

    public GestisciClient(Server server, Socket connessione) {
        this.connessione = connessione;
        this.nomeClient = "";
        this.server = server;
    }

    public String getNomeClient() {
        return nomeClient;
    }

    @Override
    public void run() {
        try {
            System.out.println("CONNESSO Client ");

            in = new BufferedReader(new InputStreamReader(connessione.getInputStream()));
            out = new PrintWriter(connessione.getOutputStream(), true);

            String nomeMittente, nomeDestinatario, tipoMessaggio;
            int numMsgDaLeggere;

            while (true) {
                nomeMittente = in.readLine();
                System.out.println("nomeMittente-> " + nomeMittente);
                nomeDestinatario = in.readLine();
                System.out.println("nomeDestinatario-> " + nomeDestinatario);
                tipoMessaggio = in.readLine();
                System.out.println("tipoMessaggio-> " + tipoMessaggio);
                numMsgDaLeggere = Integer.parseInt(in.readLine());
                switch (tipoMessaggio) {
                    case "LOGIN":

                        String usernameLogin = in.readLine();
                        String passwordLogin = in.readLine();

                        if (SQLHelper.isLoginCorretto(usernameLogin, passwordLogin)) {
                            this.nomeClient = usernameLogin;
                            ArrayList<String> listaUtenti = server.getListaNomiClientAttivi();
                            String[] arrayUtenti = new String[listaUtenti.size()];
                            arrayUtenti = listaUtenti.toArray(arrayUtenti);
                            String[] msg = new String[arrayUtenti.length + 1];
                            msg[0] = usernameLogin;//prima cella username dell'utente che ha fatto login
                            for (int i = 1; i < msg.length; i++) {
                                msg[i] = arrayUtenti[i - 1];
                            }
                           
                            for (int i = 0; i < msg.length; i++) {
                                System.out.println(msg[i]);
                            }
                           
                            scriviMsg(ANY, ANY, "LOGIN", msg);
                            server.aggiungiClient(this);
                            server.notificaUpdateListaUtentiAttivi(server.getListaNomiClientAttivi());
                        } else {
                            scriviMsg(ANY, ANY, "ERRORE", new String[]{"Credenziali errate!"});
                        }
                        break;
                    case "REGISTRAZIONE":
                        String usernameRegistrazione = in.readLine();
                        String passwordRegistrazione = in.readLine();
                        if (!usernameRegistrazione.equals("") && !passwordRegistrazione.equals("") && !SQLHelper.esisteUsername(usernameRegistrazione)) {
                            SQLHelper.inserisciAccount(usernameRegistrazione, passwordRegistrazione);
                        } else {
                            scriviMsg(ANY, ANY, "ERRORE", new String[]{"Account gia esistente!"});
                        }
                        break;
                    case "MESSAGGIO":
                        //trova altro destinatario da nome
                        //String nomeDestinatario = in.readLine();
                        String messaggio = in.readLine();
                        GestisciClient clientDestinatario = server.getClientAttivoFromUsername(nomeDestinatario);
                        if (clientDestinatario != null) {
                            clientDestinatario.scriviMsg(nomeClient, nomeDestinatario, "MESSAGGIO", new String[]{messaggio});
                        } else {
                            //non esiste destinatario attivo
                            // non faccio nulla
                        }
                        break;
                    case "UPDATELISTAUTENTIATTIVI":
                        /*System.out.println("UPDATE LISTA UTENTI ATTIVI[@@@@@@@@@@@@@@@");
                        //ogni volta che uno si logga o esce aggiornare lista utenti attivi
                        ArrayList<String> listaUpdate = server.getListaNomiClientAttivi();
                        String[] arrayUpdate = new String[listaUpdate.size()];
                        arrayUpdate = listaUpdate.toArray(arrayUpdate);

                        scriviMsg(ANY, ANY, "UPDATELISTAUTENTIATTIVI", arrayUpdate);*/
                        break;
                }
            }

        } catch (IOException ex) {
            System.out.println("ERRORE: ->qualcosa è andato storto ma non importa me ne frego e mi rallegro :)");
        } finally {
            server.rimuoviClient(this);
            try {
                connessione.close();
                server.rimuoviClient(this);
                server.notificaUpdateListaUtentiAttivi(server.getListaNomiClientAttivi());
                System.out.println("DISCONNESSO Client");
            } catch (IOException e) {
                System.out.println("Impossibile chiudere il socket");
            }
        }
    }

    public synchronized void scriviMsg(String mittente, String destinatario, String tipoMessaggio, String[] messaggi) {
        out.println(mittente);//nome mittente
        out.println(destinatario);//nome destinatario
        out.println(tipoMessaggio);//tipo messaggio
        int numMessaggi = messaggi.length;
        out.println(numMessaggi + "");//numero messaggi successivi
        for (int i = 0; i < numMessaggi; i++) {
            out.println(messaggi[i]);
        }
    }

}
