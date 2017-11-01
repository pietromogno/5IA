/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Chat;

/**
 *
 * @author enrico.daronche
 */
public class SQLHelper {

    static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Enrico\\Desktop\\Quinta\\TPSIT\\ProgettoChatTCP\\Server\\db\\ChatTCP.db");
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
                c.setIsPrivata(true);
                ris.add(c);
            }

            q = "SELECT * FROM CHAT,APPARTENENZA WHERE CHAT.IdChat=APPARTENENZA.IdChat AND APPARTENENZA.IdUtente=?";
            st = con.prepareStatement(q);
            st.setInt(1, idUtente);
            rs = st.executeQuery();
            while (rs.next()) {
                Chat c = new Chat();
                c.setIdChat(rs.getInt("IdChat"));
                c.setNomeChat(rs.getString("NomeGruppo"));
                c.setIsPrivata(false);
                ris.add(c);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static boolean esisteContatto(int idUtente, int idContatto) {
        boolean ris = false;
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM CHAT WHERE (IdUtente1 = ? AND IdUtente2=?) OR (IdUtente1 = ? AND IdUtente2=?) ";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idUtente);
            st.setInt(2, idContatto);
            st.setInt(3, idContatto);
            st.setInt(4, idUtente);
            ResultSet rs = st.executeQuery();
            ris = rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static int aggiungiContatto(int idUtente, int idContatto) {
        Connection conn = getConnection();
        String query = "INSERT INTO CHAT (IdUtente1,IdUtente2)"
                + "     VALUES(?,?)";
        PreparedStatement st;
        try {
            st = conn.prepareStatement(query);
            st.setInt(1, idUtente);
            st.setInt(2, idContatto);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        int ris = -1;

        query = "SELECT * FROM CHAT WHERE IdUtente1=? AND IdUtente2=?";
        try {
            st = conn.prepareStatement(query);
            st.setInt(1, idUtente);
            st.setInt(2, idContatto);
            ResultSet rs = st.executeQuery();
            ris = rs.getInt("IdChat");
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ris;
    }

    public static ArrayList<String> getMessaggi(int idChat, int idUtente) {
        ArrayList<String> ris = new ArrayList<>();
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM MESSAGGIO,UTENTE WHERE MESSAGGIO.IdUtente=UTENTE.IdUtente AND IdChat=? ORDER BY IdMessaggio";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idChat);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String messaggio = (idUtente == rs.getInt("IdUtente") ? "Tu:" : (rs.getString("Username") + ":")) + rs.getString("Testo");
                ris.add(messaggio);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static void addMessaggio(String testo, int idChat, int idUtente) {
        Connection conn = getConnection();
        String query = "INSERT INTO MESSAGGIO (Testo,IdChat,IdUtente)"
                + "     VALUES(?,?,?)";
        PreparedStatement st;
        try {
            st = conn.prepareStatement(query);
            st.setString(1, testo);
            st.setInt(2, idChat);
            st.setInt(3, idUtente);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<String> getContatti(int idUtente) {
        ArrayList<String> ris = new ArrayList<>();
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM CHAT WHERE IdUtente1=? OR IdUtente2=?";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idUtente);
            st.setInt(2, idUtente);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int u1 = rs.getInt("IdUtente1"), u2 = rs.getInt("IdUtente2");

                q = "SELECT * FROM UTENTE WHERE IdUtente=?";
                st = con.prepareStatement(q);
                st.setInt(1, u1 == idUtente ? u2 : u1);
                ResultSet rs1 = st.executeQuery();

                ris.add(rs1.getString("Username"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static int creaGruppo(String nomeGruppo) {
        Connection conn = getConnection();
        String query = "INSERT INTO CHAT (NomeGruppo)"
                + "     VALUES(?)";
        PreparedStatement st;
        try {
            st = conn.prepareStatement(query);
            st.setString(1, nomeGruppo);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        int ris = -1;

        query = "SELECT * FROM CHAT WHERE NomeGruppo=?";
        try {
            st = conn.prepareStatement(query);
            st.setString(1, nomeGruppo);
            ResultSet rs = st.executeQuery();
            ris = rs.getInt("IdChat");
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ris;
    }

    public static void aggiungiMembro(int idChat, int idUtente) {
        Connection conn = getConnection();
        String query = "INSERT INTO APPARTENENZA (IdUtente,IdChat)"
                + "     VALUES(?,?)";
        PreparedStatement st;
        try {
            st = conn.prepareStatement(query);
            st.setInt(1, idUtente);
            st.setInt(2, idChat);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<Integer> getDestinatari(int idChat) {
        ArrayList<Integer> ris = new ArrayList<>();
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM CHAT WHERE IdChat=?";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idChat);
            ResultSet rs = st.executeQuery();
            String nomeGruppo = rs.getString("NomeGruppo");
            if (nomeGruppo == null) {
                ris.add(rs.getInt("IdUtente1"));
                ris.add(rs.getInt("IdUtente2"));
            } else {
                q = "SELECT * FROM APPARTENENZA WHERE IdChat=?";
                st = con.prepareStatement(q);
                st.setInt(1, idChat);
                rs = st.executeQuery();
                while (rs.next()) {
                    ris.add(rs.getInt("IdUtente"));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static boolean isGruppo(int idChat) {
        boolean ris = false;
        Connection conn = getConnection();
        String query = "SELECT * FROM CHAT WHERE IdChat=?";
        PreparedStatement st;
        try {
            st = conn.prepareStatement(query);
            st.setInt(1, idChat);
            ResultSet rs = st.executeQuery();
            ris = rs.getString("NomeGruppo") != null;
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static ArrayList<String> getContattiNonInGruppo(int idChat, int idUtente) {
        ArrayList<String> ris = new ArrayList<>();
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM CHAT WHERE IdUtente1=? OR IdUtente2=?";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idUtente);
            st.setInt(2, idUtente);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int u1 = rs.getInt("IdUtente1"), u2 = rs.getInt("IdUtente2");

                q = "SELECT * FROM UTENTE WHERE IdUtente=? AND IdUtente NOT IN (SELECT IdUtente FROM APPARTENENZA WHERE IdChat=?)";
                st = con.prepareStatement(q);
                st.setInt(1, u1 == idUtente ? u2 : u1);
                st.setInt(2, idChat);
                ResultSet rs1 = st.executeQuery();

                if(rs1.next())ris.add(rs1.getString("Username"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }
    
    public static String nomeGruppo(int idChat){
        String ris="";
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM CHAT WHERE IdChat=? ";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idChat);
            ResultSet rs = st.executeQuery();
            ris = rs.getString("NomeGruppo");
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }
}
