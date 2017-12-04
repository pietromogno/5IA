/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.*;
import java.net.*;

/**
 * @author Pagotto Emanuele
 */
class MessageReceiver extends Thread {

    private final DatagramSocket socket;
    private final byte[] buffer;

    public MessageReceiver(DatagramSocket socket) {
        this.socket = socket;
        buffer = new byte[1024];
    }

    @Override
    public void run() {
        while (true) {
            try {
                DatagramPacket message = new DatagramPacket(buffer, buffer.length);
                socket.receive(message);
                String received = new String(message.getData(), 0, message.getLength());
                if (received.charAt(0) == 'l') {
                    int l = Integer.parseInt(received.substring(received.indexOf(" ") + 1));
                    String[] msgs = new String[l];
                    for (int i = 0; i < l; i++) {
                        DatagramPacket tmpP = new DatagramPacket(buffer, buffer.length);
                        socket.receive(message);
                        String tmpS = new String(message.getData(), 0, message.getLength());
                        msgs[i] = tmpS;
                    }
                    Client.updateForm(msgs);
                } else {
                    Client.updateForm(received);
                }
            } catch (IOException e) {
                System.err.println("Client Error: " + e.getMessage());
                System.err.println("Localized: " + e.getLocalizedMessage());
                System.err.print("Stack Trace: ");
                e.printStackTrace();
            }
        }
    }

}
