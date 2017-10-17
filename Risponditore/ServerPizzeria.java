/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverpizzeria;

/**
 *
 * @author manuel.vivian
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerPizzeria {

    public static void main(String[] args) throws Exception {
        System.out.println("Server avviato");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(9898);
        try {
            while (true) {
                // crea il thread e lo lancia
                new ServerTask(listener.accept(), clientNumber++).start();
            }
        } finally {
            listener.close();
        }
    }

    private static class ServerTask extends Thread {

        private Socket socket;
        private int clientNumber;
        BufferedReader in;
        PrintWriter out;
        Pizzeria p;

        public ServerTask(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            //myLog("New connection with client #" + clientNumber + " at " + socket);
            p = new Pizzeria();
        }

        @Override
        public void run() {
            try {

                in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                p.getStato(in, out);//______getStato pizzeria

                // Send a welcome message to the client.
                //out.println("Hello, you are client #" + clientNumber + ".");
                //out.println("Enter a line with only a period to quit\n");

                // Get messages from the client, line by line; 
                // return them capitalized
                while (true) {
                    String input = in.readLine();
                    if (input == null || input.equals(".")) {
                        out.println("Connessione chiusa al client#" + clientNumber + "exit");
                        socket.close();
                        break;
                    }
                    out.println(input.toUpperCase());
                }
            } catch (IOException e) {
                myLog("Errore gestione del client# " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    // log("Couldn't close a socket, what's going on?");
                }
                myLog("Connessione col client# " + clientNumber + " chiusa");
            }
        }

        private void myLog(String message) {
            System.out.println(message);
        }
    }
}
