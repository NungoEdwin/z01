import java.util.*;

public class Wedding {
    public static Map<String, String> createCouple(Set<String> first, Set<String> second) {
        // Convert sets to lists so we can shuffle & index
        List<String> list1 = new ArrayList<>(first);
        List<String> list2 = new ArrayList<>(second);

        // Randomize the order of names
        Collections.shuffle(list1);
        Collections.shuffle(list2);

        // Limit number of couples to min(size1, size2)
        int limit = Math.min(list1.size(), list2.size());

        Map<String, String> couples = new HashMap<>();

        for (int i = 0; i < limit; i++) {
            couples.put(list1.get(i), list2.get(i));
        }

        return couples;
    }
     public static void main(String[] args) {
        System.out.println(Wedding.createCouple(Set.of("Pikachu", "Dracaufeu", "Tortank"), Set.of("Legolas", "Aragorn", "Gimli")));
    }
}
