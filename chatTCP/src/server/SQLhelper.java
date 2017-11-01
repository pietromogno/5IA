package server;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Emanuele Pagotto
 */
public class SQLhelper {

    private static Connection dbConn = null;

    private static Connection connect() {
        if (dbConn == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                dbConn = DriverManager.getConnection("jdbc:sqlite:db\\Users.db");
            } catch (ClassNotFoundException | SQLException e) { //altri errori, altri ty catch
                System.err.println("Client Error: " + e.getMessage());
                System.err.println("Localized: " + e.getLocalizedMessage());
                System.err.print("Stack Trace: ");
                e.printStackTrace();
            }
        }
        return dbConn;
    }

    public static void register(String name, String password) { //to do, add more data to the account
        Connection c = connect();
        if (!usrNameExist(name)) {
            String qry = "INSERT INTO UTENTI(nomeUtente,password)"
                    + "VALUES(?,?)";
            try {
                PreparedStatement update = c.prepareStatement(qry);
                update.setString(1, name);
                update.setString(2, password);
                update.executeUpdate();
            } catch (SQLException e) { //altri errori, altri ty catch
                System.err.println("Client Error: " + e.getMessage());
                System.err.println("Localized: " + e.getLocalizedMessage());
                System.err.print("Stack Trace: ");
                e.printStackTrace();
            }
        }else{
            System.err.println("l'utente esiste già");
        }
    }

    private static boolean usrNameExist(String name) {
        Connection c = connect();
        boolean ris = false;
        String qry = "SELECT * FROM UTENTI WHERE nomeUtente LIKE ?";
        try {
            PreparedStatement control = c.prepareStatement(qry);
            control.setString(1, name);
            ris = control.executeQuery().next();
        } catch (SQLException e) { //altri errori, altri ty catch
            System.err.println("Client Error: " + e.getMessage());
            System.err.println("Localized: " + e.getLocalizedMessage());
            System.err.print("Stack Trace: ");
            e.printStackTrace();
        }
        return ris;
    }

    public static boolean login(String name, String password) {
        Connection c = connect();
        boolean usrNameFound = false;
        String p = "";
        String qry = "SELECT * FROM UTENTI WHERE nomeUtente LIKE ?";
        try {
            PreparedStatement control = c.prepareStatement(qry);
            control.setString(1, name);
            ResultSet rs = control.executeQuery();
            if (rs.next()) {
                p = rs.getString("password");
                usrNameFound = true;
            }
        } catch (SQLException e) { //altri errori, altri ty catch
            System.err.println("Client Error: " + e.getMessage());
            System.err.println("Localized: " + e.getLocalizedMessage());
            System.err.print("Stack Trace: ");
            e.printStackTrace();
        }
        return usrNameFound && p.equals(password);
    }

}
