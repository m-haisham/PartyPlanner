package cerberus.party.addons;

import cerberus.party.Party;
import cerberus.party.types.Birthday;
import cerberus.party.types.Celebration;
import cerberus.party.types.Farewell;
import cerberus.party.types.Wedding;

import java.util.ArrayList;

public class AddonFactory {

    private AddonFactory() {}

    public static ArrayList<Addon> availableDecorationsFor(Party party) {
        ArrayList<Addon> addons = new ArrayList<Addon>() {{

            // generic for all

            // confetti
            add(new Addon("Medium confetti", 100));
            add(new Addon("Large confetti", 120));
            add(new Addon("Giant confetti", 140));

            add(new Addon("Medium confetti gun", 110));
            add(new Addon("Large confetti gun", 130));
            add(new Addon("Giant confetti gun", 150));

            // chair
            add(new Addon("Chair standard", 10));
            add(new Addon("Chair premium", 15));


            if (party.getClass() == Birthday.class) {
                // number set
                add(new Addon("Small number set", 40));
                add(new Addon("Medium number set", 70));
                add(new Addon("Large number set", 110));

                // signs
                add(new Addon("Yay Sign kit", 100));
                add(new Addon("Cheers Sign kit", 100));
                add(new Addon("Happy Birthday Sign kit", 120));
                add(new Addon("Party Sign kit", 100));

            } else if (party.getClass() == Wedding.class) {
                // con
            } else if (party.getClass() == Farewell.class) {

            } else if (party.getClass() == Celebration.class) {

            }

        }};

        return addons;
    }
}
