package cerberus.party.decorations;

public class QuantifiedDecoration extends Decoration {

    private int quantity;

    public QuantifiedDecoration(Decoration decoration, int quantity) {
        this(decoration.getLabel(), decoration.getCost(), quantity);
    }

    public QuantifiedDecoration(String label, double cost, int quantity) {
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
