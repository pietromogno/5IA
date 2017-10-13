package risponditore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Emanuele Pagotto
 */
public class Client {

    private Socket s;
    private BufferedReader in;
    private PrintWriter out;

    public void connect() throws IOException {
        s = new Socket("localhost", 9090);
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out = new PrintWriter(s.getOutputStream());
    }

    public void main(String[] args) throws Exception{
        connect();
    }

}
