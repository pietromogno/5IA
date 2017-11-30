/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author simone.pasutti
 */
public class Sender extends Thread {

    private static MulticastSocket socket;
    private static DatagramPacket data;
    private final int PORT = 4446;
    byte[] msg;

    Sender(byte[] msg) {
        try {
            this.msg = msg;
            socket = new MulticastSocket(PORT);
            socket.joinGroup(InetAddress.getByName("224.1.0.1"));
        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            data = new DatagramPacket(msg, msg.length, InetAddress.getByName("224.1.0.1"), PORT);
            socket.send(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
