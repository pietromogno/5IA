#Documentazione Progetto Pizzeria TPSIT
####di Matteo Mognato
##Il progetto si basa sul 3 file .java:
>
>Client.java <br>
>Server.java <br>
>AutomaPizzeria.java <br>
>
###DataClient
è basato su una semplice interfaccia da tastiera e dalla finestra di output è possibile vedere le varie domande e risposte al server.
>
###DataServer
E' basato sempre su un interfaccia da tastiera sulla finestra di output. Attraverso l'uso di un esecutore.newCachedThreadPool() permette l'accesso a infiniti Client. Ogni qual volta un client accede il server lo rileva e fa un submit del NuovoClient. <br>
Il data server sfrutta per l'appunto un altra classe NuovoClient che implementa runnable e al suo avvio instaura la comunicazione tra quel preciso Client e il Server.
>
###AutomaPizzeria
E' una classe/struttura che si occupa di fornire al server un protocollo di operazioni e domande che il server dovrà fare al client a seconda della risposta che ha ricevuto da quest ultimo. La struttura del grafo è stata scelta statica mediante la rappresentazione con una matrice e un array. Questo poichè la struttura delle domande della pizzeria saranno sempre le stesse e difficilmente cambieranno in futuro inoltre la gestione di un grafo statico impiega meno tempo di produzione del codice.<br>
E' presente tra i file anche statiAutoma.jpg che rappresenta gli stati del grafo.
