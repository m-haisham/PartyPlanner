package cerberus.party.decorations;

public class Decoration {
    private String label;
    private double cost;

    public Decoration(String label, double cost) {
        this.label = label;
        this.cost = cost;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
