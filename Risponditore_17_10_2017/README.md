# Risponditore automatico in Java
Il progetto del risponditore contiene tre file:
```
Pizzeria.java
Server.java
Client.java
```
I primi due dentro la cartella "Server" ed il terzo dentro la cartella "Client".

```
Pizzeria.java
```
E' il file che contiene l'automa a stati finiti realizzata con una matrice di matrice di tipo Object. Ogni stato stato consiste in una stringa che indica al client cosa fare.

```
Server.java
``` 
E' il file che contiene la classe "Cameriere" che implementa "Runnable" e la classe "Sever" che mette in piedi la comunicazione con il client con un scoket e tramite un executor manda in esecuzione la task di ogni cameriere. (Tecnica del pooling)

```
Client.java
```
E' il file che contiene la classe client e comunica con il server tramite socket: prendendo tutti gli input del cliente e mandandolo al server.

## Strumenti utilizzati per lo sviluppo del progetto
- NetBeans (per scrivere il codice e testarlo)
- StarUml (per realizzare il diagramma di attività della macchina a stati finiti)
