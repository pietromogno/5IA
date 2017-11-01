package oggettiperchat;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Davide Porcu
 */
public class Conversazione implements Serializable, Comparable<Conversazione> {
    
    public static final long serialVersionUID = 20170721L;
    
    private String nomeConversazione;

    private ArrayList<Contatto> listaPartecipanti;//nrei gruppi compreso anche me
    private ArrayList<Messaggio> listaMessaggi;
    private int idDestinatario;
    private boolean isGruppo;
    private boolean hasMessaggiDaLeggere;

    public Conversazione() {
        this.idDestinatario = -1;
        this.listaPartecipanti = new ArrayList<>();
        this.listaMessaggi = new ArrayList<>();
        this.isGruppo = false;
        this.nomeConversazione = "";
        this.hasMessaggiDaLeggere = false;
    }

    public Conversazione(String nomeConversazione, int idDestinatario, ArrayList<Contatto> listaPartecipanti, ArrayList<Messaggio> listaMessaggi, boolean isGruppo, boolean hasMessaggiDaLeggere) {
        this.nomeConversazione = nomeConversazione;
        this.idDestinatario = idDestinatario;
        this.listaPartecipanti = listaPartecipanti;
        this.listaMessaggi = listaMessaggi;
        this.isGruppo = isGruppo;
        this.hasMessaggiDaLeggere = hasMessaggiDaLeggere;
    }

    public int getIdDestinatario() {
        return idDestinatario;
    }

    public ArrayList<Contatto> getListaPartecipanti() {
        return listaPartecipanti;
    }

    public ArrayList<Messaggio> getListaMessaggi() {
        return listaMessaggi;
    }

    public boolean isGruppo() {
        return isGruppo;
    }

    public String getNomeConversazione() {
        return nomeConversazione;
    }

    public boolean hasMessaggiDaleggere() {
        return hasMessaggiDaLeggere;
    }

    public void setNomeConversazione(String nomeConversazione) {
        this.nomeConversazione = nomeConversazione;
    }

    public void setIdDestinatario(int idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public void setListaPartecipanti(ArrayList<Contatto> listaPartecipanti) {
        this.listaPartecipanti = listaPartecipanti;
    }

    public void setListaMessaggi(ArrayList<Messaggio> listaMessaggi) {
        this.listaMessaggi = listaMessaggi;
    }

    public void setIsGruppo(boolean isGruppo) {
        this.isGruppo = isGruppo;
    }

    public void addPartecipante(Contatto contatto) {
        this.listaPartecipanti.add(contatto);
    }

    public void addMessaggio(Messaggio msg) {
        this.listaMessaggi.add(msg);
    }

    public void setHasMessaggiDaLeggere(boolean hasMessaggiDaLeggere) {
        this.hasMessaggiDaLeggere = hasMessaggiDaLeggere;
    }
    
    public void rimuoviPartecipante(Contatto contatto){
        for(int i=0;i<listaPartecipanti.size();i++){
            if(listaPartecipanti.get(i).getIdAccount()==contatto.getIdAccount()){
               listaPartecipanti.remove(i);
               break;
            }
        }
        
    }

    //ordinare secondo ultimo messaggio inviato
    @Override
    public String toString() {
        return nomeConversazione;
    }

    @Override
    public int compareTo(Conversazione conversazione2) {
        if (this.listaMessaggi.isEmpty() && conversazione2.listaMessaggi.isEmpty()) {//non verrebbe mai usato ma va beh
            return this.nomeConversazione.compareTo(conversazione2.nomeConversazione);
        }
        if (this.listaMessaggi.isEmpty() && !conversazione2.listaMessaggi.isEmpty()) {//non verrebbe mai usato ma va beh
            return -1;
        }
        if (!this.listaMessaggi.isEmpty() && conversazione2.listaMessaggi.isEmpty()) {//non verrebbe mai usato ma va beh
            return 1;
        }
        return (conversazione2.listaMessaggi.get(conversazione2.listaMessaggi.size()-1).getDataInvio()).compareTo(this.listaMessaggi.get(this.listaMessaggi.size()-1).getDataInvio());///dalla più recente alla meno recente
    }

}
