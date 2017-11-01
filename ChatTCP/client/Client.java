package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import oggettiperchat.Account;
import oggettiperchat.Messaggio;
import oggettiperchat.Contatto;
import oggettiperchat.Conversazione;

/**
 *
 * @author Davide Porcu
 */
public class Client {

    private Account account;
    private ArrayList<Contatto> listaContatti;
    private Conversazione conversazioneCorrente;
    private ArrayList<Conversazione> listaConversazioni;

    private String indirizzoServer;
    private int porta;
    private Socket connessione;
    private ObjectOutputStream out;

    protected GuiSignIn guiSignIn;
    protected GuiSignUp guiSignUp;
    protected GuiChat guiChat;

    public Client(String indirizzo, int porta) {
        this.indirizzoServer = indirizzo;
        this.porta = porta;
        account = new Account(-1, "", "", "");//account iniziale in attesa di login
        guiSignIn = new GuiSignIn(this);
        connectToServer();
    }

    public ArrayList<Contatto> getListaContatti() {
        return this.listaContatti;
    }

    public Account getAccount() {
        return this.account;
    }

    public ArrayList<Conversazione> getListaConversazioni() {
        return this.listaConversazioni;
    }

    public void setListaContatti(ArrayList<Contatto> listaContatti) {
        this.listaContatti = listaContatti;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setConversazioneCorrente(Conversazione conversazione) {
        this.conversazioneCorrente = conversazione;
        guiChat.showMessaggiConversazione(conversazioneCorrente);
    }

    public void setListaConversazioni(ArrayList<Conversazione> listaConversazioni) {
        this.listaConversazioni = listaConversazioni;
    }

    /*###########*/
    private void connectToServer() {
        try {
            connessione = new Socket(indirizzoServer, porta);
            out = new ObjectOutputStream(connessione.getOutputStream());
            Reader r = new Reader(this, connessione);
            new Thread(r).start();//non ha senso creare un Executor per il pooling quando c'è solo un task...
            //System.out.println("reader in ascolto");
        } catch (IOException ex) {
            showMessaggioErrore("Impossbile collegarsi al server!");
        }
    }

    public void sendMessaggio(Messaggio messaggio) {
        try {
            out.writeObject(messaggio);
        } catch (IOException ex) {
            showMessaggioErrore("Connessione Interrotta! Impossbile inviare il messaggio");
        }
    }

    public void sendLogin(String username, String password) {
        if (!this.isConessioneInterrotta()) {
            Account login = new Account();
            login.setUsername(username);
            login.setPassword(password);
            Messaggio msg = new Messaggio();
            msg.setTipoMessaggio(Messaggio.LOGIN);
            msg.setMessaggio(login);
            msg.setIdMittente(account.getIdAccount());
            sendMessaggio(msg);
        } else {
            this.showMessaggioErrore("Connessione al server interrotta!");
        }
    }

    public void sendRegistrazione(String username, String password, String cellulare) {
        if (!this.isConessioneInterrotta()) {
            Account registrazione = new Account();
            registrazione.setUsername(username);
            registrazione.setPassword(password);
            registrazione.setCellulare(cellulare);
            Messaggio msg = new Messaggio();
            msg.setTipoMessaggio(Messaggio.REGISTRAZIONE);
            msg.setMessaggio(registrazione);
            msg.setIdMittente(account.getIdAccount());
            sendMessaggio(msg);
        } else {
            this.showMessaggioErrore("Connessione al server interrotta!");
        }
    }

    public void sendMessaggioChat(String messaggio) {//aggiungo il messagio che invio alla conversazione corrente... così non serve che ogni volta faccia l'aggiornamento della conversazione da server
        if (!this.isConessioneInterrotta()) {
            Messaggio msg = new Messaggio();
            msg.setMessaggio(messaggio);
            msg.setTipoMessaggio(Messaggio.MESSAGGIO);
            if (!listaConversazioni.contains(conversazioneCorrente)) {//se la conversazione corrente è nuova, la aggiungo solo se si fanno messaggi
                listaConversazioni.add(conversazioneCorrente);
                guiChat.updateListaConversazioni();
            }
            if (conversazioneCorrente.isGruppo()) {
                msg.setIdDestinatario(-1);
                msg.setIdgruppo(conversazioneCorrente.getIdDestinatario());
            } else {
                msg.setIdDestinatario(conversazioneCorrente.getIdDestinatario());
                msg.setIdgruppo(-1);
            }
            msg.setIdMittente(account.getIdAccount());
            sendMessaggio(msg);
            conversazioneCorrente.addMessaggio(msg);
            listaConversazioni.remove(conversazioneCorrente);
            listaConversazioni.add(0, conversazioneCorrente);
            guiChat.updateListaConversazioni();
            guiChat.showMessaggioInviato(msg);
        } else {
            this.showMessaggioErrore("Connessione al server interrotta!");
        }
    }

    public void sendAddNuovoContatto(String cellulare, String nomeContatto) {
        if (!this.isConessioneInterrotta()) {
            Messaggio msg = new Messaggio();
            msg.setTipoMessaggio(Messaggio.ADDCONTATTO);
            msg.setIdMittente(account.getIdAccount());
            Contatto contatto = new Contatto();
            contatto.setCellulare(cellulare);
            contatto.setNomeContatto(nomeContatto);
            msg.setMessaggio(contatto);
            sendMessaggio(msg);
        } else {
            this.showMessaggioErrore("Connessione al server interrotta!");
        }
    }

    public void addNuovaConversazione(Contatto contatto) {
        if (!this.isConessioneInterrotta()) {
            int idAccountDestinatario = contatto.getIdAccount();
            for (Conversazione conversazione : listaConversazioni) {
                if (!conversazione.isGruppo() && idAccountDestinatario == conversazione.getIdDestinatario()) {//solo se non sono gruppi
                    setConversazioneCorrente(conversazione);
                    return;//usciamo subito che risparmiamo tempo
                }
            }
            //se non siamo usciti vuol dire che esiste una nuova conversazione
            ArrayList<Contatto> partecipanti = new ArrayList<>();
            partecipanti.add(contatto);
            Conversazione conversazione = new Conversazione(contatto.getNomeContatto(), idAccountDestinatario, partecipanti, new ArrayList<>(), false, false);
            setConversazioneCorrente(conversazione);
        } else {
            this.showMessaggioErrore("Connessione al server interrotta!");
        }
    }

    public void sendAddNuovoGruppo(Conversazione nuovoGruppo) {
        if (!this.isConessioneInterrotta()) {
            Messaggio msg = new Messaggio();
            msg.setTipoMessaggio(Messaggio.ADDGRUPPO);
            msg.setMessaggio(nuovoGruppo);
            sendMessaggio(msg);
        } else {
            this.showMessaggioErrore("Connessione al server interrotta!");
        }
    }

    public void sendLogout() {
        Messaggio msg = new Messaggio();
        msg.setTipoMessaggio(Messaggio.LOGOUT);//importante è solo il flag
        sendMessaggio(msg);
    }

    public boolean isConversazioneScelta() {
        return conversazioneCorrente != null;
    }

    public boolean isConessioneInterrotta() {
        return connessione == null || connessione.isClosed();
    }

    public void addNuovoContatto(Contatto nuovoContatto) {
        listaContatti.add(nuovoContatto);
        for (int i = 0; i < listaConversazioni.size(); i++) {
            Conversazione conversazione = listaConversazioni.get(i);
            ArrayList<Contatto> listaPartecipanti = conversazione.getListaPartecipanti();
            for (int j = 0; j < listaPartecipanti.size(); j++) {
                Contatto partecipante = listaPartecipanti.get(j);
                if (partecipante.getIdAccount() == nuovoContatto.getIdAccount()) {
                    partecipante.setNomeContatto(nuovoContatto.getNomeContatto());
                    if (!conversazione.isGruppo()) {
                        conversazione.setNomeConversazione(nuovoContatto.getNomeContatto());
                    }
                }
            }
        }
        guiChat.updateListaConversazioni();
        if (conversazioneCorrente != null) {
            guiChat.showMessaggiConversazione(conversazioneCorrente);
        }
    }

    public void addNuovaConversazioneGruppo(Conversazione conversazioneGruppo) {
        this.listaConversazioni.add(conversazioneGruppo);
        guiChat.updateListaConversazioni();
    }

    public void addMessaggioRicevuto(Messaggio msgRicevuto) {
        if (msgRicevuto.getIdGruppo() == -1) {//è un messaggio tra conversazione 1vs1
            boolean isMessaggioAggiunto = false;
            for (int i = 0; i < listaConversazioni.size() && !isMessaggioAggiunto; i++) {
                Conversazione conversazione = listaConversazioni.get(i);
                if (!conversazione.isGruppo() && conversazione.getIdDestinatario() == msgRicevuto.getIdMittente()) {
                    conversazione.addMessaggio(msgRicevuto);
                    listaConversazioni.remove(conversazione);
                    conversazione.setHasMessaggiDaLeggere(true);
                    listaConversazioni.add(0, conversazione);
                    isMessaggioAggiunto = true;
                }
            }
            if (!isMessaggioAggiunto) {//se non è stato ancora aggiunto vuol dire che si tratta di una nuova conversazione
                Conversazione nuovaConversazione = new Conversazione();
                int indexContatto = listaContatti.indexOf(msgRicevuto.getIdMittente());
                Contatto mittenteMsg;
                if (indexContatto >= 0) {//ho il mittente della nuova conversazione in lista
                    mittenteMsg = listaContatti.get(indexContatto);
                } else {
                    mittenteMsg = new Contatto(msgRicevuto.getIdMittente(), "Sconosciuto", "Sconosciuto", "Sconosciuto");
                }
                nuovaConversazione.setIdDestinatario(msgRicevuto.getIdMittente());
                nuovaConversazione.setHasMessaggiDaLeggere(true);
                nuovaConversazione.addMessaggio(msgRicevuto);
                nuovaConversazione.addPartecipante(mittenteMsg);
                nuovaConversazione.setNomeConversazione(mittenteMsg.getNomeContatto());
                listaConversazioni.add(0, nuovaConversazione);//aggiungo in testa alla lista
            }
        } else {
            boolean isMessaggioAggiunto = false;
            for (int i = 0; i < listaConversazioni.size() && !isMessaggioAggiunto; i++) {
                Conversazione conversazione = listaConversazioni.get(i);
                if (conversazione.isGruppo() && conversazione.getIdDestinatario() == msgRicevuto.getIdGruppo()) {
                    conversazione.addMessaggio(msgRicevuto);
                    listaConversazioni.remove(conversazione);
                    conversazione.setHasMessaggiDaLeggere(true);
                    listaConversazioni.add(0, conversazione);
                    isMessaggioAggiunto = true;
                }
            }
        }
        if (conversazioneCorrente != null && (conversazioneCorrente.getIdDestinatario() == msgRicevuto.getIdMittente() || (conversazioneCorrente.isGruppo() && conversazioneCorrente.getIdDestinatario() == msgRicevuto.getIdGruppo()))) {
            ArrayList<Contatto> contattiPartecipanti = conversazioneCorrente.getListaPartecipanti();
            Contatto contatto = null;//esiste per forza altrimenti non avrei ricevuto il messaggio
            for (int i = 0; i < contattiPartecipanti.size(); i++) {
                contatto = contattiPartecipanti.get(i);
                if (contatto.getIdAccount() == msgRicevuto.getIdMittente()) {
                    break;
                }
            }
            guiChat.showMessaggioRicevuto(msgRicevuto, conversazioneCorrente.isGruppo(), contatto);
        }
        guiChat.updateListaConversazioni();

    }

    /*######################### */
    public void showGuiRegistrazione() {
        if (guiSignIn != null) {
            guiSignIn.resetCampi();
            guiSignIn.setVisible(false);
        }
        if (guiChat != null) {
            guiChat.resetCampi();
            guiChat.setVisible(false);
        }
        if (guiSignUp == null) {
            guiSignUp = new GuiSignUp(this);
        }
        guiSignUp.setMessaggioErrore("");
        guiSignUp.setVisible(true);
    }

    public void showGuiLogin() {
        if (guiChat != null) {
            guiChat.resetCampi();
            guiChat.setVisible(false);
        }
        if (guiSignUp != null) {
            guiSignUp.resetCampi();
            guiSignUp.setVisible(false);
        }
        if (guiSignIn == null) {
            guiSignIn = new GuiSignIn(this);
        }
        guiSignIn.setMessaggioErrore("");
        guiSignIn.setVisible(true);
    }

    public void showGuiChat() {
        if (guiSignUp != null) {
            guiSignUp.dispose();//tanto non servirà più
            guiSignUp = null;
        }
        if (guiSignIn != null) {
            guiSignIn.dispose();
            guiSignIn = null;
        }
        if (guiChat == null) {
            guiChat = new GuiChat(this);
        }
        guiChat.setMessaggioErrore("");
        guiChat.update();
        guiChat.setVisible(true);
    }

    public void hideAllGui() {
        if (guiSignUp != null) {
            guiSignUp.setVisible(false);
        }
        if (guiSignIn != null) {
            guiSignIn.setVisible(false);
        }
        if (guiChat != null) {
            guiChat.setVisible(false);
        }
    }

    public void showMessaggioErrore(String msgErrore) {
        if (guiSignUp != null && guiSignUp.isVisible()) {
            guiSignUp.setMessaggioErrore(msgErrore);
        } else if (guiSignIn != null && guiSignIn.isVisible()) {
            guiSignIn.setMessaggioErrore(msgErrore);
        } else if (guiChat != null && guiChat.isVisible()) {
            guiChat.setMessaggioErrore(msgErrore);
        }
    }

    /*#################################*/
    public static void main(String[] args) {
        String serverAddress = "localhost"; // server string
        int porta = 9999;
        Client c = new Client(serverAddress, porta);

    }
}

class Reader implements Runnable {

