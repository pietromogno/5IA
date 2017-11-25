/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 *
 * @author pago1
 */
public class Client extends Thread{
	
	private static final int PORT_IN = 4446;
	private static final int PORT_OUT = 4445;
	private int id;
	
	MulticastSocket socket;
	InetAddress group;
	InetAddress server;
	
	public Client() throws IOException{
		server = InetAddress.getByName("localhost");
		group = InetAddress.getByName("224.0.1.l");
		socket = new MulticastSocket(PORT_IN);
		socket.joinGroup(group);
	}
	
	public void run(){
		boolean continua = true;
		byte[] buffer = new byte[1024];
		
	}
	
	public void sendMessage(DatagramPacket p){
		socket.send(new DatagramPacket);
	}
	
	public void getChat(){
		
	}
	
	public void login(String usrName, String pw){
		
	}
	
	public void register(String usrName, String pw){
		
	}
	
	
}
