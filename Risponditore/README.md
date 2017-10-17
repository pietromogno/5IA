Musone Mattia

Con questo progetto si vuole simulare un ristorante dotato di una funzione di prenotazione pasti online. Ogni cliente potrà collegarsi al Server del Ristorante Zuccante per prenotare il proprio pasto. L'utente ha a disposizione un'interfaccia grafica minimale (JFrame), dove è presente lo storico della chat, il suo nome utente attuale, lo stato della connessione ed alcuni pulsanti per terminare la chat ed inviare le risposte attraverso un campo di testo. Prima di ricevere l'ordine l'utente riceverà un riepilogo di ciò che ha ordinato in modo da non sbagliare.

Il Server utilizza una macchina a stati finiti (Ristorante.java) che prende in carico le risposte del client memorizzandole ed elaborando una successiva risposta in base alle scelte del client.

La chat terminerà quando l'utente deciderà di chiudere la sessione.

La macchina a stati finiti è stata realizzata tramite una matrice nel seguente modo costituita:

 Posizione [0][0] = id Nodo,
 Posizione [0][1] = domanda/risposta,
 Posizione [0][2] = id prossimo nodo (risposta positiva o prima scelta tra le opzioni proposte all'utente),
 Posizione [0][3] (facoltativa) = id prossimo nodo (risposta negativa o seconda scelta tra le opzioni proposte all'utente)

La matrice è una matrice di stringhe. 

Per memorizzare le risposte dell'utente è stata usata una matrice di stringhe di grandezza 3x2 memorizzando:

 Posizione [0][0] = campo, 
 Posizione [0][1] = risposta utente

il Server utilizza il multiThreading estendendo la classe Thread di Java e ObjectInputStream/Output per comunicare con il client.