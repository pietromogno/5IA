package server;

import server.Objects.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static server.SQLHelper.*;

public class Server {

    private int clientId;
    private ArrayList<ClientThread> clientList;
    private SimpleDateFormat date;
    private int portNumber;
    private boolean keepGoingMainServer;

    /**
     * The builder
     *
     * @param portNumber
     */
    public Server(int portNumber) {
        this.clientId = 0;
        this.portNumber = portNumber;
        this.date = new SimpleDateFormat();
        this.clientList = new ArrayList<>();
        getConnection();
    }

    /**
     * Method to start the server socket and accept all the client request to
     * connect to the server. This method also closes all the client socket at
     * the end.
     */
    public void start() {
        keepGoingMainServer = true;
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            ExecutorService executor = Executors.newCachedThreadPool();
            while (keepGoingMainServer) {

                serverDisplay("Server listening at port " + portNumber + ".");

                Socket socket = serverSocket.accept();

                ClientThread clientListener = new ClientThread(socket, clientId++);
                clientList.add(clientListener);
                executor.submit(clientListener);
            }
            try {
                closeAllConections();
                serverSocket.close();
                for (int i = 0; i < clientList.size(); i++) {
                    ClientThread tc = clientList.get(i);
                    try {
                        tc.socket.close();
                    } catch (IOException ioE) {
                        ioE.printStackTrace();
                    }
                }
            } catch (Exception e) {
                serverDisplay("Exception closing the server and clients: ");
                e.printStackTrace();
            }
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
    }

    /**
     * Method to display the messages in the server display
     *
     * @param msg
     */
    synchronized void serverDisplay(String msg) {
        String timeMsg = date.format(new Date()) + " " + msg + "\n";
        System.out.println(timeMsg);
    }

    /**
     * Method to remove a client if it logs out
     *
     * @param id
     */
    synchronized void remove(int id) {
        for (int i = 0; i < clientList.size(); i++) {
            ClientThread ct = clientList.get(i);
            if (ct.id == id) {
                clientList.remove(i);
                return;
            }
        }
    }

    /**
     * Synchronized method to send a message to all the users
     *
     * @param message
     */
    private synchronized void broadcast(String message) {
        String timeMsg = date.format(new Date()) + " " + message + "\n";
        for (int i = clientList.size(); i >= 0; i--) {
            ClientThread ct = clientList.get(i);
            if (!ct.writeMsg(new Message(Message.BROADMESSAGE, new String[]{timeMsg}))) {
                clientList.remove(i);
                serverDisplay("Disconnected Client " + ct.username + " removed from list.");
            }
        }
    }

    /**
     * Synchronized method to send a message to a specific client
     *
     * @param dest
     * @param msg
     */
    private synchronized void unicast(String dest, String msg) {
        String timeMsg = date.format(new Date()) + " " + msg + "\n";
        for (int i = clientList.size(); i >= 0; i--) {
            ClientThread ct = clientList.get(i);
            if (ct.username.equalsIgnoreCase(dest)) {
                if (!ct.writeMsg(new Message(Message.SINGMESSAGE, new String[]{timeMsg}))) {
                    clientList.remove(i);
                    serverDisplay("Disconnected Client " + ct.username + " removed from list.");
                }
            }
        }
    }

    public static void main(String[] args) {
        int portNumber = 9898;
        Server server = new Server(portNumber);
        server.start();
    }

    /**
     * Private class which implements the runnable, each client has its own
     * client thread
     */
    private class ClientThread implements Runnable {

        Socket socket;
        ObjectInputStream sInput;
        ObjectOutputStream sOutput;
        int id;
        String username = "";
        Message clientMessage;
        String connObtDate;

        ClientThread(Socket socket, int clientID) {
            id = clientID;
            this.socket = socket;

            threadDisplay("Thread trying to create Object Input/Output Streams");

            try {
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput = new ObjectInputStream(socket.getInputStream());

            } catch (IOException e) {
                serverDisplay("Exception creating new Input/output Streams: " + e);
                return;
            }
            connObtDate = new Date().toString() + "\n";
        }

        private void threadDisplay(String message) {
            System.out.println(message);
        }

        @Override
        public void run() {
            boolean keepGoing = true;
            while (keepGoing) {

                try {
                    clientMessage = (Message) sInput.readObject();
                } catch (IOException IoE) {
                    serverDisplay(id + " Exception reading Streams: " + IoE);
                    break;
                } catch (ClassNotFoundException CnfE) {
                    break;
                }

                keepGoing = handleClientMessage(clientMessage);
            }
            remove(id);
            close();
        }

        private void close() {
            try {
                if (sOutput != null) {
                    sOutput.close();
                }
            } catch (Exception e) {
            }
            try {
                if (sInput != null) {
                    sInput.close();
                }
            } catch (Exception e) {
            }
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception e) {
            }
        }

        /**
         * Function to send any kind of message to the client
         *
         * @param msg
         * @return
         */
        private boolean writeMsg(Message msg) {
            if (!socket.isConnected()) {
                close();
                return false;
            }
            try {
                sOutput.writeObject(msg);
            } catch (IOException e) {
                serverDisplay("Error sending message to " + username);
                serverDisplay(e.toString());
            }
            return true;
        }

        private void registerClient(String[] message) {
            if (insertAccount(id, (String) message[0], (String) message[1])) {
                writeMsg(new Message(Message.SATISFAIED, new String[]{"Registration succede : " + date.format(new Date()) + "\n"}));
                username = (String) message[0];
            } else {
                writeMsg(new Message(Message.SINGMESSAGE, new String[]{"Registration not succede : " + date.format(new Date()) + "\n"}));
            }
        }

        private void loginClient(String[] message) {
            if (loginAccount((String) message[0], (String) message[1])) {
                writeMsg(new Message(Message.SATISFAIED, new String[]{"Login succede : " + date.format(new Date()) + "\n"}));
                username = (String) message[0];
            } else {
                writeMsg(new Message(Message.SINGMESSAGE, new String[]{"Login not succede : " + date.format(new Date()) + "\n"}));
            }
        }

        private boolean logoutClient() {
            return false;
        }

        private void getWhoIsIn() {
            writeMsg(new Message(Message.SINGMESSAGE, new String[]{"List of the users connected at " + date.format(new Date()) + "\n"}));
            for (int i = 0; i < clientList.size(); i++) {
                ClientThread ct = clientList.get(i);
                writeMsg(new Message(Message.SINGMESSAGE, new String[]{(i + 1) + ") " + ct.username + " since " + ct.connObtDate}));
            }
        }

        private void sendSinMessage(String[] message) {
            unicast(message[0], username + ": " + message[0]);
        }

        private void sendBroadMessage(String[] message) {
            broadcast(username + ": " + message[0]);
        }

        /**
         * Function which handle the message received from the client
         *
         * @param clientMessage
         * @return
         */
        boolean handleClientMessage(Message clientMessage) {
            boolean ris = true;
            String[] message = clientMessage.getMessage();
            int type = clientMessage.getType();

            switch (type) {
                case Message.REGISTER:
                    registerClient(message);
                    break;
                case Message.LOGIN:
                    loginClient(message);
                    break;
                case Message.LOGOUT:
                    ris = logoutClient();
                    break;
                case Message.WHOISIN:
                    getWhoIsIn();
                    break;
                case Message.SINGMESSAGE:
                    sendSinMessage(message);
                    break;
                case Message.BROADMESSAGE:
                    sendBroadMessage(message);
                    break;
            }
            return ris;
        }
    }

}
