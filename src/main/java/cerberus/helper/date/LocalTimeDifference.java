package cerberus.helper.date;

public class LocalTimeDifference {
    private int hours;
    private int mins;
    private int secs;

    public LocalTimeDifference(int hours, int mins, int secs) {
        this.hours = hours;
        this.mins = mins;
        this.secs = secs;
    }

    public int getSecs() {
        return secs;
    }

    public int getMins() {
        return mins;
    }

    public int getHours() {
        return hours;
    }
}
