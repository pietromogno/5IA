package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Davide Porcu
 */
public class UtilSQLiteDB {

    public final static String NOME_DB = "listino.db";
    public final static String DRIVE = "database\\";

    static Connection conn = null;

    public static Connection getConnection() {//singleton
        if (conn == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:" + DRIVE + NOME_DB);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UtilSQLiteDB.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(UtilSQLiteDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            conn.close();
            conn = null;
        } catch (SQLException ex) {
            Logger.getLogger(UtilSQLiteDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ArrayList<Categoria> getListaCategorie() {
        ArrayList<Categoria> ris = new ArrayList<>();
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM CATEGORIA";
            PreparedStatement st = con.prepareStatement(q);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ris.add(new Categoria(rs.getString("nome")));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UtilSQLiteDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }
    
     public static ArrayList<Prodotto> getListaProdottiDellaCategoria(Categoria categoria) {
        ArrayList<Prodotto> ris = new ArrayList<>();
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM PRODOTTO WHERE (SELECT IdCategoria FROM CATEGORIA WHERE nome=?)=PRODOTTO.IdCategoria";
            PreparedStatement st = con.prepareStatement(q);
            st.setString(1, categoria.getNomeCategoria());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ris.add(new Prodotto(rs.getString("nome"),categoria,rs.getDouble("prezzo")));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UtilSQLiteDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }
}
