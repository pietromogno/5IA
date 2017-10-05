/*
 * DataServer.java
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


import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;


public class DataServer {

    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(9090);
        System.out.println("running srver");
        try {
	
       
            while (true) {
                Socket socket = listener.accept();
          
                try {
                    PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true); // with autoflush
                    out.println(new Date().toString());
                    out.println("Ti ho inviato la data corrente!\nOra puoi chiudere questa finestra ed il server");
                } finally {
                    // autocloseable ...
                    socket.close();
                }
            }
        }
        finally {
            listener.close();
        }
    }
}

