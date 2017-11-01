package client;

import client.Objects.Message;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends Observable {

    private ObjectInputStream sInput;
    private ObjectOutputStream sOutput;
    private Socket socket;
    private String server, username, password;
    private int port;
    private Message msg = null;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Client(String serverAddress, int portNumber) {
        this.server = serverAddress;
        this.port = portNumber;
        start();
    }

    public void start() {
        try {
            socket = new Socket(server, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
        refresh(new Message(Message.SINGMESSAGE, new String[]{msg}));

        try {
            sInput = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException eIO) {
            eIO.printStackTrace();
        }
        new ListenFromServer().start();
    }

    public void sendRegistration(String username, String password) {
        try {
            sOutput.writeObject(new Message(Message.REGISTER, new String[]{username, password}));
            setUsername(username);
            setPassword(password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendLogin(String username, String password) {
        try {
            sOutput.writeObject(new Message(Message.LOGIN, new String[]{username, password}));
            setUsername(username);
            setPassword(password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendLogout() {
        try {
            sOutput.writeObject(new Message(Message.LOGOUT, null));
            disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendWhoIsIn() {
        try {
            sOutput.writeObject(new Message(Message.WHOISIN, null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String dest, String msg) {
        try {
            sOutput.writeObject(new Message(Message.SINGMESSAGE, new String[]{dest, msg}));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendBroadMessage(String msg) {
        try {
            sOutput.writeObject(new Message(Message.BROADMESSAGE, new String[]{msg}));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
        }
    }

    private void refresh(Object o) { //Update the views
        this.setChanged();
        notifyObservers(o);
    }

    class ListenFromServer extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    msg = (Message) sInput.readObject();
                    if (msg.getType() == Message.SATISFAIED) {
                        refresh(msg);
                        sleep(100);
                        refresh(this);
                    } else {
                        refresh(msg);
                    }
                } catch (IOException e) {
                    refresh(new Message(Message.SINGMESSAGE, new String[]{"Server has close the connection: " + e}));
                    break;
                } catch (ClassNotFoundException e2) {
                    e2.printStackTrace();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
