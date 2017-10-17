package server;

/**
 *
 * @author Davide Porcu
 */
public class Categoria {
    private String nomeCategoria;

    public Categoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Categoria other = (Categoria) obj;
        return this.nomeCategoria.equalsIgnoreCase(other.nomeCategoria);
    }

    public String toString() {
        return nomeCategoria;
    }
}
