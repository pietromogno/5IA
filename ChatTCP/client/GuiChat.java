package client;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DebugGraphics;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.UnsupportedLookAndFeelException;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import oggettiperchat.Messaggio;
import oggettiperchat.Contatto;
import oggettiperchat.Conversazione;

/**
 *
 * @author Davide Porcu
 */
public class GuiChat extends javax.swing.JFrame {

    private Client client;
    private DefaultListModel listModelConversazioni;

    private StyledDocument doc;
    private SimpleAttributeSet left;
    private SimpleAttributeSet right;
    private SimpleDateFormat dateFormat;

    /**
     * Creates new form GuiSignIn
     */
    public GuiChat(Client client) {
        setLookAndFeel();
        initComponents();
        this.client = client;
        this.setTitle(client.getAccount().getUsername() + " - Client Chat TCP");
        dateFormat = new SimpleDateFormat("'Il' dd/MM/yyyy 'alle' HH:mm:ss ");
        resetCampi();
        listModelConversazioni = new DefaultListModel();

        listConversazioni.setCellRenderer(new MyListCellRenderer());
        listConversazioni.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
        jTextPane1.setEditable(false);
        doc = jTextPane1.getStyledDocument();
        left = new SimpleAttributeSet();
        StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
        StyleConstants.setForeground(left, Color.RED);

        right = new SimpleAttributeSet();
        StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
        StyleConstants.setForeground(right, Color.BLUE);
        jTextPane1.setText("");

    }

    public void resetCampi() {
        this.lblErrore.setText("");
    }

    public void update() {
        jTextPane1.setText("");
        updateListaConversazioni();
    }

    public void updateListaConversazioni() {
        listModelConversazioni.removeAllElements();
        ArrayList<Conversazione> listaConversazioni = client.getListaConversazioni();//richiedo lista
        for (Conversazione conversazione : listaConversazioni) {
            listModelConversazioni.addElement(conversazione);
        }
        listConversazioni.setModel(listModelConversazioni);
    }

    public void showMessaggiConversazione(Conversazione conversazione) {//scrivi messaggi delle conversazioni e vai a capo dopo un tot caratteri
        jTextPane1.setText("");
        lblConversazione.setText(conversazione.getNomeConversazione());
        conversazione.setHasMessaggiDaLeggere(false);
        //carica messaggi precedenti 
        int idClient = client.getAccount().getIdAccount();
        if (!conversazione.isGruppo()) {
            for (Messaggio msg : conversazione.getListaMessaggi()) {
                if (msg.getIdMittente() == idClient) {
                    showMessaggioInviato(msg);
                } else {
                    showMessaggioRicevuto(msg, false, null);
                }
            }
        } else {
            ArrayList<Contatto> contattiPartecipanti = conversazione.getListaPartecipanti();
            for (Messaggio msg : conversazione.getListaMessaggi()) {
                if (msg.getIdMittente() == idClient) {
                    showMessaggioInviato(msg);//se un gruppo scrivi nome mittente
                } else {
                    Contatto contatto = null;//esiste per forza altrimenti non avrei ricevuto il messaggio
                    for (int i = 0; i < contattiPartecipanti.size(); i++) {
                        contatto = contattiPartecipanti.get(i);
                        if (contatto.getIdAccount() == msg.getIdMittente()) {
                            break;
                        }
                    }
                    showMessaggioRicevuto(msg, true, contatto);
                }
            }
        }
    }

    public void setMessaggioErrore(String msg) {
        lblErrore.setText(msg);
    }

    public void showMessaggioInviato(Messaggio msgInUscita) {
        try {
            doc.setParagraphAttributes(doc.getLength(), 1, right, false);
            doc.insertString(doc.getLength(), dateFormat.format(msgInUscita.getDataInvio()) + "\n" + ((String) msgInUscita.getMessaggio()) + "\n", right);
        } catch (BadLocationException ex) {
            Logger.getLogger(GuiChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showMessaggioRicevuto(Messaggio msgInArrivo, boolean isGruppo, Contatto mittente) {//contatto mittente se serve altrimenti è null
        try {
            doc.setParagraphAttributes(doc.getLength(), 1, left, false);
            String messaggio;
            if (!isGruppo) {
                messaggio = (dateFormat.format(msgInArrivo.getDataInvio()) + "\n" + ((String) msgInArrivo.getMessaggio()));
            } else {
                messaggio = (mittente.getNomeContatto() + "\n" + dateFormat.format(msgInArrivo.getDataInvio()) + "\n" + ((String) msgInArrivo.getMessaggio()));
            }
            doc.insertString(doc.getLength(), messaggio + "\n", left);
        } catch (BadLocationException ex) {
            Logger.getLogger(GuiChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnInvioMessaggio = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        listConversazioni = new javax.swing.JList();
        lblConversazione = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        lblErrore = new javax.swing.JLabel();
        btnAddNuovaConversazione = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaInserisciMessaggio = new javax.swing.JTextArea();
        btnAddContatto = new javax.swing.JButton();
        lblEtcListaConversazioni = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblEtcConversazioneCon = new javax.swing.JLabel();
        btnCreaGruppo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        btnInvioMessaggio.setText("Invia");
        btnInvioMessaggio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvioMessaggioActionPerformed(evt);
            }
        });

        listConversazioni.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listConversazioni.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listConversazioniValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(listConversazioni);

        lblConversazione.setText("Seleziona una conversazione o creane una nuova");

        jTextPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextPane1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jScrollPane3.setViewportView(jTextPane1);

        btnAddNuovaConversazione.setText("Nuova Conversazione");
        btnAddNuovaConversazione.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNuovaConversazioneActionPerformed(evt);
            }
        });

