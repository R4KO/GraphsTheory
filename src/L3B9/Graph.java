package L3B9;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Graph {
    private int nombreDeSommets;
    private int nombredArcs;
    private ArrayList<Arc> list = new ArrayList<Arc>();

    public void setNombreDeSommets(int nombreDeSommets) {
        this.nombreDeSommets = nombreDeSommets;
    }

    public void setNombredArcs(int nombredArcs) {
        this.nombredArcs = nombredArcs;
    }

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
        Scanner sc = null;

        try {
            File f = new File(pathname);
            sc = new Scanner(f);
            Graph g1 = new Graph();
            g1.setNombreDeSommets(sc.nextInt());
            g1.setNombredArcs(sc.nextInt());
            System.out.println(g1.toString());
            while (sc.hasNextInt()) {
                int origine = sc.nextInt();
                int destination =  sc.nextInt();
                int valeur = sc.nextInt();
                System.out.println(origine + " -> " + destination + " = " + valeur);
                g1.list.add(new Arc(origine, destination, valeur));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}