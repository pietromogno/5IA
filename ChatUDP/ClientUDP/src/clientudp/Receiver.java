/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author simone.pasutti
 */
public class Receiver extends Thread {

    private static MulticastSocket socket;
    private static DatagramPacket data;

    Receiver() {
        try {
            socket = new MulticastSocket(4446);
            socket.joinGroup(InetAddress.getByName("224.1.0.1"));
            data = new DatagramPacket(new byte[1024], 1024);
        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                socket.receive(data);
                String str = new String(data.getData(), StandardCharsets.UTF_8);
                Service.messageRecived(new Messaggio(str.substring(str.indexOf(",") + 1, str.length()), str.substring(0,str.indexOf(",")))); //seleziona il nome dell'utente che ha inviato il messaggio
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
