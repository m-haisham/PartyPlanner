package cerberus.party;

import java.time.LocalDateTime;

/**
 * Provides time difference between two different {@link LocalDateTime}
 */
public class DateTimeDifference {
    private int years;
    private int months;
    private int days;
    private int hours;
    private int minutes;
    private int seconds;

    public DateTimeDifference(LocalDateTime from, LocalDateTime to) {
        this.years = to.getYear() - from.getYear();
        this.months = to.getMonth().getValue() - from.getMonth().getValue();
        this.days = to.getDayOfYear() - from.getDayOfYear();
        this.hours = to.getHour() - from.getHour();
        this.minutes = to.getMinute() - from.getMinute();
        this.seconds = to.getSecond() - from.getSecond();
    }

    public int getYears() {
        return years;
    }

    public int getMonths() {
        return months;
    }

    public int getDays() {
        return days;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    @Override
    public String toString() {
        String string = "";
        String divider = "";
        if (years > 0) {
            string += divider + years + "y";
            if (divider.equals(""))
                divider = " ";
        }
        if (months > 0) {
            string += divider + months + "m";
            if (divider.equals(""))
                divider = " ";
        }
        if (days > 0) {
            string += divider + days + "d";
            if (divider.equals(""))
                divider = " ";
        }
        if (hours > 0) {
            string += divider + hours + "h";
            if (divider.equals(""))
                divider = " ";
        }
        if (minutes > 0) {
            string += divider + minutes + "m";
            if (divider.equals(""))
                divider = " ";
        }
        if (seconds > 0) {
            string += divider + seconds + "s";
            if (divider.equals(""))
                divider = " ";
        }

        return string;
    }
}
