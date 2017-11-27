/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.net.*;

/**
 * @author Pagotto Emanuele
 */
public class Client {

    private static final int PORT_OUT = 4445;
    private DatagramSocket socket;
    private String hostname;

    public Client() throws SocketException {
        this("localhost");
    }

    public Client(String hostname) throws SocketException {
        String host = hostname;
        DatagramSocket socket = new DatagramSocket();
        MessageReceiver receiver = new MessageReceiver(socket);
        Thread r = new Thread(receiver);
        r.start();
    }

    private void sendMessage(String m) throws IOException {
        byte[] buffer = m.getBytes();
        InetAddress dest = InetAddress.getByName(hostname);
        DatagramPacket message = new DatagramPacket(buffer, buffer.length, dest, PORT_OUT);
        socket.send(message);
    }

    public static void main(String[] args) throws SocketException {
        String host = "localhost";
        DatagramSocket socket = new DatagramSocket();
        MessageReceiver receiver = new MessageReceiver(socket);
        Thread r = new Thread(receiver);
        r.start();
    }

}
