package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class Client {

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty nickname = new SimpleStringProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty error = new SimpleStringProperty();

    private int port, clientId;
    private String address;
    private Socket connection;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private Thread receiver;
    private messageReceiver messageReceiver;
    private messagingController messagingController;

    private HashMap<Integer, String> newClientList;
    private Message message;

    public Client(String address, int port) {
        this.address = address;
        this.port = port;
        this.newClientList = new HashMap<>();
        connectToServer();
    }

    public void stopReciving() throws InterruptedException {
        try {
            connection.close();
            messageReceiver.terminate();
            receiver.join();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void startReceiver() {
        messageReceiver = new messageReceiver(this, this.inputStream);
        receiver = new Thread(messageReceiver);
        receiver.start();
    }

    private void connectToServer() {
        try {
            connection = new Socket(this.address, this.port);
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            inputStream = new ObjectInputStream(connection.getInputStream());
        } catch (IOException iOe) {
            iOe.printStackTrace();
        }
    }

    public boolean logIn() {
        boolean ris = false;
        Login log = new Login();
        log.setUsername(this.getUsername());
        log.setPassword(this.getPassword());
        Message msgLog = new Message();
        msgLog.setMessageType(Message.LOGIN);
        msgLog.setMessage(log);
        try {
            outputStream.writeObject(msgLog);
            Message msgIn = (Message) inputStream.readObject();
            if (msgIn.getMessageType() == Message.LOGIN) {
                this.setClientId(Integer.parseInt((String) msgIn.getMessage()));
                ris = true;
            } else {
                this.setError((String) msgIn.getMessage());
            }
        } catch (ClassNotFoundException cle) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ris;
    }

    public boolean registerUser() {
        boolean ris = false;
        Registration registration = new Registration();
        registration.setName(this.getName());
        registration.setNickname(this.getNickname());
        registration.setPassword(this.getPassword());
        registration.setUsername(this.getUsername());
        Message msgReg = new Message();
        msgReg.setMessageType(Message.REGISTRATION);
        msgReg.setMessage(registration);
        try {
            outputStream.writeObject(msgReg);
            Message msgIn = (Message) inputStream.readObject();
            if (msgIn.getMessageType() == Message.REGISTRATION) {
                this.setClientId(Integer.parseInt((String) msgIn.getMessage()));
                ris = true;
            } else {
                this.setError((String) msgIn.getMessage());
            }
        } catch (ClassNotFoundException cle) {
        } catch (IOException e) {
        }
        return ris;
    }

    public void updateClientList() {
        Message mesUp = new Message();
        mesUp.setMessageType(Message.UPDATE);
        try {
            outputStream.writeObject(mesUp);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void sendMessage(int detinationId, String message) {
        Message sendMessage = new Message();
        sendMessage.setMessageType(Message.MESSAGE);
        sendMessage.setSenderId(clientId);
        sendMessage.setDestinationId(detinationId);
        sendMessage.setMessage(message);
        try {
            outputStream.writeObject(sendMessage);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void sendLogout() {
        Message mLogOut = new Message();
        mLogOut.setMessageType(Message.LOGOUT);
        try {
            outputStream.writeObject(mLogOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMessagingController(sample.messagingController messagingController) {
        this.messagingController = messagingController;
    }

    public HashMap<Integer, String> getNewClientList() {
        return newClientList;
    }

    public void setNewClientList(HashMap<Integer, String> newClientList) {
        this.newClientList = newClientList;
        this.messagingController.setListViewData(this.newClientList);
    }

    public void setMessage(Message message) {
        this.message = message;
        messagingController.setMessage(this.message);
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getNickname() {
        return nickname.get();
    }

    public void setNickname(String nickname) {
        this.nickname.set(nickname);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getError() {
        return error.get();
    }

    public void setError(String error) {
        this.error.set(error);
    }

}
