# 5IA
Il prgetto è composto da 3 file:
-SERVER: utilizza dei thread per rispondere alle richieste dei vari client. Inoltre si appoggia alla classe Spaccino, da cui prende 
         i metodi che permettono la risposta in base allo stato in cui ci si trova.
-CLIENT: dopo l'attivazione del server, permette il dialogo tramite la connessione creatasi con esso.
-SPACCINO: classe da cui il server prende i metodi necessari per inviare la giusta risposta. Per rappresentare il diagramma a stati finiti,
           si utilizza una matrice di array. Gli array contengono le possibili risposte che può dare il client.
           Le domande, inoltre, vengono prese da un array all'interno della classe.
