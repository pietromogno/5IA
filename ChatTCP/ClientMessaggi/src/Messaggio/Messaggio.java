package Messaggio;

import java.io.Serializable;

/**
 *
 * @author Francesco Forcellato
 */
public class Messaggio implements Serializable {

    private String mittente;
    private String destinatario;
    private String messaggio;
    private String ora;

    public Messaggio(String mittente, String destinatario, String messaggio, String ora) {
        this.mittente = mittente;
        this.destinatario = destinatario;
        this.messaggio = messaggio;
        this.ora = ora;
    }

    public String getMittente() {
        return mittente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public String getOra() {
        return ora;
    }

    public void setMittente(String mittente) {
        this.mittente = mittente;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    @Override
    public String toString() {
        return "Messaggio{" + "mittente=" + mittente + ", destinatario=" + destinatario + ", messaggio=" + messaggio + ", ora=" + ora + '}';
    }

}
