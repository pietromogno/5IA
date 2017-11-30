package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Matteo Favaro
 */
public class Client {

    private final String ANY = "";
    private final int PORTASERVER = 9999;
    private String username;
    private Socket connessione;
    private PrintWriter out;
    private String usernameDestinatarioAttuale;
    private String[] listaUtentiAttivi;

    private GraficaLogin graficaLogin;
    private GraficaRegistrazione graficaRegistrazione;
    private GraficaChat graficaChat;

    public Client() {
        this.username = "";
        this.usernameDestinatarioAttuale = "";
        listaUtentiAttivi=new String[0];
        start();
    }

    private void start() {
        try {
            connessione = new Socket("localhost", PORTASERVER);
            out = new PrintWriter(connessione.getOutputStream(), true);
            RicevitoreMessaggi ricevitore = new RicevitoreMessaggi(this, connessione);
            new Thread(ricevitore).start();
            graficaLogin = new GraficaLogin(this);
        } catch (IOException ex) {
            //showMessaggioErrore("Impossbile collegarsi al server!");
        }
    }

    public String[] getListaUtentiAttivi() {
        return this.listaUtentiAttivi;
    }

    public String getUsernameClient() {
        return this.username;
    }

    public void sendMessaggio(String messaggio) {
        out.println(username);
        out.println(usernameDestinatarioAttuale);
        out.println("MESSAGGIO");
        out.println(1);
        out.println(messaggio);
    }

    public void sendLogin(String username, String password) {
        out.println(this.username);
        out.println(ANY);
        out.println("LOGIN");
        out.println(2);
        out.println(username);
        out.println(password);
    }

    public void sendRegistrazione(String username, String password) {
        out.println(this.username);
        out.println(ANY);
        out.println("REGISTRAZIONE");
        out.println(2);
        out.println(username);
        out.println(password);
        
    }

    public void showMessaggio(String usernameMittente, String messaggio) {
        if (usernameMittente.equals(usernameDestinatarioAttuale)) {
            graficaChat.showMessaggio(usernameMittente, messaggio);
        }
    }

    public void setListaUtentiAttivi(String[] listaUtentiAttivi) {
        this.listaUtentiAttivi = listaUtentiAttivi;
        graficaChat.updateListaUtentiAttivi();
    }

    public void showErrore(String messaggioErrore) {
        //va beh... bah 
        System.out.println("Errore-> " + messaggioErrore);
    }

    public void showGraficaChat() {
        if (graficaRegistrazione != null) {
            graficaRegistrazione.dispose();
            graficaRegistrazione = null;
        }
        if (graficaLogin != null) {
            graficaLogin.dispose();
            graficaLogin = null;
        }
        graficaChat = new GraficaChat(this);
    }

    public void showGraficaLogin() {
        if (graficaRegistrazione != null) {
            graficaRegistrazione.dispose();
            graficaRegistrazione = null;
        }
        if (graficaChat != null) {
            graficaChat.dispose();
            graficaChat = null;
        }
        graficaLogin = new GraficaLogin(this);
    }

    public void showGraficaRegistrazione() {
        if (graficaChat != null) {
            graficaChat.dispose();
            graficaChat = null;
        }
        if (graficaLogin != null) {
            graficaLogin.dispose();
            graficaLogin = null;
        }
        graficaRegistrazione = new GraficaRegistrazione(this);
    }

    public void setUtenteInVisualizzazione(String nomeUtente) {
        if (!nomeUtente.equals(usernameDestinatarioAttuale)) {
            this.usernameDestinatarioAttuale = nomeUtente;
            graficaChat.resetMessaggi();
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static void main(String[] args) {
        Client c = new Client();
    }

}

class RicevitoreMessaggi implements Runnable {

    private Client client;
    private Socket connessione;
    private BufferedReader input;

    public RicevitoreMessaggi(Client client, Socket connessione) {
        this.connessione = connessione;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(connessione.getInputStream()));
            String nomeMittente, nomeDestinatario, tipoMessaggio;
            int numMsgDaLeggere;

            while (true) {
                nomeMittente = input.readLine();
                System.out.println("nomeMittente->"+nomeMittente);
                nomeDestinatario = input.readLine();
                System.out.println("nomeDestinatario->"+nomeDestinatario);
                tipoMessaggio = input.readLine();
                System.out.println("tipoMessaggio->"+tipoMessaggio);
                numMsgDaLeggere = Integer.parseInt(input.readLine());
                switch (tipoMessaggio) {
                    case "LOGIN":
                        String username = input.readLine();
                        client.setUsername(username);
                        client.showGraficaChat();
                        String[] listUtentiAttivi = new String[numMsgDaLeggere-1];
                        System.out.println("msg da leggere:"+numMsgDaLeggere);
                        System.out.println("UTENTI ATTIVI:");
                        for (int i = 0; i < numMsgDaLeggere-1; i++) {
                            listUtentiAttivi[i] = input.readLine();
                            System.out.println(listUtentiAttivi[i]);
                        }
                        System.out.println("----end----");
                        client.setListaUtentiAttivi(listUtentiAttivi);
                        break;
                    case "REGISTRAZIONE":
                        client.showGraficaLogin();
                        break;
                    case "MESSAGGIO":
                        String messaggio = input.readLine();
                        client.showMessaggio(nomeMittente, messaggio);
                        break;
                    case "UPDATELISTAUTENTIATTIVI":
                        System.out.println("ARRIVATO UPDATE LISTA ATTIVI");
                        String[] listUtentiAttiviUpdt = new String[numMsgDaLeggere];
                        System.out.println("msg da leggere:"+numMsgDaLeggere);
                        System.out.println("UTENTI ATTIVI:");
                        for (int i = 0; i < numMsgDaLeggere; i++) {
                            listUtentiAttiviUpdt[i] = input.readLine();
                            System.out.println(listUtentiAttiviUpdt[i]);
                        }
                        System.out.println("----end----");
                        client.setListaUtentiAttivi(listUtentiAttiviUpdt);
                        break;
                    case "ERRORE":
                        String messaggioErrore = input.readLine();
                        client.showErrore(messaggioErrore);
                        break;
                }
            }
        } catch (IOException e) {
        } finally {
            try {
                connessione.close();
            } catch (IOException e) {

            }
        }
    }
}
