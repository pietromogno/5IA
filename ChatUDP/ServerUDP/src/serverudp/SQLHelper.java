/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverudp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author simone.pasutti
 */
public class SQLHelper {

    Connection conn;

    /**
     * Avviene la connessione col database
     */
    SQLHelper() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("C:\\Users\\Simone\\Documents\\sql\\\\prova.db");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    String table = "CREATE TABLE IF NOT EXISTS UTENTI ("
            + "idUtente INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "cognomeNome VARCHAR(50), "
            + "nomeUtente VARCHAR(50), "
            + "password VARCHAR (50), "
            + "UNIQUE (nomeUtente));";
    
    String messaggi = "CREATE TABLE IF NOT EXISTS MESSAGGI ("
            + "idMessaggio INTEGER PRIMARI KEY AUTOINCREMENT, "
            + "idUtente INTEGER REFERENCES UTENTI (idUtente), "
            + "messaggio VARCHAR (500), "
            + "dataOra DATETIME);";

    String isRegistered = "SELECT UTENTI.nomeUtente "
            + "FROM UTENTI "
            + "WHERE UTENTI.nomeUtente LIKE ?";

    String newUtente = "INSERT INTO UTENTI "
            + "(cognomeNome,nomeUtente,password) "
            + "VALUES (?,?,?)";
}
