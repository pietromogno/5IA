package server;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Emanuele Pagotto
 */
public class UtilDb {

    private static Connection dbConn = null;

    private static Connection connect() throws ClassNotFoundException, SQLException {
        if (dbConn == null) {
            Class.forName("org.sqlite.JDBC");
            dbConn = DriverManager.getConnection("jdbc:sqlite:db\\chatUDP.db");
        }
        return dbConn;
    }

    public static boolean register(String name, String password) throws SQLException, ClassNotFoundException { //to do, add more data to the account
        Connection c = connect();
        if (!usrNameExist(name)) {
            String qry = "INSERT INTO UTENTI(userName,password)"
                    + "VALUES(?,?)";
            PreparedStatement update = c.prepareStatement(qry);
            update.setString(1, name);
            update.setString(2, password);
            update.executeUpdate();
            return true;
        } else {
            System.err.println("l'utente esiste già");
            return false;
        }
    }

    private static boolean usrNameExist(String name) throws SQLException, ClassNotFoundException {
        Connection c = connect();
        boolean ris = false;
        String qry = "SELECT * FROM UTENTI WHERE userName LIKE ?";
        PreparedStatement control = c.prepareStatement(qry);
        control.setString(1, name);
        ris = control.executeQuery().next();
        return ris;
    }

    public static boolean login(String name, String password) throws SQLException, ClassNotFoundException {
        Connection c = connect();
        boolean usrNameFound = false;
        String p = "";
        String qry = "SELECT * FROM UTENTI WHERE userName LIKE ?";
        PreparedStatement control = c.prepareStatement(qry);
        control.setString(1, name);
        ResultSet rs = control.executeQuery();
        if (rs.next()) {
            p = rs.getString("password");
            System.out.println(p.length());
            System.out.println(password.length());
            usrNameFound = true;
        }
        boolean isPwRight = p.equals(password);
        return usrNameFound && isPwRight;
    }

    public static void inserisciMessaggio(int idUtente, String messaggio) throws SQLException, ClassNotFoundException {
        Connection c = connect();
        String qry = "INSERT INTO CHAT (idUtente,messaggio) VALUES (?,?)";
        PreparedStatement control = c.prepareStatement(qry);
        control.setInt(1, idUtente);
        control.setString(2, messaggio);
        control.executeUpdate();
    }

    public static ArrayList<String> getMessages() throws SQLException, ClassNotFoundException {
        ArrayList<String> chat = new ArrayList<>();
        Connection c = connect();
        String qry = "SELECT * FROM CHAT";
        PreparedStatement control = c.prepareStatement(qry);
        ResultSet rs = control.executeQuery();
        while (rs.next()) {
			int id = rs.getInt("idUtente");
			String m = rs.getString("messaggio");
            chat.add(getNameById(id)+": "+m);
        }
        return chat;
    }

    public static int getIdByName(String name) throws SQLException, ClassNotFoundException {
        Connection c = connect();
        String qry = "SELECT * FROM UTENTI WHERE userName=?";
        PreparedStatement control = c.prepareStatement(qry);
        control.setString(1, name);
        return control.executeQuery().getInt("idUtente");
    }

    public static String getNameById(int id) throws SQLException, ClassNotFoundException {
        Connection c = connect();
        String qry = "SELECT * FROM UTENTI WHERE idUtente=?";
        PreparedStatement control = c.prepareStatement(qry);
        control.setInt(1, id);
        return control.executeQuery().getString("userName");
    }

}
