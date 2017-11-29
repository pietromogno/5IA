package sample;

import java.io.Serializable;

public class Message implements Serializable {

    public static final long serialVersionUID = 19990701L;

    public static final int LOGIN = 0, LOGOUT = 1, REGISTRATION = 2, MESSAGE = 3, UPDATE = 4, ERROR = 5;

    private int messageType, destinationId, senderId;
    private Object message;

    public Message() {
    }

    public Message(int messageType, int senderId, int destinationId, Object message) {
        this.messageType = messageType;
        this.senderId = senderId;
        this.destinationId = destinationId;
        this.message = message;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getMessage() {
        return message;
    }

    public int getMessageType() {
        return messageType;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public int getSenderId() {
        return senderId;
    }


    @Override
    public String toString() {
        return "Message{" +
                "messageType=" + messageType +
                ", destinationId=" + destinationId +
                ", senderId=" + senderId +
                ", message=" + message +
                '}';
    }
}
