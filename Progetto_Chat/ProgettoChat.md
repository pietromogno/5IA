#Documentazione Progetto Chat <br>

di Matteo Mognato

Il progetto Chat ha lo scopo di permettere la comunicazione Chat tra più Client in modo simultaneo. Il sistema è come un programma di messaggistica che richiede una registrazione al primo accesso e un login ogni volta si avvierà il Client.
I dati di ogni utenti sono gestiti dal server e memorizzati su un DataBase SQL locale.
Il progetto è composto da 5 file: 

```
Server.java
Client.java
JFrameAccessoClient.java
JFrameClient.java
SQLHelper.java
```

##Server 
Fa uso di esecutori per gestire l'accesso contemporaneo di pià Client. 
E' composto quindi da una classe(NuovoClient) che implementa Runnable e gestisce inizialmente l'accesso al programma con Login e Regstrazione e a seconda delle entries fornisce l'accesso o dei messaggi d'errore.
Il server basa i suoi controlli per l'accesso con la classe SQLHelper.
Inoltre il Server per evitare l'accesso di più client con lo stesso nome Utente e password crea un HashMap generare che contenente tutti i Client attivi e il loro socket(utile per un invio rapido dei messaggi al destinatario).

##Client 
Gestisce le varie finestre JFrameAccessoClient e JFrameClient

##JFrameAccessoClient
E' un form per l'accesso del client con proprio username e password appena questi sono corretti la finestra verrà chiusa dalla classe principale del client.

##JFrameClient
Contiene un form per inviare messaggi a altri Client contiene inoltre una textArea che tiene traccia grazie a un Classe Ascoltatore di tipo Thread di tutti i nuovi messaggi e ti quelli spediti

##SQLHelper 
contiene 4 metodi:
getConnection  -> per trovare connettersi con il database
inserisciUser  -> per inserire un nuovo user al database
isUserPresente -> dice se un username è già presente nel database
isUserCorrect  -> dice se dato username e password questi sono corretti

Ps. Nella Cartella del progetto sono presenti anche Chat.db che è il database e sqlite-jdbc-3.20.0.jar che è la libreria utilizzata per la gestione del database