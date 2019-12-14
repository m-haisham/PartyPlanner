package cerberus.party;

import cerberus.party.decorations.QuantifiedDecoration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Party {

    public static double prepaymentPercent = 0.15;
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma dd MMM yyyy");

    private String label;
    private Venue venue;
    private Duration on;
    private double paidPercentile;
    protected LocalDateTime created;
    private ArrayList<Contact> contacts;
    private Contact contact;
    private ArrayList<QuantifiedDecoration> decorations;

    public Party(String label, Venue venue, Duration on) {
        this.label = label;
        this.venue = venue;
        this.on = on;

        paidPercentile = 0;
        created = LocalDateTime.now();
        contacts = new ArrayList<>();
        decorations = new ArrayList<>();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Duration getOn() {
        return on;
    }

    public void setOn(Duration on) {
        this.on = on;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void addContact(Contact contact) {
        this.contacts.add(contact);
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public ArrayList<QuantifiedDecoration> getDecorations() {
        return decorations;
    }

    public void setDecorations(ArrayList<QuantifiedDecoration> decorations) {
        this.decorations = decorations;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public double getPaidPercentile() {
        return paidPercentile;
    }

    public void setPaidPercentile(double paidPercentile) {
        this.paidPercentile = paidPercentile;
    }
}
