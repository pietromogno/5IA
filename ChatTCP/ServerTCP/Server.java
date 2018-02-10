package servertcp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author jimmy.giana
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket listener;
        try {
            listener = new ServerSocket(1337);
            while (true) {
                try {
                    Socket socket = listener.accept();
                    executor.submit(new ClientManaging(socket));
                } catch (IOException e) {
                    executor.shutdown();
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static HashMap<Integer, Socket> utentiConnessi = new HashMap<>();

    public static void addUtente(int idUtente, Socket s) {
        utentiConnessi.put(idUtente, s);
    }

    public static void removeUtente(int idUtente) {
        utentiConnessi.remove(idUtente);
    }

    public static boolean isUtenteConnesso(int idUtente) {
        return utentiConnessi.containsKey(idUtente);
    }

    public static void notificaNuovaChat(int idContatto, String nomeChat, int idChat) throws IOException {
        if (utentiConnessi.containsKey(idContatto)) {
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(utentiConnessi.get(idContatto).getOutputStream())), true);
            out.println("AggiungiChat");
            out.println(nomeChat);
            out.println(idChat);
        }
    }

    public static void inviaMessaggio(int idDestinatario, String testo, int idChat) throws IOException {
        if (utentiConnessi.containsKey(idDestinatario)) {
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(utentiConnessi.get(idDestinatario).getOutputStream())), true);
            out.println("Leggi");
            out.println(idChat);
            out.println(testo);
        }
    }

    public static void notificaNuovoMembro(int idContatto, String utente) throws IOException {
        if (utentiConnessi.containsKey(idContatto)) {
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(utentiConnessi.get(idContatto).getOutputStream())), true);
            out.println("Rimuovi");
            out.println(utente);
        }
    }
}