
package server;

import static server.Pizzeria.StatoPizzeria.ELENCOBIBITE;
import static server.Pizzeria.StatoPizzeria.ELENCOPANINI;
import static server.Pizzeria.StatoPizzeria.ELENCOPIZZE;
import static server.Pizzeria.StatoPizzeria.ORDINAPIZZE;
import static server.Pizzeria.StatoPizzeria.ORDINAPRODOTTO;

/**
 *
 * @author Matteo Favaro
 */
public class Pizzeria {
    
    public enum StatoPizzeria{
        INIZIO,
        ORDINAPRODOTTO,
        ELENCOPIZZE,
        ELENCOBIBITE,
        ELENCOPANINI,
        ORDINAPANINI,
        ORDINABIBITE,
        ORDINAPIZZE,
        TERMINA
        
    }
    private StatoPizzeria statoCorrente;
    String nome;
    private boolean clienteFinito;
    
    public Pizzeria(){
        this.statoCorrente = StatoPizzeria.INIZIO;
        clienteFinito = false;
    }
    public boolean clienteFinito(){
        return clienteFinito;
    }
    public String messaggio(){
        String mess="";
        switch (statoCorrente){
            case INIZIO:
                mess="BENVENUTO   COME TI CHIAMI ?";
                break;
            case ORDINAPRODOTTO :
                mess = nome + "SCEGLI COSA ORDINARE  : 1.PIZZA 2.BIBITA 3.PANINO ";
                break;
            case ELENCOPIZZE: 
                mess = nome + " ELENCO PIZZE : 1.Margherita 2.Diavola 3.Patatosa 4.Viennese ";
                break;
            case ORDINAPIZZE:
                mess = nome + "ordina ancora pizza 2.ordina un altro prodotto";
                break;
            case ELENCOBIBITE : 
                mess = nome + "Elenco BIbite : 1.the 2.acqua 3.coca-cola 4.vino 5.fanta 6.birra";
                break;
            case ELENCOPANINI :
                mess = nome + "Elenco Panini : 1.Bacon King 2.CrispyMcBecon 3.tost";
                break;
            case ORDINABIBITE :
                mess = nome + "ordina bibita : 1.ordina ancora bibita 2. ordina un altro prodotto";
                break;
            case ORDINAPANINI:
                mess = nome + "ordina panino : 1.ordina altro panino 2.ordina altro prodotto ";
                break;
            case TERMINA :
                mess = nome + "arrivederci";
                break; 
        }
        return mess;
    }
    public void changeStato(String mess){
        switch(statoCorrente){
            case INIZIO:
                nome = mess;
                this.statoCorrente= ORDINAPRODOTTO;
                break;
            case ORDINAPRODOTTO :
                switch (mess){
                    case "1" : 
                        this.statoCorrente=ELENCOPIZZE;
                        break;
                    case "2" : 
                        this.statoCorrente=ELENCOBIBITE;
                        break;
                    case "3" : 
                        this.statoCorrente=ELENCOPANINI;
                        break;
                }
                break;
            case ELENCOPIZZE :
                this.statoCorrente= ORDINAPIZZE;
                break;
            case TERMINA :
                if(mess.toUpperCase().equals("exit")) clienteFinito = true;
                break;
                
        }
    }
    
}