    private Client client;
    private Socket connessione;
    private ObjectInputStream input;

    public Reader(Client client, Socket connessione) {
        this.connessione = connessione;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            input = new ObjectInputStream(connessione.getInputStream());
            while (true) {
                Messaggio msgRicevuto = (Messaggio) input.readObject();
                switch (msgRicevuto.getTipoMessaggio()) {
                    case Messaggio.LOGIN:
                        client.setAccount((Account) msgRicevuto.getMessaggio());
                        Messaggio msgListaConversazioni = (Messaggio) input.readObject();
                        client.setListaConversazioni((ArrayList<Conversazione>) msgListaConversazioni.getMessaggio());
                        Messaggio msgListaContatti = (Messaggio) input.readObject();
                        client.setListaContatti((ArrayList<Contatto>) msgListaContatti.getMessaggio());
                        client.showGuiChat();
                        break;
                    case Messaggio.REGISTRAZIONE:
                        client.showGuiLogin();
                        client.showMessaggioErrore((String) msgRicevuto.getMessaggio());
                        break;
                    case Messaggio.LOGOUT:// invia messaggio di saluto e elimina client da lista client attivi e chiudi connessione
                        client.hideAllGui();
                        connessione.close();
                        break;
                    case Messaggio.ERRORE:
                        client.showMessaggioErrore(msgRicevuto.getMessaggio().toString());
                        break;
                    case Messaggio.MESSAGGIO://invia messaggio a destinatario corretto
                        client.addMessaggioRicevuto(msgRicevuto);
                        break;
                    case Messaggio.UPDATELISTACONTATTI://get la lista dei contatti aggiornata
                        client.setListaContatti((ArrayList<Contatto>) msgRicevuto.getMessaggio());
                        break;
                    case Messaggio.ADDCONTATTO:
                        Object contenutoMsgAddContatto = msgRicevuto.getMessaggio();
                        Contatto nuovoContatto = (Contatto) contenutoMsgAddContatto;
                        client.addNuovoContatto(nuovoContatto);
                        break;
                    case Messaggio.ADDGRUPPO:
                        Conversazione conversazioneGruppo = (Conversazione) msgRicevuto.getMessaggio();
                        client.addNuovaConversazioneGruppo(conversazioneGruppo);
                        break;
                }
            }

        } catch (IOException e) {
            //System.out.println("Errore nel gestire il client "+cliente.getNumeroCliente());
        } catch (ClassNotFoundException ex) {
            //Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connessione.close();
                client.showMessaggioErrore("Connessione server interrotta!");
            } catch (IOException e) {

            }
        }

    }
}
