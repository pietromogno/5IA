package risponditore;

import java.io.IOException;
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
        System.out.println("Connesso al client " + cNumber + " sul socket " + s);
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
