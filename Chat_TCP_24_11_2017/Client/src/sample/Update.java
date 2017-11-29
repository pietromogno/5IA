package sample;

import java.io.Serializable;
import java.util.HashMap;

public class Update implements Serializable {

    public static final long serialVersionUID = 39990701L;

    private HashMap<Integer, String> clientList;

    public Update(HashMap<Integer, String> clientList) {
        this.clientList = clientList;
    }

    public HashMap<Integer, String> getClientList() {
        return clientList;
    }

    public void setClientList(HashMap<Integer, String> clientList) {
        this.clientList = clientList;
    }
}