package com.example.matti.clientchat;


import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Musone Mattia
 */
class ServiceClass extends Thread {

    static Socket socket;
    static ObjectOutputStream out;
    static ObjectInputStream in;
    String serverAddress = "192.168.1.11";

    @Override
    public synchronized void run() {
        try {
            socket = new Socket(serverAddress, 9090);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    synchronized String[] isRegistered(String nomeUtente) {
        Message1 m = new Message1();
        m.execute("0", "", nomeUtente);
        try {
            return (String[]) m.get(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            String[] ris = {"Sei disconnesso dal server", "1"};
            return ris;
        }
    }

    synchronized String[] registerToDB(String nome, String cognome, String nomeUtente, String password) {
        String invio = nome + "," + cognome + "," + nomeUtente + "," + password;
        Message1 m = new Message1();
        m.execute("1", "", invio);
        try {
            return (String[]) m.get(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            String[] ris = {"Sei disconnesso dal server", "1"};
            return ris;
        }

    }

    synchronized String[] accedi(String nomeUtente, String password) {
        String invio = nomeUtente + "," + password;
        Message1 m = new Message1();
        m.execute("3", "", invio);
        try {
            return (String[]) m.get(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            String[] ris = {"Sei disconnesso dal server", "1"};
            return ris;
        }
    }


    synchronized ArrayList<String[]> showChat(String utenteDest, String utenteSorg) {
        String invio = utenteDest + "," + utenteSorg;
        try {
            Message1 m = new Message1();
            m.execute("4", "", invio);

            return (ArrayList<String[]>) m.get(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            ArrayList<String[]> error = new ArrayList<>();
            String temp[] = {"Disconnesso"};
            error.add(temp);
            return error;
        }
    }

    synchronized ArrayList<String> getUtenti(String utente) {
        Message1 m = new Message1();
        m.execute("5", "", utente);
        try {
            return (ArrayList<String>) m.get(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException  | ClassCastException | TimeoutException e) {
            e.printStackTrace();
            ArrayList<String> temp = new ArrayList<>();
            temp.add("Errore di comunicazione");
            return temp;
        }

    }

    synchronized protected String invioChat(String chat, String utenteSorg, String utenteDest, String idUtente) {
        String invio = utenteSorg + "," + utenteDest + "," + idUtente;
        Message1 m = new Message1();
        m.execute("6", chat, invio);
        try {

            return String.valueOf(m.get(1000, TimeUnit.MILLISECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return "Sei disconnesso dal server";
    }

    synchronized protected String deleteAccount(String utente, String password) {
        String invio = utente + "," + password;
        Message1 m = new Message1();
        m.execute("7", "", invio);
        try {

            return String.valueOf(m.get(1000, TimeUnit.MILLISECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return "Sei disconnesso dal server";
    }

    class Message1 extends AsyncTask<String, String, Object> {

        @Override
        protected synchronized Object doInBackground(String... ints) {
            int function = Integer.parseInt(ints[0]);
            String chat = ints[1];
            String invio = (String) ints[2];

            try {

                out.writeInt(function);
                out.writeUTF(invio);
                if (!chat.isEmpty()) {
                    out.writeUTF(chat);
                }
                out.flush();

                return in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                return null;
            }
        }
    }

}

