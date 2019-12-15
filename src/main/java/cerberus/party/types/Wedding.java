package cerberus.party.types;

import cerberus.party.*;
import com.google.gson.Gson;
import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;

public class Wedding extends Party implements Mappable {

    private Person wife;
    private Person husband;

    public Wedding() {
        this("", Venue.empty(), Duration.zero());
    }

    public Wedding(String label, Venue venue, Duration on) {
        this(label, venue, on, Person.empty(), Person.empty());
    }

    public Wedding(String label, Venue venue, Duration on, Person wife, Person husband) {
        super(label, venue, on);
        this.wife = wife;
        this.husband = husband;
    }

    public Person getHusband() {
        return husband;
    }

    public void setHusband(Person husband) {
        this.husband = husband;
    }

    public Person getWife() {
        return wife;
    }

    public void setWife(Person wife) {
        this.wife = wife;
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
            Wedding b = gson.fromJson((String) document.get(this.getClass().getName()), this.getClass());

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
            this.setWife(b.getWife());
            this.setHusband(b.getHusband());
        }
    }
}
