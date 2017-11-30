Favaro Matteo  5 IA   C.Zuccante



# Progetto chat con più utenti (con protocollo TCP)


il progetto è composto da un client e un server che comunicano tra loro permettendo la realizzazione di una chat tra più utenti

l'utente comunica con gli altri utenti attraverso una grafica dove può scegliere a chi mandare il messaggio selezionando il nome dell'utente.
 

### Server

il server è composto da tre classi :  `Server.java`  , `GestisciClient.java` e `SQLHelper.java`

`Server.java` : crea il ServerSocket che accetterà le connessioni in arrivo dal client e contiene i metodi per la gestione degli utenti attivi.

`GestisciClient.java` : permette la gestione del client a cui viene associato e la gestione dei messaggi da e verso esso. Il messaggio è una sequenza di stringhe che ne identificano il mittente, il destinatario, tipo messaggio e altre informazioni da trasmettere

`SQLHelper.java` : permette la connessione al database , e contiene le query per controllare se l'utente esiste già oppure no , se le credenziali sono esatte  e la creazione degli account


### Client


il client è composto da una classe e tre Jframe per implementare la grafica  :  `Client.java` , `GraficaChat.java`, `GraficaLogIn.java` e `GraficaRegistrazione.java`

`Client.java` : consente di creare la connessione con il server.  E gestisce le varie operazione che può compiere l'utente cioè :  inviare e ricevere un messaggio , registrarsi o loggarsi

`GraficaChat.java`, `GraficaLogIn.java` e `GraficaRegistrazione.java` : questi tre Jframe contengono il codice fatto con java swing per la creazione della grafica