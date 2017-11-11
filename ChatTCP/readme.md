I file contenuti in questa repository contengono:

-SQLHelper: è una classe che assolve i compiti di inserimento/verifica dei dati degli utenti che si 
	    registrano/loggano.
-Client: è la classe principale dei client dove vi è il main d'inizio che lancia l'interaccia grafica di logIn.
-LogIn: è una classe contenente l'interfaccia di logIn e consente di andare nell'interfaccia di registrazione 
	per gli utenti non registrati o di entrare nell'interfaccia del client per coloro che si loggano.
-Registrazione: è una classe contenente l'interfaccia di registrazione degli utenti, una volta presi i dati 
	        vengono fatti controllare al server, se non vi sono errori, l'utente viene registrato nel DB 
		e si riapre l'interfaccia di log in.
-ClientFrame: è una classe contenente l'interfaccia del client, vi è un area dove vengono visualizzati 
	      dinamicamente gli utenti connessi un area dove vengono visualizzati i messaggi con i destinatari 
	      e il nome del destinatario, infine un area di testo dove scrivere il messagio. Vi è poi una 
	      sottoclasse chiamate Ascoltatore che è un thread che esegue (se possibile) l'operazione di 
	      ricezione dei messaggi.
-Server: è una classe che che ogni volta che si lancia un client crea un threadPool. Il thread si occupa di 
	 verificare i dati di registrazione/logIn ricevuto dal client e di dare eventuali messaggi di errore. 
	 Una volta passata questa fase si occupa di inviare imessaggi ai vari client di destinazione e di 
	 aggiornare la parte riguardanti gli utenti arrivi.

N.B prima di inviare un messaggio dal client bisogna selexionare un utente dalla lista degli utenti
    connessi presente a destra