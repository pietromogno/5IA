package server;

import java.util.ArrayList;

/**
 * @author Emanuele Pagotto
 */
public class Risponditore {

    private class Node {

        String message;
        String[] answers = null;
        int nodeId;

        public Node(String message, String[] answers, int nodeId) {
            this.message = message;
            this.answers = answers;
            this.nodeId = nodeId;
        }

        public Node(String message, int nodeId) {
            this.message = message;
            this.nodeId = nodeId;
        }

        public String getAnswers() {
            String ris = "";
            if (answers != null) {
                for (int i = 0; i < answers.length; i++) {
                    ris += i + ":" + answers[i] + " ";
                }
            }
            return ris;
        }

        public String[] getAnswersArray() {
            return answers;
        }

        public boolean compareTo(Node other) {
            return this.nodeId == other.nodeId;
        }

    }

    private final String[][] gamesList = {
        {"Bonestorm", "Chiamata della duty", "Chiamata della duty - Moneygrab edition", "MinieraCreare - Peggiore Together edition", "PockettoMonsta o'Sole"},
        {"30", "70", "100", "30", "40"}
    };
    private final String[][] consolesList = {
        {"Pleistescion cuattro", "Icsbocs uan", "Nintento Suicc", "Ouya (non è un meme?)"},
        {"275", "250", "310", "25"}
    };
    private Node[][] automa;
    private Node current, end;
    private ArrayList<String> cart;
    private int total;

    private Node welcome = new Node("Benvenuto al negozio di videogiochi, cosa vuoi comprare?", new String[]{"Un gioco", "Una console", "il conto,prego"}, 0);
    private Node games = new Node("Che gioco vuoi comprare?", gamesList[0], 1);
    private Node consoles = new Node("Che Console vuoi comprare?", consolesList[0], 2);
    private Node checkout = new Node("Il totale è:", 3);
    private Node cya = new Node("Arrivederci!", 4);

    public Risponditore() {
        automa = new Node[][]{
            {games, consoles, checkout},
            {welcome},
            {welcome},
            {cya}
        };
        current = welcome;
        end = cya;
        total = 0;
        cart = new ArrayList<>();
    }

    public String getCurrentState() {
        if (current == checkout) {
            return current.message + total;
        } else if (current == end) {
            return current.message;
        } else {
            return current.message + "\n" + current.getAnswers() + "\t totale: " + total;
        }
    }

    public int getCurrentId() {
        return current.nodeId;
    }

    public int addToCart(int input) {
        cart.add(current.getAnswersArray()[input]);
        if (current.equals(games)) {
            total += Integer.parseInt(gamesList[1][input]);
        } else if (current.equals(consoles)) {
            total += Integer.parseInt(consolesList[1][input]);
        }
        return 0;
    }

    public boolean isConversationEnded() {
        return current == checkout;
    }

    public void executeUpdate(int input) {
        current = automa[current.nodeId][(current == games || current == consoles) ? addToCart(input) : input];
    }
}
