package cerberus.database;

import cerberus.party.Party;
import cerberus.party.filter.PaidPercent;
import cerberus.party.filter.PartyType;
import cerberus.party.types.*;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.io.File;
import java.util.ArrayList;

/**
 * a nitrite database wrapper
 */
public class Database {

    private Nitrite db;

    private ObjectRepository<Birthday> birthdays;
    private ObjectRepository<Wedding> weddings;
    private ObjectRepository<Farewell> farewells;
    private ObjectRepository<Celebration> celebrations;

    public Database(File file) {
        db = Nitrite
                .builder()
                .filePath(file)
                .openOrCreate();

        birthdays = db.getRepository(Birthday.class);
        weddings = db.getRepository(Wedding.class);
        farewells = db.getRepository(Farewell.class);
        celebrations = db.getRepository(Celebration.class);
    }

    public void insertParty(Party party) {
        if (party.getClass() == Birthday.class) {
            birthdays.insert((Birthday) party);
        }
        if (party.getClass() == Wedding.class) {
            weddings.insert((Wedding) party);
        }
        if (party.getClass() == Farewell.class) {
            farewells.insert((Farewell) party);
        }
        if (party.getClass() == Celebration.class) {
            celebrations.insert((Celebration) party);
        }
    }

    public void removeParty(ObjectFilter filter, Class<? extends Party> typeOfParty) {
        if (typeOfParty == Birthday.class) {
            birthdays.remove(filter);
        }
        if (typeOfParty == Wedding.class) {
            weddings.remove(filter);
        }
        if (typeOfParty == Farewell.class) {
            farewells.remove(filter);
        }
        if (typeOfParty == Celebration.class) {
            celebrations.remove(filter);
        }
    }

    public ArrayList<Party> getAll() {
        ArrayList<Party> all = new ArrayList<>();

        birthdays.find().forEach(all::add);
        weddings.find().forEach(all::add);
        farewells.find().forEach(all::add);
        celebrations.find().forEach(all::add);

        return all;
    }

    public ArrayList<Party> getAll(PaidPercent paidPercent, PartyType type) {
        ArrayList<Party> paid = new ArrayList<>();
        ArrayList<Party> unpaid = new ArrayList<>();

        for (Birthday birthday : birthdays.find()) {
            if (birthday.getPaidPercentile() != 0)
                paid.add(birthday);
            else
                unpaid.add(birthday);

        }
        for (Wedding wedding : weddings.find()) {
            if (wedding.getPaidPercentile() != 0)
                paid.add(wedding);
            else
                unpaid.add(wedding);

        }
        for (Farewell farewell : farewells.find()) {
            if (farewell.getPaidPercentile() != 0)
                paid.add(farewell);
            else
                unpaid.add(farewell);

        }
        for (Celebration celebration : celebrations.find()) {
            if (celebration.getPaidPercentile() != 0)
                paid.add(celebration);
            else
                unpaid.add(celebration);

        }

        ArrayList<Party> out = new ArrayList<>();
        if (paidPercent == PaidPercent.p15)
            out = paid;
        else if (paidPercent == PaidPercent.p0)
            out = unpaid;
        else {
            out.addAll(paid);
            out.addAll(unpaid);
        }

        if (type == PartyType.All)
            return out;
        else {
            if (type == PartyType.Birthday) return Party.filter(out, Birthday.class);
            if (type == PartyType.Celebration) return Party.filter(out, Celebration.class);
            if (type == PartyType.Farewell) return Party.filter(out, Farewell.class);
            if (type == PartyType.Wedding) return Party.filter(out, Wedding.class);
        }

        // empty
        System.out.println("ERROR");
        return new ArrayList<>();
    }

    public Nitrite getDb() {
        return db;
    }

    public ObjectRepository<Birthday> getBirthdays() {
        return birthdays;
    }

    public ObjectRepository<Wedding> getWeddings() {
        return weddings;
    }

    public ObjectRepository<Farewell> getFarewells() {
        return farewells;
    }

    public ObjectRepository<Celebration> getCelebrations() {
        return celebrations;
    }
}
