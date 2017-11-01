/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chattcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @brief @author Piergiorgio
 * @date 28/10/2017
 * @version 1.0
 */
public class Server {

    int porta = 1234;
    String log = "";

    Server() throws Exception {
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(porta);
        try {
            while (true) {
                // crea il thread e lo lancia
                new Capitalizer(listener.accept(), clientNumber++).start();
            }
        } finally {
            listener.close();
        }
    }

    private class Capitalizer extends Thread {

        private Socket socket;
        private final int clientNumber;
        private BufferedReader in;
        private PrintWriter out;

        public Capitalizer(Socket socket, int clientNumber) throws IOException {
            this.socket = socket;
            this.clientNumber = clientNumber;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.out.println(log);
        }

        public void run() {
            String input;
            try {
                while (true) {
                    input = in.readLine();
                    if (input.compareTo("DISCONNESSO") == 0) {
                        socket.close();
                        break;
                    }
                    //out.println(input.toUpperCase());
                }
                socket.close(); // log("Couldn't close a socket, what's going on?");
                myLog("Connection with client# " + clientNumber + " closed");
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private void myLog(String message) {
            log += message;
            System.out.println(log);
        }
    }

}
