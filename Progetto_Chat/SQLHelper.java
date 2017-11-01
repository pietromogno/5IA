package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @brief Gestisce la connessione, i controlli, la ricerca e l'inserimento delle 
 * persone sul database. Fa uso della libreria SQLite
 * @author Matteo
 */
public class SQLHelper {
    static Connection conn = null;

    public static Connection getConnection() { // Design Pattern SINGLETON 
        if (conn == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:/home/matteo/Documenti/Scuola/Chat.db"); //percorso database
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return conn;

    }
    
    public static void inserisciUser(String username, String pw){
        try {
            String query = "INSERT INTO User(Username, Password)"
                    + " values(?,?)";
            PreparedStatement st = getConnection().prepareStatement(query);
            st.setString(1, username);
            st.setString(2, pw);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean isUserCorrect(String username, String pw){
        String query1="SELECT * FROM User "
                + "    WHERE Username=? AND Password=? ";
        try {
            System.out.println("CIAO DA UTIL");
            PreparedStatement s1=getConnection().prepareStatement(query1);
            s1.setString(1, username);
            s1.setString(2, pw);
            ResultSet rs1=s1.executeQuery();
            if(rs1.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static boolean isUserPresente(String username){
        String query1="SELECT * FROM User "
                + "    WHERE Username=? ";
        try {
            PreparedStatement s1=getConnection().prepareStatement(query1);
            s1.setString(1, username);
            ResultSet rs1=s1.executeQuery();
            if(rs1.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
