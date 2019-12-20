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
                new Venue("Chaply", 1407),
                new Venue("Artificial Beach", 2300),
                new Venue("Anantara Veli Resort", 19122),
                new Venue("Cocoa Island", 34468),
                new Venue("Paradise Island", 6533),
                new Venue("Constance Halaveli", 23024),
                new Venue("Taj Exotica", 15420),
                new Venue("Gobiig Maldives", 1350)
        ));
    }
}
