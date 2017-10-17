package spaccino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import static spaccino.Spaccino.Spaccino;
import static spaccino.Spaccino.getDomandaSuccessiva;
import static spaccino.Spaccino.getRisposta;

public class ServerSpaccio {

    public static void main(String[] args) throws Exception {
        System.out.println("The capitalization server is running.");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(9898);
        Spaccino();
        try {
            while (true) {
                // crea il thread e lo lancia
                new Capitalizer(listener.accept(), clientNumber++).start();
            }
        } finally {
            listener.close();
        }
    }

    private static class Capitalizer extends Thread {

        private Socket socket;
        private int clientNumber;

        public Capitalizer(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            myLog("New connection with client #" + clientNumber + " at " + socket);
        }

        public void run() {
            try {

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                String domanda = Spaccino.domanda[0];
                String risposta = getRisposta(domanda);
                while (true) {
                    out.println(domanda + risposta);
                    System.out.println(risposta);
                    String input = in.readLine();
                    domanda = getDomandaSuccessiva(domanda, input);
                    System.out.println("UTENTE HA RISPOSTO: " + input);
                    if (input == null || input.equals(".")) {
                        socket.close();
                        break;
                    }
                }
            } catch (IOException e) {
                myLog("Error handling client# " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {

                }
                myLog("Connection with client# " + clientNumber + " closed");
            }
        }

        private void myLog(String message) {
            System.out.println(message);
        }
    }
}
