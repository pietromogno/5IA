
package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Matteo Favaro
 */
public class Client {

    
    public static void main(String[] args) throws IOException {
        String sA = "localhost"; // server string
        int p = 7000;
        Socket s = new Socket(sA,p);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()) );
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()) );
        Scanner t = new Scanner(System.in);
        String str = "";
        String mess;
        do {
            mess = in.readLine();
            System.out.println(mess);
            mess = t.nextLine();
            out.write(mess+"\n");
            out.flush();
            
        }while(!mess.toUpperCase().equals("exit"));
        in.close();
        out.close();
        s.close();
        System.out.println("disconesso");
    
    }
    
}
