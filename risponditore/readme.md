Favaro Matteo  5 IA   C.Zuccante  

# Progetto Risponditore per Pizzeria

Il progetto è composto da un client e un server che comunicano tra loro simulando l'ordine di un cliente ad una pizzeria.

Il cliente comunica con una semplice interfaccia che propone di volta in volta la domanda con le risposte o altre informazioni.

### Server

Il server è composto da due classi:

`Server.java`: consente di creare l'oggetto ServerSocket che sarà utilizzato per accedere alle 
		connessioni in arrivo a una porta. Ogni connessione in arrivo verrà accettata e
		 creata un thread che gestirà la comunicazione con quel determinato client.

`Pizzeria.java`  : classe che contiene i vari switch case che permettono al client di dare una risposta alle domande del server.

### Client

Il client è composto da una classe:

`Client.java`: consente di creare la connessione con il server. Ulteriori informazioni di benvenuto, domande, informazioni e possibili scelte dal server.