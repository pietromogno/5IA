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
        System.out.println("Buongiorno");
        
        String text = ""; //stringa di input da tastiera
        System.out.println("Per uscire premi '.' \n");
        while (true) { //ciclo principale della conversazione
            text = in.readLine(); //si prende l'input del server
                System.out.println(text); //viene comunicata la risposta del server
            out.println(stdIn.readLine()); //il cliente risponde
        }
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.connectToServer();
    }

}