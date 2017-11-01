package server;

import oggettiperchat.Messaggio;
import oggettiperchat.Account;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import oggettiperchat.Contatto;
import oggettiperchat.Conversazione;

/**
 * @author Davide Porcu
 */
public class ManageClientTask implements Runnable {

    protected int idClient;//preso dal database
    private Server server;
    private Socket connessione;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ManageClientTask(Server server, Socket connessione) {
        this.connessione = connessione;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            System.out.println((char) 27 + "[32mCONNESSO Client ");

            in = new ObjectInputStream(connessione.getInputStream());
            out = new ObjectOutputStream(connessione.getOutputStream());

            while (true) {
                Messaggio msgRicevuto = (Messaggio) in.readObject();
                switch (msgRicevuto.getTipoMessaggio()) {
                    case Messaggio.LOGIN:
                        Account daVerificare = (Account) (msgRicevuto.getMessaggio());
                        String usernameLogin = daVerificare.getUsername();
                        String passwordLogin = daVerificare.getPassword();

                        boolean isValidLogin = SQLHelper.isLoginCorretto(passwordLogin, usernameLogin);
                        Messaggio msgLogin = new Messaggio();
                        if (isValidLogin) {
                            Account accountUtenteLogin = SQLHelper.getAccount(usernameLogin, passwordLogin);
                            this.idClient = accountUtenteLogin.getIdAccount();
                            if (!server.isClientGiaInEsecuzione(idClient)) {
                                msgLogin.setTipoMessaggio(Messaggio.LOGIN);
                                msgLogin.setMessaggio(accountUtenteLogin);
                                sendMessage(msgLogin);//account
                                sendMessage(new Messaggio(Messaggio.LOGIN, SQLHelper.getListaConversazioniAccount(idClient), -1, idClient, -1, new Date()));//conversazioni
                                sendMessage(new Messaggio(Messaggio.LOGIN, SQLHelper.getListaContatti(idClient), -1, idClient, -1, new Date()));//contatti
                                server.aggiungiClient(this);
                            } else {
                                msgLogin.setTipoMessaggio(Messaggio.ERRORE);
                                msgLogin.setMessaggio("Ammesso l'accesso da un solo dispositivo per volta");
                                sendMessage(msgLogin);
                            }
                        } else {
                            msgLogin.setTipoMessaggio(Messaggio.ERRORE);
                            msgLogin.setMessaggio("Credenziali di accesso errate! Controlla username e password!");
                            sendMessage(msgLogin);
                        }
                        break;
                    case Messaggio.REGISTRAZIONE:
                        Account daInserire = (Account) (msgRicevuto.getMessaggio());
                        String usernameSignIn = daInserire.getUsername();
                        String passwordSignIn = daInserire.getPassword();
                        String cellulareSignIn = daInserire.getCellulare();
                        String errori = Util.validateAccount(usernameSignIn, passwordSignIn, cellulareSignIn);
                        Messaggio msgSignIn = new Messaggio();
                        if (errori.isEmpty()) {
                            SQLHelper.inserisciAccount(usernameSignIn, passwordSignIn, cellulareSignIn);
                            msgSignIn.setTipoMessaggio(Messaggio.REGISTRAZIONE);
                            msgSignIn.setMessaggio("Inserito utente! Fai il login!");
                        } else {
                            msgSignIn.setTipoMessaggio(Messaggio.ERRORE);
                            msgSignIn.setMessaggio(errori);
                        }
                        sendMessage(msgSignIn);
                        break;
                    case Messaggio.LOGOUT:
                        server.rimuoviClient(this);
                        connessione.close();
                        break;
                    case Messaggio.MESSAGGIO:
                        ArrayList<Integer> listaDestinatari = new ArrayList<>();
                        if (msgRicevuto.getIdGruppo() == -1) {
                            listaDestinatari.add(msgRicevuto.getIdDestinatario());
                        } else {
                            listaDestinatari = SQLHelper.getIdAccountPartecipantiGruppo(msgRicevuto.getIdGruppo());
                        }
                        SQLHelper.addMessaggio(msgRicevuto);//salvo messaggio nel database
                        for (int idDestinatario : listaDestinatari) {
                            if (idDestinatario != idClient) {//non mi mando la roba da solo ;)
                                ManageClientTask destinatario = server.getDestinatario(idDestinatario);
                                if (destinatario != null) {
                                    try {
                                        destinatario.sendMessage(msgRicevuto);
                                    } catch (IOException ex) {
                                    }
                                }
                            }
                        }

                        break;
                    case Messaggio.ADDCONTATTO://client invia la sua nuova lista contatti aggiornata
                        Contatto possibileContatto = (Contatto) msgRicevuto.getMessaggio();
                        String cellulare = possibileContatto.getCellulare();
                        String erroriTelefono = Util.isTelefonoValido(cellulare);
                        if (erroriTelefono.isEmpty()) {//se cellulare è un numero valido
                            int idAccountCorrispondente = SQLHelper.getIdAccountFromCellulare(cellulare);
                            if (idAccountCorrispondente == -1) {//numero cellulare non corrisponde a nessun account
                                sendMessage(new Messaggio(Messaggio.ERRORE, "Il cellulare non corrisponde a nessun account registrato!", -1, -1, -1, new Date()));
                            } else if (idAccountCorrispondente != idClient) {
                                if (SQLHelper.isGiaContattoDiAccount(idClient,idAccountCorrispondente)) {//invio contatto a utente perchè lo aggiunga
                                    SQLHelper.addNuovoContattoAUtente(this.idClient, idAccountCorrispondente, possibileContatto.getNomeContatto());
                                    Contatto nuovoContatto = SQLHelper.generaContattoDiAccount(idAccountCorrispondente);
                                    nuovoContatto.setNomeContatto(possibileContatto.getNomeContatto());
                                    sendMessage(new Messaggio(Messaggio.ADDCONTATTO, nuovoContatto, -1, idClient, -1, new Date()));
                                }else{
                                     sendMessage(new Messaggio(Messaggio.ERRORE, "Contatto in rubrica già esistente!", -1, -1, -1, new Date()));
                                }
                            } else {
                                sendMessage(new Messaggio(Messaggio.ERRORE, "Non puoi aggiungere il tuo cellulare come contatto!", -1, -1, -1, new Date()));
                            }
                        } else {//cellulare non valido (Devono eserci solo numeri)
                            sendMessage(new Messaggio(Messaggio.ERRORE, erroriTelefono, -1, -1, -1, new Date()));
                        }
                        break;

                    case Messaggio.ADDGRUPPO:
                        Conversazione possibileConversazionegruppo = (Conversazione) msgRicevuto.getMessaggio();

                        int idGruppoConversazione = SQLHelper.addGruppo(possibileConversazionegruppo);
                        possibileConversazionegruppo.setIdDestinatario(idGruppoConversazione);

                        ArrayList<Contatto> listaPartecipanti = possibileConversazionegruppo.getListaPartecipanti();
                        for (Contatto contatto : listaPartecipanti) {
                            ManageClientTask destinatarioAddGruppo = server.getDestinatario(contatto.getIdAccount());//notifico tutti i partecipanti
                            if (destinatarioAddGruppo != null) {
                                try {
                                    possibileConversazionegruppo.setListaPartecipanti(SQLHelper.adattaContattiADestinatario(destinatarioAddGruppo.idClient, listaPartecipanti));
                                    destinatarioAddGruppo.sendMessage(new Messaggio(Messaggio.ADDGRUPPO, possibileConversazionegruppo, -1, -1, -1, null));//sono essenziali tipo mesaggio e contenuto messaggio
                                } catch (IOException ex) {
                                    //System.out.println("Destinatario non raggiungibile...salvo in database messaggio");
                                }
                            }
                        }
                        break;
                }
            }
        } catch (IOException e) {
            //System.out.println("Errore nel gestire il client ");
        } catch (ClassNotFoundException ex) {
            //Logger.getLogger(ManageClientTask.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connessione.close();
                server.rimuoviClient(this);
                System.out.println((char) 27 + "[34mDISCONNESSO Client " + idClient + " !\"");
            } catch (IOException e) {
                System.out.println("Impossibile chiudere il socket");
            }
        }
    }

    public synchronized void sendMessage(Messaggio msg) throws IOException {
        out.writeObject(msg);
    }
}
