package cerberus.party.types;

import cerberus.party.*;
import com.google.gson.Gson;
import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;

public class Wedding extends Party implements Mappable {

    private Person bride;
    private Person groom;

    public Wedding() {
        this("", Venue.empty(), Duration.zero());
    }

    public Wedding(String label, Venue venue, Duration on) {
        this(label, venue, on, Person.empty(), Person.empty());
    }

    public Wedding(String label, Venue venue, Duration on, Person bride, Person groom) {
        super(label, venue, on);
        this.bride = bride;
        this.groom = groom;
    }

    public Person getGroom() {
        return groom;
    }

    public void setGroom(Person groom) {
        this.groom = groom;
    }

    public Person getBride() {
        return bride;
    }

    public void setBride(Person bride) {
        this.bride = bride;
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
            this.setBride(b.getBride());
            this.setGroom(b.getGroom());
        }
    }
}
