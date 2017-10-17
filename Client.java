/**
 * @brief è il Client/cliente della pizzeria
 * @author Matteo Mognato
 * @date 10/10/2017
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    static int port = 9090;

    public static void main(String[] args) throws IOException {

        Socket sock = new Socket("localhost", port);
        BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        PrintWriter writer = new PrintWriter(sock.getOutputStream(), true);
        Scanner t = new Scanner(System.in);
        System.out.println("INIZIO:");
        String str = reader.readLine();
        do{
            System.out.println(str);
            System.out.println("SCRIVI: ");
            writer.println(t.nextLine());
            str=reader.readLine();
        }while (!str.equals("FINE"));
    }
}
