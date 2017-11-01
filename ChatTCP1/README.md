Il progetto è costituito da:

- 1 file Chat.java, che esegue Client, Server e i diversi thread a loro corrispondenti;

- 1 file Client.java, contenente due classi: la classe Client gestisce l'interfaccia grafica e i vari eventi a essa correlata e si 
  occupa inoltre di connettere il client al database, e la classe ChatConnection, che connette il client al Server;
  
- 1 file Message.java, dove troviamo l'utente e il messaggio con i relativi getter e setter, in modo da renderli più facili e 
  veloci da utilizzare nelle altre classi;
  
- 1 file Server.java, dove troviamo la gestione del thread del server. Inoltre, associa a ogni client un thread (ClientThread), 
  da cui riceve e invia i vari messaggi da e verso gli altri client;
  
- 1 database (vuoto), atto a contenere nomeUtente e password che verranno immessi al momento della registrazione dai vari client.
