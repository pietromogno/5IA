Musone Mattia

Aggiornamento server dovuto a errato caricamente

L'idea è di creare una piattaforma raggiungibile a più persone possibili, quindi sono state realizzate due applicazioni, una su piattaforma Android e una su piattaforma Java eseguibile da PC. Il database è connesso al Server per una maggiore sicurezza, quindi la mancata connessione ad esso comporta ai client il non accesso alla piattaforma e la non visualizzazione delle chat.

- APPLICAZIONE SU PC

-CLIENT:

E' stata utilizzata JavaFX per creare l'interfaccia di tutte le classi.

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

1) LoginActivity: gestione del login e schermata iniziale.
2) UsersList: succesiva schermata in cui vengono mostrati tutti gli utenti registrati con la possibilità di aggiornare questa lista. Lista che utilizza la classe:
3) MyAdapter: per il popolamento della stessa.
4) ChatActivity: invocata alla scelta di un utente. La classe gestisce la visualizzazione dei messaggi, attraverso un menù laterale è possibile vedere gli utenti collegati oppure ritornare alla lista degli utenti. Tramite un menù a comparsa è possibile aggiornare la chat, eliminare l'account attraverso la classe:
5) DleleteActivity: con vsualizzazione a popup, o uscire dalla sessione attuale.
La chat è gestita da una classe:
6) MyAdapterChat: che ha il compito di popolare la lista delle chat e di gestire l'invio.
7) RegisterActivity: consente la registrazione dell'utente.
8) ServiceClass è la classe che comunica col server alla stessa maniera dell'applicazione per PC.

Tutte le classi hanno una gestione degli errori, ma l'applicazione non è esente da crash in casi particolari, non risolti a causa del tempo.

Purtroppo entrambe le applicazioni non aggiornano le chat in modo automatico ma bisogna aggiornarle in modo manuale attraverso gli appositi menù.

Si ricordi di modificare l'indirizzo ip e il path per il collegamento al database.
Il database viene creato automaticamente in caso di sua assenza.