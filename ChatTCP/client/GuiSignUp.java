package client;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Davide Porcu
 */
public class GuiSignUp extends javax.swing.JFrame {

    private Client client;

    public GuiSignUp(Client client) {
        setLookAndFeel();
        initComponents();
        resetCampi();
        setTitle("Registrazione - Chat TCP");
        this.client = client;
    }

    public void resetCampi() {
        this.lblErrore.setText("");
        this.txtFldCellulare.setText("");
        this.txtFldUsername.setText("");
        this.pswFldPassword.setText("");
    }

    public void setMessaggioErrore(String msg) {
        lblErrore.setText(msg);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblErrore = new javax.swing.JLabel();
        lblEtcUsername = new javax.swing.JLabel();
        lblEtcPassword = new javax.swing.JLabel();
        txtFldUsername = new javax.swing.JTextField();
        pswFldPassword = new javax.swing.JPasswordField();
        btnSignUp = new javax.swing.JButton();
        lblEtcCellulare = new javax.swing.JLabel();
        txtFldCellulare = new javax.swing.JTextField();
        btnIndietro = new javax.swing.JButton();
        lblEtcRegistrazione = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        lblEtcUsername.setText("Username");

        lblEtcPassword.setText("Password");

        btnSignUp.setText("Sign Up");
        btnSignUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignUpActionPerformed(evt);
            }
        });

        lblEtcCellulare.setText("Cellulare");

        btnIndietro.setText("Indietro");
        btnIndietro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIndietroActionPerformed(evt);
            }
        });

        lblEtcRegistrazione.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblEtcRegistrazione.setText("Registrazione");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblErrore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEtcRegistrazione)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnIndietro)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSignUp))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(lblEtcCellulare)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                            .addComponent(txtFldCellulare, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblEtcPassword)
                                .addComponent(lblEtcUsername))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtFldUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                                .addComponent(pswFldPassword)))))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblEtcRegistrazione)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEtcUsername)
                    .addComponent(txtFldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEtcPassword)
                    .addComponent(pswFldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEtcCellulare)
                    .addComponent(txtFldCellulare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSignUp)
                    .addComponent(btnIndietro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrore, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSignUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignUpActionPerformed
        if (!client.isConessioneInterrotta()) {
            String username = txtFldUsername.getText();
            String password = String.valueOf(pswFldPassword.getPassword());
            String cellulare = txtFldCellulare.getText();
            client.sendRegistrazione(username, password, cellulare);
        } else {
            setMessaggioErrore("Connessione al server interrotta!");
        }
    }//GEN-LAST:event_btnSignUpActionPerformed

    private void btnIndietroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIndietroActionPerformed
        client.showGuiLogin();
    }//GEN-LAST:event_btnIndietroActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIndietro;
    private javax.swing.JButton btnSignUp;
    private javax.swing.JLabel lblErrore;
    private javax.swing.JLabel lblEtcCellulare;
    private javax.swing.JLabel lblEtcPassword;
    private javax.swing.JLabel lblEtcRegistrazione;
    private javax.swing.JLabel lblEtcUsername;
    private javax.swing.JPasswordField pswFldPassword;
    private javax.swing.JTextField txtFldCellulare;
    private javax.swing.JTextField txtFldUsername;
    // End of variables declaration//GEN-END:variables

    private void setLookAndFeel() {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GuiSignIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
