package oggettiperchat;

import java.io.Serializable;

/**
 * @author Davide Porcu
 */
public class Contatto implements Serializable {

    public static final long serialVersionUID = 20171106L;

    private int idAccount;
    private String username;//nome dal database 
    private String cellulare;

    private String nomeContatto;

    public Contatto() {
        this.idAccount = -1;
        this.username = "";
        this.cellulare = "";
        this.nomeContatto = "";
    }

    public Contatto(int idAccount, String username, String cellulare, String nomeContatto) {
        this.idAccount = idAccount;
        this.username = "~" + username;
        this.cellulare = cellulare;
        this.nomeContatto = nomeContatto;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public String getUsername() {
        return username;
    }

    public String getCellulare() {
        return cellulare;
    }

    public String getNomeContatto() {
        return nomeContatto;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public void setUsername(String username) {
        this.username = "~" + username;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public void setNomeContatto(String nomeContatto) {
        this.nomeContatto = nomeContatto;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Integer) {
            return this.idAccount == (Integer) obj;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Contatto other = (Contatto) obj;
        if (this.idAccount != other.idAccount) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nomeContatto;
    }

}
