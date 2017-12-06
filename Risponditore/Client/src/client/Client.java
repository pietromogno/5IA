package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Emanuele Pagotto
 */
public class Client {

    private static Socket s;
    private static BufferedReader in;
    private static PrintWriter out;
    private static Scanner kbrd;

    public static void connect() throws IOException {
        s = new Socket("localhost", 9090);
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out = new PrintWriter(s.getOutputStream(),true);
        kbrd = new Scanner(System.in);
    }

    public static void conversation() {
        try {
            boolean exit = false;
            while (!exit) {
                String message = in.readLine();
                String answers = in.readLine();
                if (!message.equals("exit")) {
                    System.out.println(message);
                    System.out.println(answers);
                    String output = kbrd.nextLine();
                    out.println(output);
                }
            }
            s.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        connect();
        conversation();
    }

}
