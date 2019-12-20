package cerberus.party.addons;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * extends {@link Addon} to provide quantity of addon
 */
public class QuantifiedAddon extends Addon {

    public static Type arrayType = new TypeToken<ArrayList<QuantifiedAddon>>() {}.getType();

    private int quantity;

    public QuantifiedAddon(Addon addon, int quantity) {
        this(addon.getLabel(), addon.getCost(), quantity);
    }

    public QuantifiedAddon(String label, double cost, int quantity) {
        super(label, cost);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
