package client;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Davide Porcu
 */
public class GuiAggiungiContatto extends javax.swing.JFrame {

    private Client client;

    public GuiAggiungiContatto(Client client) {
        setLookAndFeel();
        initComponents();
        this.client = client;
        this.setTitle(client.getAccount().getUsername() + " - Aggiungi Contatto");
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAggiungiContatto = new javax.swing.JButton();
        lblEtcScegliContatto = new javax.swing.JLabel();
        txtFldCellulare = new javax.swing.JTextField();
        lblErrore = new javax.swing.JLabel();
        txtFldNomeContatto = new javax.swing.JTextField();
        lblEtcNomeContatto = new javax.swing.JLabel();
        lblEtcNumeroCellulare = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        btnAggiungiContatto.setText("Aggiungi Contatto");
        btnAggiungiContatto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAggiungiContattoActionPerformed(evt);
            }
        });

        lblEtcScegliContatto.setText("Aggiungi un contatto:");

        lblEtcNomeContatto.setText("Nome contatto:");

        lblEtcNumeroCellulare.setText("Cellulare contatto:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblEtcNumeroCellulare, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                            .addComponent(lblEtcNomeContatto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFldNomeContatto)
                            .addComponent(txtFldCellulare)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblEtcScegliContatto)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblErrore, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAggiungiContatto)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(lblEtcScegliContatto)
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFldNomeContatto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEtcNomeContatto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFldCellulare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEtcNumeroCellulare))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAggiungiContatto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAggiungiContattoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAggiungiContattoActionPerformed
        String numCellulare = txtFldCellulare.getText().trim();
        String nomeContatto = txtFldNomeContatto.getText().trim();
        if (!numCellulare.isEmpty() && !nomeContatto.isEmpty()) {
            client.sendAddNuovoContatto(numCellulare, nomeContatto);
            this.dispose();
        }
    }//GEN-LAST:event_btnAggiungiContattoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAggiungiContatto;
    private javax.swing.JLabel lblErrore;
    private javax.swing.JLabel lblEtcNomeContatto;
    private javax.swing.JLabel lblEtcNumeroCellulare;
    private javax.swing.JLabel lblEtcScegliContatto;
    private javax.swing.JTextField txtFldCellulare;
    private javax.swing.JTextField txtFldNomeContatto;
    // End of variables declaration//GEN-END:variables

    private void setLookAndFeel() {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GuiSignIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
