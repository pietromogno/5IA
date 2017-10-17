package clientperserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * @author Davide Porcu
 */
public class Client {

    private String indirizzoServer;
    private int porta;
    private Socket connessione;
    private BufferedReader in;
    private PrintWriter out;
    private InterfacciaGraficaClient graficaClient;

    public Client(String indirizzo, int porta) {
        this.indirizzoServer = indirizzo;
        this.porta = porta;
        this.graficaClient = new InterfacciaGraficaClient(this);
    }

    public void connectToServer() {
        try {
            connessione = new Socket(indirizzoServer, porta);
            in = new BufferedReader(new InputStreamReader(connessione.getInputStream()));
            out = new PrintWriter(connessione.getOutputStream(), true);
            leggiDaServer();
        } catch (IOException e) {
            graficaClient.setTextErrori("Impossibile collegarsi al server!");
        }
    }

    private void chiudiConnessione() {
        try {
            connessione.close();
        } catch (IOException ex) {
            //errore in chiusura
            System.out.println("errore chiusura");
        }
        graficaClient.setTextErrori("Connessione Terminata!");
        graficaClient.setTextDomanda("");
    }

    public void scriviAlServer(String messaggio) {
        if (!messaggio.equalsIgnoreCase("exit")) {
            out.println(messaggio);
        } else {
            chiudiConnessione();
        }
    }

    public String leggiDaServer() {
        String msgDaServer = "";
        try {
            while (true) {//legge per sempre-> finchè la connessione non viene interrotta
                msgDaServer = in.readLine();
                int numRigheDaLeggere = Integer.parseInt(msgDaServer);
                msgDaServer = in.readLine();//la prima è sempre la domanda
                graficaClient.setTextDomanda("<html>"+msgDaServer+"</html>");
                msgDaServer = "";
                for (int i = 1; i < numRigheDaLeggere; i++) {
                    msgDaServer +=in.readLine() + "\n";//ricevo -> non scrivere null quando chiudo comunicazione
                }
                graficaClient.setTextElencoInformazioni(msgDaServer);              
            }
        } catch (IOException e) {
            chiudiConnessione();
        }
        return msgDaServer;
    }

    public static void main(String[] args) {
        String serverAddress = "localhost"; // server string
        int porta = 9999;
        Client c = new Client(serverAddress, porta);
        c.connectToServer();
    }

}
