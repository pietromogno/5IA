
package clienttcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTCP {

    public static void main(String[] args) throws IOException {
        Socket s=new Socket("localhost",1337);
        BufferedReader in=new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
        LoginView v = new LoginView(in,out);
        v.setVisible(true);
    }
    
}
