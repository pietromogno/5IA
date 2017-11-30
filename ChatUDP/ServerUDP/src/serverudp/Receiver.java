/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverudp;

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
 * @author MATTI
 */
public class Receiver extends Thread {

    private final int PORT = 4445;
    private DatagramSocket socket;
    private DatagramPacket data;
    private InetAddress server;

    Receiver() {
        try {
            socket = new DatagramSocket(PORT);
            server = InetAddress.getByName("127.0.0.1");
        } catch (SocketException | UnknownHostException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                data = new DatagramPacket(new byte[1024], 1024);
                socket.receive(data);
                new Thread(new Sender(data.getData())).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
