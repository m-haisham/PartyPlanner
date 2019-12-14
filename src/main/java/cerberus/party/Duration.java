package cerberus.party;

import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Duration implements Mappable {
    private LocalDateTime from;
    private LocalDateTime to;

    public Duration() {
        this(LocalDateTime.now(), LocalDateTime.now());
    }

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

    @Override
    public Document write(NitriteMapper nitriteMapper) {
        Document document = new Document();

        document.put("from", from.toString());
        document.put("to", to.toString());

        return document;
    }

    @Override
    public void read(NitriteMapper nitriteMapper, Document document) {
        if (document != null) {
            setFrom(LocalDateTime.parse((String) document.get("from")));
            setTo(LocalDateTime.parse((String) document.get("to")));
        }
    }
}
