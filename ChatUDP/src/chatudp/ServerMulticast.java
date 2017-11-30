package chatudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rikic
 */
public class ServerMulticast implements Runnable {

    MulticastSocket serverSocket;
    InetAddress group;
    int portClients = 9898;
    
    DatagramPacket msgIn;
    DatagramPacket msgOut;

    @Override
    public void run() {
        try {
            group = InetAddress.getByName("224.0.0.1");
            serverSocket = new MulticastSocket(portClients);
            // Ricevo le risposte
            byte[] buf = new byte[1024];
            msgIn = new DatagramPacket(buf, buf.length);
            while (true) {
                serverSocket.receive(msgIn);
                msgOut = new DatagramPacket(msgIn.getData(), msgIn.getData().length, group, portClients);
                serverSocket.send(msgOut);
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //indirizzo ip: 224.0.0.1
}
