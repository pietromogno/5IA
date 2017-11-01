package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.logging.*;
import static server.Pizzeria.*;

public class Server {

    public static void main(String[] args) throws IOException {
        System.out.println("The pizza server is running.");

        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(9898);

        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            while (true) {
                executor.submit(new Cameriere(listener.accept(), clientNumber++));
            }
        } finally {
            listener.close();
        }
    }

    private static class Cameriere implements Runnable {

        private Socket socket;
        private int clientNumber, lastKeyId;
        private String clientName;
        private Object[] presentKey, lastKey;

        public Cameriere(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            myLog("New connection with client #" + clientNumber + " at " + socket.getInetAddress());
        }

        private void myLog(String message) {
            System.out.println(message);
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Benvenuto nella pizzeria! come ti chiami?");
                clientName = in.readLine();

                out.println(getMenuLength());
                presentKey = getFirstQuestion();
                out.println(clientName + " " + presentKey[1]);//menu >> trattamento speciale

                lastKey = getLastQuestion();
                lastKeyId = (int) lastKey[0];
                out.println(clientName + " " + lastKey[1]);

                while (true) {
                    String input = in.readLine();
                    presentKey = getQuestion((int) presentKey[0], input);
                    out.println(clientName + " " + presentKey[1]);
                    if ((int) presentKey[0] == lastKeyId) {
                        socket.close();
                        break;
                    }
                }

            } catch (IOException ex) {
                myLog("Error handling client numeber" + clientNumber);
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    socket.close();
                } catch (IOException ex) {
                    myLog("Error handling client numeber" + clientNumber);
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                myLog("Connection with client# " + clientNumber + " closed");
            }

        }

    }

}
