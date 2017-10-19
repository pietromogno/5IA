package pizzeria;

import java.io.BufferedReader;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/*******************************************************************************
 * @author Pietro Mogno & Simone Trevisan 5^IA
 * @date 16/10/2017
 ******************************************************************************/

public class Server {

    private static int numThread = 10;

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("...Siamo Aperti!...");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(4567);

        ExecutorService esecutore = Executors.newFixedThreadPool(numThread);
        for (int i = 0; i < 500; i++) {
            Runnable worker = new MyThread(listener.accept(), clientNumber++);
            esecutore.execute(worker);
        }
        esecutore.shutdown();
    }
}

class MyThread implements Runnable {

    private final Socket s;
    private final int num;
    private final String[][] mangiare = new String[10][10];
    private final String[][] bere = new String[10][10];

    public MyThread(Socket socket, int clientNum) {
        this.s = socket;
        this.num = clientNum;
    }

    public void run() {
        try {
            List<String> pizze;
            List<String> bibite;
            Pizzeria<String> albero = new Pizzeria<>();

            BufferedReader in;
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);

            mangiare[num][0] = in.readLine();
            bere[num][0] = mangiare[num][0];

            pizze = albero.insPizze(out, mangiare[num][0], in, 0);
            pizze.add(0, mangiare[num][0]);
            System.out.println(pizze);

            bibite = albero.insBibite(out, bere[num][0], in, 0);
            bibite.add(0, bere[num][0]);
            System.out.println(bibite);
            
        } catch (IOException e) {
            Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
