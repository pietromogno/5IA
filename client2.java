/*
 * client2.java
 * 
 * Copyright 2017 Studente ITIS Zuccante <studente@lap2-ws07>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.Socket;

/*
 * start server using:
 * php -S localhost:8080
 */

public class client2 {

    public static void main(String[] args) throws IOException {
        String serverAddress = "localhost"; // server string
        
        Socket s = new Socket(serverAddress, 9090);
        DataOutputStream dOut = new DataOutputStream(s.getOutputStream());
       // dOut.writeBytes("GET /index.php HTTP/1.0\n\n"); // minimal GET request
      
        BufferedReader input =
            new BufferedReader(new InputStreamReader(s.getInputStream()));
        String str = "DATA RECEIVED:";
        do {
            System.out.println(str);
            str = input.readLine();
        } while(str != null);
    }
}

