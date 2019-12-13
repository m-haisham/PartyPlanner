package cerberus.helper.proceed;

public class Proceeder {
    private boolean value = true;

    public void add(boolean value) {
        if (this.value)
            this.value = value;
    }

    public boolean shouldProceed() {
        return value;
    }
}
