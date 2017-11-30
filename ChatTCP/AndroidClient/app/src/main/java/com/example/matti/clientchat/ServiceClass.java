package com.example.matti.clientchat;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import oggetti.Messaggio;

/**
 * @author Musone Mattia
 */
class ServiceClass extends Observable implements Serializable {

    private static Socket socket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private String serverAddress = "192.168.1.10";
    protected Messaggio msg;
    private boolean run;
    private Receiver t1;

    void tryConnection() {
        if (socket == null || !socket.isConnected()) {
            try {
                new Connection().execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        if (socket != null && socket.isConnected()) {
            run = true;
            t1 = new Receiver();
            t1.start();
        }
    }

    void registerToDB(String nome, String cognome, String nomeUtente, String password) {
        Sender sender = new Sender();
        sender.execute(new Messaggio(Messaggio.REGISTRAZIONE, null, nomeUtente, null, nome, cognome, password));
    }

    void accedi(String nomeUtente, String password) {
        Sender sender = new Sender();
        sender.execute(new Messaggio(Messaggio.ACCESSO, null, nomeUtente, null, null, null, password));
    }

    void invioChat(String chat, String sorg, String dest) {
        Sender sender = new Sender();
        sender.execute(new Messaggio(Messaggio.SALVACONVERSAZIONE, chat, sorg, dest, null, null, null));
    }

    void showChat(String nomeUtetne, String utenteDest) {
        Sender sender = new Sender();
        sender.execute(new Messaggio(Messaggio.MOSTRAMESSAGGIO, null, nomeUtetne, utenteDest, null, null, null));
    }

    void getUtenti(String utente) {
        Sender sender = new Sender();
        sender.execute(new Messaggio(Messaggio.OTTIENIUTENTI, null, utente, null, null, null, null));
    }

    void deleteAccount(String password, String nomeUtente) {
        Sender sender = new Sender();
        sender.execute(new Messaggio(Messaggio.CANCELLAZIONE, null, nomeUtente, null, null, null, password));
    }

    void setImage(String nomeUtente, byte[] immagine) {
        Sender sender = new Sender();
        sender.execute(new Messaggio(Messaggio.IMMAGINE, immagine, nomeUtente, null, null, null, null));
    }

    void closeConnection(String nomeUtente) {
        Sender sender = new Sender();
        sender.execute(new Messaggio(Messaggio.INTERROMPI, null, nomeUtente, null, null, null, null));
        new CloseConnection().execute();
    }

    private void connectionError() {
        msg = new Messaggio(Messaggio.ERRORECONNESSIONE, "Sei disconnesso dal server", null, null, null, null, null);
        refresh();
    }

    private void refresh() {  //aggiorna le viste
        setChanged();
        notifyObservers();
    }

    private class CloseConnection extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    private class Sender extends AsyncTask<Messaggio, Void, Void> {

        @Override
        protected synchronized Void doInBackground(Messaggio... ints) {
            try {
                out.reset();
                out.writeObject(ints[0]);
                out.flush();
            } catch (IOException | NullPointerException e) {
                connectionError();
                return null;
            }
            return null;
        }
    }

    private class Connection extends AsyncTask<Void, Void, Void> {

        @Override
        protected synchronized Void doInBackground(Void... ints) {
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(serverAddress, 9191), 1000);
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
            } catch (Exception e) {
                connectionError();
            }
            return null;
        }
    }

    private class Receiver extends Thread implements Serializable {
        @Override
        public synchronized void run() {
            try {
                msg = new Messaggio(Messaggio.CONNESSO, null, null, null, null, null, null);
                refresh();
                while (run && socket != null && socket.isConnected()) {
                    msg = (Messaggio) in.readObject();
                    if (msg.getFunzione() == Messaggio.ACCESSO) {
                        if (!((Object[]) msg.getMessaggio())[0].equals("1")) {
                            run = false;
                        }
                    }
                    refresh();
                }
            } catch (IOException | ClassNotFoundException e) {
                socket = null;
                connectionError();
            }
        }
    }

}


