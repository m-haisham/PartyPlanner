package cerberus.party.types;

import cerberus.party.*;

public class Wedding extends Party {

    private Person wife;
    private Person husband;

    public Wedding(String label, Address venue, Duration on) {
        this(label, venue, on, Person.empty(), Person.empty());
    }

    public Wedding(String label, Address venue, Duration on, Person wife, Person husband) {
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
}
