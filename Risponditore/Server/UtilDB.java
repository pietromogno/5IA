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
import server.Ordine;
import server.Prenotazione;

/**
 *
 * @author Enrico
 */
public class UtilDB {

    static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Enrico\\Desktop\\Quinta\\TPSIT\\ProgettoPizzeriaPizzaPazza\\Server\\db\\Pizzeria.db");
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conn;
    }

    public static void inserisciUtente(String username, String password) {
        Connection conn = dbutil.UtilDB.getConnection();
        String query = "INSERT INTO UTENTE (Username,Password)"
                + "     VALUES(?,?)";
        PreparedStatement st;
        try {
            st = conn.prepareStatement(query);
            st.setString(1, username);
            st.setString(2, password);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean esisteUsername(String username) {
        boolean ris = false;
        try {
            Connection con = getConnection();
            String q = "SELECT * UTENTE WHERE Username = ?";
            PreparedStatement st = con.prepareStatement(q);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            ris = rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trovatoUsername && password.equals(p);
    }

    public static int idUtente(String username) {
        int ris=-1;
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM UTENTE WHERE Username=?";
            PreparedStatement st = con.prepareStatement(q);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            ris=rs.getInt("IdUtente");
        } catch (SQLException ex) {
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static ArrayList<Ordine> listaOrdini(int id){
        ArrayList<Ordine>ris=new ArrayList<>();
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM ORDINE WHERE IdUtente=?";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Ordine o=new Ordine();
                o.setIndirizzo(rs.getString("Indirizzo"));
                o.setOrario(rs.getString("Orario"));
                o.setProdotti(listaProdotti(rs.getInt("IdOrdine")));
                ris.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }
    
    private static ArrayList<String> listaProdotti(int idOrdine){
        ArrayList<String>ris=new ArrayList<>();
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM PRODOTTO WHERE IdOrdine=?";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idOrdine);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                ris.add(rs.getString("NomeProdotto"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }
    
    public static ArrayList<Prenotazione> listaPrenotazioni(int id){
        ArrayList<Prenotazione>ris=new ArrayList<>();
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM PRENOTAZIONE WHERE IdUtente=?";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Prenotazione p=new Prenotazione();
                p.setNominativo(rs.getString("Nominativo"));
                p.setOrario(rs.getString("Orario"));
                p.setnPersone(rs.getInt("NumeroPersone"));
                ris.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }
    
    public static boolean esisteNominativo(String nominativo){
        boolean ris = false;
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM PRENOTAZIONE WHERE Nominativo = ?";
            PreparedStatement st = con.prepareStatement(q);
            st.setString(1, nominativo);
            ResultSet rs = st.executeQuery();
            ris = rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }
    
    public static boolean esistonoNominativoIdUtente(String nominativo,int idUtente){
        int id=-1;
        boolean trovatoNominativo = true;
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM PRENOTAZIONE WHERE Nominativo=?";
            PreparedStatement st = con.prepareStatement(q);
            st.setString(1, nominativo);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                id = rs.getInt("IdUtente");
            } else {
                trovatoNominativo = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trovatoNominativo && id==idUtente;
    }
    
    public static void inserisciOrdine(Ordine o,int idUtente){
        Connection conn = dbutil.UtilDB.getConnection();
        String query = "INSERT INTO ORDINE (Indirizzo,Orario,IdUtente)"
                + "     VALUES(?,?,?)";
        PreparedStatement st;
        try {
            st = conn.prepareStatement(query);
            st.setString(1, o.getIndirizzo());
            st.setString(2, o.getOrario());
            st.setInt(3, idUtente);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int idOrdine=-1;
        try {
            String q = "SELECT * FROM ORDINE WHERE IdUtente=?";
            PreparedStatement st1 = conn.prepareStatement(q);
            st1.setInt(1, idUtente);
            ResultSet rs = st1.executeQuery();
            while(rs.next()){
                if(rs.getInt("IdOrdine")>idOrdine)idOrdine=rs.getInt("IdOrdine");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ArrayList<String>prodotti=o.getProdotti();
        for(String prod:prodotti){
            inserisciProdotto(prod,idOrdine);
        }
    }
    
    private static void inserisciProdotto(String prodotto,int idOrdine){
        Connection conn = dbutil.UtilDB.getConnection();
        String query = "INSERT INTO PRODOTTO (NomeProdotto,IdOrdine)"
                + "     VALUES(?,?)";
        PreparedStatement st;
        try {
            st = conn.prepareStatement(query);
            st.setString(1, prodotto);
            st.setInt(2, idOrdine);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void inserisciPrenotazione(Prenotazione p,int idUtente){
        Connection conn = dbutil.UtilDB.getConnection();
        String query = "INSERT INTO PRENOTAZIONE (Nominativo,Orario,NumeroPersone,IdUtente)"
                + "     VALUES(?,?,?,?)";
        PreparedStatement st;
        try {
            st = conn.prepareStatement(query);
            st.setString(1, p.getNominativo());
            st.setString(2, p.getOrario());
            st.setInt(3, p.getnPersone());
            st.setInt(4, idUtente);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void cancellaPrenotazione(String nominativo){
        Connection conn = dbutil.UtilDB.getConnection();
        String query = "DELETE FROM PRENOTAZIONE WHERE Nominativo=?";
        PreparedStatement st;
        try {
            st = conn.prepareStatement(query);
            st.setString(1,nominativo);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
