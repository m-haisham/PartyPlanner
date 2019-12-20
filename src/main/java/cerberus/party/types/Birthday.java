package cerberus.party.types;

import cerberus.party.Venue;
import cerberus.party.Duration;
import cerberus.party.Party;
import cerberus.party.Person;
import com.google.gson.Gson;
import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;

public class Birthday extends Party implements Mappable {

    private Person person;
    private int age;

    public Birthday(String label, Venue venue, Duration on) {
        this(label, Person.empty(), venue, on);
    }

    public Birthday(String label, Person person, Venue venue, Duration on) {
        this(label, person, 0, venue, on);
        this.setAge(this.getAgeOnPartyDate(person));
    }

    public Birthday() {
        this("", Person.empty(), 0, Venue.empty(), Duration.zero());
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

    @Override
    public Document write(NitriteMapper nitriteMapper) {
        Gson gson = new Gson();
        Document document = super.write(nitriteMapper);

        document.put("person", gson.toJson(getPerson()));
        document.put("age", getAge());

        return document;
    }

    @Override
    public void read(NitriteMapper nitriteMapper, Document document) {
        if (document != null) {
            Gson gson = new Gson();

            super.read(nitriteMapper, document);
            setPerson(gson.fromJson((String) document.get("person"), Person.class));
            setAge((Integer) document.get("age"));

        }
    }
}
