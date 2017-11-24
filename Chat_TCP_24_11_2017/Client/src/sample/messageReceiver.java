package sample;

import java.io.IOException;
import java.io.ObjectInputStream;

public class messageReceiver implements Runnable {

    private Client client;
    private ObjectInputStream inputStream;
    private volatile boolean running = true;

    public messageReceiver(Client client, ObjectInputStream inputStream) {
        this.client = client;
        this.inputStream = inputStream;

    }

    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        try {
            while (running) {
                Message catchedMessage = (Message) inputStream.readObject();
                switch (catchedMessage.getMessageType()) {
                    case Message.UPDATE:
                        Update up = (Update) catchedMessage.getMessage();
                        client.setNewClientList(up.getClientList());
                        break;
                    case Message.MESSAGE:
                        client.setMessage(catchedMessage);
                        break;
                    case Message.ERROR:
                        client.setError((String) catchedMessage.getMessage());
                        break;
                }
            }
        } catch (ClassNotFoundException cle) {
        } catch (IOException ioe) {
        }
    }
}
