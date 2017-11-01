/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chattcp;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Piergiorgio
 * @brief Pogramma che simula una chat sfruttando il proto-
 * collo TCP dove vi è un server che smista i messaggi
 * di piu client che comunicano
 * @date 28/10/2017
 * @version 1.0
 */
public class ChatTCP {
    
    static Server s;
    static ArrayList<DatiClient> connessi;


    public static void main(String[] args) { 
        connessi= new ArrayList<>();
        try {
            s = new Server();
        } catch (Exception ex) {
            Logger.getLogger(ChatTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
