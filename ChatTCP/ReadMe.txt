versione 2.0 data 30/11
Il progetto si compone di un client e un server che comunicano tramite protocollo tcp.
Il client tcp è formato da Client.java(main), ViewLogin.java(visualizza opzioni di login o registrazione),
ViewUsers.java(visualizza menu utenti registrati) e ViewChat.java(interfaccia alla messaggistica). 
Il server tcp da Server.java(inizializza un socket e una porta e gestisce i client che si connettono), ClientManaging.java
(gestisce le richieste del client connesso), SQLHelper.java(accede al database quando necessario con le query sql),
Chat.java(oggetto chat con attributi) e il ChatTCP.db. 
Il database si compone di due tabelle: UTENTE e CHAT.