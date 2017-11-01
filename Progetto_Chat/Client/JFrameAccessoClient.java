package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @brief JFrameAccessoClient gestisce per l'appunto l'accesso alla Chat trammite
 * Sign in e sign up i dati vengo controllati poi dal server che usa un database 
 * SQLite.
 * @author Matteo
 */
public class JFrameAccessoClient extends javax.swing.JFrame {

    PrintWriter writer;
    BufferedReader r;
    
    public JFrameAccessoClient(PrintWriter writer, BufferedReader r) {
        this.r=r;
        this.writer=writer;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblAccesso = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        lblPw = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPw = new javax.swing.JTextField();
        btnLogin = new javax.swing.JButton();
        btnRegistrati = new javax.swing.JButton();
        lblAvvisi = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblAccesso.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblAccesso.setText("Accesso Client");

        lblUsername.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblUsername.setText("Username: ");

        lblPw.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblPw.setText("Password: ");

        txtUsername.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txtPw.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        btnLogin.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnLogin.setText("LOGIN");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnRegistrati.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnRegistrati.setText("REGISTRATI");
        btnRegistrati.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistratiActionPerformed(evt);
            }
        });

        lblAvvisi.setText("/");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(85, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblAvvisi, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsername)
                            .addComponent(lblPw)
                            .addComponent(btnLogin))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUsername)
                            .addComponent(txtPw)
                            .addComponent(btnRegistrati))))
                .addGap(74, 74, 74))
            .addGroup(layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(lblAccesso)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAccesso)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsername)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPw)
                    .addComponent(txtPw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin)
                    .addComponent(btnRegistrati))
                .addGap(32, 32, 32)
                .addComponent(lblAvvisi)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * @breief si tratta di un Action Performed che viene eseguito al click sul
     * bottone Login.
     * @param evt 
     */
    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        writer.println("login");
        writer.println(txtUsername.getText().trim());
        writer.println(txtPw.getText().trim());
        leggi();
    }//GEN-LAST:event_btnLoginActionPerformed
    //leggi i dati di accesso
    private void leggi() {
        try {
            String avviso=r.readLine();
            if(avviso.equals("OK")){
                Client.pw=txtPw.getText().trim();
                Client.username=txtUsername.getText().trim();
                setVisible(false);
                dispose();
            }
            lblAvvisi.setText(avviso);
        } catch (IOException ex) {
            Logger.getLogger(JFrameAccessoClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @breief si tratta di un Action Performed che viene eseguito al click sul
     * bottone Registrati.
     * @param evt 
     */
    private void btnRegistratiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistratiActionPerformed
        writer.println("registrazione");
        writer.println(txtUsername.getText().trim());
        writer.println(txtPw.getText().trim());
        leggi();
    }//GEN-LAST:event_btnRegistratiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnRegistrati;
    private javax.swing.JLabel lblAccesso;
    private javax.swing.JLabel lblAvvisi;
    private javax.swing.JLabel lblPw;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JTextField txtPw;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
