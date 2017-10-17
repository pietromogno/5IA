package spaccino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSpaccio {

    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader stdIn;

    public void connectToServer() throws IOException {

        String serverAddress = "127.0.0.1";
        String userInput;
        Socket socket = new Socket(serverAddress, 9898);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        stdIn = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println(in.readLine()); //scrive a video cosa arriva dal server
            userInput = stdIn.readLine(); //legge da tastiera la risposta
            out.println(userInput); //invia al server la risposta
            if (userInput.equals("exit")) { //esce dal ciclo 
                break;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ClientSpaccio client = new ClientSpaccio();
        client.connectToServer();
    }
}
