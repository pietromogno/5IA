/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author BUS
 */
public class SQLHelper {
    
    Connection conn;
    String dbName = "regDB.db";
    
    public SQLHelper()throws ClassNotFoundException, SQLException{
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
    }

    public void add(String s) throws SQLException  {
        PreparedStatement s2 = conn.prepareStatement("INSERT INTO PERSONA "
                + "(Nome, Cognome, Numcell, Nascita, Username, Password) "
                + " VALUES (?,?,?,?,?,?)");
        s2.setString(1, getInfoClient(s,1,'§'));
        s2.setString(2, getInfoClient(s,2,'§'));
        s2.setString(3, getInfoClient(s,3,'§'));
        s2.setString(4, getInfoClient(s,4,'§'));
        s2.setString(5, getInfoClient(s,5,'§'));
        s2.setString(6, getInfoClient(s,6,'§'));
        s2.execute();
    }
    
    public static String getInfoClient(String segmento, int indice, char spaziatore) { //da scegliere da 1 a 6 o
            String ris = "null";
            byte cnt = 0;
            char temp;
            for (int i = 0; i < segmento.length(); i++) {
                if (segmento.charAt(i) == spaziatore) {
                    cnt++;
                    if (cnt == indice) {
                        ris = "";
                        for (i = i + 1; i < segmento.length(); i++) {
                            temp = segmento.charAt(i);
                            if (temp == spaziatore) {
                                return ris;
                            } else {
                                ris += "" + temp;
                            }
                        }
                    }
                }
            }

            return ris;
        }
    

}
