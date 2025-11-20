import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ParseDate {

    public static LocalDateTime parseIsoFormat(String stringDate) {
        return  LocalDateTime.parse(stringDate);
    }

    public static LocalDate parseFullTextFormat(String stringDate) {
       DateTimeFormatter format=DateTimeFormatter.ofPattern("yyyy-MM-dd",new Locale("fr","FR"));
		   return LocalDate.parse(stringDate,format);
    }

    public static LocalTime parseTimeFormat(String stringDate) {
         DateTimeFormatter format=DateTimeFormatter.ofPattern("HH.mm.ss",new Locale("fr","FR"));
		   return LocalTime.parse(stringDate,format);
    }
 public static void main(String[] args) {
        System.out.println(ParseDate.parseIsoFormat("2022-04-25T20:51:28.709039322"));
        System.out.println(ParseDate.parseFullTextFormat("lundi 25 avril 2022"));
        System.out.println(ParseDate.parseTimeFormat("09 heures du soir, 07 minutes et 23 secondes"));
    }

}
