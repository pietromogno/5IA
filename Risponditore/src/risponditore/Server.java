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

    public void main(String[] args) {
        try {
            System.out.println("The server has started.");
            int cNumber = 0;
            ServerSocket socket = new ServerSocket(9090);
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
            while (cNumber <= 100) {
                System.out.println("A new client has connected.");
                executor.execute(new ConnectClient(socket.accept(), cNumber++));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ConnectClient implements Runnable {

    private final Socket s;
    private final int cNumber;

    public ConnectClient(Socket s, int cNumber) {
        this.s = s;
        this.cNumber = cNumber;
        System.out.println("Connesso al client " + cNumber + " sul socket " + s.toString());
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
                int input = reader.read();
                automa.executeUpdate(input);
                conversazioneFinita = automa.isConversationEnded();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
