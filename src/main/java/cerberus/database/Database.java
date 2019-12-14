package cerberus.database;

import cerberus.party.Party;
import cerberus.party.types.Birthday;
import cerberus.party.types.Celebration;
import cerberus.party.types.Farewell;
import cerberus.party.types.Wedding;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import java.io.File;

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
            assert party instanceof Birthday;
            birthdays.insert((Birthday) party);
        }
        if (party.getClass() == Wedding.class) {
            assert party instanceof Wedding;
            weddings.insert((Wedding) party);
        }
        if (party.getClass() == Farewell.class) {
            assert party instanceof Farewell;
            farewells.insert((Farewell) party);
        }
        if (party.getClass() == Celebration.class) {
            assert party instanceof Celebration;
            celebrations.insert((Celebration) party);
        }

        System.out.println(party);
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
