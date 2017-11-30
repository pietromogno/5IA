/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientudp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class MessageSender implements Runnable {

    public final static int PORTA = 9000;
    private DatagramSocket sck;
    private String hostname;

    MessageSender(DatagramSocket s, String h) {
        sck = s;
        hostname = h;
    }

    private void sendMessage(String s) throws Exception {
        byte buf[] = s.getBytes();
        InetAddress indirizzo = InetAddress.getByName(hostname);
        DatagramPacket pacchetto = new DatagramPacket(buf, buf.length, indirizzo, PORTA);
        sck.send(pacchetto);
    }

    @Override
    public void run() {
        boolean connesso = false;
        do {
            try {
                sendMessage("CHIUSO.");
                connesso = true;
            } catch (Exception e) {

            }
        } while (!connesso);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                while (!input.ready()) {
                    Thread.sleep(100);
                }
                sendMessage(input.readLine());
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
}

class MessageReceiver implements Runnable {

    DatagramSocket sck;
    byte buf[];

    MessageReceiver(DatagramSocket s) {
        sck = s;
        buf = new byte[1024];
    }

    @Override
    public void run() {
        while (true) {
            try {
                DatagramPacket pacchetto = new DatagramPacket(buf, buf.length);
                sck.receive(pacchetto);
                String pacchettoRicevuto = new String(pacchetto.getData(), 0, pacchetto.getLength());
                System.out.println(pacchettoRicevuto);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
}

public class ClientUDP {

    public static void main(String args[]) throws Exception {
        String host = null;
        if (args.length < 1) {
            System.out.println("Usage: java ChatClient <server_hostname>");
            System.exit(0);
        } else {
            host = args[0];
        }
        DatagramSocket socket = new DatagramSocket();
        MessageReceiver r = new MessageReceiver(socket);
        MessageSender s = new MessageSender(socket, host);
        Thread rt = new Thread(r);
        Thread st = new Thread(s);
        rt.start();
        st.start();
    }
}
