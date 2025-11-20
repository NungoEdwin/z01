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
       DateTimeFormatter format=DateTimeFormatter.ofPattern("EEEE d MMMM yyyy",Locale.FRANCE);//new Locale("fr","FR"));
		   return LocalDate.parse(stringDate,format);
    }

    public static LocalTime parseTimeFormat(String stringDate) {
        if(stringDate==null){
            return null;
        }
        // Extract numbers using regex
        String[] parts = stringDate.split("\\D+"); // split on non-digits
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        int second = Integer.parseInt(parts[2]);

        // Adjust for "du soir" (PM)
        if (stringDate.contains("du soir") && hour < 12) {
            hour += 12;
        }

        LocalTime time = LocalTime.of(hour, minute, second);
        return time;
    }
 public static void main(String[] args) {
        System.out.println(ParseDate.parseIsoFormat("2022-04-25T20:51:28.709039322"));
        System.out.println(ParseDate.parseFullTextFormat("lundi 25 avril 2022"));
        System.out.println(ParseDate.parseTimeFormat("09 heures du soir, 07 minutes et 23 secondes"));
    }

}
