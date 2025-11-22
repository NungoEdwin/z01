import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        return dateTime.format(fmt);
    }

    public static String formatSimple(LocalDate date) {
        // Italian locale for "febbraio …"
        Locale italian = Locale.ITALIAN;
        // Month name, day-of-month, two-digit year
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(
            "MMMM d yy",
            italian
        );
        return date.format(fmt);
    }

    public static String formatIso(LocalTime time) {
        // We want hours:minutes:seconds.fractional, with all nano digits
        // Use pattern with 9 S's to include nanoseconds up to full precision
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSSSSS");
        return time.format(fmt);
    }
    public static void main(String[] args) {
        System.out.println(FormatDate.formatToFullText(LocalDateTime.of(2021, 8, 22, 13, 25, 46)));
        System.out.println(FormatDate.formatSimple(LocalDate.of(2022, 2, 13)));
        System.out.println(FormatDate.formatIso(LocalTime.of(16, 18, 56, 8495847)));
    }

}
