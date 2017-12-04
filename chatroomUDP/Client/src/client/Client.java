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
	private static final String HOST_NAME = "localhost";
	private static DatagramSocket socket;
	private static MessageReceiver receiver;
	private static ClientForm form;
	private static InetAddress serverAddress;

	protected static void sendMessage(String m) throws IOException {
		byte[] buffer = m.getBytes();
		DatagramPacket message = new DatagramPacket(buffer, buffer.length, serverAddress, PORT_OUT);
		socket.send(message);
	}

	protected static void updateForm(String[] m) {
		form.chatArea.setText("");
		for(String s: m){
			form.chatArea.append(s+"\n");
		}
	}

	protected static void updateForm(String s) {
		form.messages.setText(s.substring(s.indexOf(" ")));
	}

	public static void main(String[] args) throws SocketException, UnknownHostException {
		socket = new DatagramSocket();
		receiver = new MessageReceiver(socket);
		receiver.start();
		form = new ClientForm();
		serverAddress = InetAddress.getByName(HOST_NAME);
	}

}
