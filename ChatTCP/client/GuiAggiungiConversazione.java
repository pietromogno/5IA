package client;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;
import oggettiperchat.Contatto;

/**
 *
 * @author Davide Porcu
 */
public class GuiAggiungiConversazione extends javax.swing.JFrame {

    private Client client;
    
    public GuiAggiungiConversazione(Client client) {
        setLookAndFeel();
        initComponents();
        this.client=client;
        cmbBoxContatti.removeAllItems();
        ArrayList<Contatto> listaContatti=client.getListaContatti();
        for(Contatto contatto:listaContatti){
            cmbBoxContatti.addItem(contatto);
        }
        this.setTitle(client.getAccount().getUsername()+" - Crea Conversazione");
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbBoxContatti = new javax.swing.JComboBox();
        btnConfermaScelta = new javax.swing.JButton();
        lblEtcScegliContatto = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        cmbBoxContatti.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnConfermaScelta.setText("Crea Conversazione");
        btnConfermaScelta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfermaSceltaActionPerformed(evt);
            }
        });

        lblEtcScegliContatto.setText("Scegli il contatto per la nuova chat:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEtcScegliContatto)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmbBoxContatti, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnConfermaScelta)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(lblEtcScegliContatto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbBoxContatti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfermaScelta))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfermaSceltaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfermaSceltaActionPerformed
        if(cmbBoxContatti.getItemCount()>0){//se c'è qualcosa dentro allora faccio azione di selezione
            Contatto contattoPerConversazione=(Contatto)cmbBoxContatti.getSelectedItem();
            client.addNuovaConversazione(contattoPerConversazione);
            this.dispose();
        }
    }//GEN-LAST:event_btnConfermaSceltaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfermaScelta;
    private javax.swing.JComboBox cmbBoxContatti;
    private javax.swing.JLabel lblEtcScegliContatto;
    // End of variables declaration//GEN-END:variables

     private void setLookAndFeel() {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GuiSignIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
