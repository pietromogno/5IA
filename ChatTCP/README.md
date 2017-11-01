**Davide Porcu**

**Classe 5IA**

**ITIS "C. Zuccante"**

---

# Chat TCP in Java
Il progetto `Chat TCP` consiste nel realizzare una chat in cui più utenti possono comunicare tra loro, direttamente o in gruppi, inviando dei messaggi

Gli utenti per acceder al "servizio" dovranno effettuare il login con username e password o se non ancora registrati, dovranno registrarsi utilizzando username, password e numero di cellulare.

Ciascun utente possiederà le proprie conversazioni e ciascuna conversazione . Le conversazioni sono di due tipi: 
- `Utente vs Utente`
- `Gruppo`
La conversazione `Utente vs Utente` consiste nello scambio di messaggi tra due account. I messaggi scambiati tra loro saranno visibili solo a loro. 
La conversazione `Gruppo` consiste nello scambio di messaggi tra più account appartenenti al gruppo. I messaggi inviati saranno visibili solo ai membri del gruppo.

I messaggi di una conversazione verranno rappresentati in un riquadro organizzandoli in questo modo: 
- `Messaggi in USCITA`: verranno rappresentati in blu e sulla destra.
- `Messaggi in INGRESSO`: verranno rappresentati in rosso e sulla sinistra 
 
`NOTA BENE`:
- Per ciascun messaggio verrà riportato anche l'istante (data e ora/minuto/secondo) in cui è stato inviato
- Per i messaggi dei gruppi sarà riportato anche il nome del mittente (se presente nella lista contatti dell'utente sarà il nome che l'utente gli ha assegnato altrimenti il numero di cellulare).

Ciascun messaggio inviato da un client passerà per il server, il quale determinerà il destinatario o destinatari. Tutti i messaggi delle conversazioni inviati al server vengono memorizzati in modo da poter riproporre le conversazioni agli utenti che accedono e di poter agli utenti ricevere messaggi anche quando non sono online.

Per gli utenti online viene anche notificato l'arrivo di un messaggio attraverso il cambiamento di sfondo del nome della conversazione.

Gli utenti possono aggiungere i propri contatti associando al numero di cellulare un nome (nickname, nomignolo, quel cavolo che voglio). E' necessario però che il cellulare sia associato a un account di un utente già registrato al servizio di messaggistica.

Dunque gli utenti possono ricevere messaggi da chiunque, anche da chi non è loro contatto (è sufficiente che chi ha inviato il messaggio vi abbia come contatto).

Gli utenti potranno creare i loro gruppi assegnando un nome del gruppo e aggiungendo i partecipanti scelti tra i loro contatti(nella fase di creazione).

Il progetto viene quindi a comporsi in tre parti : `Client` , `Server` e  `Classi di Oggetti utili alla chat` che conterranno i file utili al corretto funzionamento.


---

## Client
Il client è composto da diverse classi che sono:


- `Client`:
rappresenta un utente che accede al servizio di messaggistica e come tale possiede la propria lista di conversazioni, di contatti. 
La classe permette di creare la connessione con il server e di scambiare con esso messaggi. La classe si appoggia ad alla classe `Reader` per poter ricevere  e contemporaneamente inviare messaggi.


- `Reader`: è la classe (che implementa Runnable) che permette di gestire la ricezione dei messaggi e la loro interpretazione. Infatti a ciascun tipo di messaggio ricevuto corrisponderà un' azione differente da intraprendere.
- `GuiSignIn`:è la classe che implementa la grafica relativa alla fase di login. Essa offre due _textField_ per username e password. Se Username e password non sono valide, l'errore che il client riceverà verrà mostrato nella _label_ per gli errori.
- `GuiSignUp`: è la classe che implementa la grafica relativa alla fase di registrazione. Essa offre tre _textField_ per username, password e cellulare. Se i dati inseriti non sono validi gli errori verranno mostrati nella _label_ per gli errori.
- `GuiChat.java`: è la classe che implementa la grafica relativa alla chat. Essa è composta da:
	- una _jList_ per l'elenco delle conversazioni dell'utente
	- un _jTextPane_ per mostrare i messaggi della conversazione scelta
	- una _textArea_ per scrivere il messaggio da inviare
	- 4 _button_ per invaire il messaggio, creare una nuova conversazione, creare un nuovo gruppo, aggiungere un contatto
	- vari _label_ per facilitare la comprensione dell'interfaccia e mostrare i messaggi di errore e il nome della conversazione rappresentata
- `GuiAggiungiContatto`:è la classe che implementa la grafica relativa all'inserimento di un nuovo contatto alla lista dei contatti dell'utente.
- `GuiAggiungiConversazione`:è la classe che implementa la grafica relativa alla creazione di una nuova conversazione con uno dei contatti che l'utente possiede (se la conversazione è già esistente la mostra).
- `GuiAggiungiGruppo`:è la classe che implementa la grafica relativa alla creazione di un nuovo gruppo. essa è costituita da una _textField_ per il nome del gruppo, un _jList_ che rappresenta i partecipanti del gruppo. Sono presenti tre _button_ che permettono l'aggiunta di un partecipante alla conversazione scelto tra i contatti dell'utente, la rimozione del partecipante selezionato nella lista partecipanti e la creazione del gruppo.
- `GuiAggiungiPartecipanteGruppo`:è la classe che implementa la grafica relativa alla scelta del contatto da aggiungere come partecipante del gruppo.


