package cerberus.party.addons;

import cerberus.party.Party;
import cerberus.party.types.Birthday;
import cerberus.party.types.Celebration;
import cerberus.party.types.Farewell;
import cerberus.party.types.Wedding;

import java.util.ArrayList;

public class AddonFactory {

    private AddonFactory() {}

    public static ArrayList<Addon> availableDecorationsFor(Class<? extends Party> type) {
        ArrayList<Addon> addons = new ArrayList<Addon>() {{

        // generic for all

        // confetti
        add(new Addon("Medium confetti", 100));

        add(new Addon("Medium confetti gun", 110));
        add(new Addon("Large confetti gun", 130));
        add(new Addon("Giant confetti gun", 150));

        // chair
        add(new Addon("Chair standard", 10));
        add(new Addon("Chair premium", 15));


        if (type == Birthday.class) {
            // number set
            add(new Addon("Small number set", 40));
            add(new Addon("Medium number set", 70));
            add(new Addon("Large number set", 110));

            // signs
            add(new Addon("Yay Sign kit", 100));
            add(new Addon("Cheers Sign kit", 100));
            add(new Addon("Happy Birthday Sign kit", 120));
            add(new Addon("Party Sign kit", 100));

            // cakes
            add(new Addon("Cake. Variant 3", 70));
            add(new Addon("Cake. Variant 60", 130));
            add(new Addon("Cake. Variant 11", 420));
            add(new Addon("Cake. Variant 22", 370));

            add(new Addon("Large confetti", 120));
            add(new Addon("Giant confetti", 140));

        } else if (type == Wedding.class) {

            add(new Addon("Cake. Variant 2", 100));
            add(new Addon("Cake. Variant 20", 120));
            add(new Addon("Cake. Variant 12", 400));
            add(new Addon("Cake. Variant 32", 370));

            add(new Addon("Large confetti", 130));
            add(new Addon("Giant confetti", 160));

        } else if (type == Farewell.class) {

            add(new Addon("Cake. Variant 11", 400));
            add(new Addon("Cake. Variant 22", 370));

            add(new Addon("Large confetti", 110));
            add(new Addon("Giant confetti", 150));

        } else if (type == Celebration.class) {

            add(new Addon("Cake. Variant 7", 400));
            add(new Addon("Cake. Variant 6", 370));

            add(new Addon("Large confetti", 90));
            add(new Addon("Giant confetti", 170));
        }

        }};

        return addons;
    }
}
