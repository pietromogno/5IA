/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverudp;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author manuel.vivian
 */
public class ServerUDP {

    public final static int PORTA = 9000;
    private final static int BUFFER = 1024;

    private DatagramSocket socket;
    private ArrayList<InetAddress> indirizziClient;
    private ArrayList<Integer> porteClient;
    private HashSet<String> clientEsistenti;

    public ServerUDP() throws IOException {
        socket = new DatagramSocket(PORTA);
        indirizziClient = new ArrayList();
        porteClient = new ArrayList();
        clientEsistenti = new HashSet();
    }

    public void run() {
        byte[] buf = new byte[BUFFER];
        while (true) {
            try {
                Arrays.fill(buf, (byte) 0);
                DatagramPacket pacchetto = new DatagramPacket(buf, buf.length);
                socket.receive(pacchetto);

                String contenuto = new String(buf, buf.length);

                InetAddress indClient = pacchetto.getAddress();
                int portClient = pacchetto.getPort();

                String id = indClient.toString() + "," + portClient;
                if (!clientEsistenti.contains(id)) {
                    clientEsistenti.add(id);
                    porteClient.add(portClient);
                    indirizziClient.add(indClient);
                }

                System.out.println(id + " : " + contenuto);
                byte[] data = (id + " : " + contenuto).getBytes();
                for (int i = 0; i < indirizziClient.size(); i++) {
                    InetAddress cl = indirizziClient.get(i);
                    int cp = porteClient.get(i);
                    pacchetto = new DatagramPacket(data, data.length, cl, cp);
                    socket.send(pacchetto);
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    public static void main(String args[]) throws Exception {
        ServerUDP s = new ServerUDP();
        s.run();
    }
}
