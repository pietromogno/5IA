**Davide Porcu**

# Risponditore automatico in Java

Il progetto `Risponditore automatico` simula un negozio di ferramenta in cui un cliente interagisce con un commesso digitale.
Il commesso proporrà domande a cui il cliente dovrà rispondere sclegliendo tra le opzioni disponibili(quando proposte), 
rispondendo a piacere o premendo invio.
Il cliente comunica al commesso attraverso una semplice interfaccia grafica che propone di volta in volta la domanda 
con le possibili risposte o altre informazioni.

Il progetto è costituito da due parti: Client e Server che conterranno ciascuno i propri file utili al corretto funzionamento.

`Nota Bene` I file riguardanti la macchina a stati finiti sono contenuti nella parte Server.

## Server
Il server impiega è costituito da due classi:

- `Server.java`: permette di creare l'oggetto ServerSocket che verrà utilizzato per accettare le connessioni in arrivo a una determinata porta. Ciascuna connessione in arrivo verrà accettata e creato un Task (che implementa Runnable) che gestirà la comunicazione con quel determinato Client. Per poter garantire a più client il servizio contemporaneamente viene utilizzata la tecnica del thread pooling utilizzando Task e ExecutorService. Il server tiene anche traccia anche del numero di client accettati. 

- `ManageClientTask.java`: è la classe Runnable che gestisce la comunicazione con un determinato client. Per ciascun client esisterà un task che ne gestirà la comunicazione; in questo modo i Task verranno eseguiti da diversi thread (auto bilanciamento con CachedThreadPool) in modo concorrente.
Questa classe consente di inviare domande e ricevere risposte dal client

## Client
Il client è composto da due classi:
- `Client.java`: permette di creare la connessione con il server. Inoltre permette di ricevere domande, informazioni, possibili risposte dal server e di inviare le risposte scelte dall'utente allo stesso . Crea anche una istanza della classe _InterfacciaGraficaClient_ per poter rappresentare all'utente le domande, informazioni ricevute e poter rispondere.
- `InterfacciaGraficaClient.java`: è la classe che implenta la grafica del _client_. Svolge il compito di mostrare all'utente le domande e di proporre le possibili risposte tra cui scegliere in una `JTextArea`. L'utente esprimerà la propria scleta attraverso l'inserimento della risposta in una  JTextField e premerà il tasto _invio_ per inviarla al server. 

## Risponditore

Il risponditore è costituito da numerosi file java utili alla realizzazione di una macchina a stati finiti.
La macchina a stati finiti è costituita da un grafo rappresentato mediante una matrice di adiacenza.
La matrice contiene al suo interno degli array di oggetto, che a seconda della circostanza sono stringhe, oggetti rappresentanti delle categorie o oggetti rappresentati dei prodotti.

Ciascuna cella rappresenta dunque la lista delle possibili risposte che l'utente può utilizzare per proseguire nello stato successivo.
Lo stato attuale viene identificato dall'indice di riga mentre lo stato successivo dall'indice di colonna. Questo permette di avere per uno stesso stato di partenza (riga) più possibili stati di arrivo (colonne) a seconda della risposta data.

Esiste poi un array contente le domande corrispondenti per ciascuno stato (indice di riga delle domande = indice riga dello stato nella matrice).

A ciascuno stato è associata una funzione anonima (lambda) che permette l'elaborazione dei dati acquisiti (come il nome del cliente o l'aggiunta dei prodotti che ha scelto).

Le relazioni rappresentate con matrice di adiacenza, le possibili risposte, l'array delle domande, l'array delle funzioni anonime vengono parzialmente costruiti a seconda dei dati acquisiti dal database. 

Si avranno quindi tanti diversi stati a seconda di quante sono le categorie di prodotti contenute nel database. Per ciascuna categoria si avranno quindi diversi prodotti da mostrare e tra cui scegliere.

Il file  che contiene tutto ciò è :
- `Ferramenta.java`

Il diagramma degli stati in UML della macchina a stati finiti è stato creato con StarUML e chiamato `DgrStatiUMLAutoma.mdj `

### Altre classi utili alla realizzazione del progetto

Per rappresentare le categorie di prodotti utilizzo la classe `Categoria` contenuta in:
- `Categoria.java`
permette di creare un oggetto Categoria utile per distinguere i vari prodotti in gruppi (ad esempio suddivisione in base al reparto)

Per rappresentare i prodotti utilizzo la classe `Prodotto` contenuta in:
- `Prodotto.java`
permette di rappresentare un prodotto, con un suo nome, un suo prezzo e una categoria a cui appartiene.

Per rappresentare l'elenco di tutti i prodotti in vendita (listino)utilizzo la classe `Listino` contenuta in:
- `Listino.java`
utilizza una `LinkedHashMap` di `LinkedHashMap` per poter creare una struttura che memorizzi i prodotti suddivisi in base alla categoria (mantenendo l'ordine di inserimento)

Per rappresentare il cliente con il suo ordine utilizzo la classe `OrdineCliente` contenuta in 
- `OrdineCliente.java`
contiene il nome del cliente (impostato con la funzione anonima), contiene il numero di servizio del cliente e il carrello(che tiene traccia dei prodotti scelti)

Per rappresentare i prodotti che il cliente sceglie utilizzo la classe Carrello contenuta nel file
- `Carrello.java`
utilizza una `LinkedHashMap` di `LinkedHashMap` per poter creare una struttura tale da memorizzare i prodotti scelti dal cliente suddividendoli in base alla categoria e tenendo traccia anche del numero di pezzi

Utilizzo inoltre una classe contenente metodi statici per ottenere e caricare il listino dei prodotti dal database. Il file contenente i metodi e il percorso del database è:
- `UtilSQLiteDB.java`
permette di eseguire delle query SQL per ottenere la lista di categorie esistenti (nella tabella CATEGORIA del database) e la lista dei prodotti per la categoria indicata (contenuti nella tabella PRODOTTO).

