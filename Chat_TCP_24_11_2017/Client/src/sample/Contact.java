package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contact {

    private final StringProperty username=new SimpleStringProperty();
    private int clientId;

    public Contact(int clientId, String username) {
        this.clientId=clientId;
        this.setUsername(username);
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    @Override
    public String toString() {
        return username.get();
    }
}

