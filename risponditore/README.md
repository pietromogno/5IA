Busetto Alvise,

l'esercizio è stato svolto tramite l'impiego di un grafo come automa a stati finiti al fine di rappresentare la discussione con un taxista.
I principali meccanismi per il funzionamento della discussione sono svolti dai metodi all'interno della classe ServizioTaxi, qui viene usata una matrice di adiacenza contenente le risposte possibili ad ogni domanda, ad essa è affiancato un'array parallelo contenente le domande corrispondenti alle varie celle.
All'interno della classe vi è una variabile "cont" che funge da contrassegno del punto del discorso a cui si è arrivati.
Praticamente ogni tal volta che viene comunicata una risposta da parte del cliente si individuerà all'interno della matrice dove si colloca tale stringa, con ciò si può risalire alla domanda successiva che verrà estratta dall'array parallelo secondo cont.