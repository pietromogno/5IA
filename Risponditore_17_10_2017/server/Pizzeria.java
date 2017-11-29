package server;

public class Pizzeria {

    final static int idFirstQuestion = 0;
    final static int idLastQuestion = 3;

    final static String menu = ""
            + "\nMenu pizzeria\nPizze:\n1.Margherita\t2.Diavola\n"
            + "Bevande:\n1.Cocacola\t2.Fanta\n";

    final static Object[][] questions = {
        {"questo e' il nostro menu:" + menu + "Cosa vuoi fare? Ordinare(pizza, bevanda) o (finire)?",
            new Object[][]{{"pizza", 1}, {"bevanda", 2}, {"finire", 3}, {"", 4}}},
        {"quale pizza vuoi ordinare? (Margherita, Diavola) oppure preferisci una (bevanda) o (finire)?",
            new Object[][]{{"margherita", 1}, {"diavola", 1}, {"bevanda", 2}, {"finire", 3}, {"", 4}}},
        {"quale bevanda vuoi ordinare? (Cocacola, Fanta) oppure vuoi una (pizza) o (finire)?",
            new Object[][]{{"cocacola", 2}, {"fanta", 2}, {"pizza", 1}, {"finire", 3}, {"", 4}}},
        {"grazie per aver scelto la nostra pizzeria. Arivederci!", null},
        {"scusami, Non ho capito. Potresti ripetere la risposta alla domanda precedente?",
            new Object[][]{{"pizza", 1}, {"margherita", 1}, {"diavola", 1}, {"bevanda", 2}, {"cocacola", 2}, {"fanta", 2}, {"finire", 3}, {"", 4},}}
    };

    public static int getMenuLength() {
        int menuLength = 1;
        for (int i = 0; i < menu.length(); i++) {
            if (menu.charAt(i) == '\n') {
                menuLength++;
            }
        }
        return menuLength;
    }

    public static Object[] getFirstQuestion() {
        return new Object[]{(int) (idFirstQuestion), (String) (questions[idFirstQuestion][0])};
    }

    public static Object[] getLastQuestion() {
        return new Object[]{(int) (idLastQuestion), (String) (questions[idLastQuestion][0])};
    }

    public static Object[] getQuestion(int idQuestion, String answear) {
        Object[][] transitions = (Object[][]) (questions[idQuestion][1]);
        int idAnswear = 0;
        while ((!transitions[idAnswear][0].equals(answear.toLowerCase())) && idAnswear < transitions.length - 1) {
            idAnswear++;
        }
        return new Object[]{(int) transitions[idAnswear][1], (String) (questions[(int) (transitions[idAnswear][1])][0])};
    }

}
