/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chattcp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Piergiorgio Favaretto
 * @brief La classe si occupa di gestire il DataBase della chat e svolge le
 * operazioni di inserimento delle credenziali degli utenti, di ricerca
 * dell'account in fase di accesso e di verificare se un utente è gia connesso
 * @date 10/11/2017
 * @version 1.0
 */
public class SQLHelper {

    private static Connection conn = null;
    private static final String nomeDataBase = "jdbc:sqlite:DatiUtenti.db";
    private static final String qryInserisci = "INSERT INTO PERSONA(Nome, Cognome, Username, Password) values(?,?,?,?)";
    private static final String qryCerca = "SELECT *\nFROM PERSONA\n WHERE Username=? AND Password=? ";
    private static final String qryPresente = "SELECT *\n FROM PERSONA\n WHERE Username=? ";
		
    SQLHelper() {
        if (conn == null) {
            try {
                Class.forName("org.sqlite.JDBC");//singelton
                conn = DriverManager.getConnection(nomeDataBase);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @brief la procedura inserisce nel DB i nuovi utenti
     * @param dati array contenente tutti i campi del database
     * @throws SQLException per eventuali errori di inserimento
     */
    static void inserisci(String[] dati) throws SQLException {
        try {
            String qry = qryInserisci;
            PreparedStatement st = conn.prepareStatement(qry);
            st.setString(1, dati[0]);
            st.setString(2, dati[1]);
            st.setString(3, dati[2]);
            st.setString(4, dati[3]);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @brief la funzione cerca con una query il l'username e la password scitti dallo
     * user
     * @param username lo username scritto dallo user
     * @param password la password scritta dallo user
     * @return true se l'utente esiste
     * @throws SQLException
     */
    static boolean esiste(String username, String password) throws SQLException {
        String qry = qryCerca;
        try {
            PreparedStatement s1 = conn.prepareStatement(qry);
            s1.setString(1, username);
            s1.setString(2, password);
            ResultSet rs1 = s1.executeQuery();
            if (rs1.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * @brief indica se l'username è gia stato usato per un altro account
     * @param username username da cercare nel DB
     * @return true = gia usato false = non usato
     */
    public static boolean presente(String username) {
        String qry = qryPresente;
        try {
            PreparedStatement s1 = conn.prepareStatement(qry);
            s1.setString(1, username);
            ResultSet rs1 = s1.executeQuery();
            if (rs1.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
