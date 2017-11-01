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
 * Avviene la connessione col database
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
 * Funzione che controlla se un utente è registrato o meno
 * @param nomeUtente nome da controllare se è registrato
 * @return una stringa contenente gli errori o il risutato 
 */
    String[] isRegistered(String nomeUtente) throws SQLException {
        try {
            ResultSet rs;
            PreparedStatement tabella = conn.prepareStatement("SELECT UTENTI.nomeUtente "
                    + "FROM UTENTI "
                    + "WHERE UTENTI.nomeUtente LIKE ?");
            tabella.setString(1, nomeUtente);
            rs = tabella.executeQuery();
            if (rs.next()) {
                //System.out.println("ok");
                String[] ris = {"", ""};//se c'è una tabella nei risultati significa che è stato trovato un utente con tale nome
                return ris;
            }
        } catch (SQLException ex) {
            //ex.printStackTrace();
            //System.out.println("Errore");
            String[] ris = {"Non sei registrato", "1"};//altrimenti non esiste nessun utente con quel nome

            return ris;
        }
        //System.out.println("out");
        String[] ris = {"Non sei registrato", "1"};

        return ris;
    }
/**
 * Funzione che si occupa della registrazione di ogni utente nel DB e alla creazione dell tabella chat
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
                    + "(utenteDestinatario VARCHAR(50), "
                    + "utenteSorgente VARCHAR(50), "
                    + "chat VARCHAR(500), "
                    + "dataOra VARCHAR(20));");
            tabellaUtente.execute();
        } catch (SQLException ex) {
            //Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
            String[] temp = {"Nome utente già registrato", "1"};
            return temp;
        }
        String[] temp = {"", ""};
        return temp;
    }
/**
 * Funzione che esegue l'accesso di un utente
 * @param nomeUtente
 * @param password
 * @return id dell'utente che ha effettuato l'accesso e il suo nomeUtente
 */
    String[] accedi(String nomeUtente, String password) throws SQLException {
        ResultSet rs;
        try {
            PreparedStatement login = conn.prepareStatement("SELECT UTENTI.idUtente,UTENTI.nomeUtente, UTENTI.password "
                    + "FROM UTENTI "
                    + "WHERE UTENTI.nomeUtente LIKE ? "
                    + "AND UTENTI.password LIKE ?");
            login.setString(1, nomeUtente.toUpperCase());
            login.setString(2, password);
            rs = login.executeQuery();
            if (rs.next()) {
                String[] ris = {rs.getString("idUtente"), rs.getString("nomeUtente")};
                return ris;
            }
            //System.out.println("Sbagliato");
            String[] ris = {"Il nome utente o la password non sono corretti", "1"};//se il risultato non contiene tabelle, significa che l'utente o la password non sono uguali a quelle nel DB
            return ris;
        } catch (SQLException e) {
            //e.printStackTrace();
            //System.out.println("errore");
            String[] ris = {"Non sei registrato", "1"};//eccezzione perchè le credenziali immesse non esistono
            return ris;
        }
    }
/**
 * Funzione che ha il compito di suddividere le chat per ogni utente
 * @param utenteDest utente a cui ho scritto
 * @param nomeUtente utente che ha scritto
 * @return Arraylist di array di stringhe dove ogni array di stringhe è un messsaggio
 */
    ArrayList<String[]> showChat(String utenteDest, String nomeUtente) throws SQLException {
        ArrayList<String[]> ris = new ArrayList<>();
        try {
            PreparedStatement chat = conn.prepareStatement(
                    "SELECT CHAT.utenteDestinatario,CHAT.chat,CHAT.dataOra "
                    + "FROM CHAT "
                    + "WHERE CHAT.utenteDestinatario = '" + utenteDest + "' AND CHAT.utenteSorgente = '" + nomeUtente + "' "
                    + "UNION ALL "
                    + "SELECT CHAT.utenteDestinatario,CHAT.chat,CHAT.dataOra "
                    + "FROM CHAT "
                    + "WHERE CHAT.utenteDestinatario = '" + nomeUtente + "' AND CHAT.utenteSorgente = '" + utenteDest + "' "
                    + "ORDER BY dataOra;");
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
            //e.printStackTrace();
            String error[] = {"Errore, contatta l'amministratore", "1"};
            ris.add(error);
            return ris;
        }
        return ris;
    }
/**
 * Funzione che fornisce tutti gli utenti registrati
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
            //e.printStackTrace();         
            ris.add("Errore, contatta l'amministratore");
        }
        return ris;
    }
/**
 * Funzione che salva le chat sul DB
 * @param chat chat inviata
 * @param utenteSorg chi l'ha inviata
 * @param utenteDest chi la riceve
 * @param idUtente identificativo dell'utente mittente
 * @return true se è avvenuta l'operazione con successo, false c'è stato un errore
 */
    boolean saveChat(String chat, String utenteSorg, String utenteDest, String idUtente) throws SQLException {
        System.out.println(chat + "   " + utenteSorg + "   " + utenteDest + "   " + idUtente);
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
 *  Funzione che cancella l'utente dal DB e le sue chat
 * @param nomeUtente
 * @param password
 * @return true se è avvenuta l'operazione con successo, false c'è stato un errore
 */
    boolean deleteAccount(String nomeUtente, String password) throws SQLException {
        String ris[] = accedi(nomeUtente, password);
        if (ris[1].equals(nomeUtente)) {
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
}
