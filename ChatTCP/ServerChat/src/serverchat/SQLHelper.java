/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author MATTI
 */
public class SQLHelper {

    Connection conn;

    /**
     * Avviene la connessione col database con design pattern Singleton
     */
    SQLHelper() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\\\Users\\\\MATTI\\\\Documents\\\\Server.db");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Funzione che si occupa della registrazione di ogni utente nel DB e alla
     * creazione dell tabella chat
     *
     * @param nome nome dell'utente
     * @param cognome cognome dell'utente
     * @param nomeUtente nome univoco dell'utente
     * @param password password dell'utente
     * @return una array di stringhe contenente gli errori o il risutato
     */
    String[] resisterToDB(String nome, String cognome, String nomeUtente, String password) throws SQLException {
        try {
            PreparedStatement tabella = conn.prepareStatement("CREATE TABLE IF NOT EXISTS UTENTI "//creo la tabella se non esiste(primo utente registrato)
                    + "(idUtente INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "cognomeNome VARCHAR(50),"
                    + "nomeUtente VARCHAR(50),"
                    + "password VARCHAR(50),"
                    + "immagineProfilo BLOB,"
                    + "UNIQUE(nomeUtente));");
            tabella.execute();
            PreparedStatement nuovoUtente = conn.prepareStatement(//inserisco l'utente nella tabella
                    "INSERT INTO UTENTI "
                    + "(cognomeNome,nomeUtente,password) "
                    + "VALUES (?,?,?)");
            nuovoUtente.setString(1, nome + " " + cognome);
            nuovoUtente.setString(2, nomeUtente);
            nuovoUtente.setString(3, password);
            nuovoUtente.executeUpdate();
            PreparedStatement tabellaUtente = conn.prepareStatement("CREATE TABLE IF NOT EXISTS CHAT "//creo una tabella contenente lo storico di tutte le chat
                    + "(utenteDestinatario INTEGER, "
                    + "utenteSorgente INTEGER, "
                    + "chat VARCHAR(500), "
                    + "dataOra VARCHAR(20), "
                    + "FOREIGN KEY (utenteDestinatario) REFERENCES UTENTI(nomeUtente), "
                    + "FOREIGN KEY (utenteSorgente) REFERENCES UTENTI(nomeUtente));");
            tabellaUtente.execute();
        } catch (SQLException ex) {
            //ex.printStackTrace();
            String[] temp = {"1","Nome utente già registrato"};
            return temp;
        }
        String[] temp = {"", ""};
        return temp;
    }

    /**
     * Funzione che esegue l'accesso di un utente
     *
     * @param nomeUtente
     * @param password
     * @return nomeUtente dell'utente che ha effettuato l'accesso e il suo nomeUtente
     */
    Object[] accedi(String nomeUtente, String password) throws SQLException {
        ResultSet rs;
        try {
            PreparedStatement login = conn.prepareStatement("SELECT UTENTI.nomeUtente, UTENTI.password, UTENTI.immagineProfilo "
                    + "FROM UTENTI "
                    + "WHERE UTENTI.nomeUtente LIKE ? "
                    + "AND UTENTI.password LIKE ? ");
            login.setString(1, nomeUtente.toUpperCase());
            login.setString(2, password);
            rs = login.executeQuery();
            if (rs.next()) {
                Object[] ris = {rs.getString("nomeUtente"),rs.getBytes("immagineProfilo")};
                return ris;
            }
            String[] ris = {"1","Il nome utente o la password non sono corretti"};//se il risultato non contiene tabelle, significa che l'utente o la password non sono uguali a quelle nel DB
            return ris;
        } catch (SQLException e) {
            String[] ris = {"1","Non sei registrato"};//eccezzione perchè le credenziali immesse non esistono
            return ris;
        }
    }

