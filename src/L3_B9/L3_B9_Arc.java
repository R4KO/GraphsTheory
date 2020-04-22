package L3_B9;

public class L3_B9_Arc {
    private int origine;
    private int destination;
    private int valeur;

    public L3_B9_Arc(int origine, int destination, int valeur) {
        this.origine = origine;
        this.destination = destination;
        this.valeur = valeur;
    }

    public int getOrigine() {
        return origine;
    }

    public int getDestination() {
        return destination;
    }

    public int getValeur() {
        return valeur;
    }

    @Override
    public String toString() {
        String output = "";
        output += origine
                + " -> "
                + destination
                + " = "
                + valeur
                + "\n";
        return output;
    }
}
