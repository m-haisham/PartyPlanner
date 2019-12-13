package cerberus.party.decorations;

import cerberus.party.Party;
import cerberus.party.types.Birthday;
import cerberus.party.types.Celebration;
import cerberus.party.types.Farewell;
import cerberus.party.types.Wedding;

import java.util.ArrayList;
import java.util.List;

public class DecorationFactory {

    private DecorationFactory() {}

    public static ArrayList<Decoration> availableDecorationsFor(Party party) {
        ArrayList<Decoration> decorations = new ArrayList<Decoration>() {{

            // generic for all

            // confetti
            add(new Decoration("Medium confetti", 100));
            add(new Decoration("Large confetti", 120));
            add(new Decoration("Giant confetti", 140));

            add(new Decoration("Medium confetti gun", 110));
            add(new Decoration("Large confetti gun", 130));
            add(new Decoration("Giant confetti gun", 150));

            // chair
            add(new Decoration("Chair standard", 10));
            add(new Decoration("Chair premium", 15));


            if (party.getClass() == Birthday.class) {
                // number set
                add(new Decoration("Small number set", 40));
                add(new Decoration("Medium number set", 70));
                add(new Decoration("Large number set", 110));

                // signs
                add(new Decoration("Yay Sign kit", 100));
                add(new Decoration("Cheers Sign kit", 100));
                add(new Decoration("Happy Birthday Sign kit", 120));
                add(new Decoration("Party Sign kit", 100));

            } else if (party.getClass() == Wedding.class) {
                // con
            } else if (party.getClass() == Farewell.class) {

            } else if (party.getClass() == Celebration.class) {

            }

        }};

        return decorations;
    }
}
