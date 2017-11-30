package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Matteo Favaro
 */
public class SQLHelper {

    final static String NOME_DB = "chat.db";

    final static String DRIVE = "database\\";

    static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:" + DRIVE + NOME_DB);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conn;
    }

    public static void closeConnection() throws SQLException {
        try {
            conn.close();
            conn = null;
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean esisteUsername(String username) {
        boolean ris = false;
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM ACCOUNT WHERE username = ?";
            PreparedStatement st = con.prepareStatement(q);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            ris = rs.next();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static int getIdAccount(String username) {
        int idPersona = -1;
        try {
            Connection con = getConnection();
            PreparedStatement s = con.prepareStatement("SELECT IdAccount FROM ACCOUNT WHERE username=?");
            s.setString(1, username);
            ResultSet rs = s.executeQuery();
            idPersona = rs.getInt("IdAccount");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idPersona;
    }

    public static boolean isLoginCorretto(String username,String password ) {
        boolean ris = false;
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM ACCOUNT WHERE password = ? AND username=?";
            PreparedStatement st = con.prepareStatement(q);
            st.setString(1, password);
            st.setString(2, username);
            ResultSet rs = st.executeQuery();
            ris = rs.next();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static boolean esisteIdAccount(int idAccount) {
        boolean ris = false;
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM ACCOUNT WHERE idAccount=?";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idAccount);
            ResultSet rs = st.executeQuery();
            ris = rs.next();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static void inserisciAccount(String username, String password) {
        try {
            Connection con = getConnection();
            String query = "INSERT INTO ACCOUNT (username, password) VALUES(?,?)";
            PreparedStatement insert = con.prepareStatement(query);
            insert.setString(1, username);
            insert.setString(2, password);
            insert.executeUpdate();
            insert.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
