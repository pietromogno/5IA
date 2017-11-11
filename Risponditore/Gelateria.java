
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Simone
 */
public class Gelateria {
     Socket socket;
    private String domande[] = {"Quanti gelati vuoi?", "quante palline?", "a che gusto?","cono o coppetta?"};
    private String risposte[] = {"ciao un gelato", "ciao due gelati", "2 palline", "1 pallina","fragola","menta e liquirizia","cono","coppetta"};
    public Gelateria(){
        this.socket=socket;
}
    int contatore=0;
    String [] risposta=new String[2];
    String[] risp(){
        if(contatore==0){
            risposta[0]=risposte[0];
            risposta[1]=risposte[1];
            
        }if(contatore==1){
            risposta[0]=risposte[3];
            risposta[1]=risposte[4];
        }if(contatore==3){
            risposta[0]=risposte[5];
            risposta[1]=risposte[6];
        }else if(contatore==4){
            risposta[0]=risposte[7];
            risposta[1]=risposte[8];
        }
        return risposta;
    }
}
