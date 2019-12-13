package cerberus.party.types;

import cerberus.party.Venue;
import cerberus.party.Duration;
import cerberus.party.Party;
import cerberus.party.Person;

public class Birthday extends Party {

    private Person person;
    private int age;

    public Birthday(String label, Venue venue, Duration on) {
        this(label, Person.empty(), venue, on);
    }

    public Birthday(String label, Person person, Venue venue, Duration on) {
        this(label, person, 0, venue, on);
        this.setAge(this.getAgeOnPartyDate(person));
    }

    public Birthday(String label, Person person, int age, Venue venue, Duration on) {
        super(label, venue, on);
        this.person = person;
        this.age = age;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAgeOnPartyDate() {
        return getAgeOnPartyDate(this.person);
    }

    public int getAgeOnPartyDate(Person person) {
        return getOn().getFrom().plusDays(1).getYear() - person.getBirthDate().getYear();
    }
}
