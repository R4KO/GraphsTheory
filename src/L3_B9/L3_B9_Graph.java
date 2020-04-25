/**
 * L3_B9_Graph.java
 */

package L3_B9;

import java.io.*;
import java.util.*;

public class L3_B9_Graph {
    private int nombreDeSommets;
    private int nombredArcs;
    public ArrayList<L3_B9_Arc> listArcs;
    private TreeMap<Integer, Integer> rangs;
    private int[][] matriceAdjacence;
    private int[][] matriceValeurs;

    public L3_B9_Graph() {
        listArcs  = new ArrayList<>();
        rangs = new TreeMap<>();
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
        for (L3_B9_Arc a : listArcs) {
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

        for (L3_B9_Arc a : listArcs) {
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

        setMatriceAdjacence(); // au cas où ça n'a pas été fait

        while (end){
            end = false;
            for (int i = 0; i < nombreDeSommets; i++) {
                predecesseur = false; // initialisation de la variable
                for (int j = 0; j < nombreDeSommets; j++) {
                    if (matriceAdjacence[j][i] == 1 || sommetsSupprimes.contains(i)) {
                        predecesseur = true;
                        break;
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

    public void calculRangs() {
        int rang = 0;
        boolean end = true; // Initialisé à true au cas où
        boolean predecessor;
        ArrayList<Integer> sommetsSupprimes = new ArrayList<>();

        setMatriceAdjacence(); // Au cas où ça n'a pas été fait
        System.out.println("* Méthode d'élimination des points d'entrée");

        while (end) {
            end = false; // au moins un tour de boucle

            System.out.println("Rang courant = " + rang);
            System.out.println("Point d'entrée : ");

            for (int i = 0; i < nombreDeSommets; i++) {
                predecessor = false; // initialisation de la variable
                for (int j = 0; j < nombreDeSommets; j++) {
                    if (matriceAdjacence[j][i] == 1 || rangs.containsKey(i)) {
                        // prédecesseur
                        predecessor = true;
                        break;
                    }
                }
                // Fin de test colonne
                if (!predecessor) {
                    // suppression
                    System.out.println(i + " ");
                    sommetsSupprimes.add(i);
                    rangs.put(i, rang);
                    end = true;
                }
            }

            while (!sommetsSupprimes.isEmpty()) {
                int lastValue = sommetsSupprimes.get(sommetsSupprimes.size() - 1);
                for (int i = 0; i < nombreDeSommets; i++) {
                    matriceAdjacence[lastValue][i] = 0;
                }
                sommetsSupprimes.remove((Integer) lastValue); // Confonds la valeur lastValue et l'index lastValue
            }
            rang++;
        }

        System.out.println("Graphe vide");
        System.out.println("Rangs calculés");

        /*
        Set set = rangs.entrySet();
        Iterator i = set.iterator();

        while (i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            System.out.print(me.getKey() + ": ");
            System.out.print(me.getValue() + "\t");
        }
        System.out.println();
         */

        System.out.print("Sommet\t");
        printKeys(rangs);
        System.out.println();

        System.out.print("Rang\t");
        printValues(rangs);
        System.out.println();
    }

    public static void printKeys(TreeMap t) {
        Set set = t.entrySet();
        Iterator i = set.iterator();

        while (i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            System.out.print(me.getKey() + "\t");
        }
    }

    public static void printValues(TreeMap t) {
        Set set = t.entrySet();
        Iterator i = set.iterator();

        while (i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            System.out.print(me.getValue() + "\t");
        }
    }

    @Override
    public String toString() {
        String output = "";
        output += nombreDeSommets + " sommets" + "\n"
                + nombredArcs + " arcs";
        return output;
    }
}