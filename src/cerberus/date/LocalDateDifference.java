package cerberus.date;

public class LocalDateDifference {
    private final int years;
    private final int months;
    private final int days;

    public LocalDateDifference(int years, int month, int days) {
        this.years = years;
        this.months = month;
        this.days = days;
    }

    public int getDays() {
        return days;
    }

    public int getMonths() {
        return months;
    }

    public int getYears() {
        return years;
    }

    public int inHours() {
        int hours = 0;
        hours += years * 365 * 24;
        hours += months * 30 * 24;
        hours += days * 24;

        return hours;
    }
}
