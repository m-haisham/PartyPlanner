package cerberus.party.types;

import cerberus.party.Venue;
import cerberus.party.Duration;
import cerberus.party.Party;

public class Celebration extends Party {

    private String message;

    public Celebration(String label, Venue venue, Duration on) {
        this(label, "", venue, on);
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
}
