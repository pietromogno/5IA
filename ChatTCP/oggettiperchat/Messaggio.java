package oggettiperchat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Davide Porcu
 */
public class Messaggio implements Serializable{
    
    public static final long serialVersionUID = 19990701L;
    
    public static final int LOGIN=0,REGISTRAZIONE=1,LOGOUT=2,CONFERMA=3,ERRORE=4,MESSAGGIO=5,MESSAGGIOGRUPPO=6,LISTACONTATTI=7,UPDATELISTACONTATTI=8,ADDCONTATTO=10,ADDGRUPPO=11;
    
    private int idMittente;
    private int idDestinatario;
    private int tipoMessaggio;
    private Object messaggio;
    private int idGruppo;
    private Date dataInvio;

    public Messaggio(){
        this.tipoMessaggio=MESSAGGIO;
        this.messaggio=null;
        this.idMittente=-1;
        this.idDestinatario=-1;
        this.idGruppo=-1;
        this.dataInvio=new Date();
    }
    
    public Messaggio(int tipoMessaggio,Object messaggio,int idMittente,int idDestinatario,int idGruppo, Date dataInvio){
        this.tipoMessaggio=tipoMessaggio;
        this.messaggio=messaggio;
        this.idMittente=idMittente;
        this.idDestinatario=idDestinatario;
        this.idGruppo=idGruppo;
        this.dataInvio=dataInvio;
    }

    public int getTipoMessaggio(){
        return tipoMessaggio;
    }
    
    public Object getMessaggio() {
        return messaggio;
    }

    public int getIdMittente() {
        return idMittente;
    }
    
    public int getIdDestinatario() {
        return idDestinatario;
    }

    public int getIdGruppo(){
        return idGruppo;
    }
    
    public Date getDataInvio(){
        return dataInvio;
    }
    
    public void setTipoMessaggio(int tipoMessaggio){
         this.tipoMessaggio=tipoMessaggio;
    } 
    
    public void setMessaggio(Object messaggio) {
        this.messaggio = messaggio;
    }

    public void setIdMittente(int idMittente) {
        this.idMittente = idMittente;
    }

     public void setIdDestinatario(int idDestinatario) {
        this.idDestinatario = idDestinatario;
    }
     
     public void setIdgruppo(int idGruppo){
         this.idGruppo=idGruppo;
     }
     
     public void setDate(Date data){
         this.dataInvio=data;
     }

    @Override
    public String toString() {
        return "Messaggio{" + "idMittente=" + idMittente + ", idDestinatario=" + idDestinatario + ", tipoMessaggio=" + tipoMessaggio + ", messaggio=" + messaggio + ", idGruppo=" + idGruppo + ", dataInvio=" + dataInvio + '}';
    }   
}