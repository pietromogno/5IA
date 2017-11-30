package server;

import dbObjects.Utente;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Emanuele Pagotto
 */
public class Server {

    public static void main(String[] args) throws SocketException {

        try {
            ServerThread s = new ServerThread();
            s.start();
            System.out.println("Server is running");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

class ServerThread extends Thread {

    private static final int PORT = 4445;
    private DatagramSocket socket;
    private DatagramPacket packet;
    private ArrayList<Utente> utenti;
    private ArrayList<String> chat;
    private byte[] buffer;

    public ServerThread() throws SocketException, SQLException, ClassNotFoundException {
        this("ServerThread");
    }

    public ServerThread(String name) throws SocketException, SQLException, ClassNotFoundException {
        socket = new DatagramSocket(PORT);
        byte[] buffer = new byte[1024];
        packet = new DatagramPacket(buffer, buffer.length);
        utenti = new ArrayList<>();
        chat = UtilDb.getMessages();
    }

    @Override
    public void run() {
        try {
            while (true) {
                socket.receive(packet);
                String message = new String(packet.getData());
                String op = message.substring(0, message.indexOf(" "));
                if (op.equals("login")) {
                    login();
                } else if (op.equals("register")) {
                    register();
                } else {
                    receiveMessage();
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            System.err.println("Client Error: " + e.getMessage());
            System.err.println("Localized: " + e.getLocalizedMessage());
            System.err.print("Stack Trace: ");
            e.printStackTrace();
        }
    }

    private String getParam(String s, boolean p) {
        return (p) ? (s.substring(s.indexOf(" ")+1, s.lastIndexOf(" "))) : (s.substring(s.lastIndexOf(" ")+1, s.length()));
    }

    private int getIdByAddress(InetAddress a) {
        int i = 0;
        while (!(utenti.get(i).getAddress().toString()).equals(a.toString())) {
            i++;
        }
        return utenti.get(i).getId();
    }

    private void updateClient(Utente u) throws SQLException, ClassNotFoundException, IOException {
        for (String m : chat) {
            buffer = m.getBytes();
            socket.send(new DatagramPacket(buffer, buffer.length, u.getAddress(), u.getPORT()));
        }
    }

    private void login() throws SQLException, ClassNotFoundException, IOException {
        String username = getParam(new String(packet.getData()), true);
        String password = getParam(new String(packet.getData()), false);
        System.out.println(username.replace(" ", "_")+" "+password.replace(" ", "_"));
        if (UtilDb.login(username, password)) {
            buffer = "ok Login Effettuato".getBytes();
            socket.send(new DatagramPacket(buffer, buffer.length, packet.getAddress(), packet.getPort()));
            Utente u = new Utente(packet.getAddress(), packet.getPort(), UtilDb.getIdByName(username), username);
            utenti.add(u);
            updateClient(u);
        } else {
            buffer = "err Dati di login Errati".getBytes();
            socket.send(new DatagramPacket(buffer, buffer.length, packet.getAddress(), packet.getPort()));
        }

    }

    private void register() throws SQLException, ClassNotFoundException, IOException {
        String username = getParam(new String(packet.getData()), true);
        String password = getParam(new String(packet.getData()), false);
        if (UtilDb.register(username, password)) {
            buffer = "ok Registrazione effettuata".getBytes();
        } else {
            buffer = "err Dati già esistenti".getBytes();
        }
        socket.send(new DatagramPacket(buffer, buffer.length,packet.getAddress(),packet.getPort()));
    }

    private void receiveMessage() throws IOException, SQLException, ClassNotFoundException {
        String messaggio = new String(packet.getData());
        int id = getIdByAddress(packet.getAddress());
        UtilDb.inserisciMessaggio(id, messaggio);
        chat.add(id + ": " + messaggio);
        for (Utente u : utenti) {
            updateClient(u);
        }
    }
}
