package cerberus.party.types;

import cerberus.party.Venue;
import cerberus.party.Contact;
import cerberus.party.Duration;
import cerberus.party.Party;
import com.google.gson.Gson;
import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;

import java.util.ArrayList;

public class Farewell extends Party implements Mappable {
    private ArrayList<Contact> group;


    public Farewell() {
        this("", Venue.empty(), Duration.zero());
    }

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


    @Override
    public Document write(NitriteMapper nitriteMapper) {
        Gson gson = new Gson();
        Document document = new Document();
        document.put(this.getClass().getName(), gson.toJson(this));
        return document;
    }

    @Override
    public void read(NitriteMapper nitriteMapper, Document document) {
        if (document != null) {
            Gson gson = new Gson();
            Farewell b = gson.fromJson((String) document.get(this.getClass().getName()), this.getClass());

            // generic
            this.setLabel(b.getLabel());
            this.setVenue(b.getVenue());
            this.setOn(b.getOn());
            this.setPaidPercentile(b.getPaidPercentile());
            this.created = b.getCreated();
            this.setContacts(b.getContacts());
            this.setContact(b.getContact());
            this.setAddons(b.getAddons());

            // specific
            this.setGroup(b.getGroup());
        }
    }
}
