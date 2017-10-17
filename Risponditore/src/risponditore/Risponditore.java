package risponditore;

import javax.swing.JOptionPane;

/**
 * @author Emanuele Pagotto
 */
public class Risponditore {

    private class Node {

        String message;
        String[] answers;
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
    private Node current,end;
    private int total = 0;

    public Risponditore() {
        Node welcome = new Node("Benvenuto al negozio di videogiochi, cosa vuoi comprare?", new String[]{"Un gioco", "Una console"}, 0);
        Node games = new Node("Che gioco vuoi comprare?", gamesList[1], 1);
        Node consoles = new Node("Che Console vuoi comprare?", consolesList[1], 2);
        Node checkout = new Node("Il totale è:", 3);
        Node cya = new Node("Arrivederci!", 4);
        automa = new Node[][]{
            {games, consoles, checkout},
            {welcome},
            {welcome},
            {cya}
        };
        current = welcome;
        end = cya;
    }

    public String getCurrentState() {
        return current.message;
    }
        
    public String getPossibleAnswers(){
        String ris = "";
        for(int i = 0; i < current.answers.length;i++){
            ris += i + ":" + current.answers[i]+", ";
        }
        ris = ris.substring(0, ris.length()-1);
        return ris;
    }
    
    public boolean isConversationEnded(){
        return current == end;
    }

    public void executeUpdate(int input) {
        //current = automa[current.nodeId][input];
        JOptionPane.showMessageDialog(null, "Diocane!", "Macello!", JOptionPane.ERROR_MESSAGE);
    }
}