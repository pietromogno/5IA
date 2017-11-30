package Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;

/**
 * @author Emanuele Pagotto
 */
public class ClientForm extends javax.swing.JFrame {

    public ClientForm() {
        initComponents();
        client = new Client("localhost", 9090);
        messages = new ArrayList<>();
        utenti = new ArrayList<>();
        cbNeedUpdate = false;
        msgNeedUpdate = false;
        chatNeedUpdate = 0;
        up = new Update();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chat = new javax.swing.JPanel();
        txt_Msg = new javax.swing.JTextField();
        btn_Invia = new javax.swing.JButton();
        pnl_Chat = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        txt_userName = new javax.swing.JTextField();
        txt_password = new javax.swing.JTextField();
        btn_logIO = new javax.swing.JButton();
        btn_registra = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbl_Msg = new javax.swing.JLabel();
        cb_clients = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("chat TCP");
        setBackground(new java.awt.Color(255, 255, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        btn_Invia.setText("->");
        btn_Invia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_InviaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout chatLayout = new javax.swing.GroupLayout(chat);
        chat.setLayout(chatLayout);
        chatLayout.setHorizontalGroup(
            chatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_Msg, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Invia)
                .addContainerGap())
            .addComponent(pnl_Chat, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        chatLayout.setVerticalGroup(
            chatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chatLayout.createSequentialGroup()
                .addComponent(pnl_Chat)
                .addGap(18, 18, 18)
                .addGroup(chatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_Msg)
                    .addComponent(btn_Invia, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_userName.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        txt_password.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        btn_logIO.setText("Login");
        btn_logIO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logIOActionPerformed(evt);
            }
        });

        btn_registra.setText("Registrati");
        btn_registra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registraActionPerformed(evt);
            }
        });

        jLabel1.setText("Username");

        jLabel2.setText("password");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 50, Short.MAX_VALUE)
                        .addComponent(btn_registra)
                        .addGap(18, 18, 18)
                        .addComponent(btn_logIO))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_password)
                            .addComponent(txt_userName))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_userName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_logIO, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(btn_registra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        lbl_Msg.setText("Registrati o accedi!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_Msg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cb_clients, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_Msg)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_clients, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 385, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_logIOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logIOActionPerformed
        try {
            client.login(txt_userName.getText(), txt_password.getText());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_logIOActionPerformed

    private void btn_registraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registraActionPerformed
        try {
            client.register(txt_userName.getText(), txt_password.getText());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_registraActionPerformed

    private void btn_InviaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_InviaActionPerformed
        client.sendMessage(txt_Msg.getText(), (String) cb_clients.getSelectedItem());
    }//GEN-LAST:event_btn_InviaActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        up.start();
        client.startMessageReceiver();
    }//GEN-LAST:event_formWindowActivated

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientForm().setVisible(true);
            }
        });
    }

    public void updateCBox() {
        String[] dati;
        if (utenti.size() > 0) {
            dati = new String[utenti.size()];
            System.out.println("Mannaggia a te cBox");
            utenti.toArray(dati);
        } else {
            dati = new String[]{"non c'è nessuno online"};
        }
        this.cb_clients.setModel(new DefaultComboBoxModel<>(dati));
        cbNeedUpdate = false;

    }

    public void updateMsg() {
        this.lbl_Msg.setText(messaggio);
        System.out.println("Mannaggia a te messaggio");
        msgNeedUpdate = false;
    }

    public void updateChat() {
        while (chatNeedUpdate < messages.size()) {
            pnl_Chat.add(new JLabel(messaggio));
            chatNeedUpdate++;
        }
    }

    public static void addToChat(String m) {
        messages.add(m);
    }

    public static void receiveCBoxData(String u) {
        utenti.add(u);
        cbNeedUpdate = true;
    }

    public static void receiveMsg(String m) {
        messaggio = m;
        cbNeedUpdate = true;
    }

    class Update extends Thread {

        private final long SECOND = 1000;

        public Update() {
        }

        @Override
        public synchronized void run() {
            try {
                while (true) {
                    wait(SECOND);
                    if (msgNeedUpdate) {
                        updateMsg();
                    }
                    if (cbNeedUpdate) {
                        updateCBox();
                    }
                    if (chatNeedUpdate <= messages.size()) {
                        updateChat();
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientForm.class.getName()).log(Level.SEVERE, null, ex);

            }

        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Invia;
    private javax.swing.JButton btn_logIO;
    private javax.swing.JButton btn_registra;
    private javax.swing.JComboBox<String> cb_clients;
    private javax.swing.JPanel chat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbl_Msg;
    private javax.swing.JScrollPane pnl_Chat;
    private javax.swing.JTextField txt_Msg;
    private javax.swing.JTextField txt_password;
    private javax.swing.JTextField txt_userName;
    // End of variables declaration//GEN-END:variables
    //User declared variables - modify with caution
    private Client client;
    private Update up;
    public static String messaggio;
    public static ArrayList<String> messages;
    private static ArrayList<String> utenti;
    public static boolean msgNeedUpdate;
    private static boolean cbNeedUpdate;
    private static int chatNeedUpdate;
    //End of user declared variables
}
