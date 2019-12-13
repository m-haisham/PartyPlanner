package cerberus.party.types;

import cerberus.party.Venue;
import cerberus.party.Contact;
import cerberus.party.Duration;
import cerberus.party.Party;

import java.util.ArrayList;

public class Farewell extends Party {
    private ArrayList<Contact> group;


    public Farewell(String label, Venue venue, Duration on) {
        super(label, venue, on);
        this.group = new ArrayList<>();
    }

    public ArrayList<Contact> getGroup() {
        return group;
    }

    public void setGroup(ArrayList<Contact> group) {
        this.group = group;
    }
}
