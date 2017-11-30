/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientudp;

import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author simone.pasutti
 */
public class ClientUDP extends Thread {//manda messaggi

    private final static int PORT = 4446;
    static MulticastSocket ss;
    static int i = 0;
    static Scanner sc;
    private static final Random rnd = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        try {
            sc=new Scanner(System.in);
            ss = new MulticastSocket(PORT);
            ss.joinGroup(InetAddress.getByName("224.1.0.1"));
        } catch (IOException ex) {
            Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        new ClientUDP().start();
        
    }

    @Override
    public void run() {
        try {
            
            
            DatagramSocket s = new DatagramSocket(0);
            InetAddress group = InetAddress.getByName("127.0.0.1");
            new Thread(new Reciver()).start();
            //s.joinGroup(group);
            while (true) {
                String msg = sc.nextLine();
                DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(),
                        group, 4447);
                s.send(hi);
                System.out.println("Sent");
                // get their responses!
                /*byte[] buf = new byte[1000];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);
            s.receive(recv);*/
                //Thread.sleep(rnd.nextInt(5) * 1000 + 5000);
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }

    class Reciver extends Thread {

        public synchronized void run() {
            try {
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                while (true) {
                    System.out.println("Waiting for a  multicast message...");
                    ss.receive(packet);
                   String msg = new String(packet.getData(), packet.getOffset(),
                            packet.getLength());
                    System.out.println("[Multicast  Receiver] Received:\n    " + msg);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }
}
