
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.util.Locale;


public class ParseDate {

    public static LocalDateTime parseIsoFormat(String stringDate) {
        if (stringDate == null || stringDate.isEmpty()) {
        return null;
    }
        return LocalDateTime.parse(stringDate);
    }

   public static LocalDate parseFullTextFormat(String stringDate) {
    if (stringDate == null || stringDate.isEmpty()) {
        return null;
    }

    // Define the pattern, which is the same for both languages
    String pattern = "EEEE d MMMM yyyy";
    
    // 1. Try parsing with the FRENCH locale
    try {
        DateTimeFormatter frenchFormatter = DateTimeFormatter.ofPattern(pattern)
                                                             .withLocale(Locale.FRENCH);
        return LocalDate.parse(stringDate, frenchFormatter);
        
    } catch (java.time.format.DateTimeParseException e) {
        // If French parsing fails, try the next locale
    }

    // 2. Try parsing with the ENGLISH locale
    try {
        DateTimeFormatter englishFormatter = DateTimeFormatter.ofPattern(pattern)
                                                              .withLocale(Locale.ENGLISH);
        return LocalDate.parse(stringDate, englishFormatter);
        
    } catch (java.time.format.DateTimeParseException e) {
        // 3. If both fail, throw an exception or return null. 
        // Throwing the last exception is usually best practice for debugging.
        // For this scenario, let's just re-throw to clearly indicate the parse failed.
        throw e; 
    }
}

   public static LocalTime parseTimeFormat(String stringDate) {
    if (stringDate == null || stringDate.isEmpty()){
        return null;
    }
    String digits = stringDate.replaceAll("\\D+", " ").trim();
    String[] parts = digits.split(" ");

    int hour = Integer.parseInt(parts[0]);
    int minute = Integer.parseInt(parts[1]);
    int second = Integer.parseInt(parts[2]);

   String lower = stringDate.toLowerCase();
   boolean isPM = lower.contains("pm") || lower.contains("soir") || lower.contains("evening");
   boolean isAM = lower.contains("am") || lower.contains("morning"); 

   if (isPM && hour < 12) {
    // e.g., 6 PM -> 18
    hour += 12; 
   } else if (isAM && hour == 12) {
    // e.g., 12 AM (midnight) -> 0
    hour = 0;
 } 

 return LocalTime.of(hour, minute, second);
}
}