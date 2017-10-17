**Francesco Forcellato**
# Risponditore
Per poter creare un `Risponditore` mi sono servito di più classi:
- `Pizzeria.java`: contiene la macchina a stati finiti che viene usata dal _server_ per poter sapere le domande da inviare al _client_
- `Server.java`: serve per poter gestire i servizi da offrire ai _client_ connessi ad esso, inviando domande e risposte utili ai client 
- `Client.java`: riceve le domande e le possibili risposte e le fa visualizzare attaverso un'apposita interfaccia grafica
- `GrafiaClient.java`: è l'interfaccia grafica che permette al _client_ di visualizzare domande ed eventuali risposte, e che permette l'invio della risposta dell'utente al _server_

## Pizzeria
Per la realizzazione della macchina a stati finiti, ho utilizzato un grafo, rappresentato da una matrice di adiacenza.

Ho utilizzato l'array `String[] domande;` con al suo interno, per ogni cella una domanda. La matrice `private String[][][] grafo;` invece, al numero della riga la domanda, mentre il numero della colonna è l'indirizzo per la prossima domanda, il contenuto della cella è invece l'array contenente le possibili risposte; ad esempio la cella `[4][5]` contiene le _risposte_ alla domanda `4`, mentre la _prossima domanda_ si trova in `domande[5];`, in questa maniera in una riga della matrice, possono esserci più risposte, che portano a domande differenti.


## Server
Il server è composto da 2 classi annidate: 
- `Server` ha il compito di creare l'oggetto `ServerSocket listener` e di rimanere in ascolto, infatti inizia un `while(true)` dove al suo interno, quando `listener` accetta una connessione al _Server_ allora crea e lancia la seconda classe, che è _Runnable_

- _Gestore_ è una classe _Runnable_, che quindi può venire eseguita in parallelo, attraverso il multithreading; il compito di quest'ultima è quello di inviare la domanda al _client_, attendere la risposta ed inviare una nuova domanda, fino a quando l'utente conferma l'ordine, a quel punto viene inviata la conferma dell'ordine e la connessione viene conclusa.


## Client
Il client è composto da due parti: 
- `Client.java`: ha il compito di connettersi al server, e una volta connesso riceve le domande e le possibili risposte e poi le fa visualizzare su _GraficaClient_ in maniera differente.

- `GraficaClient.java`: è la grafica del _client_, ha il compito di far visualizzare in maniera appropriata le domande e le risposte, e dà la possibilità di scegliere le risposte attraverso una `JList` e scriverle in un `JTextField`, che con il comando di invio, attraverso un metodo statico della classe `Client`, riesce ad inviare la risposta al _server_.



