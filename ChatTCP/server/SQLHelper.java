package server;

import oggettiperchat.Messaggio;
import oggettiperchat.Account;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import oggettiperchat.Contatto;
import oggettiperchat.Conversazione;

/**
 * @author Davide Porcu
 */
public class SQLHelper {

    final static String NOME_DB = "ChatTCP.db";

    final static String DRIVE = "database\\";

    static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:" + DRIVE + NOME_DB);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conn;
    }

    public static void closeConnection() throws SQLException {
        try {
            conn.close();
            conn = null;
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean esisteUsername(String userName) {
        boolean ris = false;
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM ACCOUNT WHERE username = ?";
            PreparedStatement st = con.prepareStatement(q);
            st.setString(1, userName);
            ResultSet rs = st.executeQuery();
            ris = rs.next();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static int getIdAccount(String userName) {
        int idPersona = -1;
        try {
            Connection con = getConnection();
            PreparedStatement s = con.prepareStatement("SELECT IdAccount FROM ACCOUNT WHERE username=?");
            s.setString(1, userName);
            ResultSet rs = s.executeQuery();
            idPersona = rs.getInt("IdAccount");//ottengo l'identificativo della persona--->> posso usarlo per prendere dati da altre tabelle ;)
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idPersona;
    }

    public static boolean isLoginCorretto(String password, String nomeUtente) {
        boolean ris = false;
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM ACCOUNT WHERE password = ? AND username=?";
            PreparedStatement st = con.prepareStatement(q);
            st.setString(1, password);
            st.setString(2, nomeUtente);
            ResultSet rs = st.executeQuery();
            ris = rs.next();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static boolean esisteIdAccount(int idAccount) {
        boolean ris = false;
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM ACCOUNT WHERE idAccount=?";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idAccount);
            ResultSet rs = st.executeQuery();
            ris = rs.next();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static Account getAccount(String username, String password) {
        Account a = null;
        try {
            Connection con = getConnection();
            PreparedStatement select = con.prepareStatement("SELECT * FROM ACCOUNT WHERE username=? AND password=?");
            select.setString(1, username);
            select.setString(2, password);
            ResultSet rs = select.executeQuery();
            a = new Account();
            a.setIdAccount(rs.getInt("IdAccount"));
            a.setUsername(rs.getString("username"));
            a.setPassword(rs.getString("password"));
            a.setCellulare(rs.getString("cellulare"));
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }

    public static Account getAccount(int idAccount) {
        Account a = null;
        try {
            Connection con = getConnection();
            PreparedStatement select = con.prepareStatement("SELECT * FROM ACCOUNT WHERE IdAccount=?");
            select.setInt(1, idAccount);
            ResultSet rs = select.executeQuery();
            a = new Account();
            a.setIdAccount(idAccount);
            a.setUsername(rs.getString("username"));
            a.setPassword(rs.getString("password"));
            a.setCellulare(rs.getString("cellulare"));
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }

    public static void inserisciAccount(String username, String password, String cellulare) {
        try {
            Connection con = getConnection();
            String query = "INSERT INTO ACCOUNT (username, password,cellulare) VALUES(?,?,?)";
            PreparedStatement insert = con.prepareStatement(query);
            insert.setString(1, username);
            insert.setString(2, password);
            insert.setString(3, cellulare);
            insert.executeUpdate();
            insert.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean esisteCellulare(String cellulare) {
        boolean ris = false;
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM ACCOUNT WHERE cellulare=?";
            PreparedStatement st = con.prepareStatement(q);
            st.setString(1, cellulare);
            ResultSet rs = st.executeQuery();
            ris = rs.next();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static int getIdAccountFromCellulare(String cellulare) {
        int idAccount = -1;
        try {
            Connection con = getConnection();
            String q = "SELECT IdAccount "
                    + "FROM ACCOUNT "
                    + "WHERE cellulare=?";
            PreparedStatement st = con.prepareStatement(q);
            st.setString(1, cellulare);
            ResultSet rs = st.executeQuery();
            idAccount = rs.getInt("IdAccount");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idAccount;
    }

    public static ArrayList<Contatto> getListaContatti(int idAccount) {
        ArrayList<Contatto> ris = new ArrayList<>();
        try {
            Connection con = getConnection();
            String q = "SELECT CONTATTO.IdAccountComeContatto, ACCOUNT.username, CONTATTO.nomeContatto, ACCOUNT.cellulare "
                    + "FROM CONTATTO ,ACCOUNT "
                    + "WHERE (CONTATTO.IdAccount=? AND ACCOUNT.IdAccount=CONTATTO.IdAccountComeContatto) "
                    + "ORDER BY CONTATTO.nomeContatto";

            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idAccount);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ris.add(new Contatto(rs.getInt("IdAccountComeContatto"), rs.getString("username"), rs.getString("cellulare"), rs.getString("nomeContatto")));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static void addNewContatti(int idAccount, ArrayList<Contatto> listaAggiornata) {
        ArrayList<Contatto> listaContattiDB = getListaContatti(idAccount);
        for (Contatto contatto : listaContattiDB) {
            listaAggiornata.remove(contatto);
        }
        try {
            for (Contatto contatto : listaAggiornata) {
                Connection con = getConnection();
                String query = "INSERT INTO CONTATTO (idAccount, idAccountComeContatto,nomeContatto) VALUES(?,?,?)";
                PreparedStatement insert = con.prepareStatement(query);
                insert.setInt(1, idAccount);
                insert.setInt(2, contatto.getIdAccount());
                insert.setString(3, contatto.getNomeContatto());
                insert.executeUpdate();
                insert.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addMessaggio(Messaggio msg) {
        try {
            Connection con = getConnection();
            String query = "INSERT INTO MESSAGGIO (IdMittente, IdDestinatario,messaggio,IdGruppo,dataInvio) VALUES(?,?,?,?,?)";
            PreparedStatement insert = con.prepareStatement(query);
            insert.setInt(1, msg.getIdMittente());
            insert.setInt(2, msg.getIdDestinatario());
            insert.setString(3, (String) msg.getMessaggio());
            insert.setInt(4, msg.getIdGruppo());
            insert.setDate(5, new java.sql.Date(msg.getDataInvio().getTime()));
            insert.executeUpdate();
            insert.close();

        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<Integer> getListaIdDestinatariDiMessaggiDiAccount(int idAccount) {
        ArrayList<Integer> listaDestinatari = new ArrayList<>();
        try {
            Connection con = getConnection();
            String q = "SELECT MESSAGGIO.IdDestinatario "
                    + "FROM MESSAGGIO "
                    + "WHERE MESSAGGIO.IdMittente=? AND (MESSAGGIO.IdDestinatario<>-1) "//cioè idgruppo=-1 cioè destinatario è unico (conversazione 1vs1)
                    + "GROUP BY MESSAGGIO.IdDestinatario";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idAccount);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                listaDestinatari.add(rs.getInt("IdDestinatario"));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDestinatari;
    }

    public static ArrayList<Integer> getListaIdMittentiDiMessaggiVersoAccount(int idAccount) {
        ArrayList<Integer> listaDestinatari = new ArrayList<>();
        try {
            Connection con = getConnection();
            String q = "SELECT MESSAGGIO.IdMittente "
                    + "FROM MESSAGGIO "
                    + "WHERE MESSAGGIO.IdDestinatario=? "
                    + "GROUP BY MESSAGGIO.IdMittente";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idAccount);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                listaDestinatari.add(rs.getInt("IdMittente"));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDestinatari;
    }

    public static ArrayList<Messaggio> getMessaggiConversazioneTraDueAccount(int idAccount1, int idAccount2) {
        ArrayList<Messaggio> listaMessaggi = new ArrayList<>();
        try {
            Connection con = getConnection();
            String q = "SELECT IdMittente, IdDestinatario, messaggio, IdGruppo, dataInvio "
                    + "FROM MESSAGGIO "
                    + "WHERE (IdMittente=? AND idDestinatario=?) OR (IdMittente=? AND idDestinatario=?) "
                    + "ORDER BY dataInvio";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idAccount1);
            st.setInt(2, idAccount2);
            st.setInt(3, idAccount2);
            st.setInt(4, idAccount1);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                listaMessaggi.add(new Messaggio(Messaggio.MESSAGGIO, rs.getString("messaggio"), rs.getInt("IdMittente"), rs.getInt("IdDestinatario"), -1, new Date(rs.getDate("dataInvio").getTime())));//-1 perchè è di sicuro una covnersazione tra due account
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaMessaggi;
    }

    public static Contatto generaContattoDiAccount(int idAccount) {
        Contatto ris = new Contatto();
        try {
            Connection con = getConnection();
            String q = "SELECT ACCOUNT.username, ACCOUNT.cellulare "
                    + "FROM ACCOUNT "
                    + "WHERE ACCOUNT.IdAccount=?";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idAccount);
            ResultSet rs = st.executeQuery();
            ris.setIdAccount(idAccount);
            ris.setUsername(rs.getString("username"));
            ris.setNomeContatto(rs.getString("cellulare"));//non ho un nome contatto quindi uso il cellulare
            ris.setCellulare(rs.getString("cellulare"));
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static ArrayList<Integer> getListaIdGruppiACuiAppartiene(int idAccount) {
        ArrayList<Integer> listaIdGruppo = new ArrayList<>();
        try {
            Connection con = getConnection();
            String q = "SELECT IdGruppo "
                    + "FROM MEMBROGRUPPO "
                    + "WHERE IdAccount=? ";//non ci sono duplicati (una persona appartiene solo un volta a un gruppo)
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idAccount);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                listaIdGruppo.add(rs.getInt("IdGruppo"));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaIdGruppo;
    }

    public static ArrayList<Messaggio> getMessaggiGruppo(int idGruppo) {
        ArrayList<Messaggio> listaMessaggi = new ArrayList<>();
        try {
            Connection con = getConnection();
            String q = "SELECT IdMittente, IdDestinatario, messaggio, IdGruppo, dataInvio "
                    + "FROM MESSAGGIO "
                    + "WHERE idGruppo=? AND idDestinatario=-1 "
                    + "ORDER BY dataInvio";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idGruppo);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                listaMessaggi.add(new Messaggio(Messaggio.MESSAGGIO, rs.getString("messaggio"), rs.getInt("IdMittente"), -1, idGruppo, new Date(rs.getDate("dataInvio").getTime())));//-1 perchè destinatario è sempre 
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaMessaggi;
    }

    public static ArrayList<Integer> getIdAccountPartecipantiGruppo(int idGruppo) {
        ArrayList<Integer> listaPartecipanti = new ArrayList<>();
        try {
            Connection con = getConnection();
            String q = "SELECT ACCOUNT.idAccount,ACCOUNT.username,ACCOUNT.cellulare "
                    + "FROM MEMBROGRUPPO , ACCOUNT "
                    + "WHERE MEMBROGRUPPO.idGruppo=? AND MEMBROGRUPPO.idAccount=ACCOUNT.idAccount ";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idGruppo);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                listaPartecipanti.add(rs.getInt("idAccount"));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaPartecipanti;
    }

    public static ArrayList<Contatto> getListaPartecipantiGruppo(int idGruppo, ArrayList<Contatto> listaContatti) {
        ArrayList<Contatto> listaPartecipanti = new ArrayList<>();
        try {
            Connection con = getConnection();
            String q = "SELECT ACCOUNT.idAccount,ACCOUNT.username,ACCOUNT.cellulare "
                    + "FROM MEMBROGRUPPO , ACCOUNT "
                    + "WHERE MEMBROGRUPPO.idGruppo=? AND MEMBROGRUPPO.idAccount=ACCOUNT.idAccount ";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idGruppo);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int idContatto = rs.getInt("idAccount");
                int idContattoInLista = indexOfContattoDellAccount(idContatto, listaContatti);
                if (idContattoInLista >= 0) {//contatto presente nella lista contatti
                    listaPartecipanti.add(listaContatti.get(idContattoInLista));
                } else {//altrimenti ne genero uno per sconosciuti
                    listaPartecipanti.add(new Contatto(idContatto, rs.getString("username"), rs.getString("cellulare"), rs.getString("cellulare")));
                }
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaPartecipanti;
    }

    public static String getNomeGruppo(int idGruppo) {
        String nomeGruppo = "";
        try {
            Connection con = getConnection();
            String q = "SELECT nomeGruppo FROM GRUPPO WHERE idGruppo=?";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idGruppo);
            ResultSet rs = st.executeQuery();
            nomeGruppo = rs.getString("nomeGruppo");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nomeGruppo;
    }

    private static int indexOfContattoDellAccount(int idContatto, ArrayList<Contatto> contattiAccountUtente) {
        int i = 0;
        for (Contatto contatto : contattiAccountUtente) {
            if (contatto.getIdAccount() == idContatto) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static ArrayList<Conversazione> getListaConversazioniAccount(int idAccount) {//da alleggerire
        ArrayList<Integer> listaIdDestinatari = getListaIdDestinatariDiMessaggiDiAccount(idAccount);// e se uno ha ricevuto messaggi e basta? non ha destinatari ma ha mittenti...
        //get lista mittenti di account... se attuale account ha solo ricevuto e mai inviato
        ArrayList<Conversazione> listaConversazioni = new ArrayList<>();
        ArrayList<Contatto> contattiAccountUtente = getListaContatti(idAccount);
        if (listaIdDestinatari.isEmpty()) {
            listaIdDestinatari = getListaIdMittentiDiMessaggiVersoAccount(idAccount);
        }
        for (int idContatto : listaIdDestinatari) {
            ArrayList<Messaggio> listaMessaggiConversazione = getMessaggiConversazioneTraDueAccount(idContatto, idAccount);
            Contatto altroUtente;
            int indexContatto = indexOfContattoDellAccount(idContatto, contattiAccountUtente);
            if (indexContatto >= 0) {
                altroUtente = contattiAccountUtente.get(indexContatto);
            } else {
                altroUtente = generaContattoDiAccount(idContatto);
            }
            ArrayList<Contatto> destinatarioLista = new ArrayList<>();
            destinatarioLista.add(altroUtente);
            listaConversazioni.add(new Conversazione(altroUtente.getNomeContatto(), idContatto, destinatarioLista, listaMessaggiConversazione, false, false));
        }

        ArrayList<Integer> listaIdGruppiACuiAppartiene = getListaIdGruppiACuiAppartiene(idAccount);
        for (int idGruppo : listaIdGruppiACuiAppartiene) {
            ArrayList<Messaggio> listaMessaggiGruppo = getMessaggiGruppo(idGruppo);
            ArrayList<Contatto> listaPartecipanti = getListaPartecipantiGruppo(idGruppo, contattiAccountUtente);//se non sono presenti nei miei contatti allora numero di telefono come nomeContatto, se sono io scrivi me stesso...
            String nomegruppo = getNomeGruppo(idGruppo);
            listaConversazioni.add(new Conversazione(nomegruppo, idGruppo, listaPartecipanti, listaMessaggiGruppo, true, false));
        }
        listaConversazioni.sort(null);//ordinate secondo la data dell'ultimo messaggio
        return listaConversazioni;
    }

    public static void addNuovoContattoAUtente(int idAccount, int idAccountCorrispondente, String nomeContatto) {
        try {
            Connection con = getConnection();
            String query = "INSERT INTO CONTATTO (IdAccount, IdAccountComeContatto,nomeContatto) VALUES(?,?,?)";
            PreparedStatement insert = con.prepareStatement(query);
            insert.setInt(1, idAccount);
            insert.setInt(2, idAccountCorrispondente);
            insert.setString(3, nomeContatto);
            insert.executeUpdate();
            insert.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<Contatto> adattaContattiADestinatario(int idDestinatario, ArrayList<Contatto> contatti) {
        ArrayList<Contatto> contattiAdattati = new ArrayList<>();
        ArrayList<Contatto> listaContattiUtente = getListaContatti(idDestinatario);

        for (Contatto c : contatti) {
            int idContatto = c.getIdAccount();
            if (idDestinatario != idContatto) {//sono me stesso
                int indexContatto = indexOfContattoDellAccount(idContatto, listaContattiUtente);
                if (indexContatto >= 0) {
                    contattiAdattati.add(listaContattiUtente.get(indexContatto));
                } else {
                    contattiAdattati.add(generaContattoDiAccount(idContatto));
                }
            }
        }

        Account accountUtente = getAccount(idDestinatario);
        contattiAdattati.add(new Contatto(idDestinatario, accountUtente.getUsername(), accountUtente.getCellulare(), accountUtente.getCellulare()));

        return contattiAdattati;
    }

    public static void addPartecipanteGruppo(int idGruppo, int idAccount) {
        try {
            Connection con = getConnection();
            String query = "INSERT INTO MEMBROGRUPPO (IdGruppo,IdAccount) VALUES(?,?)";
            PreparedStatement insert = con.prepareStatement(query);
            insert.setInt(1, idGruppo);
            insert.setInt(2, idAccount);
            insert.executeUpdate();
            insert.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int getLastIdGruppo() {
        int idGruppo = -1;
        try {
            Connection con = getConnection();
            String query = "SELECT IdGruppo FROM GRUPPO ORDER BY IdGruppo DESC LIMIT 1";
            PreparedStatement select = con.prepareStatement(query);
            ResultSet rs = select.executeQuery();
            idGruppo = rs.getInt("IdGruppo");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idGruppo;
    }

    //retsituisco anche numero del gruppo ;)
    public static synchronized int addGruppo(Conversazione possibileConversazionegruppo) {//nessun altro inserimento mentre getto il numero del gruppo
        int idGruppo = -1;
        try {
            Connection con = getConnection();
            String query = "INSERT INTO GRUPPO (nomeGruppo) VALUES(?)";
            PreparedStatement insert = con.prepareStatement(query);
            insert.setString(1, possibileConversazionegruppo.getNomeConversazione());
            insert.executeUpdate();
            insert.close();
            idGruppo = getLastIdGruppo();
            ArrayList<Contatto> listaPartecipanti = possibileConversazionegruppo.getListaPartecipanti();//in lista c'è anche chi ha creato il gruppo
            for (Contatto c : listaPartecipanti) {
                addPartecipanteGruppo(idGruppo, c.getIdAccount());
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idGruppo;
    }

    public static boolean isGiaContattoDiAccount(int idClient, int idAccountCorrispondente) {

        boolean ris = false;
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM CONTATTO WHERE idAccount = ? AND idAccountComeContatto=?";
            PreparedStatement st = con.prepareStatement(q);
            st.setInt(1, idClient);
            st.setInt(2, idAccountCorrispondente);
            ResultSet rs = st.executeQuery();
            ris = rs.next();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;

    }

    /*public static void main(String[] args) {
        ArrayList<Conversazione> conv = getListaConversazioniAccount(1);
         for (Conversazione c : conv) {
         System.out.println(c.toString());
         }
         System.out.println(conv.get(0).getListaPartecipanti());

        System.out.println(getLastIdGruppo());
    }*/

}
