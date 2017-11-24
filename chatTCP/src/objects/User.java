package objects;

import java.io.Serializable;

/**
 * @author Emanuele Pagotto
 */
public class User implements Serializable{
    private int userId;
    private String userName;

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
    
    public boolean compareTo(User other){
        return this.userId == other.userId;
    }
    
    public String getUsrName(){
        return userName;
    }
            
}
