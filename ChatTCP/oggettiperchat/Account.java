package oggettiperchat;

import java.io.Serializable;

/**
 * @author Davide Porcu
 */
public class Account implements Serializable{

    public static final long serialVersionUID = 20171116L;
    
    private int idAccount;
    private String username;
    private String password;
    private String cellulare;

    public Account(){
        this.idAccount = -1;
        this.username = "";
        this.password = "";
        this.cellulare = "";
    }
    
    public Account(int idAccount, String username, String password, String cellulare) {
        this.idAccount = idAccount;
        this.username = username;
        this.password = password;
        this.cellulare = cellulare;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    @Override
    public String toString() {
        return "Account{" + "idAccount=" + idAccount + ", username=" + username + ", password=" + password + ", cellulare=" + cellulare + '}';
    }
    
    
    
    
}
