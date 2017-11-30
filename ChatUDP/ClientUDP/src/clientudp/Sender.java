/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author simone.pasutti
 */
public class Sender extends Thread {

    private final int PORT = 4445;
    private DatagramSocket socket;
    private DatagramPacket data;
    private InetAddress server;

    Sender() {
        try {
            socket = new DatagramSocket(0);
            server = InetAddress.getByName("127.0.0.1");
        } catch (SocketException | UnknownHostException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run(Messaggio msg) {
        try {
            data = new DatagramPacket(msg.getBytes(), msg.length(), server, PORT);
            socket.send(data);
            System.out.println("Inviato");
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
