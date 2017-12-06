Pagotto Emanuele - progetto risponditore

In questo progetto si mira a simulare l'interazione con il cassiere di un negozio di videogiochi, che ci chiederà cosa vorremo comprare.

La determinazione delle frasi da dire al client è fatta tramite un automa a stati finiti, "Risponditore.java":
esso è rappresentato da una matrice di adiacenza di "Node", ogni nodo è identificato dal suo id univoco,che corrisponde al suo indice sull'asse y della matrice di adiacenza, e gli aggiornamenti avvengono in base alle risposte del client.

Vengono inoltre salvate le cose acquistate nel carrello, e tenuto traccia del valore dell'ordine.

Per facilitare l'accesso ai dati da parte del risponditore è stato creato un Node current, indicante l'attuale nodo in cui si trova l'automa.
 