    /**
     * Funzione che ha il compito di suddividere le chat per ogni utente
     *
     * @param utenteDest utente a cui ho scritto
     * @param nomeUtente utente che ha scritto
     * @return Arraylist di array di stringhe dove ogni array di stringhe è un
     * messsaggio
     */
    ArrayList<String[]> showChat(String utenteDest, String nomeUtente) throws SQLException {
        ArrayList<String[]> ris = new ArrayList<>();
        try {
            System.out.println(nomeUtente+"  show   "+utenteDest);
            PreparedStatement chat = conn.prepareStatement(
                    "SELECT * "
                    + "FROM CHAT "
                    + "WHERE CHAT.utenteSorgente = ? AND CHAT.utenteDestinatario = ? "
                    + "UNION ALL "
                    + "SELECT * "
                    + "FROM CHAT "
                    + "WHERE CHAT.utenteSorgente = ? AND CHAT.utenteDestinatario = ? "
                    + "ORDER BY dataOra;");
            chat.setString(1, nomeUtente);
            chat.setString(2, utenteDest);
            chat.setString(3, utenteDest);
            chat.setString(4, nomeUtente);
            ResultSet rs = chat.executeQuery();
            while (rs.next()) {
                String utenteTemp = rs.getString("utenteDestinatario");
                if (utenteTemp.equals(utenteDest)) {
                    utenteTemp = "Tu: ";
                } else {
                    utenteTemp = utenteDest + ": ";
                }
                String chatStr[] = {utenteTemp, rs.getString("dataOra").substring(0, 16), rs.getString("chat")};
                ris.add(chatStr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            String error[] = {"1","Errore, contatta l'amministratore"};
            ris.add(error);
            return ris;
        }
        return ris;
    }

    /**
     * Funzione che fornisce tutti gli utenti registrati
     *
     * @param utente serve per evitare di mettere mè stesso tra i risultati
     * @return Arraylist di utenti
     */
    ArrayList<String> getUtenti(String utente) throws SQLException {
        ArrayList<String> ris = new ArrayList<>();
        try {
            PreparedStatement utenti = conn.prepareStatement(
                    "SELECT UTENTI.nomeUtente "
                    + "FROM UTENTI "
                    + "WHERE UTENTI.nomeUtente NOT LIKE '" + utente + "';");
            ResultSet rs = utenti.executeQuery();
            while (rs.next()) {
                ris.add(rs.getString("nomeUtente"));
            }
        } catch (SQLException e) {
            e.printStackTrace();         
            ris.add("Errore, contatta l'amministratore");
        }
        return ris;
    }

    /**
     * Funzione che salva le chat sul DB
     *
     * @param chat chat inviata
     * @param utenteSorg chi l'ha inviata
     * @param utenteDest chi la riceve
     * @param idUtente identificativo dell'utente mittente
     * @return true se è avvenuta l'operazione con successo, false c'è stato un
     * errore
     */
    boolean saveChat(String chat, String utenteSorg, String utenteDest) throws SQLException {
        System.out.println(chat + "   " + utenteSorg + "   " + utenteDest);
        String time = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss").format(Calendar.getInstance().getTime());
        try {
            PreparedStatement insertChat = conn.prepareStatement(
                    "INSERT INTO CHAT "
                    + "(utenteDestinatario,utenteSorgente,chat,dataOra) "
                    + "VALUES (?,?,?,?)");
            insertChat.setString(1, utenteDest);
            insertChat.setString(2, utenteSorg);
            insertChat.setString(3, chat);
            insertChat.setString(4, time);
            insertChat.executeUpdate();
        } catch (SQLException e) {
            //  e.printStackTrace();         
            return false;
        }
        return true;
    }

    /**
     * Funzione che cancella l'utente dal DB e le sue chat
     *
     * @param nomeUtente
     * @param password
     * @return true se è avvenuta l'operazione con successo, false c'è stato un
     * errore
     */
    boolean deleteAccount(String nomeUtente, String password) throws SQLException {
        Object ris[] = accedi(nomeUtente, password);
        if (ris[0].equals(nomeUtente)) {
            try {
                PreparedStatement delete = conn.prepareStatement(
                        "DELETE "
                        + "FROM CHAT "
                        + "WHERE CHAT.utenteDestinatario LIKE '" + nomeUtente + "' "
                        + "OR CHAT.utenteSorgente LIKE '" + nomeUtente + "' ;");
                delete.execute();
                PreparedStatement deleteTable = conn.prepareStatement("DELETE "
                        + "FROM UTENTI "
                        + "WHERE UTENTI.nomeUtente LIKE '" + nomeUtente + "';");
                deleteTable.execute();
                return true;
            } catch (SQLException e) {
                //e.printStackTrace();             
                return false;
            }
        }
        return false;
    }

    boolean insertImage(String nomeUtente, byte[] image) throws SQLException {
        try {
            PreparedStatement inImage = conn.prepareStatement(
                    "UPDATE UTENTI "
                    + "SET immagineProfilo = ? "
                    + "WHERE UTENTI.nomeUtente = ? ;");
            inImage.setBytes(1, image);
            inImage.setString(2, nomeUtente);
            inImage.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();             
            return false;
        }
    }
}
