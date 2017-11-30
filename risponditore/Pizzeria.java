package server;

import static server.Pizzeria.StatoPizzeria.*;

/**
 * @author Matteo Favaro
 */
public class Pizzeria {

    public enum StatoPizzeria {

        INIZIO,
        ORDINAPRODOTTO,
        ELENCOPIZZE,
        ELENCOBIBITE,
        ELENCOPANINO,
        ORDINAPIZZA,
        ORDINABIBITA,
        ORDINAPANINO,
        TERMINA
    }

    private StatoPizzeria statoCorrente;
    /*private*/ String nomeCliente;
    private boolean hasClienteFinito;

    public Pizzeria() {
        this.statoCorrente = StatoPizzeria.INIZIO;
        hasClienteFinito = false;
    }

    public boolean hasClienteFinito() {
        return hasClienteFinito;
    }

    public String getMessaggio() {
        String messaggio = "";
        switch (statoCorrente) {
            case INIZIO:
                messaggio = "Benvenuto! Come ti chiami?";
                break;
            case ORDINAPRODOTTO:
                messaggio = nomeCliente + " Scegli cosa ordinare: 1.Pizza 2.Bibita 3.Panino";
                break;
            case ELENCOPIZZE:
                messaggio = nomeCliente + " Elenco Pizze: 1.Margherita 2.Patatosa 3.Viennese 4.Quattro formaggi 5.Diavola";
                break;
            case ELENCOBIBITE:
                messaggio = nomeCliente + " Elenco bibite: 1.Coca-Cola 2.Fanta 3.The 4.Birra 5.Vino";
                break;
            case ELENCOPANINO:
                messaggio = nomeCliente + " Elenco panini: 1.Bacon King 2.CrispyMcBacon 3.Toast";
                break;
            case ORDINAPIZZA:
                messaggio = nomeCliente + " 1.Ordina ancora pizza 2.Ordina un altro prodotto 3.Termina e Paga";
                break;
            case ORDINABIBITA:
                messaggio = nomeCliente + " 1.Ordina ancora bibita 2.Ordina un altro prodotto 3.Termina e Paga";
                break;
            case ORDINAPANINO:
                messaggio = nomeCliente + " 1.Ordina ancora panino 2.Ordina un altro prodotto 3.Termina e Paga";
                break;
            case TERMINA:
                messaggio = nomeCliente + " Grazie e arrivederci! Digita \"exit\" per uscire";
                break;
        }
        return messaggio;
    }

    public void cambiaStato(String msg) {
        switch (statoCorrente) {
            case INIZIO:
                nomeCliente = msg;
                this.statoCorrente = ORDINAPRODOTTO;
                break;
            case ORDINAPRODOTTO:
                switch (msg) {
                    case "1"://Pizza
                        this.statoCorrente = ELENCOPIZZE;
                        break;
                    case "2"://Bibita
                        this.statoCorrente = ELENCOBIBITE;
                        break;
                    case "3"://Panino
                        this.statoCorrente = ELENCOPANINO;
                        break;
                }
                break;
            case ELENCOPIZZE:
                this.statoCorrente = ORDINAPIZZA;
                break;
            case ELENCOBIBITE:
                this.statoCorrente = ORDINABIBITA;
                break;
            case ELENCOPANINO:
                this.statoCorrente = ORDINAPANINO;
                break;
            case ORDINAPIZZA:
                switch (msg) {
                    case "1"://ordina altra pizza
                        this.statoCorrente = ELENCOPIZZE;
                        break;
                    case "2"://ordina altro prodotto
                        this.statoCorrente = ORDINAPRODOTTO;
                        break;
                    case "3"://termina
                        this.statoCorrente = TERMINA;
                        break;
                }
                break;
            case ORDINABIBITA:
                switch (msg) {
                    case "1"://ordina altra pizza
                        this.statoCorrente = ELENCOBIBITE;
                        break;
                    case "2"://ordina altro prodotto
                        this.statoCorrente = ORDINAPRODOTTO;
                        break;
                    case "3"://termina
                        this.statoCorrente = TERMINA;
                        break;
                }
                break;
            case ORDINAPANINO:
                switch (msg) {
                    case "1"://ordina altra pizza
                        this.statoCorrente = ELENCOPANINO;
                        break;
                    case "2"://ordina altro prodotto
                        this.statoCorrente = ORDINAPRODOTTO;
                        break;
                    case "3"://termina
                        this.statoCorrente = TERMINA;
                        break;
                }
                break;
            case TERMINA:
                if(msg.toUpperCase().equals("EXIT")) hasClienteFinito = true;
                break;
        }
    }

}