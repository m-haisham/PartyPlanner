package cerberus.party;

import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * date time encapsulation {@link #from} to {@link #to}
 */
public class Duration {
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * overloaded constructor for {@link #Duration(LocalDateTime, LocalDateTime)} with {@link LocalDateTime#now()} for both
     */
    public Duration() {
        this(LocalDateTime.now(), LocalDateTime.now());
    }

    /**
     * default constructor
     * @param from start of duration
     * @param to end of duration
     */
    public Duration(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    public static Duration zero() {
        return new Duration(LocalDateTime.now(), LocalDateTime.now());
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public DateTimeDifference difference() {
        return new DateTimeDifference(this.from, this.to);
    }
}
