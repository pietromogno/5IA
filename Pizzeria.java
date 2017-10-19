package pizzeria;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;

/*******************************************************************************
 * @author Pietro Mogno & Simone Trevisan 5^IA
 * @date 16/10/2017
 ******************************************************************************/

class Albero<E> {
    public E element;
    public Albero<E> parent;
    public Albero<E> leftChild;
    public Albero<E> rightChild;
    public Albero(E e) {
        this.element = e;
    }
}

public class Pizzeria<E extends Comparable<E>> {
    Albero<E> radice = null;

    String[][] menuPizze = {{"margherita", "3"}, {"patatosa", "5"}, {"salsiccia", "6"}, {"funghi", "5"}, {"marinara", "2"}, {"diavola", "5"}};
    String[][][] menuBibite = {{{"birra", " piccola", "5"}, {"birra", " grande", "6"}}, {{"cocacola", " piccola", "3"}, {"cocacola", " grande", "4"}}, {{"the", " piccola", "3"}, {"the", " grande", "4"}}, {{"fanta", " piccola", "3"}, {"fanta", " grande", "4"}},};

    List<String> pizze = new ArrayList<>();
    List<String> bibite = new ArrayList<>();
    
    public Pizzeria() {
        radice = new Albero<>((E) "Che pizza desideri ordinare? Scrivi BASTA quando hai finito, abbiamo ");
        Albero<E> nodo = radice;
        nodo.leftChild = new Albero<>((E) "Che bibita desideri ordinare? Scrivi BASTA quando hai finito");
        nodo.rightChild = radice;
        nodo = nodo.leftChild;
        nodo.leftChild = new Albero<>((E) "Grazie! ");
        nodo.rightChild = nodo;
    }

    public List<String> insPizze(PrintWriter out, String nome, BufferedReader in, int ctrl) throws IOException {
        Integer prezzo = 0;
        out.println(radice.element);
        String tipo = "vuoto";
        while (!tipo.equals("BASTA")) {
            tipo = in.readLine();
            if (!tipo.equals("BASTA")) {
                System.out.println(nome + " ha ordinato una " + tipo);
                boolean vai = false;
                for (int i = 0; i < menuPizze.length; i++) {
                    if (tipo.equals(menuPizze[i][0])) {
                        prezzo = prezzo + parseInt(menuPizze[i][1]);
                        vai = true;
                        break;
                    }
                }
                if (vai) {
                    pizze.add(tipo);
                } else {
                    System.out.println("la " + tipo + " non è sul menu");
                }
                radice = radice.rightChild;
                out.println(radice.element);
            }

        }
        pizze.add(0, prezzo + "€");
        radice = radice.leftChild;
        System.out.println(nome + " non desidera altro da mangiare");
        out.println(radice.element);
        return pizze;
    }

    public List<String> insBibite(PrintWriter out, String nome, BufferedReader in, int ctrl) throws IOException {
        Integer prezzo = 0;
        String tipo = "vuoto";
        String size = "vuoto";
        while (!tipo.equals("BASTA")) {
            tipo = in.readLine();
            if (!tipo.equals("BASTA")) {

                boolean vai = false;
                for (int i = 0; i < menuBibite.length; i++) {
                    if (tipo.equals(menuBibite[i][0][0])) {
                        out.println("piccola o grande?");
                        size = in.readLine();
                        System.out.println(nome + " ha ordinato una " + tipo + " " + size);

                        switch (size) {
                            case "piccola":
                                prezzo = prezzo + parseInt(menuBibite[i][0][2]);
                                break;
                            case "grande":
                                prezzo = prezzo + parseInt(menuBibite[i][1][2]);
                                break;
                        }
                        vai = true;
                        break;
                    }
                }
                if (vai) {
                    bibite.add(tipo + " " + size);
                } else {
                    System.out.println("la " + tipo + " non è sul menu");
                }
                radice = radice.rightChild;
                out.println(radice.element);
            }

        }
        bibite.add(0, prezzo + "€");
        radice = radice.leftChild;
        System.out.println(nome + " non desidera altro da bere");
        out.println(nome + " il tuo ordine è : " + pizze + " da mangiare, e: " + bibite + " da bere " + radice.element);
        return bibite;
    }
}
