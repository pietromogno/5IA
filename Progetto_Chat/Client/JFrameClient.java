package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @brief JFramecLIENT gestisce per la messagistica viene infatti avviato a
 * login/registrazione eseguita. Permette di inviare e ricevere messaggi.
 * @author Matteo
 */
public class JFrameClient extends javax.swing.JFrame {

    PrintWriter writer;
    BufferedReader r;

    /**
     * @param writer è il Server
     * @param r e il socket da cui leggere
     * @param nome è il nome del Clint che ha fatto l'accesso
     */
    public JFrameClient(PrintWriter writer, BufferedReader r, String nome) {
        this.r = r;
        this.writer = writer;
        initComponents();
        lblTitolo.setText(lblTitolo.getText() + "  " + nome);
        Ascoltatore a = new Ascoltatore();
        a.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitolo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAMessaggi = new javax.swing.JTextArea();
        txtNomeDest = new javax.swing.JLabel();
        lblMes = new javax.swing.JLabel();
        txtDestinatario = new javax.swing.JTextField();
        btnInvia = new javax.swing.JButton();
        txtMes = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitolo.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblTitolo.setText("CLIENT");

        txtAMessaggi.setColumns(20);
        txtAMessaggi.setRows(5);
        jScrollPane1.setViewportView(txtAMessaggi);

        txtNomeDest.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNomeDest.setText("Destinatario Messaggio:");

        lblMes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMes.setText("Messaggio:");

        btnInvia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnInvia.setText("INVIA");
        btnInvia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInviaActionPerformed(evt);
            }
        });

        txtMes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtNomeDest)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDestinatario))
                    .addComponent(txtMes)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblMes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnInvia))
                            .addComponent(lblTitolo, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitolo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNomeDest)
                    .addComponent(txtDestinatario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMes)
                    .addComponent(btnInvia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @brief è un action Performed che gestisce l'invio del messaggio al momento
     * che viene premuto invio
     * @param evt 
     */
    private void btnInviaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInviaActionPerformed
        String dest=txtDestinatario.getText().trim(), mes=txtMes.getText().trim();
        writer.println(dest);
        writer.println(mes);
        txtAMessaggi.setText(txtAMessaggi.getText() + "\n" + "Io a "+dest+": "+mes);
        txtDestinatario.setText("");
        txtMes.setText("");
    }//GEN-LAST:event_btnInviaActionPerformed


    private class Ascoltatore extends Thread {
        public void run() {
            try {
                while (true) {
                    String x=r.readLine();
                    txtAMessaggi.setText(txtAMessaggi.getText() + "\n" + x);
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInvia;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMes;
    private javax.swing.JLabel lblTitolo;
    private javax.swing.JTextArea txtAMessaggi;
    private javax.swing.JTextField txtDestinatario;
    private javax.swing.JTextField txtMes;
    private javax.swing.JLabel txtNomeDest;
    // End of variables declaration//GEN-END:variables
}
