import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;

public class DifferenceBetweenDate {

    public static Duration durationBetweenTime(LocalTime time1, LocalTime time2) {
        if (time1 == null && time2 == null) {
            return null;
        } else if (time1 == null) {
            // duration from 00:00 to time2
            return null;
        } else if (time2 == null) {
            return null;
        } else {
            return Duration.between(time1, time2).abs();
        }
    }

    public static Period periodBetweenDate(LocalDate date1, LocalDate date2) {
        if (date1 == null && date2 == null) {
            return null;
        } else if (date1 == null) {
            return null;
        } else if (date2 == null) {
            return null;
        } else {
            // Period.between(a, b) can return a negative period if b < a. :contentReference[oaicite:0]{index=0}  
            Period p = Period.between(date1, date2);
            // We want always positive. But Period doesn't have an `abs()` directly.
            // We'll reconstruct a positive Period:
            int years = Math.abs(p.getYears());
            int months = Math.abs(p.getMonths());
            int days = Math.abs(p.getDays());
            return Period.of(years, months, days);
        }
    }

    public static Long numberOfHoursBetweenDateTime(LocalDateTime dt1, LocalDateTime dt2) {
        if (dt1 == null && dt2 == null) {
            return null;
        } else if (dt1 == null) {
            // from epoch-ish, but better to just compute absolute of between dt2 and now or midnight?
            // Simpler: treat missing dt1 as dt2, so difference = 0 
            return null;
        } else if (dt2 == null) {
            return null;
        } else {
            Duration d = Duration.between(dt1, dt2).abs();
            return d.toHours();
        }
    }
      public static void main(String[] args) {
        Duration duration = DifferenceBetweenDate.durationBetweenTime(LocalTime.of(12, 54, 32), LocalTime.of(21, 23, 53));
        System.out.println(duration.toHoursPart() + "H" + duration.toMinutesPart() + "M" + duration.toSecondsPart() + "S");
        Period period = DifferenceBetweenDate.periodBetweenDate(LocalDate.of(2020, 10, 13), LocalDate.of(2022, 5, 8));
        System.out.println(period.getYears() + "Y" + period.getMonths() + "M" + period.getDays() + "D");
        System.out.println(DifferenceBetweenDate.numberOfHoursBetweenDateTime(LocalDateTime.of(2022, 4, 12, 16, 18, 56), LocalDateTime.of(2022, 5, 10, 21, 54, 56)));
    }

}
