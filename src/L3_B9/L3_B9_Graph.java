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
    private TreeMap<Integer, Integer> DAPTo; // Date au plus tôt
    private TreeMap<Integer, Integer> DAPTa; // Date au plus tard
    private int[][] matriceAdjacence;
    private int[][] matriceValeurs;

    // pour l'ordonnancement
    private int entree;
    private int sortie;

    public L3_B9_Graph() {
        listArcs  = new ArrayList<>();
        rangs = new TreeMap<>();
        DAPTo = new TreeMap<>();
        DAPTa = new TreeMap<>();
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
                matriceValeurs[i][j] = Integer.MAX_VALUE;

        for (L3_B9_Arc a : listArcs) {
            matriceValeurs[a.getOrigine()][a.getDestination()] = a.getValeur();
        }
    }

    public void printMatriceValeurs() {
        setMatriceValeurs(); // Au cas où

        System.out.println("Matrice des valeurs");
        for (int i = 0; i < nombreDeSommets; i++) {
            System.out.print("\t" + i);
        }
        System.out.println();
        for (int i = 0; i < nombreDeSommets; i++) {
            System.out.print(i + "\t");
            for (int j = 0; j < nombreDeSommets; j++) {
                if (matriceValeurs[i][j] == Integer.MAX_VALUE) {
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
                            // supprimmer l'adjacence pour éviter de boucler à l'infini
                            matriceAdjacence[i][k] = 0;
                        }
                    }
                }
            }
        }

        if (sommetsSupprimes.size() == nombreDeSommets) {
            // Tous les sommets sont supprimés
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


    // Partie 2
    public boolean checkOrdonnancement() {
        boolean predecessor;
        boolean successor;
        int nombreEntree = 0;
        int nombreSortie = 0;

        // Vérifier présence de valeurs négatives
        for (L3_B9_Arc a : listArcs) {
            if (a.getValeur() < 0) {
                System.out.println("Dans l'arc " + a.getOrigine() + " -> " + a.getDestination() + " = " + a.getValeur());
                System.out.println("La valeur est négative.");
                System.out.println("Ce graphe ne peut pas être un graphe d'ordonnancement.");
                return false;
            }
        }

        setMatriceAdjacence(); // Au cas où ça n'a pas été fait
        // 1 point d'entrée et 1 point de sortie
        for (int i = 0; i < nombreDeSommets; i++) {
            // initialisation avant tour de boucle
            predecessor = false;
            successor = false;
            for (int j = 0; j < nombreDeSommets; j++) {
                if (matriceAdjacence[j][i] == 1) {
                    predecessor = true;
                }

                if (matriceAdjacence[i][j] == 1) {
                    successor = true;
                }
            }

            if (!predecessor) {
                // point d'entrée
                nombreEntree++;
                entree = i;
                DAPTo.put(entree, 0); // 0 car point d'entrée
            }

            if (!successor) {
                // point de sortie
                nombreSortie++;
                sortie = i;
            }
        }

        if (nombreEntree != 1) {
            System.out.println("Sommet d'entrée non unique");
            System.out.println("Il ne s'agit pas d'un graphe d'ordonnancement");
            return false;
        }

        if (nombreSortie != 1) {
            System.out.println("Sommet de sortie non unique");
            System.out.println("Il ne s'agit pas d'un graphe d'ordonnancement");
            return false;
        }

        System.out.println("Il y a un point d'entrée et de sortie unique");

        //Les différents arcs d'un sommets doivent avoir la même valeur
        ArrayList<Integer> valeursArc = new ArrayList<>();

        for (int i = 0; i < nombredArcs; i++) {
            for (L3_B9_Arc a : listArcs) {
                if (a.getOrigine() == i) { // Si l'origine match le sommet courant
                    valeursArc.add(a.getValeur()); // Stockage de la valeur du sommet
                }
            }

            // Comparaison des valeurs 2 à 2
            for (int j = 0; j < valeursArc.size() - 1; j++) {
                if (valeursArc.get(j) != valeursArc.get(j + 1)) {
                    System.out.println("2 valeurs différentes pour un arc sortant du sommet " + i + " (" + valeursArc.get(j) + " et " + valeursArc.get(j + 1) + ")");
                    System.out.println("Ce n'est pas un graphe d'ordonnancement");
                    return false;
                }
            }
            valeursArc.clear(); // Clear avant de stocker d'autres valeurs d'arcs
        }
        System.out.println("La valeur des arcs sortants d'un même sommet sont les mêmes");

        // Vérifier la nullité des valeurs sortants du sommet d'entrée
        for (L3_B9_Arc a : listArcs) {
            if (a.getOrigine() == entree) {
                // On verifie la valeur des arcs qui ont comme origine l'entrée
                if (a.getValeur() !=0) {
                    System.out.println("L'arc " + a.getOrigine() + " -> " + a.getDestination() + " a comme valeur " + a.getValeur());
                    System.out.println("Cette valeur est non nulle");
                    System.out.println("Ce n'est pas un graphe d'ordonnancement");
                    return false;
                }
            }
        }
        System.out.println("Les sommets sortants du sommet d'entrée ont une valeur nulle");

        // Si tous les tests sont passés
        System.out.println("C'est un graphe d'ordonnancement");
        return true;

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