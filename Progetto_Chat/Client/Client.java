package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * @brief Client gestisce le varie finestre quella di accesso alla chat e quella
 *        di Messaggistica con i vari client.
 * @author matteo
 */
public class Client {

    static int port = 9090;
    static String username, pw;
    
    public static void main(String[] args) throws IOException {

        Socket sock = new Socket("localhost", port);
        BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        PrintWriter writer = new PrintWriter(sock.getOutputStream(), true);
        JFrameAccessoClient jAC=new JFrameAccessoClient(writer, reader);
        jAC.setVisible(true);
        while(jAC.isVisible()) System.out.print(""); //aspettoCheLaRegistrazioneSiaAvvenuta
        JFrameClient jC=new JFrameClient(writer, reader, username);
        jC.setVisible(true);
    }
}
