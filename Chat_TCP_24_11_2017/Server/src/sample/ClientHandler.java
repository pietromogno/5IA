package sample;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

public class ClientHandler implements Runnable {
    protected int clientId, destinationId;
    protected String clientUsername;
    private Socket connection;
    private Server server;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ClientHandler(Server server, Socket connection, int clientId) {
        this.server = server;
        this.connection = connection;
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void sendMessage(Message message) throws IOException {
        try {
            outputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            inputStream = new ObjectInputStream(connection.getInputStream());
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            System.out.println("Client: " + clientId + " is runnig...");
            while (true) {
                Message catchedMessage = (Message) inputStream.readObject();
                switch (catchedMessage.getMessageType()) {
                    case Message.LOGIN:
                        Login userLog = (Login) (catchedMessage.getMessage());
                        String username = userLog.getUsername();
                        String password = userLog.getPassword();
                        Message loginResponse = new Message();
                        loginResponse.setDestinationId(clientId);
                        loginResponse.setSenderId(server.serverId);
                        if (SQLhelper.isUserRegistered(username, password)) {
                            clientUsername = username;
                            server.addClient(this);
                            loginResponse.setMessageType(Message.LOGIN);
                            loginResponse.setMessage("" + clientId);
                        } else {
                            loginResponse.setMessageType(Message.ERROR);
                            loginResponse.setMessage("Login failed!");
                        }
                        sendMessage(loginResponse);
                        break;
                    case Message.REGISTRATION:
                        Registration userReg = (Registration) (catchedMessage.getMessage());
                        String name = userReg.getName();
                        String nickname = userReg.getNickname();
                        String regusername = userReg.getUsername();
                        String regpassword = userReg.getPassword();
                        Message regResponse = new Message();
                        regResponse.setDestinationId(clientId);
                        regResponse.setSenderId(server.serverId);
                        if (SQLhelper.isUsernameFree(regusername)) {
                            SQLhelper.registerAccount(regusername, regpassword, name, nickname);
                            clientUsername = regusername;
                            server.addClient(this);
                            regResponse.setMessageType(Message.REGISTRATION);
                            regResponse.setMessage("" + clientId);
                        } else {
                            regResponse.setMessageType(Message.ERROR);
                            regResponse.setMessage("Registration failed!");
                        }
                        sendMessage(regResponse);
                        break;
                    case Message.LOGOUT:
                        server.removeClient(this);
                        connection.close();
                        break;
                    case Message.UPDATE:
                        HashMap<Integer, ClientHandler> tempActiveClients = server.getActiveClient();
                        HashMap<Integer, String> activeClientsList = new HashMap<>();
                        tempActiveClients.forEach((key, value) -> {
                            if (key != clientId)
                                activeClientsList.put(key, value.clientUsername);
                        });
                        Update newUpdate = new Update(activeClientsList);
                        Message updateResponse = new Message();
                        updateResponse.setDestinationId(clientId);
                        updateResponse.setSenderId(server.serverId);
                        updateResponse.setMessageType(Message.UPDATE);
                        updateResponse.setMessage(newUpdate);
                        sendMessage(updateResponse);
                        break;
                    case Message.MESSAGE:
                        destinationId = catchedMessage.getDestinationId();
                        server.sendMessage(this, catchedMessage);
                        break;
                }
            }
        } catch (SocketException sE) {
        } catch (EOFException e) {
        } catch (IOException iOe) {
            iOe.printStackTrace();
        } catch (ClassNotFoundException cNtFe) {
            cNtFe.printStackTrace();
        }

    }
}
