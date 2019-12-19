package cerberus.party;

import cerberus.party.addons.QuantifiedAddon;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Basic Party class that provides general information
 */
public class Party {

    private String label;
    private Venue venue;
    private Duration on;
    private double paidPercentile;
    protected LocalDateTime created;
    private ArrayList<Contact> contacts;
    private Contact contact;
    private ArrayList<QuantifiedAddon> addons;


    public static double prepaymentPercent = 0.15;
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma dd MMM yyyy");

    /**
     * default constructor
     * @param label label
     * @param venue place where party is to be held
     * @param on the from, to time and their duration
     */
    public Party(String label, Venue venue, Duration on) {
        this.label = label;
        this.venue = venue;
        this.on = on;

        paidPercentile = 0;
        created = LocalDateTime.now();
        contacts = new ArrayList<>();
        addons = new ArrayList<>();
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

    /**
     * @param contact adds this to contacts
     */
    public void addContact(Contact contact) {
        this.contacts.add(contact);
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public ArrayList<QuantifiedAddon> getAddons() {
        return addons;
    }

    public void setAddons(ArrayList<QuantifiedAddon> addons) {
        this.addons = addons;
    }

    /**
     * @return total cost of the addons
     */
    public double getAddonsCost() {
        if (addons.size() <= 0)
            return 0;

        double total = 0;
        for (QuantifiedAddon addon : addons) {
            total += addon.getQuantity() * addon.getCost();
        }

        return total;
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

    public double getTotalCost() {
        return venue.getCost() + getAddonsCost();
    }

    /**
     * filters the {@link ArrayList} to only contain T
     * @param parties list of parties to be filterd
     * @param type type to be filtered for
     * @param <T> type of class that is to be filtered
     * @return filtered parties
     */
    public static  <T extends Party> ArrayList<Party> filter(ArrayList<Party> parties, Class<T> type) {
        ArrayList<Party> list = new ArrayList<>();
        for (Party party : parties) {
            if (party.getClass() == type) {
                list.add(party);
            }
        }

        return list;
    }
}
