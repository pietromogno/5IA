package objects;

/**
 * @author Emanuele Pagotto
 */
public class Message implements java.io.Serializable {
    String message, username ,password;
    int type; //cambia in base alla funzione che il messaggio deve svolgere. 0 indica login, 1 indica register, 2 indica standardMessage
    int src, dst; //tramite id si risale al destinatario e il destinatario può sapere da chi arriva al messaggio

    public Message(String message,int dst,int src) { //Costruttore per standardMessage
        this.message = message;
        this.type = 2;
        this.src = src;
        this.dst = dst;
    }

    public Message(String username, String password, int t) { //costruttore per messaggi di login o registrazione. true indica una richiesta di registrazione, false di login
        this.username = username;
        this.password = password;
        this.type = t;
    }

    public String toString() {
        return message;
    }

    public String getName() {
        return username;
    }

    public String getPw() {
        return password;
    }
    
    public String getMessage(){
        return message;
    }

    public int getType() {
        return type;
    }
    
    public int getSrc(){
        return src;
    }
    
    public int getDst(){
        return dst;
    }
}
