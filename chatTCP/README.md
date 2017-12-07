Pagotto Emanuele - Progetto chat TCP

Questo progetto mira a realizzare un servizio di chat tra due client.
Ogni client può selezionare con chi vuole conversare.

L'identificazione del client avviene attraverso una funzione di login, e al login vengono mandate al client tutte le conversazioni che lo vedevano come destinatario o mittente.

Sul client, attraverso una interfaccia grafica (che non funziona :( ) è possibile scegliere con chi parlare, scegliendo tra gli utenti disponibili online presentati in una JComboBox.
I messaggi vengono invece caricati ogniqualvolta che lo stato della combo box cambi, presentando su un pannello i messaggi scambiati tra le due persone, allineando a destra i messaggi mandati dall'utente, e a sinistra quelli dell'altro utente.
