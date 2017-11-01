package servermessaggi;

import Messaggio.Messaggio;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author Francesco Forcellato
 */
public class SQLHelper {

    static Connection conn = null;
    private static final String PERCORSO = "Account.db";

    public static synchronized Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:" + PERCORSO);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conn;
    }

    public static synchronized ArrayList<Account> getAccount() {
        ArrayList<Account> ris = new ArrayList<>();
        String query = "SELECT * FROM ACCOUNT";
        try {
            PreparedStatement c = getConnection().prepareStatement(query);
            ResultSet rs = c.executeQuery();
            while (rs.next()) {
                ris.add(new Account(rs.getString("Username"), rs.getString("Password")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static synchronized void addAccount(String username, String password) {
        String query = "INSERT INTO ACCOUNT (Username, Password) "
                + "VALUES (?,?) ";
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static synchronized void removeAccount(String username) {
        String query = "DELETE "
                + "FROM ACCOUNT "
                + "WHERE Username = \"" + username + "\" ;";
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static synchronized void aggiungiMessaggio(Messaggio m) {
        String query = "INSERT INTO CONVERSAZIONI (Mittente, Destinatario, Messaggio, Data) "
                + "VALUES (\"" + m.getMittente() + "\", \"" + m.getDestinatario() + "\", \"" + m.getMessaggio() + "\", \"" + m.getOra() + "\"); ";
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static synchronized HashMap<String, DefaultListModel> getConversazioni(String mittente) {
        HashMap<String, DefaultListModel> ris = new HashMap<>();
        try {
            ArrayList<Account> account = getAccount();
            for (int i = 0; i < account.size(); i++) {
                DefaultListModel<String> h = new DefaultListModel();
                String destinatario = account.get(i).getUsername();
                if (destinatario.compareTo(mittente) != 0) {
                    String query = "SELECT Mittente, Destinatario, Messaggio "
                            + "FROM CONVERSAZIONI "
                            + "WHERE Mittente=\"" + mittente + "\" AND Destinatario=\"" + destinatario + "\" OR Destinatario=\"" + mittente + "\"  AND Mittente=\"" + destinatario + "\" "
                            + "ORDER BY Data ASC "
                            + ";";
                    PreparedStatement ps = getConnection().prepareStatement(query);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        h.addElement(rs.getString("Mittente").compareTo(mittente) == 0 ? "Io: " + rs.getString("Messaggio") : rs.getString("Mittente") + ": " + rs.getString("Messaggio"));
                    }
                    ris.put(destinatario, h);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }
}
