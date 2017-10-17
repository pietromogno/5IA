# Progetto Repositore

Il progetto si vede diviso in tre parti:
- `Pizzeria.java` Automa;
- `ClientPizzeria` Client;
- `ServerPizzeria` Server.

**Il progetto non è funzionante.**

### Automa Pizzeria.java
L'automa prevede l'uso di una matrice di adiacenza come grafo in cui vengono contenute le possibili risposte ricevute dal Client e un array, contenente le domande per ciascuno degli stati dell' automa. Ricevendo tramite una BufferdReader il messaggio del client e controllandone l'uguaglianza ad una delle possibili risposte, si cambia di stato, passando alla domanda del Server successiva.

### ClientPizzeria.java
Sviluppa la connessione al server. Codice prelevato dalla cartella esempi tcp_socket sotto la voce CapitalizedClient.

### ServerPizzeria.java
Sviluppa la connessione ad uno o più client tramite l'utilizzo della classe ServerTask, che estende Thread. Codice prelevato dalla cartella esempi tcp_socket sotto la voce CapitalizedServer.

### Note finali
Mi scuso per non aver rispettato la consegna appieno, in quanto l'automa, se pur funzionante, avrebbe utilizzato un metodo fin troppo sequenziale per il passaggio di stato e non sono riuscito a ottenere un'idea migliore.
Nonostante svariati tentativi non riesco a capire come implementare l'automa al server.