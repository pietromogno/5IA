/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverpizzeria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author manuel.vivian
 */
public class Pizzeria {

    private String[][] grafo;
    private String[] domandeStato;
    int coordinataRiga;
    int coordinataColonna;
    String messaggioClient;
    String nomeClient;
    String erroneo;

    void Pizzeria() {
        erroneo = "Scelta non contemplata.";
        coordinataRiga = 0;
        coordinataColonna = 0;
        grafo = new String[6][6];
        domandeStato = new String[6];
        domandeStato[0] = "Inserisci nome:";
        domandeStato[1] = "Vuoi una pizza? [SI/NO]";
        domandeStato[2] = "Scegli dalla lista: [numero]"
                + "\n" + "1.Margherita;" + "\n" + "2.Viennese;" + "\n" + "3.Marinara;"
                + "\n" + "4.Patatosa";
        domandeStato[3] = "Vuoi una bibita? [SI/NO]";
        domandeStato[4] = "Scegli dalla lista:"
                + "\n" + "1.Coca cola;" + "\n" + "2.Fanta;" + "\n" + "3.Birra";
        domandeStato[5] = "Arrivederci!";
        //grafo[1][2]="Vuoi una pizza?";
        grafo[1][2] = "SI";
        //		+ "\n"+ "1.Margherita;"+ "\n"+ "2.Viennese;"+ "\n"+ "3.Marinara;"
        //		+ "\n"+ "4.Patatosa;";
        grafo[1][3] = "NO";
        //grafo[3][4]="
        grafo[3][4] = "NO";
        grafo[3][5] = "SI";
        int n = 0;
    }

    public void getStato(BufferedReader input, PrintWriter output) throws IOException {
        int n;

        if (coordinataRiga == 0) {//se non si ha appena iniziato
            output.write(domandeStato[coordinataRiga]);
            nomeClient = input.readLine();
            output.write("Benvenuto, " + nomeClient + "!");
        }

        output.write(nomeClient + domandeStato[coordinataRiga]);
        while (messaggioClient.equals(grafo[1][2]) || messaggioClient.equals(grafo[1][3])) {
            messaggioClient = input.readLine().toUpperCase();
            if (messaggioClient.equals(grafo[1][2])) {
                coordinataRiga++;//passa allo stato "menu pizze"
            } else {
                if (messaggioClient.equals(grafo[1][3])) {
                    coordinataRiga = 3;//passa allo stato "vuoi bibita?"
                } else { //non è stato inserito ne SI o NO
                    output.write(erroneo);
                }
            }
        }//end primo while

        output.write(domandeStato[coordinataRiga]);
        messaggioClient = input.readLine().toUpperCase();
        if (coordinataRiga == 2) {//sono in stato "menu pizza"
            boolean corretto = false;
            n = Integer.parseInt(messaggioClient);
            while (!corretto) {
                if (n > 0 && n <= 4) {
                    output.write("Ok");
                    coordinataRiga++;//passo a stato "vuoi bibita?"
                    corretto = true;
                } else {
                    output.write(erroneo);
                }
            }//end secondo while
        } else {//sono in stato "vuoi bibita?"
            while (messaggioClient.equals(grafo[3][4]) || messaggioClient.equals(grafo[3][5])) {
                messaggioClient = input.readLine().toUpperCase();
                if (messaggioClient.equals(grafo[3][4])) {
                    coordinataRiga++;//passa allo stato "lista bibite"
                } else {
                    if (messaggioClient.equals(grafo[3][5])) {
                        coordinataRiga = 4;//passa allo stato "arrivederci"
                    } else { //non è stato inserito ne SI o NO
                        output.write(erroneo);
                    }
                }
            }
        }

        output.write(domandeStato[coordinataRiga]);
        messaggioClient = input.readLine().toUpperCase();
        if (coordinataRiga == 3) {//sono in stato "lista bibite"
            boolean corretto = false;
            n = Integer.parseInt(messaggioClient);
            while (!corretto) {
                if (n > 0 && n <= 3) {
                    output.write("Ok");
                    coordinataRiga++;//passo a stato "arrivederci"
                    corretto = true;
                } else {
                    output.write(erroneo);
                }
            }

        }
        output.write(domandeStato[coordinataRiga]);//stato arrivederci
    }
}

