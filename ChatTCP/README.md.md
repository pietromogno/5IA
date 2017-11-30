Musone Mattia

AGGIORNAMENTO LAVORO

L'idea è di creare una piattaforma raggiungibile a più persone possibili, quindi sono state realizzate due applicazioni, una su piattaforma Android e una su piattaforma Java eseguibile da PC. Il database è connesso al Server per una maggiore sicurezza, quindi la mancata connessione ad esso comporta ai client il non accesso alla piattaforma e la non visualizzazione delle chat.

- APPLICAZIONE SU PC

-CLIENT:

E' stata utilizzata Java Swing per creare l'interfaccia di tutte le classi.

Le classi sono 4:

1) Accesso: classe principale in cui l'utente può effetture il log-in. 
2) Registrazione: classe da cui si può accedere da Accesso nella quale l'utente può registrarsi.
3) Client: Classe nella quale avvengono le principali funzioni e permette all'utente di messaggiare con altri utenti.
4) Service: classe che comunica col Server.

Ognuna delle classi prevede la gestione di errori come mancato inserimento dati, dati errati ecc.. in modo da garantire all'utente la più semplice navigazione possibile. Gli errori sono ben notificati. Tra la classe Registrazione ed Accesso ci si può andare attraverso dei link di ciascuna interfaccia. La classe Client e Service utilizzano il Design Pattern Observable in modo che l'utente sappia sempre lo stato della connessione col Server.

-SERVER:

1) Server: classe in cui avviene la comnicazione col client:
2) SQLHelper: classe in cui avvengono le operazioni sul DataBase.


- APPLICAZIONE ANDROID

Le classe sono 8:

1) LoginActivity: gestione del login, registrazione e schermata iniziale.
2) ChatActivity: La classe gestisce la visualizzazione dei messaggi, attraverso un menù laterale è possibile vedere gli utenti collegati oppure ritornare alla "home page". Tramite un menù a comparsa è possibile aggiornare la chat, eliminare l'account attraverso un Dialog.
La chat è gestita da una classe:
3) MyAdapterChat: che ha il compito di popolare la lista delle chat e di gestire l'invio.
4) ServiceClass è la classe che comunica col server in una maniera simile dell'applicazione per PC.

In caso di ricezione di un messaggio mentra non si sta utilizzando l'applicazione o si sta guardando un'altra chat, una notifica mostrerà l'arrivo di un nuovo messaggio.

Tutte le classi hanno una gestione degli errori, ma l'applicazione non è esente da crash in casi particolari.

Viene utilizzata una classe personale "Messaggio" in cui viene gestito l'invio e la ricezione di dati tra client e server.


ERRORE:
Il server riscontra il seguente problema: quando due o più client effettuano l'accesso ed iniziano a comunicare, le porte dei client dei differenti Thread vengono confuse tra loro rendendo le applicazioni dei client non utilizzabili.


Si ricordi di modificare l'indirizzo ip e il path per il collegamento al database.
Il database viene creato automaticamente in caso di sua assenza.