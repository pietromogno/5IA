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
    private ArrayList<Utente> utenti;
    private ArrayList<String> chat;
    private byte[] buffer_in, buffer_out;

    public ServerThread() throws SocketException, SQLException, ClassNotFoundException {
        this("ServerThread");
    }

    public ServerThread(String name) throws SocketException, SQLException, ClassNotFoundException {
        socket = new DatagramSocket(PORT);
        buffer_in = new byte[1024];
        buffer_out = new byte[1024];
        utenti = new ArrayList<>();
        chat = UtilDb.getMessages();
    }

    @Override
    public void run() {
        try {
            while (true) {
                buffer_in = new byte[1024];
                DatagramPacket packet_in = new DatagramPacket(buffer_in, buffer_in.length);
                socket.receive(packet_in);
                buffer_in = packet_in.getData();
                String message = new String(buffer_in, 0, packet_in.getLength());
                String op = message.substring(0, message.indexOf(" "));
                if (op.equals("login")) {
                    login(packet_in);
                } else if (op.equals("register")) {
                    register(packet_in);
                } else if (op.equals("msg")) {
                    receiveMessage(message.substring(message.indexOf(" ") + 1));
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
        return (p) ? (s.substring(s.indexOf(" ") + 1, s.lastIndexOf(" "))) : (s.substring(s.lastIndexOf(" ") + 1, s.length()));
    }

    private int getIdByAddress(InetAddress a) {
        int i = 0;
        while (!(utenti.get(i).getAddress().toString()).equals(a.toString())) {
            i++;
        }
        return utenti.get(i).getId();
    }

    private void updateClient(Utente u) throws SQLException, ClassNotFoundException, IOException {
        buffer_out = ("l " + chat.size()).getBytes();
        socket.send(new DatagramPacket(buffer_out, buffer_out.length, u.getAddress(), u.getPORT()));
        for (String m : chat) {
            buffer_out = m.getBytes();
            socket.send(new DatagramPacket(buffer_out, buffer_out.length, u.getAddress(), u.getPORT()));
        }
    }

    private void login(DatagramPacket packet) throws SQLException, ClassNotFoundException, IOException {
        String m = new String(buffer_in, 0, packet.getLength());
        String username = getParam(m, true);
        String password = getParam(m, false);
        //System.out.println(username.replace(" ", "_") + " " + password.replace(" ", "_"));
        if (UtilDb.login(username, password)) {
            buffer_out = "ok Login Effettuato".getBytes();
            socket.send(new DatagramPacket(buffer_out, buffer_out.length, packet.getAddress(), packet.getPort()));
            Utente u = new Utente(packet.getAddress(), packet.getPort(), UtilDb.getIdByName(username), username);
            utenti.add(u);
            updateClient(u);
        } else {
            buffer_out = "err Dati di login Errati".getBytes();
            socket.send(new DatagramPacket(buffer_out, buffer_out.length, packet.getAddress(), packet.getPort()));
        }

    }

    private void register(DatagramPacket packet) throws SQLException, ClassNotFoundException, IOException {
        String m = new String(buffer_in, 0, packet.getLength());
        String username = getParam(m, true);
        String password = getParam(m, false);
        if (UtilDb.register(username, password)) {
            buffer_out = "ok Registrazione effettuata".getBytes();
        } else {
            buffer_out = "err Dati già esistenti".getBytes();
        }
        socket.send(new DatagramPacket(buffer_out, buffer_out.length, packet.getAddress(), packet.getPort()));
    }

    private void receiveMessage(String m) throws IOException, SQLException, ClassNotFoundException {
        String utente = m.substring(0,m.indexOf(" "));
        int id = UtilDb.getIdByName(utente);
        String message = m.substring(m.indexOf(" ") + 1);
        m = m.substring(m.indexOf(" "));
        UtilDb.inserisciMessaggio(id, message);
        chat.add(utente + ": " + m);
        for (Utente u : utenti) {
            updateClient(u);
        }
    }
}
