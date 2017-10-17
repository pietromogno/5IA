package client;

import java.io.*;
import java.net.*;

public class Client {

    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader stdIn;

    public void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        String userInput;
        String serverOutput;
        String presentQ, finalQ;
        int menuLength;
        try {
            Socket socket = new Socket(serverAddress, 9898);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            System.out.println(in.readLine());
            userInput = stdIn.readLine();
            out.println(userInput);

            menuLength = Integer.parseInt(in.readLine());
            for (int i = 0; i < menuLength; i++) {
                System.out.println(in.readLine());
            }

            finalQ = in.readLine();

            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                presentQ = in.readLine();
                System.out.println(presentQ);
                if (finalQ.toLowerCase().equals(presentQ.toLowerCase())) {
                    socket.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.connectToServer();
    }

}
