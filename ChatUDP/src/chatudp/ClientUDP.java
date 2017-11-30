package chatudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rikic
 */
public class ClientUDP extends javax.swing.JFrame implements Runnable {

    Connection conn;
    ChatConnection chat;

    static MulticastSocket socket;

    String username;
    boolean isLogged = false;
    int nUser = 0;

    public ClientUDP() {
        initComponents();
    }

    public void login() {
        this.isLogged = true;
        nUser++;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPass = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblError = new javax.swing.JTextField();
        buttonLogin = new javax.swing.JButton();
        buttonRegistrazione = new javax.swing.JButton();
        connectButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        msg_area = new javax.swing.JTextArea();
        msg_text = new javax.swing.JTextField();
        msg_send = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Riccordo Corò - CHAT");

        txtUsername.setToolTipText("Username");
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        txtPass.setToolTipText("Password");
        txtPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassActionPerformed(evt);
            }
        });

        jLabel2.setText("Username:");

        jLabel3.setText("Password:");

        lblError.setEditable(false);
        lblError.setToolTipText("Password");
        lblError.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblError.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblErrorActionPerformed(evt);
            }
        });

        buttonLogin.setText("Login");
        buttonLogin.setToolTipText("");
        buttonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLoginClick(evt);
            }
        });

        buttonRegistrazione.setText("Registrati");
        buttonRegistrazione.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRegistrazioneregistrazione(evt);
            }
        });

        connectButton.setText("Connect");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonClick(evt);
            }
        });

        msg_area.setEditable(false);
        msg_area.setColumns(20);
        msg_area.setRows(5);
        jScrollPane1.setViewportView(msg_area);

        msg_text.setToolTipText("Scrivi il messaggio ...");
        msg_text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msg_textActionPerformed(evt);
            }
        });

        msg_send.setText(">");
        msg_send.setToolTipText("");
        msg_send.setMargin(new java.awt.Insets(0, 0, 0, 0));
        msg_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msg_sendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(buttonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonRegistrazione, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtUsername, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtPass, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblError, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(connectButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(msg_text, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(msg_send, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonLogin)
                    .addComponent(buttonRegistrazione))
                .addGap(18, 18, 18)
                .addComponent(connectButton)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(msg_text, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(msg_send, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void txtPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassActionPerformed

    private void lblErrorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblErrorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblErrorActionPerformed

    private void buttonLoginClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLoginClick
        PreparedStatement s4;
        try {
            s4 = conn.prepareStatement(" SELECT utente , password FROM UTENTIREGISTRATI WHERE utente = '" + txtUsername.getText() + "' AND password = '" + txtPass.getText() + "'");
            ResultSet r1;
            r1 = s4.executeQuery();
            if (r1.next()) {
                username = txtUsername.getText();
                lblError.setText("Clicca il tasto CONNETTI");
                txtUsername.setEnabled(isLogged);
                txtPass.setEnabled(isLogged);
            } else {
                lblError.setText("Username o Password errati");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        login();
    }//GEN-LAST:event_buttonLoginClick

    private void buttonRegistrazioneregistrazione(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRegistrazioneregistrazione
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(" INSERT INTO UTENTIREGISTRATI (utente , password) VALUES ('" + txtUsername.getText() + "','" + txtPass.getText() + "') ");
            ps.execute();
            username = txtUsername.getText();
            txtUsername.setEnabled(isLogged);
            txtPass.setEnabled(isLogged);
            login();
            lblError.setText("Clicca il tasto CONNETTI");
        } catch (SQLException ex) {
            lblError.setText("Utente già registrato");
            //Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonRegistrazioneregistrazione

    private void connectButtonClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonClick
        if (isLogged) {
            if (chat == null) {
                chat = new ChatConnection();
                new Thread(chat).start();
                msg_area.append(" ## CONNESSIONE ATTIVA \n");
            } else {
                msg_area.append(" ERRORE ## CONNESSIONE GIA ATTIVA \n");
            }
        } else {
            msg_area.append(" ERRORE ## UTENTE NON LOGGATO \n");
        }
    }//GEN-LAST:event_connectButtonClick

    private void msg_textActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msg_textActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_msg_textActionPerformed

    private void msg_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msg_sendActionPerformed
        String msgout;
        msgout = msg_text.getText().trim();
        try {
            System.out.println(msgout);
            chat.send(username + ": " + msgout);
        } catch (IOException e) {
            System.out.println(e);
        }
        msg_text.setText("");
    }//GEN-LAST:event_msg_sendActionPerformed

    public void run() {
        System.out.println("#### AVVIO CLIENT ####");
        this.setVisible(true);
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\rikic\\Desktop\\ChatUDP\\UTENTIREGISTRATI.db");
            System.out.println("### CONNESSIONE CON IL DATABASE AVVENUTA ###");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("CAUSA:" + e);
        }
    }

    class ChatConnection implements Runnable {

        MulticastSocket s;
        InetAddress group;
        int port = 9898;

        DatagramPacket toSend;
        DatagramPacket recv;
        String received;

        @Override
        public void run() {
            try {
                // Entro nel gruppo multicast e dico di essermi connesso
                group = InetAddress.getByName("224.0.0.1");
                s = new MulticastSocket(port);
                // Ricevo le risposte
                byte[] buf = new byte[1024];
                recv = new DatagramPacket(buf, buf.length);
                s.joinGroup(group);
                while (true) {
                    s.receive(recv);
                    received = new String(recv.getData(), 0, recv.getLength());
                    if (!received.equals("")) {
                        msg_area.append(received + "\n");
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void send(String msg) throws IOException {
            toSend = new DatagramPacket(msg.getBytes(), msg.length(), group, port);
            s.send(toSend);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonLogin;
    private javax.swing.JButton buttonRegistrazione;
    private javax.swing.JButton connectButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lblError;
    private javax.swing.JTextArea msg_area;
    private javax.swing.JButton msg_send;
    private javax.swing.JTextField msg_text;
    private javax.swing.JTextField txtPass;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
