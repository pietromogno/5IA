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
    private static DatagramSocket socket;
    private static String hostname;
    private static MessageReceiver receiver;
    private static ClientForm form;

    protected static void sendMessage(String m) throws IOException {
        byte[] buffer = m.getBytes();
        InetAddress dest = InetAddress.getByName(hostname);
        DatagramPacket message = new DatagramPacket(buffer, buffer.length, dest, PORT_OUT);
        socket.send(message);
    }
    
    protected static void updateForm(String s){
        String op = s.substring(0,s.indexOf(" "));
        if(op.equals("ok")||op.equals("err")){
            form.messages.setText(s.substring(s.indexOf(" ")));
        }else{
            form.chatArea.append(s);
        }
    }

    public static void main(String[] args) throws SocketException {
        hostname = "localhost";
        socket = new DatagramSocket();
        receiver = new MessageReceiver(socket);
        receiver.start();
        form = new ClientForm();
        
    }

}
