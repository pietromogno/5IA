/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Enrico
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ExecutorService executor=Executors.newCachedThreadPool();
        ServerSocket listener;
        try{
            listener=new ServerSocket(1337);
            while(true){
                try{
                    Socket socket=listener.accept();
                    executor.submit(new Dialogo(socket));
                }catch(IOException e){
                    executor.shutdown();
                }
            }
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }
    
}
