/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import dbutil.UtilDB;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static server.Pizzeria.elaboraDati;
import static server.Pizzeria.getDomanda;
import static server.Pizzeria.nextQuestion;

/**
 *
 * @author Enrico
 */
public class Dialogo implements Runnable {

    private BufferedReader in;
    private PrintWriter out;
    private String nomeCliente;
    int idUtente;

    public Dialogo(Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    }

    @Override
    public void run() {
        try {
            boolean continua = true;
            while (continua) {
                String op = in.readLine();
                String username = in.readLine();
                String password = in.readLine();
                if (op.equals("login")) {
                    if (UtilDB.esistonoCredenziali(username, password)) {
                        out.println("");
                        nomeCliente = username;
                        idUtente=UtilDB.idUtente(username);
                        gestisciVistaOrdiniPrenotazioni();
                    } else {
                        out.println("Errore");
                    }
                } else if (op.equals("registrazione")) {
                    if (!UtilDB.esisteUsername(username) && !UtilDB.esistePassword(password)) {
                        UtilDB.inserisciUtente(username, password);
                        out.println("");
                        nomeCliente = username;
                        idUtente=UtilDB.idUtente(username);
                        gestisciVistaOrdiniPrenotazioni();
                    } else {
                        out.println("Errore");
                    }
                } else {
                    continua = false;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Pizzeria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void gestisciVistaOrdiniPrenotazioni() throws IOException {
        boolean continua=true;
        while(continua){
            ArrayList<Ordine> listaOrdini = UtilDB.listaOrdini(idUtente);
            ArrayList<Prenotazione> listaPrenotazioni = UtilDB.listaPrenotazioni(idUtente);
            out.println(listaOrdini.size());
            for (Ordine o : listaOrdini) {
                out.println(o);
                ArrayList<String>prodotti=o.getProdotti();
                out.println(prodotti.size());
                for(String prod:prodotti)out.println(prod);
            }
            out.println(listaPrenotazioni.size());
            for (Prenotazione p : listaPrenotazioni) {
                out.println(p);
            }
            String op = in.readLine();
            if (op.equals("contatta")) {
                gestisciDialogo();
            }else continua=false;
        }
    }
    
    private Ordine o;
    private Prenotazione p;

    private void gestisciDialogo() throws IOException {
        o=new Ordine();
        p=new Prenotazione();
        int idDomanda = Pizzeria.getFirstId();
        int lastId=Pizzeria.getLastId();
        out.println(nomeCliente+" "+getDomanda(lastId));
        String domanda = getDomanda(idDomanda);
        out.println(nomeCliente + " " + domanda);
        do {
            String risposta = in.readLine();
            risposta=elaboraDati(o,p,risposta,idDomanda,idUtente);
            idDomanda = nextQuestion(idDomanda, risposta);
            domanda = getDomanda(idDomanda);
            out.println(nomeCliente + " " + domanda);
        } while (idDomanda != lastId);

    }
}
