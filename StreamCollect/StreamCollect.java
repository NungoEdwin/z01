import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class StreamCollect {
    public static Map<Character, List<String>> mapByFirstLetter(Stream<String> s) {
        return s
            .filter(str -> !str.isEmpty())  // optional: skip empty strings
            .collect(Collectors.groupingBy(
                str -> Character.toUpperCase(str.charAt(0)),
                Collectors.toList()
            ));
    }

    public static Map<Integer, Optional<Integer>> getMaxByModulo4(Stream<Integer> s) {
        return s
            .collect(Collectors.groupingBy(
                n -> n % 4,
                Collectors.maxBy(Comparator.naturalOrder())
            ));
    }

    public static String orderAndConcatWithSharp(Stream<String> s) {
        return s
            .sorted() // default is lexicographical order
            .collect(Collectors.joining(" # ", "{", "}"));
    }
public static void main(String[] args) {
        System.out.println(StreamCollect.mapByFirstLetter(Stream.of("Bonjour", "le", "monde !", "bonsoir")));
        System.out.println(StreamCollect.getMaxByModulo4(Stream.of(5, 12, 32, 4, 9, 17, 98, 424, 97, 5843, 48354)));
        System.out.println(StreamCollect.orderAndConcatWithSharp(Stream.of("Hello", "how are you ?", "where do you live ?", "Bordeaux")));
    }
}
