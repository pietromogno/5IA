package server;

import java.sql.*;
import java.util.ArrayList;
import objects.Message;

/**
 * @author Emanuele Pagotto
 */
public class SQLhelper {

    private static Connection dbConn = null;

    private static Connection connect() throws ClassNotFoundException, SQLException {
        if (dbConn == null) {
            Class.forName("org.sqlite.JDBC");
            dbConn = DriverManager.getConnection("jdbc:sqlite:db\\chatTCP.db");
        }
        return dbConn;
    }

    public static void register(String name, String password) throws SQLException, ClassNotFoundException { //to do, add more data to the account
        Connection c = connect();
        if (!usrNameExist(name)) {
            String qry = "INSERT INTO UTENTI(nomeUtente,password)"
                    + "VALUES(?,?)";
            PreparedStatement update = c.prepareStatement(qry);
            update.setString(1, name);
            update.setString(2, password);
            update.executeUpdate();
        } else {
            System.err.println("l'utente esiste già");
        }
    }

    private static boolean usrNameExist(String name) throws SQLException, ClassNotFoundException {
        Connection c = connect();
        boolean ris = false;
        String qry = "SELECT * FROM UTENTI WHERE nomeUtente LIKE ?";
        PreparedStatement control = c.prepareStatement(qry);
        control.setString(1, name);
        ris = control.executeQuery().next();
        return ris;
    }

    public static boolean login(String name, String password) throws SQLException, ClassNotFoundException {
        Connection c = connect();
        boolean usrNameFound = false;
        String p = "";
        String qry = "SELECT * FROM UTENTI WHERE nomeUtente LIKE ?";
        PreparedStatement control = c.prepareStatement(qry);
        control.setString(1, name);
        ResultSet rs = control.executeQuery();
        if (rs.next()) {
            p = rs.getString("password");
            usrNameFound = true;
        }
        return usrNameFound && p.equals(password);
    }

    public static void inserisciMessaggio(Message m) throws ClassNotFoundException, SQLException {
        Connection c = connect();
        String qry = "INSERT INTO MESSAGGI(messaggio)"
                    +"VALUES(?)";
        PreparedStatement control = c.prepareStatement(qry);
        control.setString(1, m.getMessage());
        control.executeUpdate();
        qry = "SELECT idMessaggio FROM MESSAGGI WHERE idMessaggio=(SELECT max(idMessaggio) FROM MESSAGGI)";
        control = c.prepareStatement(qry);
        int max = control.executeQuery().getInt("idMessaggio");
        qry = "INSERT INTO CHAT(idUtenteA,idUtenteB,idMessaggio)"
             +"VALUES(?,?,?)";
        control = c.prepareStatement(qry);
        control.setInt(1, m.getSrc());
        control.setInt(2, m.getDst());
        control.setInt(3, max);
        control.executeUpdate();
    }

    public static ArrayList<Message> getMessages(int idUtente) throws SQLException, ClassNotFoundException {
        ArrayList<Message> ris = new ArrayList<>();
        Connection c = connect();
        String qry = "SELECT * FROM CHAT WHERE idUtenteA = ? OR idUtenteB = ?";
        PreparedStatement control = c.prepareStatement(qry);
        control.setInt(1, idUtente);
        control.setInt(2, idUtente);
        ResultSet chat = control.executeQuery();
        while(chat.next()){
            int src = chat.getInt("idUtenteA");
            int dst = chat.getInt("idUtenteB");
            qry = "SELECT * FROM MESSAGGI WHERE idMessaggio = ?";
            control = c.prepareStatement(qry);
            control.setInt(1,chat.getInt("idMessaggio"));
            ResultSet message = control.executeQuery();
            Message m = new Message(message.getString("messaggio"),chat.getInt("idUtenteA"),chat.getInt("idUtenteB"));
            ris.add(m);
        }
        return ris;
    }

}
