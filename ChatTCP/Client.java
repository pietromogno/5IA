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
import java.net.Socket;

/**
 *
 * @author Piergiorgio
 */
public class Client {

    private static final int porta = 1234;

    public static void main(String args[]) throws IOException {
        Socket sock = new Socket("localhost", porta);
        BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        PrintWriter writer = new PrintWriter(sock.getOutputStream(), true);
        new LogIn(reader, writer);
    }
    
}
