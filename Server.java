package server;
/**
 * @brief è il Server ovvero la Pizzeria fa uso di un esecutore che permette 
 * la gestione concorrente di più Clienti.
 * @author Matteo Mognato
 * @date 10/10/2017
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    static int port = 9090;

    public static void main(String[] args) throws IOException {
        ServerSocket srvSocket = new ServerSocket(port);
        System.out.println("running server");
        ExecutorService esecutore = Executors.newCachedThreadPool();
        try {
            while (true) {
                Socket sock = srvSocket.accept();
                esecutore.submit(new NuovoClient(sock));;
            }
        } finally {
            esecutore.shutdown();
            srvSocket.close();
        }
    }
}

class NuovoClient implements Runnable {

    private PrintWriter writer;
    private BufferedReader reader;
    private Socket sock;
    Scanner t;

    public NuovoClient(Socket sock) {
        this.sock = sock;
        t = new Scanner(System.in);
        try {
            writer = new PrintWriter(sock.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        } catch (IOException ex) {
            System.out.println("ERRORE CON WRITER O READER");
        }
    }

    @Override
    public void run() {
        try {
            String d = AutomaPizzeria.domanda(null, null);
            writer.println(new Date().toString() + "  " + d);
            String r = reader.readLine();
            String nome = r;
            while (!d.equals("FINE")) {

                d = AutomaPizzeria.domanda(r, d); //chiede la nuova domanda
                System.out.println("Server to " + nome + " > " + d);
                writer.println(d);

                r = reader.readLine();
                System.out.println(nome + "> " + r);
            }
            writer.println("FINE");
            System.out.println("Server to " + nome + " > FINE ORDINE");
        } catch (IOException ex) {
            System.out.println("ERRORE CON WRITER O READER");
        }

    }

}
