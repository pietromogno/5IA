package client.Objects;

import java.io.*;

public class Message implements Serializable {

    protected static final long serialVersionUID = 1112122200L;

    public static final int REGISTER = 0, LOGIN = 1, LOGOUT = 2, WHOISIN = 3, SINGMESSAGE = 4, BROADMESSAGE = 5, SATISFAIED = 6;
    int type;
    String[] message;

    public Message(int type, String[] message) {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public String[] getMessage() {
        return message;
    }
}