---

## Server
Il server è costituito da quattro classi che sono:

- `Server.java`: permette di creare l'oggetto ServerSocket che verrà utilizzato per accettare le connessioni in arrivo a una determinata porta. Ciascuna connessione in arrivo verrà accettata e creato un Task (che implementa Runnable) che gestirà la comunicazione con quel determinato Client. Per poter garantire a più client il servizio contemporaneamente viene utilizzata la tecnica del thread pooling utilizzando Task e ExecutorService.
Questa classe tiene tracci dei vari client che hanno eseguito il login e che sono quindi attivi.
Possiede metodi che consentono l'aggiunta, la restituizione e la rimozione dei task relativi ai client.

- `ManageClientTask.java`: è la classe Runnable che gestisce la comunicazione con un determinato client. Per ciascun client esisterà un task che ne gestirà la comunicazione; in questo modo i Task verranno eseguiti da diversi thread (auto bilanciamento con CachedThreadPool) in modo concorrente.
Questa classe permette di gestire i messaggi in arrivo e di intraprendere azioni a seconda del tipo di messaggio. Possiede un metodo _synchronized_ che permette la trasmissione dei messaggi attraverso lo stream _ObjectOutpuStream_ verso il client. (utilizzo _synchronized_ perchè lo stream deve essere impegnato solo da un utilizzatore per volta -> altrimenti errore).
	
	`NOTA BENE`:
	- Vengono utilizzati gli ObjectInputStream e ObjectOutputStream per poter inviare oggetti serializzati attraverso gli stream. Questo perché il messaggio non è una semplice stringa ma un oggetto più complesso che possiede vari attributi utili all'identificazione, gestione e invio dello stesso.
	
	Questa classe si appoggia alle classi `SQLHelper` e `Util`.

- `SQLHelper`: è la classe di supporto per la gestione e manipolazione dei dati memorizzati sul database (ChatTCP.db).
	Contiene vari metodi per l'inserimento di nuovi account, salvataggio dei messaggi, recupero delle conversazioni e contatti e molto altro ancora.
-  `Util`: è la classe di supporto per la verifica della validità dei dati da inserire nel database. La verifica viene effettuata attraverso l'uso di espressioni regolari. 

---

### Classi utili alla chat

Sono state realizzate altre quattro classi al fine di ottenere un progetto soddisfacente.

Le quattro classi sono:
- `Account`
- `Contatto`
- `Conversazione`
- `Messaggio`

e hanno agevolato la strutturazione del progetto.

Ciascun utente è identificato da un account e possiede diverse conversazioni (quindi una lista di conversazioni) e conosce diverse persone (quindo una lista di suoi contatti). Una conversazione esiste solo se ci sono persone (quindi una lista di partecipanti o meglio una lista di contatti) che comunicano scambiandosi messaggi (quindi una lista di messaggi)

La classe `Account` permette di rappresentare un account di un utente, con il proprio username, password, cellulare e identificativo utente (idAccount).

La classe `Contatto` permette di rappresentare un contatto di un utente. Ciascun contatto appartiene all'utente che lo ha aggiunto. Infatti l'utente può decidere come chiamare il contatto durante la fase di aggiunta dello stesso.
Il contatto, però, può essere creato solo se l'account a cui corrisponde il numero di cellulare esiste (cellulare del contatto uguale a un cellulare di un account registrato).

La classe `Conversazione` permette di rappresentare una conversazione tra due o più utenti. La conversazione possiede quindi una lista di partecipanti. Nelle conversazioni di gruppo questa lista contiene al suo interno tutti i contatti che comunicano nel gruppo, mentre nelle conversazioni utente-utente la lista conterrà solo l'altro utente (cioè l'unico destinatario).
La conversazione possiede una lista di messaggi che sono stati scambiati tra i partecipanti.
La conversazione può essere distinta tra conversazione utente-utente e conversazione di gruppo.


La classe `Messaggio` permette di rappresentare un messaggio inviato. L'oggetto messaggio viene usato sia per messaggi "di servizio" tra client e server, sia per messaggi "ufficiali" tra client e client (passando sempre per il server).
Il messaggio permette di sapere chi è il mittente e chi il destinatario, e di conoscere l'istante di invio. La classe messaggio è pensata per poter consegnare qualunque oggetto al destinatario. 
(potenzialmente con leggere modifiche al client e server nella ricezione e invio di messaggi e nel salvataggio dei messaggi su database (tipo del campo BLOB) sarebbe possibile inviare qualunque tipo di informazione (immagini, video, documenti di testo,qualunque file))

---

### Database

Il database `ChatTCP.db` è costituito da 5 tabelle:
- `ACCOUNT`: tabella che contiene le informazioni riguardanti gli account (username, password, cellulare)
- `CONTATTO`: tabella che contiene le informazioni riguardanti i contatti di ciascun account. (con il nome che l'utente ha assegnato al contatto)
- `GRUPPO`: tabella che contiene le informazioni del gruppo (cioè il nome del gruppo)
- `MEMBROGRUPPO`: tabella che contiene i partecipanti ciascun gruppo attraverso l'uso della chiave del record corrispondente al gruppo e della chiave corrispondente all'account
- `MESSAGGIO`: tabella che contiene tutti i messaggi che vengono scambiati tra gli utenti nelle varie conversazioni.

`NOTA BENE`: per poter utilizzare il programma sarà necessario cambiare il percorso del database all'interno di _SQLHelper.java_



