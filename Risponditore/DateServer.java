/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capitalizeserver;

/**
 *
 * @author simone.pasutti
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DateServer {

    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(9090);
        Socket socket = null;
        System.out.println("Inizio Conversazione");
        try {
            socket = listener.accept();
            BufferedReader input
                    = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out
                    = new PrintWriter(socket.getOutputStream(), true); // with autoflush	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
