package risponditore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Emanuele Pagotto
 */
public class Server {

    public static void main(String[] args) {
        try {
            System.out.println("The server has started.");
            int cNumber = 0;
            ServerSocket sckListener = new ServerSocket(9090);
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
            while (cNumber <= 100) {
                Socket socket = sckListener.accept();
                cNumber++;
                executor.execute(new ConnectClient(socket));
                System.out.println("A new client has connected.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ConnectClient implements Runnable /*extends Thread*/ {

    private final Socket s;

    public ConnectClient(Socket s) {
        this.s = s;
        System.out.println("Connesso al client sul socket " + s.toString());
    }

    @Override
    public void run() {
        try {
            boolean conversazioneFinita = false;
            Risponditore automa = new Risponditore();
            PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            while (!conversazioneFinita) {
                writer.println(automa.getCurrentState() + "\n" + automa.getPossibleAnswers());
                String input = reader.readLine();
                automa.executeUpdate(Integer.parseInt(input));
                conversazioneFinita = automa.isConversationEnded();
            }
            writer.print("exit");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
