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
        Document document = super.write(nitriteMapper);

        document.put("group", gson.toJson(getGroup(), Contact.arrayType));

        return document;
    }

    @Override
    public void read(NitriteMapper nitriteMapper, Document document) {
        if (document != null) {
            Gson gson = new Gson();

            super.read(nitriteMapper, document);
            setGroup(gson.fromJson((String) document.get("group"), Contact.arrayType));
        }
    }
}
