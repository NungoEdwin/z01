import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class FormatDate {

    public static String formatToFullText(LocalDateTime dateTime) {
        if(dateTime==null){
            return null;
        }
        // French locale for "Le … août … à …"
        Locale french = Locale.FRENCH;
        // Day number, full month name, year, hour, minute, second
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(
            "'Le' d MMMM 'de l''an' uuuu 'à' HH'h'mm'm et 'ss's'",
            french
        );
        if(fmt==null){
            return null;
        }
        return dateTime.format(fmt);
    }

    public static String formatSimple(LocalDate date) {
        if(date==null){
            return null;
        }
        // Italian locale for "febbraio …"
        Locale italian = Locale.ITALIAN;
        // Month name, day-of-month, two-digit year
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(
            "MMMM d yy",
            italian
        );
        if(fmt==null){
            return null;
        }
        return date.format(fmt);
    }

     public static String formatIso(LocalTime time) {
        // Use a builder to append hour, minute, second, and optional fraction
        DateTimeFormatter fmt = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .appendLiteral(':')
            .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
            // appendFraction: field, minWidth, maxWidth, decimalPoint
            // Use -1 special meaning to avoid trailing zeros
            .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
            .toFormatter();
            if(fmt==null){
                return null;
            }
        return time.format(fmt);
    }
    public static void main(String[] args) {
        System.out.println(FormatDate.formatToFullText(LocalDateTime.of(2021, 8, 22, 13, 25, 46)));
        System.out.println(FormatDate.formatSimple(LocalDate.of(2022, 2, 13)));
        System.out.println(FormatDate.formatIso(LocalTime.of(16, 18, 56, 8495847)));
    }

}
