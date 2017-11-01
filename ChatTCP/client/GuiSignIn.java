/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Davide Porcu
 */
public class GuiSignIn extends javax.swing.JFrame {

    /**
     * Creates new form GuiSignIn
     */
    private Client client;

    public GuiSignIn(Client client) {
        setLookAndFeel();
        initComponents();
        this.client = client;
        resetCampi();
        setTitle("Login - Chat TCP");
        setVisible(true);
    }

    public void resetCampi() {
        this.lblErrore.setText("");
        this.pswFldPassword.setText("");
        this.txtFldUsername.setText("");
    }

    public void setMessaggioErrore(String msg) {
        lblErrore.setText(msg);
    }

    private void doLogin() {
        String username = txtFldUsername.getText();
        String password = String.valueOf(pswFldPassword.getPassword());
        client.sendLogin(username, password);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblEtcUsername = new javax.swing.JLabel();
        lblEtcPassword = new javax.swing.JLabel();
        txtFldUsername = new javax.swing.JTextField();
        pswFldPassword = new javax.swing.JPasswordField();
        btnSignIn = new javax.swing.JButton();
        btnSignUp = new javax.swing.JButton();
        lblErrore = new javax.swing.JLabel();
        lblEtcLogin = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        lblEtcUsername.setText("Username");

        lblEtcPassword.setText("Password");

        pswFldPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pswFldPasswordKeyTyped(evt);
            }
        });

        btnSignIn.setText("Sign In");
        btnSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignInActionPerformed(evt);
            }
        });

        btnSignUp.setText("Sign Up");
        btnSignUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignUpActionPerformed(evt);
            }
        });

        lblErrore.setText("jLabel1");

        lblEtcLogin.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblEtcLogin.setText("Login");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEtcLogin)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblErrore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblEtcPassword)
                                .addComponent(lblEtcUsername))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnSignIn)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnSignUp))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtFldUsername)
                                    .addComponent(pswFldPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblEtcLogin)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEtcUsername)
                    .addComponent(txtFldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEtcPassword)
                    .addComponent(pswFldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSignIn)
                    .addComponent(btnSignUp))
                .addGap(18, 18, 18)
                .addComponent(lblErrore)
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSignInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignInActionPerformed
        if (!client.isConessioneInterrotta()) {
            doLogin();
        } else {
            setMessaggioErrore("Connessione al server interrotta!");
        }
    }//GEN-LAST:event_btnSignInActionPerformed

    private void btnSignUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignUpActionPerformed
        if (!client.isConessioneInterrotta()) {
            client.showGuiRegistrazione();
        } else {
            setMessaggioErrore("Connessione al server interrotta!");
        }
    }//GEN-LAST:event_btnSignUpActionPerformed

    private void pswFldPasswordKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pswFldPasswordKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!client.isConessioneInterrotta()) {
                client.showGuiRegistrazione();
            } else {
                setMessaggioErrore("Connessione al server interrotta!");
            }
        }
    }//GEN-LAST:event_pswFldPasswordKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSignIn;
    private javax.swing.JButton btnSignUp;
    private javax.swing.JLabel lblErrore;
    private javax.swing.JLabel lblEtcLogin;
    private javax.swing.JLabel lblEtcPassword;
    private javax.swing.JLabel lblEtcUsername;
    private javax.swing.JPasswordField pswFldPassword;
    private javax.swing.JTextField txtFldUsername;
    // End of variables declaration//GEN-END:variables

    private void setLookAndFeel()  {
        try {
            javax.swing.UIManager.setLookAndFeel( javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GuiSignIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
