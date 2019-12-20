package cerberus.party.types;

import cerberus.party.Venue;
import cerberus.party.Duration;
import cerberus.party.Party;
import com.google.gson.Gson;
import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;

public class Celebration extends Party implements Mappable {

    private String message;

    public Celebration(String label, Venue venue, Duration on) {
        this(label, "", venue, on);
    }

    public Celebration() {
        this("", "", Venue.empty(), Duration.zero());
    }

    public Celebration(String label, String message, Venue venue, Duration on) {
        super(label, venue, on);
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public Document write(NitriteMapper nitriteMapper) {
        Gson gson = new Gson();
        Document document = super.write(nitriteMapper);

        document.put("message", getMessage());

        return document;
    }

    @Override
    public void read(NitriteMapper nitriteMapper, Document document) {
        if (document != null) {
            Gson gson = new Gson();

            super.read(nitriteMapper, document);
            setMessage((String) document.get("message"));
        }
    }
}
