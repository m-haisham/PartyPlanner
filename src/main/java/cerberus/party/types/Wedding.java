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
        Document document = super.write(nitriteMapper);

        document.put("bride", gson.toJson(getBride()));
        document.put("groom", gson.toJson(getGroom()));

        return document;
    }

    @Override
    public void read(NitriteMapper nitriteMapper, Document document) {
        if (document != null) {
            Gson gson = new Gson();

            super.read(nitriteMapper, document);
            setBride(gson.fromJson((String) document.get("bride"), Person.class));
            setGroom(gson.fromJson((String) document.get("groom"), Person.class));

        }
    }
}
