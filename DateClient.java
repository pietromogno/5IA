/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capitalizeclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class DateClient {

    public static void main(String[] args) throws IOException {
        String serverAddress = "localhost"; // server string
        Socket s = new Socket(serverAddress, 9090);
        DataOutputStream dOut = new DataOutputStream(s.getOutputStream());
        Scanner sc = new Scanner(System.in);
        String str = "Inizio conversazione";
        try {

            PrintWriter out
                    = new PrintWriter(s.getOutputStream(), true); // with autoflush
            BufferedReader input
                    = new BufferedReader(new InputStreamReader(s.getInputStream()));

            out.println(sc.next());
            str = input.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
