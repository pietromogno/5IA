Il ChatTCP contine diverse classi:
-Client: il quale ha il compito di connettersi al server una volta effettuato il logIn e esegue le 
	 operazioni necessarie per far comunicare con altrin utenti in linea
-DataBase: è una classe che permette la creazione del DB e esegue le operazioni di inserimento in esso 
	   e di verifica dell'esistenza di un utente che sta efettuando il logIn
-DatiClient: è una classe che contine delle informazioni riguardanti un utente che si è connesso in modo da poter
   	     farlo messaggiare con altri utenti
-LogIn: è una classe che permette di inserire i dati per poter accedere oppure di accedere alla schermata di 
	reistrazione
-Registrati: classe che permette di efettuare la registrazione e l'inserimento del DB
-Server: classe che permette lo scambio dei messaggi fra i client