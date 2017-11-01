package Server;

/**
 * @brief Server gestisce i vari Client l'accesso alla Chat con Login/registrazione
 * e gestisce la messaggistica.
 * @author Matteo Mognato\\
 * @date 10/10/2017
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {
    
    static int port = 9090;
    static HashMap <String, Socket> h= new HashMap<>();
    
    public static void main(String[] args) throws IOException {
        ServerSocket srvSocket = new ServerSocket(port);
        System.out.println("running server");
        ExecutorService esecutore = Executors.newCachedThreadPool();
        try {
            while (true) {
                Socket sock = srvSocket.accept();
                esecutore.submit(new NuovoClient(sock));
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
    
    public NuovoClient(Socket sock) {
        this.sock = sock;
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
            boolean ris = false;
            String username="", tipo="", password="";
            while(!ris) {
                tipo = reader.readLine();
                username = reader.readLine();
                password = reader.readLine();
                String avvisi="non presente in elenco";
                if (tipo.toLowerCase().equals("login")) {
                    if (SQLHelper.isUserCorrect(username, password)) {
                        if(Server.h.containsKey(username)){ 
                            avvisi="Con questi dati si è gia loggato un qualcun altro";
                        }else{
                            ris = true;
                        }
                    }
                } else if (tipo.toLowerCase().equals("registrazione")) {
                    if (!SQLHelper.isUserPresente(username)) {
                        ris = true;
                        SQLHelper.inserisciUser(username, password);
                    }else{
                        avvisi="Username già presente nel DataBase";
                    }
                }
                if (ris) {
                    avvisi="OK";
                    Server.h.put(username, sock);
                }
                writer.println(avvisi);
            }
            while(true){
                String u=reader.readLine();
                if(u!=null&&!u.equals("")&&Server.h.containsKey(u)){
                    String mes=reader.readLine();
                    PrintWriter writer2 = new PrintWriter(Server.h.get(u).getOutputStream(), true);
                    writer2.println(username+":  "+mes);
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(NuovoClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
