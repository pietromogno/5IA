/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.UnsupportedLookAndFeelException;
import oggettiperchat.Account;
import oggettiperchat.Contatto;
import oggettiperchat.Conversazione;

/**
 *
 * @author Davide Porcu
 */
public class GuiAggiungiGruppo extends javax.swing.JFrame {

    /**
     * Creates new form GuiAggiungiGruppo
     */
    private Client client;
    private DefaultListModel listModelPartecipanti;
    private Conversazione nuovoGruppo;
    private GuiAggiungiPartecipanteGruppo guiAPG;

    public GuiAggiungiGruppo(Client client) {
        setLookAndFeel();
        initComponents();
        this.client = client;
        listModelPartecipanti = new DefaultListModel();
        nuovoGruppo = new Conversazione();
        nuovoGruppo.setIsGruppo(true);
        this.setTitle(client.getAccount().getUsername()+" - Aggiungi Gruppo");
        this.setVisible(true);
    }

    public void updateListaPartecipanti() {
        listModelPartecipanti.removeAllElements();
        ArrayList<Contatto> listaPartecipanti = nuovoGruppo.getListaPartecipanti();
        for (Contatto c : listaPartecipanti) {
            listModelPartecipanti.addElement(c);
        }
        listListaPartecipanti.setModel(listModelPartecipanti);
    }

    public void addPartecipante(Contatto contattoPerConversazione) {
        guiAPG=null;
        nuovoGruppo.addPartecipante(contattoPerConversazione);
        updateListaPartecipanti();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblEtcNomeGruppo = new javax.swing.JLabel();
        txtFldNomeGruppo = new javax.swing.JTextField();
        btnCreaGruppo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listListaPartecipanti = new javax.swing.JList();
        lblEtcListaPartecipanti = new javax.swing.JLabel();
        btnAddPartecipante = new javax.swing.JButton();
        btnRimuoviPartecipante = new javax.swing.JButton();
        lblErrore = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setText("Crea un gruppo:");

        lblEtcNomeGruppo.setText("Nome Gruppo:");

        btnCreaGruppo.setText("Crea Gruppo");
        btnCreaGruppo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreaGruppoActionPerformed(evt);
            }
        });

        listListaPartecipanti.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(listListaPartecipanti);

        lblEtcListaPartecipanti.setText("Lista Partecipanti:");

        btnAddPartecipante.setText("Aggiungi Partecipante");
        btnAddPartecipante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPartecipanteActionPerformed(evt);
            }
        });

        btnRimuoviPartecipante.setText("Rimuovi Partecipante");
        btnRimuoviPartecipante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRimuoviPartecipanteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblEtcNomeGruppo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFldNomeGruppo))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnAddPartecipante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnRimuoviPartecipante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnCreaGruppo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblEtcListaPartecipanti)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblErrore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEtcNomeGruppo)
                    .addComponent(txtFldNomeGruppo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEtcListaPartecipanti)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAddPartecipante)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRimuoviPartecipante)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCreaGruppo))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrore, javax.swing.GroupLayout.DEFAULT_SIZE, 14, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreaGruppoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreaGruppoActionPerformed
        String nomeGruppo = txtFldNomeGruppo.getText().trim();
        if (!nomeGruppo.isEmpty()) {
            nuovoGruppo.setNomeConversazione(nomeGruppo);
            if (nuovoGruppo.getListaPartecipanti().size() > 0) {
                Account accountCreatoreGruppo = client.getAccount();
                client.addNuovoContatto(new Contatto(accountCreatoreGruppo.getIdAccount(), accountCreatoreGruppo.getUsername(), accountCreatoreGruppo.getCellulare(), accountCreatoreGruppo.getCellulare()));
                client.sendAddNuovoGruppo(nuovoGruppo);
                this.dispose();
            } else {
                lblErrore.setText("Devi inserire almeno un partecipante!");
            }
        } else {
            lblErrore.setText("Nome gruppo non può essere vuoto!");
        }
    }//GEN-LAST:event_btnCreaGruppoActionPerformed

    private void btnAddPartecipanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPartecipanteActionPerformed
        if (guiAPG == null) {
            guiAPG = new GuiAggiungiPartecipanteGruppo(client, this);
        } else {
            guiAPG.setVisible(true);
        }
    }//GEN-LAST:event_btnAddPartecipanteActionPerformed

    private void btnRimuoviPartecipanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRimuoviPartecipanteActionPerformed
          int index = listListaPartecipanti.getSelectedIndex();
          if(index>=0){
              nuovoGruppo.rimuoviPartecipante((Contatto)listListaPartecipanti.getSelectedValue());
              updateListaPartecipanti();
          }
    }//GEN-LAST:event_btnRimuoviPartecipanteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddPartecipante;
    private javax.swing.JButton btnCreaGruppo;
    private javax.swing.JButton btnRimuoviPartecipante;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblErrore;
    private javax.swing.JLabel lblEtcListaPartecipanti;
    private javax.swing.JLabel lblEtcNomeGruppo;
    private javax.swing.JList listListaPartecipanti;
    private javax.swing.JTextField txtFldNomeGruppo;
    // End of variables declaration//GEN-END:variables

     private void setLookAndFeel() {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GuiSignIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
