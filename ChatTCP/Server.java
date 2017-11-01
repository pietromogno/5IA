package chats;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Simone Trevisan 5IA
 */
public class Server extends javax.swing.JFrame {

    /**
     * Creates new form chat_server
     */
    static ServerSocket ss;
    static Socket s;
    static Socket s2;
    static DataInputStream din;
    static DataOutputStream dout;
    static DataInputStream din2;
    static DataOutputStream dout2;

    public static void main(String args[]) {
        String msgin = "";
        String msgin2 = "";
        try {
            ss = new ServerSocket(9898);
            System.out.println("Server Aperto");
            s = ss.accept();
            System.out.println("connesso utente 1");
            s2 = ss.accept();
            System.out.println("connesso utente 2");
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            din2 = new DataInputStream(s2.getInputStream());
            dout2 = new DataOutputStream(s2.getOutputStream());
            while (!msgin.equals("exit") && !msgin2.equals("exit")) {
                msgin = din.readUTF();
                dout2.writeUTF(msgin);
                msgin2 = din2.readUTF();
                dout.writeUTF(msgin2);
            }
        } catch (Exception e) {
        }
    }
}
