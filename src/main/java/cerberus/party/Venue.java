package cerberus.party;

import java.util.ArrayList;
import java.util.Arrays;

public class Venue {
    private String path;
    private double cost;

    public Venue() {
        this("", 0);
    }

    public Venue(String path, double cost) {
        this.path = path;
        this.cost = cost;
    }

    public static Venue empty() {
        return new Venue("", 0);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public static ArrayList<Venue> mock() {
        return new ArrayList<>(Arrays.asList(
                new Venue("Chaple", 500),
                new Venue("Beach", 800),
                new Venue("Macro", 1000),
                new Venue("Sandiago", 5000),
                new Venue("Work house", 200)));
    }
}
