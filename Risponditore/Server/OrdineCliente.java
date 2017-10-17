package server;

/**
 * @author Davide Porcu
 */
public class OrdineCliente {

    private String nome;
    private int numeroOrdine;
    private Carrello nuovoOrdine;

    public OrdineCliente(int numeroOrdine) {
        this.nome = "";
        this.numeroOrdine = numeroOrdine;
        nuovoOrdine = new Carrello();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public int getNumeroCliente() {
        return numeroOrdine;
    }

    public void addProdotto(Prodotto p) {
        nuovoOrdine.addProdotto(p);
    }

    public String[] getScontrinoOrdine() {
        return nuovoOrdine.toStringArray();
    }

}

