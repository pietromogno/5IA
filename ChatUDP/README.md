Il progetto è composto da 3 file:

- avviaChat.java: Avvia server e client e i loro rispettivi thread;

- ClientUDP.java: costituita da due parti. La prima classe crea e gestisce la grafica in JFrame, e si occupa anche di accedere e 
                  connettersi al database (dove sono registrati gli utenti e le rispettive password).
                  La seconda parte crea un oggetto che si occupa della connessione al gruppo multicast con il server;

- ServerMulticast.java: gestisce le varie interazioni con i diversi client, ricevendo i pacchetti e inviandoli ai destinatari 
                        utilizzando i DatagramPacket
