package server;

import java.io.*;
import java.net.*;

/**
 * @author Emanuele Pagotto
 */
public class Server {

    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}

class ServerThread extends Thread{
    
    private static final int PORT_IN = 4445;
    private static final int PORT_OUT = 4446;
    
    protected MulticastSocket out = null; // out
    protected DatagramSocket in = null; //in
    protected InetAddress group = null;
    
    public ServerThread(){
        this("ServerThread");
    }
    
    public ServerThread(String name){
        super(name);
        try {
            group = InetAddress.getByName("224.0.0.1");
        } catch (UnknownHostException e) {
                System.err.println("Client Error: " + e.getMessage());
                System.err.println("Localized: " + e.getLocalizedMessage());
                System.err.print("Stack Trace: ");
                e.printStackTrace();
            }
    }
    
    @Override
    public void run(){
        byte [] buffer = new byte[1024];
        DatagramPacket received = null;
        try{
            out = new MulticastSocket(PORT_OUT);
            in = new DatagramSocket(PORT_IN);
        }catch(IOException e){
            
        }
    }
}
