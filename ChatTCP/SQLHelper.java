
package servertcp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLHelper {

    static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\BUS\\Desktop\\serverTCP\\chatTCP.db");
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conn;
    }

    public static void inserisciUtente(String username, String password) {
        Connection conn = getConnection();
        String query = "INSERT INTO UTENTE (Username,Password)"
                + "     VALUES(?,?)";
        PreparedStatement st;
        try {
            st = conn.prepareStatement(query);
            st.setString(1, username);
            st.setString(2, password);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean esisteUsername(String username) {
        boolean ris = false;
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM UTENTE WHERE Username = ?";
            PreparedStatement st = con.prepareStatement(q);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            ris = rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static boolean esistePassword(String password) {
        boolean ris = false;
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM UTENTE WHERE Password = ?";
            PreparedStatement st = con.prepareStatement(q);
            st.setString(1, password);
            ResultSet rs = st.executeQuery();
            ris = rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static boolean esistonoCredenziali(String username, String password) {
        String p = "";
        boolean trovatoUsername = true;
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM UTENTE WHERE Username=?";
            PreparedStatement st = con.prepareStatement(q);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                p = rs.getString("Password");
            } else {
                trovatoUsername = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trovatoUsername && password.equals(p);
    }

    public static int idUtente(String username) {
        int ris = -1;
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM UTENTE WHERE Username=?";
            PreparedStatement st = con.prepareStatement(q);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            ris = rs.getInt("IdUtente");
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static ArrayList<Chat> getChat(int idUtente) {
        ArrayList<Chat> ris = new ArrayList<>();
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM CHAT WHERE IdUtente1=? OR IdUtente2=?";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idUtente);
            st.setInt(2, idUtente);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Chat c = new Chat();
                c.setIdChat(rs.getInt("IdChat"));
                int u1 = rs.getInt("IdUtente1"), u2 = rs.getInt("IdUtente2");

                q = "SELECT * FROM UTENTE WHERE IdUtente=?";
                st = con.prepareStatement(q);
                st.setInt(1, u1 == idUtente ? u2 : u1);
                ResultSet rs1 = st.executeQuery();

                c.setNomeChat(rs1.getString("Username"));
                ris.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }
    
    public static void creaChatNuovoUtente(int idUtente){
        ArrayList<Integer> utenti = new ArrayList<>();
        int cont = 0;
        try {
            Connection con = getConnection();
            String q = "SELECT IdUtente FROM UTENTE";
            PreparedStatement st = con.prepareStatement(q);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                utenti.add(rs.getInt("idUtente"));
                cont++;
            }
            q = "INSERT INTO CHAT (IdUtente1,IdUtente2)"
                + "     VALUES(?,?)";
            st = con.prepareStatement(q);
            for (int i = 0; i < cont; i++) {
                st.setInt(1, idUtente);
                st.setInt(2, utenti.get(i));
                st.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static int[] getDestinatari(int idChat) {
        int[] ris = new int[2];
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM CHAT WHERE IdChat=?";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idChat);
            ResultSet rs = st.executeQuery();
            ris[0] = rs.getInt("IdUtente1");
            ris[1] = rs.getInt("IdUtente2");
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

}
