package favaro.matteo.tris3;

public class Tris {
    private String[][] board;
    private static final int rows = 3;
    private static final int columns = 3;
    // Metodo Costruttore
    // Creo una matrice 3*3 vuota
    public Tris() {
        board = new String[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = " ";
            }
        }
    }
    // Metodo che restituisce lo stato della matrice
    public String tostring() {
        String r = "";
        for (int i = 0; i < rows; i++) {
            r = r + "|";
            for (int j = 0; j < columns; j++) {
                r = r + board[i][j];
            }
            r = r + "|\n";
        }
        return r;
    }
    // Metodo che permette di inserire una giocata
    public void set(int i, int j, String player) {
        if (board[i][j] != " ") {
            throw new IllegalArgumentException("Posizione occupata");
        }
        board[i][j] = player;
    }
    // Metodo che restituisce il nome del giocatore vincitore
    public String getWinner() {
        int k = 0;
        int h = 0;
        int i;
        // Verifico se il tris è presente in una riga
        for (i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (board[i][j] == "x") {
                    k++;
                    if (k == 3) {
                        return " VINCE IL GIOCATORE X ";
                    }
                } else {
                    if (board[i][j] == "o") {
                        h++;
                        if (h == 3) {
                            return " VINCE IL GIOCATORE O ";
                        }
                    }
                }
            }
            k = 0;
            h = 0;
        }
        // Verifico se il tris è presente in una colonna
        for (i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                if (board[j][i] == "x") {
                    k++;
                    if (k == 3) {
                        return " VINCE IL GIOCATORE X ";
                    }
                } else {
                    if (board[j][i] == "o") {
                        h++;
                        if (h == 3) {
                            return " VINCE IL GIOCATORE O ";
                        }
                    }
                }
            }
            k = 0;
            h = 0;
        }
        // Verifico se il tris è presente in una diagonale
        for (i = 0; i < rows; i++) {
            int j = i;
            if (board[i][j] == "x") {
                k++;
                if (k == 3) {
                    return " VINCE IL GIOCATORE X ";
                }
            } else {
                if (board[i][j] == "o") {
                    h++;
                    if (h == 3) {
                        return " VINCE IL GIOCATORE O ";
                    }
                }
            }
        }
        k = 0;
        h = 0;
        int j = 2;
        for (i = 0; i < rows; i++) {
            if (board[i][j] == "x") {
                k++;
                if (k == 3) {
                    return " VINCE IL GIOCATORE X ";
                }
            } else {
                if (board[i][j] == "o") {
                    h++;
                    if (h == 3) {
                        return " VINCE IL GIOCATORE O ";
                    }
                }
            }
            j--;
        }
        return "";
    }
    public boolean isFull(){
        boolean f=true;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(board[i][j]==" "){
                    f=false;
                }
            }
        }
        return f;
    }
}