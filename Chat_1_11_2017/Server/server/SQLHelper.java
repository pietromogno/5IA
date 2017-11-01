package server;

import server.Objects.Account;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLHelper {

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    private static ArrayList<Account> accountList = new ArrayList<>();
    private static final String DBname = "Utenti.db";
    private static final String TABname = "ACCOUNTS";

    /**
     * Method to connect the Sqlite database and select the table
     */
    public static void getConnection() {

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + DBname);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + TABname + ";");
            loadAccounts();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Method to load all the accounts in an arraylist from the Sqlite database
     */
    private static void loadAccounts() {
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + TABname + ";");
            while (rs.next()) {
                Account a = new Account(rs.getString("NAME"), rs.getString("PASSWORD"));
                accountList.add(a);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Function to see if the client can log to server
     *
     * @param username
     * @param password
     * @return boolean
     */
    static boolean loginAccount(String username, String password) {
        for (Account a : accountList) {
            if (a.getUsername().equalsIgnoreCase(username)
                    && a.getPassword().equalsIgnoreCase(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function to insert a new account to the database, if there is not already
     * one with the same username
     *
     * @param ID
     * @param username
     * @param password
     * @return
     */
    static boolean insertAccount(int ID, String username, String password) {
        boolean ris = true;
        for (Account a : accountList) {
            if (a.getUsername().equalsIgnoreCase(username)) {
                ris = false;
            }
        }
        if (ris) {
            Account a = new Account(username, password);
            try {

                String sql = "INSERT INTO " + TABname + "(ID , NAME, PASSWORD)"
                        + "VALUES (" + ID + " ,'" + a.getUsername() + "', '" + a.getPassword() + "');";
                stmt.executeUpdate(sql);
                conn.commit();
                accountList.add(a);
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
        }
        return ris;
    }

    /**
     * Method used to close all the connections with the database
     *
     * @throws SQLException
     */
    static void closeAllConections() throws SQLException {
        try {
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
