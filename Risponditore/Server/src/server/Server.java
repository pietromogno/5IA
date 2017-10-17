/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author MATTI
 */
public class Server {

    public static void main(String[] args) {
        try {
            int porta = 9090;
            ServerSocket listener = new ServerSocket(porta);
            while (true) {
                Socket socket = listener.accept();
                new GiveService(socket);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    static class GiveService extends Thread {

        private final Socket socket;
        private ObjectOutputStream out;
        private ObjectInputStream input;

        public GiveService(Socket soc) {
            socket = soc;
            this.start();
        }

        @Override
        public void run() {
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());
                Ristorante s = new Ristorante(); //machhina a stati finiti
                String in = "";
                while (socket.isConnected()) {
                    out.writeUTF(s.giveRisposta(in)); //comunico alla macchina a stati finiti la risposta/richiesta del client e invio la risposta come stringa
                    out.flush();
                    in = input.readUTF();
                }
            } catch (IOException e) {
                try {
                    socket.close();
                    out.close();
                    input.close();
                    System.out.println("Chiuso");
                } catch (IOException i) {
                    System.out.println("Errore di connessione");
                }
            }
        }
    }
}
