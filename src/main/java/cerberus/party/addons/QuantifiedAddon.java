package cerberus.party.addons;

/**
 * extends {@link Addon} to provide quantity of addon
 */
public class QuantifiedAddon extends Addon {

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