        txtAreaInserisciMessaggio.setColumns(20);
        txtAreaInserisciMessaggio.setRows(5);
        jScrollPane1.setViewportView(txtAreaInserisciMessaggio);

        btnAddContatto.setText("Aggiungi Contatto");
        btnAddContatto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddContattoActionPerformed(evt);
            }
        });

        lblEtcListaConversazioni.setText("Lista Conversazioni");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        lblEtcConversazioneCon.setText("Conversazione con:");

        btnCreaGruppo.setText("Crea Gruppo");
        btnCreaGruppo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreaGruppoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblEtcListaConversazioni)
                            .addComponent(btnAddNuovaConversazione, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btnAddContatto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCreaGruppo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnInvioMessaggio))
                            .addComponent(lblConversazione, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEtcConversazioneCon))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(lblErrore, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(56, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblEtcConversazioneCon)
                                .addGap(4, 4, 4)
                                .addComponent(lblConversazione, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblEtcListaConversazioni)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2)
                                .addGap(9, 9, 9)
                                .addComponent(btnAddNuovaConversazione)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnInvioMessaggio, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnCreaGruppo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddContatto))))
                    .addComponent(jSeparator1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblErrore, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInvioMessaggioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvioMessaggioActionPerformed
        String messaggio = txtAreaInserisciMessaggio.getText().trim();
        if (!messaggio.isEmpty() && !client.isConessioneInterrotta() && client.isConversazioneScelta()) {
            client.sendMessaggioChat(messaggio);
            txtAreaInserisciMessaggio.setText("");
            setMessaggioErrore("");
        } else {
            setMessaggioErrore("Connessione al server interrotta!");
        }
    }//GEN-LAST:event_btnInvioMessaggioActionPerformed

    private void listConversazioniValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listConversazioniValueChanged
        int index = listConversazioni.getSelectedIndex();
        if (index >= 0) {
            Conversazione conversazione = client.getListaConversazioni().get(index);
            client.setConversazioneCorrente(conversazione);
        }

    }//GEN-LAST:event_listConversazioniValueChanged

    private void btnAddNuovaConversazioneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNuovaConversazioneActionPerformed
        if (!client.isConessioneInterrotta()) {
            GuiAggiungiConversazione guiAC = new GuiAggiungiConversazione(client);
            setMessaggioErrore("");
        } else {
            setMessaggioErrore("Connessione al server interrotta!");
        }
    }//GEN-LAST:event_btnAddNuovaConversazioneActionPerformed

    private void btnAddContattoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddContattoActionPerformed
        if (!client.isConessioneInterrotta()) {
            GuiAggiungiContatto guiAC = new GuiAggiungiContatto(client);
            setMessaggioErrore("");
        } else {
            setMessaggioErrore("Connessione al server interrotta!");
        }
    }//GEN-LAST:event_btnAddContattoActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        client.sendLogout();
    }//GEN-LAST:event_formWindowClosing

    private void btnCreaGruppoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreaGruppoActionPerformed
        GuiAggiungiGruppo guiAG = new GuiAggiungiGruppo(client);
    }//GEN-LAST:event_btnCreaGruppoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddContatto;
    private javax.swing.JButton btnAddNuovaConversazione;
    private javax.swing.JButton btnCreaGruppo;
    private javax.swing.JButton btnInvioMessaggio;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JLabel lblConversazione;
    private javax.swing.JLabel lblErrore;
    private javax.swing.JLabel lblEtcConversazioneCon;
    private javax.swing.JLabel lblEtcListaConversazioni;
    private javax.swing.JList listConversazioni;
    private javax.swing.JTextArea txtAreaInserisciMessaggio;
    // End of variables declaration//GEN-END:variables

    private void setLookAndFeel() {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GuiSignIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

@SuppressWarnings("serial")
class MyListCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component superRenderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        setBackground(null);
        setForeground(null);
        if (value != null) {
            Conversazione valConv = (Conversazione) value;
            if (!isSelected && valConv.hasMessaggiDaleggere()) {
                setBackground(new Color(152, 251, 152));
            } else if (isSelected) {
                setBackground(new Color(176, 224, 230));
            }

        }
        return superRenderer;
    }
}
