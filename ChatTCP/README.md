Il progetto si compone di ClientTCP e ServerTCP.

Il primo progetto è costituito dai file:
ClientTCP, atto all'inizializzazione di socket e stream per la comunicazione;
LoginView, che gestisce il log in e la registrazione degli utenti;
UserView, per la selezione degli utenti iscritti al database con cui chattare;
ChatView, che rappresenta l'effettiva chat.

Il secondo invece:
ServerTCP, riceve le comunicazioni dal client e le inoltra alle classi di interesse;
Chat, oggetto che rappresenta le singole chat con i vari utenti;
ClientManageing, necessaria per le funzioni pricipali del server;
SQLHelper, classe che gestisce le comunicazioni con il database.

In fine il database si compone di due tabelle:
UTENTE, contenente IdUtente, Username e Password di tutti gli utenti che si registrano;
CHAT, contenente IdUtente1 e IdUtenete2, sta a rappresentare la chat che esiste tra tutti i contatti, tale chat è intesa in senso bidirezionale.
