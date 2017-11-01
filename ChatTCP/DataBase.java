/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chattcp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Piergiorgio Favaretto
 * @brief La classe si occupa di gestire il DataBase della chat
 * e svolge le operazioni di inserimento delle credenziali degli utenti e 
 * di ricerca dell'account in fase di accesso
 * @date 28/10/2017
 * @version 1.0
 */

public class DataBase {

    static Connection conn;
    static final String nomeDataBase = "jdbc:sqlite:DatiUtenti.db";
    final String fileComand = "fileComandiCreazione.sql";
    boolean first = true;
    static String inizioQueryIns = "INSERT INTO PERSONA (Nome,Cognome,Telefono,DataDiNascita,UserName,Password)\nVALUES ('";
    static String inizioQueryCerca = "SELECT UserName, Password \nFROM PERSONA \nWHERE UserName LIKE '";
    static String s = "','";

    DataBase() throws ClassNotFoundException, IOException, SQLException {
        Class.forName("org.sqlite.JDBC");
        if(first){        
        creaBD(fileComand);
        first = !first;
        }
    }
    
    /**
     * @brief procedura che crea il database se è la prima volta che viene 
     * eseguito il programma
     * @param nomeFileDB contiene il nome del file che ha le istruzioni di creazione del DB
     * @throws IOException
     * @throws SQLException 
     */
    static void creaBD(String nomeFileDB) throws IOException, SQLException {
        conn = DriverManager.getConnection(nomeDataBase);
        String testoSQL = "";
        BufferedReader b = new BufferedReader(new FileReader(nomeFileDB));
        String st;
        while ((st = b.readLine()) != null) {
            testoSQL += st;
        }
        b.close();
        String[] comandiSQL = testoSQL.split(";");
        for (int i = 0; i < comandiSQL.length; i++) {
            try {
                Statement stmt = conn.createStatement();
                System.out.println("Esecuzione di " + comandiSQL[i]);
                stmt.executeUpdate(comandiSQL[i]);
            } catch (SQLException ex) {
                System.out.println("Eccezione SQL: " + ex.toString());
            }
        }
        conn.close();
    }
    
    /**
     * @brief la procedura inserisce nel DB i nuovi utenti
     * @param dati array contenente tutti i campi del database
     * @throws SQLException 
     */
    static void inserisci(String [] dati) throws SQLException{
       String sql = inizioQueryIns;
        for (int i = 0; i < dati.length; i++) {
            if(i != dati.length - 1)sql += dati[i] + s;
            else sql += dati[i] + "');";
        }
       conn = DriverManager.getConnection(nomeDataBase);
       Statement stmt  = conn.createStatement();
       stmt.executeUpdate(sql);
       conn.close();
    }
    
    /**
     * @brief la funzione cerca con una query il nome e la password scitti dallo user 
     * @param username lo username scritto dallo user
     * @param password la password scritta dallo user
     * @return true se l'utente esiste
     * @throws SQLException 
     */
    static boolean esiste(String username, String password) throws SQLException{
        boolean ris = false;
        String sql = inizioQueryCerca;
        sql += username + "' AND Password LIKE '" + password + "';";
        conn = DriverManager.getConnection(nomeDataBase);
        Statement stmt  = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        conn.close();
        // se vi è più di una riga vuol 
        //dire che vi è la corrispondenza
        return rs.next(); 
    }

}
