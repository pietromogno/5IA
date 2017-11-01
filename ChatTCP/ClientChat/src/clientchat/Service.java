/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Musone Mattia
 */
class Service extends Observable {

    private static Socket socket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static Client c;
    private static String idUtente, nomeUtente;
    private String serverAddress = "192.168.1.11";

    public static void main(String args[]) {
        idUtente = args[0];
        nomeUtente = args[1];
        c = new Client(nomeUtente);
        c.setVisible(true);
        new Service();
    }

    Service() {
        try {
            this.addObserver(c);
        } catch (NullPointerException ex) {

        }
        try {
            socket = new Socket(serverAddress, 9090);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            refresh();
        } catch (IOException e) {
            refresh();
            //System.out.println("Errore");
        }
    }

    public static void closeConnection() {
        if (connectionState()) {
            try {
                socket.close();
                in.close();
                out.close();
            } catch (Exception e) {
                //System.out.println("Errore chiusura");
            }
        }
    }
    
     void reconnect(){
        try {
            socket = new Socket(serverAddress, 9090);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            refresh();
        }
    }

     String[] isRegistered(String nomeUtente) {
        try {
            out.writeInt(0);
            out.writeUTF(nomeUtente);
            out.flush();
            return (String[]) in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            //Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);ù
            reconnect();
            String[] ris = {"Sei disconnesso dal server", "1"};
            return ris;
        }
    }

    String[] registerToDB(String nome, String cognome, String nomeUtente, String password) {
        String invio = nome + "," + cognome + "," + nomeUtente + "," + password;
        try {
            out.writeInt(1);
            out.writeUTF(invio);
            out.flush();
            return (String[]) in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            // Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            reconnect();
            String[] ris = {"Sei disconnesso dal server", "1"};
            return ris;
        }
    }

     String[] accedi(String nomeUtente, String password) {
        String invio = nomeUtente + "," + password;
        try {
            out.writeInt(3);
            out.writeUTF(invio);
            out.flush();
            return (String[]) in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            //Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            reconnect();
            String[] ris = {"Sei disconnesso dal server", "1"};
            return ris;
        }
    }

     protected String saveChat(String chat, String dest) {
        String invio = nomeUtente + "," + dest + "," + idUtente;
        try {
            out.writeInt(6);
            out.writeUTF(invio);
            out.writeUTF(chat);
            out.flush();
            return String.valueOf(in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
            //System.out.println("Errore ricezione");
            reconnect();
            return "Sei disconnesso dal server";
        }
        
    }

     ArrayList<String[]> showChat(String utenteDest) {
        String invio = utenteDest + "," + nomeUtente;
        Object c = null;
        try {
            out.writeInt(4);
            out.writeUTF(invio);
            out.flush();
            return (ArrayList<String[]>) in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            //ex.printStackTrace();
            reconnect();
            ArrayList<String[]> error = new ArrayList<>();
            String temp[] = {"Disconnesso"};
            error.add(temp);
            return error;
        }

    }

     ArrayList<String> getUtenti(String utente) {
        Object c = null;
        try {
            out.writeInt(5);
            out.writeUTF(utente);
            out.flush();
            return (ArrayList<String>) in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            //ex.printStackTrace();
            reconnect();
            ArrayList<String> temp = new ArrayList<>();
            temp.add("Errore di comunicazione");
            return temp;
        }

    }

     String deleteAccount(String password) {
        String invio = nomeUtente + "," + password;
        try {
            out.writeInt(7);
            out.writeUTF(invio);
            out.flush();
            return String.valueOf(in.readObject());
        } catch (IOException | ClassNotFoundException ex) {
            reconnect();
            return "Sei disconnesso dal server";
        }
    }

    static boolean connectionState() {
        return socket != null ? socket.isConnected() : false;
    }

    public void refresh() {  //aggiorna le viste
        this.setChanged();
        this.notifyObservers();
    }
}
