#Chat TCP 
di **Farhan Latif Gazi** `5^IA`

'Il progetto Chat TCP' simula una chat tra più client, che tramite un server, si scambiano messagi.
I messaggi possono essere inviati sia in modalità unicast che multicast.

Per la realizzazione del progetto sono stati creati due sotto-progetti:
```
Server.java
Client.java
```

##Server contiene al suo interno:

`server.java`

Ha il compito di accettare tutte le richieste da parte dei client instaurando dei socket che viengono gestiti a loro volta da processi concorrenti creati dal Server con la tecnica del `theadpool`. `server.java` contiene appunto anche la classe ClientThread che implementa Runnable, e viene istanziata dal server per gestire la comunicazione con ogni clinet.

`SQLHelper.java`
Contiene tutte le Funzioni/Metodi che permettono al server di interagire con il database.
Il server utilizza il database per registrare oppure loggare il cliente.
Viene utilizzata una classe Account contenuta in`Account.java` per rappresentare il cliente registrato.

##Clinet contiene al suo interno:

###ClientMod:

contenente il modello client (`client.java`) che è la classe che instaura il socket con il server ed invia e riceve i miessagi.
Contiene inoltre tutte le informszione riguardo al client.

###ClientView: 

contiene tutte le viste del modello client relizzate in `javaFx` seguendo il modello `MVC` (model view control). Quindi essa contiene anche i controller delle viste.

###Oggetti usate sia da Server che Client:

`Message`: un oggetto che rapperesenta il mesaggi che scambiati tra server e client.

##Strumenti utilizzati per lo sviluppo del progetto

`Netbeans` per scrivere il codice.
`SceneBuilder` per disegnare la grafica in `javaFX`. 
