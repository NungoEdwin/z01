import java.io.IOException;
import java.util.*;
import java.util.stream.*;

public class StreamMap {
    public static Integer sumOfStringLength(Stream<String> s) {
        // Option 1: use mapToInt then sum
        return s
            .mapToInt(str -> str.length())
            .sum();
        
        // Option 2: use collecting with summingInt
        // return s.collect(Collectors.summingInt(str -> str.length()));
    }

    public static List<String> upperCaseAllString(Stream<String> s) {
        return s
            .map(String::toUpperCase)
            .collect(Collectors.toList());
    }

    public static Set<Integer> uniqIntValuesGreaterThan42(Stream<Double> s) {
        return s
            .filter(d -> d > 42)        // only doubles > 42
            .map(Double::intValue)      // convert to integer
            .collect(Collectors.toSet());
    }

    public static void main(String[] args) throws IOException {
        System.out.println(StreamMap.sumOfStringLength(Stream.of("Bonjour", "le", "monde !")));
        System.out.println(StreamMap.upperCaseAllString(Stream.of("IL", "Fait", "beaU !!")));
        System.out.println(StreamMap.uniqIntValuesGreaterThan42(Stream.of(23.439, 42.34, 39194.4)));
    }
}
 
    

