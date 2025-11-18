import java.io.IOException;
import java.util.stream.Stream;

public class StreamReduce {
    public static Integer sumAll(Stream<Integer> s) {
        // Using reduce with identity 0 and Integer::sum to sum all elements.
        return s.reduce(0, Integer::sum);
        // Alternatively:
        // return s.mapToInt(Integer::intValue).sum();
    }

    public static Integer divideAndAddElements(Stream<Integer> s, int divider) {
        // Sum up (element / divider) for each integer in the stream.
        return s
            .map(n -> n / divider)    // integer division
            .reduce(0, Integer::sum);
    }
    public static void main(String[] args) throws IOException {
        System.out.println(StreamReduce.sumAll(Stream.of(3, 5, 7, 10)));
        System.out.println(StreamReduce.sumAll(Stream.of()));
        System.out.println(StreamReduce.divideAndAddElements(Stream.of(3, 5, 7, 10), 2));
        System.out.println(StreamReduce.divideAndAddElements(Stream.of(), 2));
    }
}
