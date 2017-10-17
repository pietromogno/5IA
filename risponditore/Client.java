package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader stdIn;

    public void connectToServer() throws IOException {

        String serverAddress = "127.0.0.1";
        String userInput;

        //vengono creati e inizializzati gli streams
        Socket socket = new Socket(serverAddress, 9898);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        stdIn = new BufferedReader(new InputStreamReader(System.in));
        // messaggi di apertura del discorso
        System.out.println("buongiorno, come si chiama?");
        out.println(stdIn.readLine()); //viene comunicato il nome del cliente
        String text = ""; //stringa di input da tastiera
        while (true) { //ciclo pruincipale della discussione in concomitanza con quello del server
            text = in.readLine(); //si prende l'input del server
            System.out.println("autista [premi . per andartene]: " + text); //viene comunicata la risposta del server
            out.println(stdIn.readLine()); //il cliente risponde
        }
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.connectToServer();
    }

}
