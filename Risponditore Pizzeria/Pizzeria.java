
import java.net.Socket;



/**
 *
 * @author Federico Doria
 */
public class Pizzeria {
    Socket s;
    private String domande[] = {"Che pizza desideri?", "Vuoi una bibita?", "che bibita desideri?","confermi l'ordine?"};
    private String pizze[] = {"Margherita", "Viennese", "Funghi", "Capricciosa"};
    private String bibita[] = {"Coca Cola", "Fanta", "Sprite", "Acqua"};
    public String ris;
    private int d;
    public Pizzeria(){
        this.s=s;
        d=0;
        System.out.println("Benvenuto!");
     
    }
    String getDomanda(int d) {
        String risultato = null;
        if (d==0){
            risultato=domande[0];
            d++;
        }else if(d==1 && ris.equals("si")){
           risultato=domande[2]; 
           d++;
        }else if(d==1 && ris.equals("no")){
             risultato=domande[3]; 
             d+=2;
        }else if(d==3 && ris.equals("no")){
             risultato="rincominciamo l'ordine!";
             d=0;
        }
    return risultato;
    }
}

