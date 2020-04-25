/**
 * L3_B9_Graph.java
 */

package L3_B9;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class L3_B9_Graph {
    private int nombreDeSommets;
    private int nombredArcs;
    private ArrayList<L3_B9_Arc> list;
    private int[][] matriceAdjacence;
    private int[][] matriceValeurs;

    public L3_B9_Graph() {
    list  = new ArrayList<>();
    }

    public void setNombreDeSommets(int nombreDeSommets) {
        this.nombreDeSommets = nombreDeSommets;
    }

    public void setNombredArcs(int nombredArcs) {
        this.nombredArcs = nombredArcs;
    }

    public int getNombreDeSommets() {
        return nombreDeSommets;
    }

    public int getNombredArcs() {
        return nombredArcs;
    }

    private void setMatriceAdjacence() {
        matriceAdjacence = new int[nombreDeSommets][nombreDeSommets];
        for (L3_B9_Arc a : list) {
            matriceAdjacence[a.getOrigine()][a.getDestination()] = 1;
        }
    }

    public void printMatriceAdjacence() {
        setMatriceAdjacence();

        System.out.println("Matrice d'adjacence");
        for (int i = 0; i < nombreDeSommets; i++) {
            System.out.print("\t" + i);
        }
        System.out.println();
        for (int i = 0; i < nombreDeSommets; i++) {
            System.out.print(i + "\t");
            for (int j = 0; j < nombreDeSommets; j++) {
                System.out.print(matriceAdjacence[i][j] + "\t");
            }
            System.out.println();
        }
    }

    private void setMatriceValeurs() {
        matriceValeurs = new int[nombreDeSommets][nombreDeSommets];
        for (int i = 0; i < nombreDeSommets; i++)
            for (int j = 0; j < nombreDeSommets; j++)
                matriceValeurs[i][j] = -1;

        for (L3_B9_Arc a : list) {
            matriceValeurs[a.getOrigine()][a.getDestination()] = a.getValeur();
        }
    }

    public void printMatriceValeurs() {
        setMatriceValeurs();

        System.out.println("Matrice des valeurs");
        for (int i = 0; i < nombreDeSommets; i++) {
            System.out.print("\t" + i);
        }
        System.out.println();
        for (int i = 0; i < nombreDeSommets; i++) {
            System.out.print(i + "\t");
            for (int j = 0; j < nombreDeSommets; j++) {
                if (matriceValeurs[i][j] == -1) {
                    System.out.print("*\t");
                } else {
                    System.out.print(matriceValeurs[i][j] + "\t");
                }
            }
            System.out.println();
        }
    }

    public boolean detectCircuit() {
        boolean predecesseur; // Check a predecessor
        boolean end = true; // Initialisé à true au cas où aucun sommet est supprimé
        ArrayList<Integer> sommetsSupprimes = new ArrayList<>();

        while (end){
            end = false;
            for (int i = 0; i < nombreDeSommets; i++) {
                predecesseur = false; // initialisation de la variable
                for (int j = 0; j < nombreDeSommets; j++) {
                    if (matriceAdjacence[j][i] == 1 || sommetsSupprimes.contains(i)) {
                        predecesseur = true;
                    }
                }
                if (!predecesseur) {
                    // pas de prédecesseur donc suppression
                    System.out.println("Le sommet " + i + " n'a pas de prédecesseur");
                    System.out.println("suppression...");
                    sommetsSupprimes.add(i);
                    end = true;
                }
            }

            for (int i = 0; i < nombreDeSommets; i++) {
                for (int j : sommetsSupprimes) {
                    if (j == i) {
                        for (int k = 0; k < nombreDeSommets; k++) {
                            matriceAdjacence[i][k] = 0;
                        }
                    }
                }
            }
        }

        if (sommetsSupprimes.size() == nombreDeSommets) {
            // Aucun sommet supprimés
            System.out.println("Pas de circuit");
            return false;
        }

        System.out.println("Sommets restants:");
        for (int i = 0; i < nombreDeSommets; i++) {
            if(!sommetsSupprimes.contains(i)) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
        
        System.out.println("Il y a au moins un circuit");
        return true;
    }

    @Override
    public String toString() {
        String output = "";
        output += nombreDeSommets + " sommets" + "\n"
                + nombredArcs + " arcs";
        return output;
    }

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
                g1.list.add(new L3_B9_Arc(origine, destination, valeur));
            }

            // Affichage de la matrice d'adjacence
            System.out.println("* Représentation du graphe sous forme matricielle");
            g1.printMatriceAdjacence();

            // Affichage de la matrice des valeurs
            System.out.println();
            g1.printMatriceValeurs();

            // Détection de circuit
            g1.detectCircuit();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}