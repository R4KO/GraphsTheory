import L3_B9.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class L3_B9_Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choix;

        do {
            System.out.print("Quel graphe voulez-vous traiter ? (0 pour quitter) ");
            choix = sc.nextInt();
            readGraph(choix);
        } while (choix != 0);
    }

    public static void readGraph(int number) {
        String pathname = "L3-B9-" + number + ".txt";

        try {
            // Open file
            File f = new File(pathname);
            Scanner sc = new Scanner(f);

            // Declare a graph
            L3_B9_Graph g1 = new L3_B9_Graph();
            g1.setNombreDeSommets(sc.nextInt());
            g1.setNombredArcs(sc.nextInt());

            // Output
            System.out.println("* Lecture du graphe sur fichier");
            System.out.println(g1.toString());

            // Go through the file
            while (sc.hasNextInt()) {
                int origine = sc.nextInt();
                int destination =  sc.nextInt();
                int valeur = sc.nextInt();
                System.out.println(origine + " -> " + destination + " = " + valeur);
                g1.listArcs.add(new L3_B9_Arc(origine, destination, valeur));
            }

            // Affichage de la matrice d'adjacence
            System.out.println("* Représentation du graphe sous forme matricielle");
            g1.printMatriceAdjacence();

            // Affichage de la matrice des valeurs
            System.out.println();
            g1.printMatriceValeurs();

            // Détection de circuit
            boolean circuit = g1.detectCircuit();

            if (!circuit) {
                System.out.println("Le graphe " + number + " ne contient pas de circuit");
                g1.calculRangs();
            }

            // Vérifier l'ordonnancement
            boolean ordonnancement = g1.checkOrdonnancement();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
