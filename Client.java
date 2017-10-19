package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/*******************************************************************************
 * @author Pietro Mogno & Simone Trevisan 5^IA
 * @date 16/10/2017
 ******************************************************************************/

public class Client {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String serverAddress = "localhost"; 
        Socket so = new Socket(serverAddress, 4567);
        BufferedReader in= new BufferedReader(new InputStreamReader(so.getInputStream()));
        PrintWriter pr = new PrintWriter(so.getOutputStream(), true);

        String st = "Benvenuto nella pizzeria, digita il tuo nome :  ";
        System.out.println(st);
        do {
            st = sc.nextLine();
            pr.println(st);
            System.out.println(in.readLine());

            insPizze(sc, pr, in);
            insBibite(sc, pr, in);
        } while (st != null);

    }

    public static void insPizze(Scanner in, PrintWriter out, BufferedReader input) throws IOException {
        String pizze = "vuoto";
        while (!pizze.equals("BASTA")) {
            pizze = in.nextLine();
            out.println(pizze);
            System.out.println(input.readLine());
        }
    }

    public static void insBibite(Scanner in, PrintWriter out, BufferedReader input) throws IOException {
        String bibite = "vuoto";
        while (!bibite.equals("BASTA")) {
            bibite = in.nextLine();
            out.println(bibite);
            System.out.println(input.readLine());
            out.println(in.nextLine());
            System.out.println(input.readLine());
        }
    }
}
