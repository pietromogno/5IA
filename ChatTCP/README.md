# Progetto chat TCP

### Autore: Enrico Da Ronche

### Classe 5^IA

### Istituto C.Zuccante

Il progetto chat TCP consente di simulare un programma di instant messaging tramite l'utilizzo di due differenti applicazioni JAVA denominate Client e Server. In particolare l'applicazione Client consente di accedere ai diversi servizi di tale applicazione tramite l'utilizzo di interfacce grafice sviluppate con JAVA swing. La connessione al server viene effettuata tramite l'utilizzo di socket TCP mentre il server accetta le richieste dei vari Client tramite l'utilizzo della tecnica del pooling.
Il client grafico si articola in 4 viste differenti: VistaAutenticazione, VistaUtente, VistaChat e VistaCreazioneGruppo. VistaAutenticazione consente all'utente di accedere o di registrarsi al servizio. VistaUtente mostra la lista delle chat e offre la possibilità di aggiungere contatti o di creare nuovi gruppi (chat che includono più persone). VistaChat rappresenta l'interfaccia di una singola chat, privata o di gruppo che sia. In particolare, in caso di chat di gruppo, dà la possibilità di aggiungere ulteriori contatti. VistaCreazioneGruppo consente, appunto, di creare una nuova chat di gruppo indicando i contatti che si desidera inserire.
Il server, dal canto suo, gestisce il tutto grazie alla classe ClientHandler (che implementa Runnable e che viene istanziata per ogni client che tenta di connettersi) e alla classe Chat (che definisce gli oggetti di tipo Chat).
La gestione dei dati viene effettuata dal server tramite database SQL con l'ausilio di una classe SQLHelper utile appunto per effettuare operazioni sui db stessi. Il database (denominato ChatTCP.db) risulta costituito da 4 differenti tabelle (UTENTE, CHAT, APPARTENENZA e MESSAGGI). La prima consente di salvare le credenziali dei vari utenti. La seconda salva i dati inerenti le varie chat (private o di gruppo che siano). "APPARTENENZA" contiene le associazioni utente-gruppo e permette dunque di definire quali sono i membri di un determinato gruppo. La tabella MESSAGGI archivia invece i messaggi delle varie chat.

N.B.: perchè il programma funzioni è necessario modificare il path del database all'interno di SQLHelper in relazione a dove viene spostato il db.