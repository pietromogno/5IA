package objects;

import java.io.Serializable;
import java.net.Socket;

/**
 * @author Emanuele Pagotto
 */
public class User implements Serializable{
    private int userId;
    private String userName;
    private Socket s;

    public User(int userId, String userName, Socket s) {
        this.userId = userId;
        this.userName = userName;
        this.s = s;
    }
    
    public boolean compareTo(User other){
        return this.userId == other.userId;
    }
    
    public Socket getSocket(){
        return s;
    }
    
    public String getUsrName(){
        return userName;
    }
            
}
