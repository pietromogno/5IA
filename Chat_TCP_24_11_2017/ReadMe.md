# Chat TCP 
di **Farhan Latif Gazi** `5^IA`

`Il progetto Chat TCP` simula una chat tra più client, che tramite un server, si scambiano messaggi.

Per la realizzazione del progetto sono stati creati due sotto-progetti:
```
- Server
- Client
```

## Server contiene al suo interno:
```
- Server.java
- ClientHandler.java
- SQLhelper.java
```
Ha il compito di accettare tutte le richieste da parte dei client instaurando dei socket che viengono gestiti a loro volta da processi concorrenti creati dal Server con la tecnica del `theadpool`. `Server.java` appunto utilizza la classe `ClientThread.java` che implementa Runnable, ed è gestita da un esecutore `Executor`.

`SQLHelper.java`
Contiene tutte le Funzioni/Metodi che permettono al server di interagire con il database.
Il server utilizza il database per registrare oppure loggare il cliente.

Vengono inoltre utilizzate le classi:
```
- Message.java
- Registration.java
- Login.java
- Update.java
```
Tali oggetti implementano `Serializable` e possono essere spediti da Client a Server, o viceversa, per identificare le operazioni richieste o ricevute. 
`N.B. Registration.java, Login.java, Update.java sono 'incapsulate' al interno di Message.java: l'unica classe che viene spedita`

## Clinet contiene al suo interno:
```
- Luncher.java
- Client.java
- messageReceiver.java
```
E' il programma con l'interfaccia grafica usato dall'utente per `acceder, registarsi e messaggiare`.
Il tutto è stato sviluppato seguendo il `MVC`, nel quale `Client.java` funge da modello per tutte le viste. `Client.java` utilizza la classe `messageReceiver.java` (che implementa Runnable) per ricevere i messaggi.
`Luncher.java` è la classe che crea la prima vista ed assegna a quest'ultimo un controllore ed il modello `Client`.

### Le viste sono:
```
- Lonin.fxml
- Registration.fxml
- Messaging.fxml
```
### I controllori:
```
- loginController.java
- registrationController.java
- messagingController.java
```

## Strumenti utilizzati per lo sviluppo del progetto

- `IntelliJ IDEA`: per scrivere il codice.
- `SceneBuilder` :  per `disegnare` la grafica in `javaFX`. 
