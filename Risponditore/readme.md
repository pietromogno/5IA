Progetto Risponditore di Favaretto Piergiorgio 5^IA

Il progetto fa uso di grafica Java nella quale si possono distinguere due interfaccie grafiche:
- interfaccia del Server
- interfaccia del Client

Classe Drogheria
La classe Drogheria è composta da un ArrayList che contiene un'array di stringhe. L'array di stringhe 
contiene delle domande che il server pone al cliente e in qualche caso viene controllata la risposta 
fatta dal cliente in modo da rendere la conversazione più lunga.

Classe Server
La maggior parte del funzionamento del server è stata copiata dal progetto capitalizeServer del professore, 
ma è stata modificata una parte del metodo run in quanto la conversazione va avanti finchè non si arriva alla 
fine dei messaggi previsti dall'automa.

Classe Client
La classe Client è stata fatta dall'alunno e contiene una funzione "connetti" chiamata solo all'avvio del client 
e una procedura "comunica" che viene riachiamata ogni volta che si invia un messaggio; essa invia la stringa 
contenuta nella casella di testo inserisci e aspetta di ricevere la risposta. Finita la comunicazione il client 
resta attivo finche non si invia un'altra volta una qualsiasi stringa o si schiaccia esci, ciò è stato fatto solo 
a scopo di poter rileggere la conversazione.

Sia la classe Client che la classe Server fanno uso di una procedura myLog, la quale viene richiamata ogni volta 
che si invia e ricve una stringa per permette di mandare a video l'intera conversazione.

P.S Si vuole simulare una drogheria (vendita di spezie NO DROGHE)