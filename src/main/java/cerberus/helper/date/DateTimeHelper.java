package cerberus.helper.date;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {

    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public static LocalDateDifference dateDifference(LocalDate from, LocalDate to) {
        int years = to.getYear() - from.getYear();
        int months = to.getMonth().getValue() - from.getMonth().getValue();
        int days = to.getDayOfYear() - from.getDayOfYear();

        return new LocalDateDifference(years, months, days);
    }

    public static LocalTimeDifference timeDifference(LocalTime from, LocalTime to) {
        int hours = to.getHour() - from.getHour();
        int mins = to.getMinute() - from.getMinute();
        int secs = to.getSecond() - from.getSecond();

        return new LocalTimeDifference(hours, mins, secs);
    }
}
