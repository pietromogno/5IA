### Matteo Mognato
##### Classe 5^iA
## Progetto Tris con Observer
Il progetto Tris/TicTacToe creato in ambiente android permette di giocare in 2 modalità:
-uomo contro uomo
-uomo contro macchina 
Il progetto è stato creato utilizzando il modello MVC e fa uso degli Observer.
L'applicazione è costituita da 2 activity:
> ### Prompt Giocatori
> Gestisce il click su due bottoni che fanno scegliere la modalità di gioco come Single Player o Multiplayer nel caso sia multiplayer permette di scegliere precedentemente il nome dei giocatori. Tramite l'uso delle lamda di Java 8 gestisce il click sui bottoni e utilizzando gli Indent passa alla Main Activity ovvero l'activity per giocare.
> ### Main Activity
> Gestisce il caso che venga chiamata per giocare uomoVsUomo e UomoVsComputer, fa utilizzo della classe Gioco.
Implementa View.OnClickListener e trammite il metodo onclick, fa uno switch per capire il bottone in cui si trova e trammite il motodo clickCella(int riga, int colonna)  esegue il click sulla cella. Se poi sarà possibile verrà notificata la Main Activity che estende Observer e con il metodo update andrà ad aggiornare la vista.
E' presente un bottone da cliccare una volta terminata la partita per continuare a giocare con la stessa modalità.
Sono presenti 3 textView:
-1 per dire di chi è il turno 
-2 per mostrare il punteggio dei giocatori