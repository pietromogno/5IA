package server;

import java.util.Objects;

/**
 *
 * @author Davide Porcu
 */
public class Prodotto {
    
    private Categoria categoria;
    private String nomeProdotto;
    private double prezzo;

    public Prodotto(String nomeProdotto, Categoria categoria, double prezzo) {
        this.nomeProdotto = nomeProdotto;
        this.categoria = categoria;
        this.prezzo = prezzo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public double getPrezzo() {
        return prezzo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Prodotto other = (Prodotto) obj;
        if (!this.nomeProdotto.equalsIgnoreCase(other.nomeProdotto)) {
            return false;
        }
        if (!Objects.equals(this.nomeProdotto, other.nomeProdotto)) {
            return false;
        }
        if (Double.doubleToLongBits(this.prezzo) != Double.doubleToLongBits(other.prezzo)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return nomeProdotto;
    }
}
