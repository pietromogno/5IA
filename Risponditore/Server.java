package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws Exception {
        System.out.println("Panificio aperto.");
        String clientName = "";
        ServerSocket listener = new ServerSocket(9898);
        try {
            while (true) {
                // creazione e inizio del thread
                new Risponditore(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }

    private static class Risponditore extends Thread {

        private Socket socket;
        private String clientName;

        public Risponditore(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Panificio luca = new Panificio();
                String input;
                String output = "";
                
                while (true) { //cilo della comunicazione
                    output +=luca.getDomanda(); //viene stabilita la domanda da porre
                    out.println(output); //si comunica al client tale domanda
                    input = in.readLine(); //viene ricevuta la risposta dal client
                    if (input == null || input.equals(".")) { //se si è inserito il . la comunicazione viene chiusa
                        out.println(clientName + " se n'è andato");
                        socket.close();
                        break;
                    }
                    output = luca.getRisposta(input);

                }
            } catch (IOException e) {
                myLog("Error handling client# " + clientName + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    // log("Couldn't close a socket, what's going on?");
                }
                myLog("Connection with client# " + clientName + " closed");
            }
        }

        private void myLog(String message) {
            System.out.println(message);
        }
    }

}