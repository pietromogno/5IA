package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matteo Favaro
 */
public class Server {

    public static void main(String[] args) throws IOException {
        int p = 7000;
        try {
            System.out.println("avvio");
            ServerSocket s = new ServerSocket(p);
            System.out.println("pronto");
            while (true) {
                try {
                    Socket conn = s.accept();
                    Thread client = new ServerTask(conn);
                    client.start();
                } catch (IOException e) {
                    System.out.println("errore" + e);
                }
            }

        } catch (IOException e) {
            System.err.println("avvio impossibile ");
        }

    }

}

class ServerTask extends Thread {

    private Socket conn;
    private OutputStreamWriter out;
    private BufferedReader in;

    public ServerTask(Socket conn) {
        this.conn = conn;
    }

    @Override
    public void run() {
        System.out.println("Debug connesso");
        Pizzeria pizzeria = new Pizzeria();
        try {
            out = new OutputStreamWriter(conn.getOutputStream());
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String mess;
            out.write(pizzeria.getMessaggio());
            out.write("\n");
            out.flush();
            do {
                mess = in.readLine();
                pizzeria.cambiaStato(mess);
                out.write(pizzeria.getMessaggio());
                out.write("\n");
                out.flush();
            } while (!pizzeria.hasClienteFinito());

        } catch (IOException ex) {
            Logger.getLogger(ServerTask.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
