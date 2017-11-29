package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLhelper {

    //var to get the connection
    final static String SQLITE_JDBC = "org.sqlite.JDBC";
    final static String DRIVE = "jdbc:sqlite:";
    final static String DB_PATH = "/home/farhan/Desktop/PROVE/ChatTCP/Server/";
    final static String DB = "Agenda.db";

    //connection status
    static Connection conn = null;

    //DB structure
    //table ACCOUNT
    final static String TABLE_ACCOUNT = "ACCOUNT";
    final static String ACCOUNT_USERNAME = "username";
    final static String ACCOUNT_PASSWORD = "password";
    final static String ACCOUNT_NAME = "nome";
    final static String ACCOUNT_SURNAME = "cognome";

    //queries
    final static String LOGIN_USER = "SELECT * FROM " + TABLE_ACCOUNT
            + " WHERE " + TABLE_ACCOUNT + "." + ACCOUNT_USERNAME + "= ? AND " + TABLE_ACCOUNT + "." + ACCOUNT_PASSWORD + "= ?";
    final static String FREE_USERNAME = "SELECT * FROM " + TABLE_ACCOUNT
            + " WHERE " + TABLE_ACCOUNT + "." + ACCOUNT_USERNAME + "= ?";
    final static String REGISTER_USER = "INSERT INTO " + TABLE_ACCOUNT
            + " (" + ACCOUNT_USERNAME + ", " + ACCOUNT_PASSWORD + ", " + ACCOUNT_NAME + ", " + ACCOUNT_SURNAME + ")"
            + " VALUES(?,?,?,?)";

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName(SQLITE_JDBC);
                conn = DriverManager.getConnection(DRIVE + DB_PATH + DB);
            } catch (ClassNotFoundException clsNtFndEx) {
                Logger.getLogger(SQLhelper.class.getName()).log(Level.SEVERE, null, clsNtFndEx);
            } catch (SQLException slqEx) {
                Logger.getLogger(SQLhelper.class.getName()).log(Level.SEVERE, null, slqEx);

            }
        }
        return conn;
    }

    public static void closeConnection() throws SQLException {
        try {
            conn.close();
            conn = null;
        } catch (SQLException slqEx) {
            Logger.getLogger(SQLhelper.class.getName()).log(Level.SEVERE, null, slqEx);
        }
    }

    public static boolean isUserRegistered(String username, String password) {
        boolean ris = false;
        try {
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(LOGIN_USER);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            ris = rs.next();
            rs.close();
        } catch (SQLException sqlEx) {
            Logger.getLogger(SQLhelper.class.getName()).log(Level.SEVERE, null, sqlEx);
        }
        return ris;
    }

    public static boolean isUsernameFree(String username) {
        boolean ris = false;
        try {
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(FREE_USERNAME);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            ris = rs.next();
            rs.close();
        } catch (SQLException sqlEx) {
            Logger.getLogger(SQLhelper.class.getName()).log(Level.SEVERE, null, sqlEx);
        }
        return !ris;
    }

    public static void registerAccount(String username, String password, String nome, String cognome) {
        try {
            Connection con = getConnection();
            PreparedStatement reg = con.prepareStatement(REGISTER_USER);
            reg.setString(1, username);
            reg.setString(2, password);
            reg.setString(3, nome);
            reg.setString(4, cognome);
            reg.executeUpdate();
            reg.close();
        } catch (SQLException sqlEx) {
            Logger.getLogger(SQLhelper.class.getName()).log(Level.SEVERE, null, sqlEx);
        }
    }
}