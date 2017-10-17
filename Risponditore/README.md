# Progetto risponditore automatico

### Autore: Enrico Da Ronche

### Classe 5^IA

### Istituto C.Zuccante

Il progetto "Risponditore" risulta costituito dalle due distinte applicazioni Client e Server.
In particolare l'applicazione Client consente di connettersi al Server tramite l'utilizzo di un socket TCP.
Il Server, a sua volta, accetta le ricieste di connessione da parte dei Client con la tecnica del pooling.
L'utilizzo dell'applicazione Client consente, attraverso un'interfaccia grafica articolata in tre viste differenti(VistaIniziale, VistaOrdiniPrenotazioni e VistaDialogo), di dialogare con il risponditore automatico di una pizzeria per poter effettuare determinate richieste.
Per poter gestire tale dialogo il Server sfrutta un automa a stati finiti risiedente in "Pizzeria.java".
Tale automa è implementato tramite l'utilizzo di un particolare array di stringhe e array che consente di definire un grafo in cui gli stati sono le domande e le transizioni le risposte. La rappresentazione in UML dell'automa si può trovare nel file "Automa.png".
Sempre in "Pizzeria.java" è definito un array di espressioni lambda che consente di elaborare i dati acquisiti in maniera differente a seconda della domanda a cui si ha risposto.
Il Client fornisce inoltre un servizio di autenticazione che consente di visualizzare gli ordini e le prenotazioni per l'utente che si collega.
Tutto ciò è gestito dal Server tramite l'utilizzo di un database "Pizzeria.db" composto da 4 tabelle "UTENTE", "ORDINE", "PRENOTAZIONE" e "PRODOTTO" che permettono il salvataggio, appunto, di utenti, ordini con relativi prodotti e prenotazioni (con ordini e prodotti associati all'utente richiedente).

N.B.: per far funzionare il programma modificare i percorsi dell'immagine e del database rispettivamente nei file "VistaIniziale.java" e "UtilDB.java" in relazione a dove essi vengono spostati.