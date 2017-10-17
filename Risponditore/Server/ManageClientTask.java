package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import static server.Ferramenta.eseguiAzioneCorrispondente;
import static server.Ferramenta.getDomandaSuccessiva;
import static server.Ferramenta.getElencoRispostePossibili;

/**
 * @author Davide Porcu
 */
public class ManageClientTask implements Runnable {

    private Socket connessione;
    private OrdineCliente cliente;

    public ManageClientTask(Socket connessione, int numeroCliente) {
        this.connessione = connessione;
        cliente = new OrdineCliente(numeroCliente);
    }

    @Override
    public void run() {
        try {
            System.out.println((char) 27 + "[32mCONNESSO Cliente " + cliente.getNumeroCliente());
            BufferedReader in = new BufferedReader(new InputStreamReader(connessione.getInputStream()));
            PrintWriter out = new PrintWriter(connessione.getOutputStream(), true);
            
            String domanda = "", risposta = "";
            System.out.println("Inzio a servire il cliente "+cliente.getNumeroCliente());
            while (true) {
                String[] esito = eseguiAzioneCorrispondente(domanda, risposta, cliente);
                domanda = getDomandaSuccessiva(domanda, risposta);
                String[] daInviare = getElencoRispostePossibili(domanda).toArray(new String[0]);
                daInviare = daInviare.length!=0 ? daInviare : esito;
                out.println(1 + daInviare.length);//righe da trasmettere
                out.println(cliente.getNome() + " " + domanda);
                for (String opzioniRisposte : daInviare) {
                    out.println(opzioniRisposte);
                }         
                risposta = in.readLine();
                if (risposta == null || risposta.equals("exit")) {//il cliente può scappare in qualunque momento :)
                    connessione.close();
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Errore nel gestire il client "+cliente.getNumeroCliente());
        } finally {
            try {
                connessione.close();
                System.out.println((char) 27 + "[34mDISCONNESSO Cliente "  + cliente.getNumeroCliente()+" nome:" +cliente.getNome() + " !\"");
            } catch (IOException e) {
                System.out.println("Impossibile chiudere il socket");
            }
        }
    }

}
