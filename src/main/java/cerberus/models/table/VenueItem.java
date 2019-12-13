package cerberus.models.table;

import cerberus.party.Venue;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VenueItem extends RecursiveTreeObject<VenueItem> {
    private StringProperty address;
    private DoubleProperty cost;

    private Venue venue;

    public VenueItem(Venue venue) {
        this.address = new SimpleStringProperty(venue.getPath());
        this.cost = new SimpleDoubleProperty(venue.getCost());
        this.venue = venue;
    }

    public StringProperty getAddress() {
        return address;
    }

    public DoubleProperty getCost() {
        return cost;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
}
