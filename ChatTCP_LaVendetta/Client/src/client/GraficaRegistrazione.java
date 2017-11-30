package client;

/**
 *
 * @author Davide
 */
public class GraficaRegistrazione extends javax.swing.JFrame {

    private Client client;

    public GraficaRegistrazione(Client client) {
        initComponents();
        this.client = client;
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtPwPassword = new javax.swing.JPasswordField();
        btnIndietro = new javax.swing.JButton();
        btnRegistrazione = new javax.swing.JButton();
        txtFldUsername = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnIndietro.setText("Indietro");
        btnIndietro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIndietroActionPerformed(evt);
            }
        });

        btnRegistrazione.setText("Registrati");
        btnRegistrazione.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrazioneActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Registrazione");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(btnRegistrazione)
                        .addGap(26, 26, 26)
                        .addComponent(btnIndietro))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFldUsername)
                            .addComponent(txtPwPassword)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(jLabel1)))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(31, 31, 31)
                .addComponent(txtFldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPwPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrazione)
                    .addComponent(btnIndietro))
                .addGap(101, 101, 101))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIndietroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIndietroActionPerformed
        client.showGraficaLogin();
    }//GEN-LAST:event_btnIndietroActionPerformed

    private void btnRegistrazioneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrazioneActionPerformed
        String username = txtFldUsername.getText();
        String password = txtPwPassword.getText();
        if (!username.equals("") && !password.equals("")) {
            System.out.println("Cliccato");
            client.sendRegistrazione(username, password);
        }
    }//GEN-LAST:event_btnRegistrazioneActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIndietro;
    private javax.swing.JButton btnRegistrazione;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtFldUsername;
    private javax.swing.JPasswordField txtPwPassword;
    // End of variables declaration//GEN-END:variables
}
