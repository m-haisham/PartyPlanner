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

        boolean deductedYear = false;
        this.months = to.getMonth().getValue() - from.getMonth().getValue();
        if (this.months < 0) {
            this.years--;
            deductedYear = true;

            this.months += 12;
        }

        this.days = to.getDayOfYear() - from.getDayOfYear();
        if (this.days < 0) {
            if (!deductedYear){
                this.years--;
            } else
                this.months -= 12;

            this.days += 365;
        }

        this.hours = to.getHour() - from.getHour();
        if (this.hours < 0) {
            this.days--;
            this.hours += 24;
        }

        this.minutes = to.getMinute() - from.getMinute();
        if (this.minutes < 0) {
            this.hours--;
            this.minutes += 60;
        }

        this.seconds = to.getSecond() - from.getSecond();

        while (this.years < 0 && this.days > 0) {
            this.days -= 365;
            this.years++;
        }
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
        if (years != 0) {
            string += divider + years + "y";
            if (divider.equals(""))
                divider = " ";
        }
        if (months != 0) {
            string += divider + months + "mo";
            if (divider.equals(""))
                divider = " ";
        }
        if (days != 0) {
            string += divider + days + "d";
            if (divider.equals(""))
                divider = " ";
        }
        if (hours != 0) {
            string += divider + hours + "h";
            if (divider.equals(""))
                divider = " ";
        }
        if (minutes != 0) {
            string += divider + minutes + "m";
            if (divider.equals(""))
                divider = " ";
        }
        if (seconds > 0) {
            string += divider + seconds + "s";
            if (divider.equals(""))
                divider = " ";
        }

        return string.equals("") ? "NULL" : string;
    }
}
