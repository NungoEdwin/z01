import java.util.*;

public class WeddingComplex {

    public static Map<String, String> createBestCouple(
            Map<String, List<String>> firstPrefs,
            Map<String, List<String>> secondPrefs) {

        // Let "first" side be proposers
        // Make a queue of free proposers (first side)
        Deque<String> freeProposers = new ArrayDeque<>(firstPrefs.keySet());

        // Track proposals: for each proposer, which index in their preference list they will propose next
        Map<String, Integer> nextProposalIndex = new HashMap<>();
        for (String p : firstPrefs.keySet()) {
            nextProposalIndex.put(p, 0);
        }

        // Current matches: second side -> proposer
        Map<String, String> secondPartner = new HashMap<>();
        // And we also want proposer -> second for the final answer
        Map<String, String> result = new HashMap<>();

        // For quick preference comparison on the second side:
        // Map each person on second side to a map of ranking of proposers
        Map<String, Map<String, Integer>> secondRank = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : secondPrefs.entrySet()) {
            String person = entry.getKey();
            List<String> prefs = entry.getValue();
            Map<String, Integer> rankMap = new HashMap<>();
            for (int i = 0; i < prefs.size(); i++) {
                rankMap.put(prefs.get(i), i);
            }
            secondRank.put(person, rankMap);
        }

        // Gale–Shapley main loop: while there is a free proposer who still has someone to propose to
        while (!freeProposers.isEmpty()) {
            String proposer = freeProposers.poll();
            int i = nextProposalIndex.get(proposer);
            List<String> prefs = firstPrefs.get(proposer);

            // Propose to next in their preference list
            String proposeTo = prefs.get(i);
            nextProposalIndex.put(proposer, i + 1);

            // Check if the proposeTo is currently matched
            if (!secondPartner.containsKey(proposeTo)) {
                // If free, match them
                secondPartner.put(proposeTo, proposer);
                result.put(proposer, proposeTo);
            } else {
                // Already matched, compare preferences
                String current = secondPartner.get(proposeTo);
                Map<String, Integer> rankMap = secondRank.get(proposeTo);

                // Lower index means more preferred
                if (rankMap.get(proposer) < rankMap.get(current)) {
                    // They prefer the new proposer
                    secondPartner.put(proposeTo, proposer);
                    result.put(proposer, proposeTo);

                    // The old match becomes free again
                    freeProposers.add(current);
                    result.remove(current);
                } else {
                    // They reject the new proposer → proposer remains free if they have more to propose
                    if (nextProposalIndex.get(proposer) < prefs.size()) {
                        freeProposers.add(proposer);
                    }
                }
            }
        }

        return result;
    }

    // For quick test
    public static void main(String[] args) {
        Map<String, List<String>> first = Map.of(
            "Naruto", List.of("Sakura", "Hinata"),
            "Sasuke", List.of("Sakura", "Hinata")
        );
        Map<String, List<String>> second = Map.of(
            "Sakura", List.of("Sasuke", "Naruto"),
            "Hinata", List.of("Naruto", "Sasuke")
        );

        System.out.println(createBestCouple(first, second));
        // expected: {Sasuke=Sakura, Naruto=Hinata} (or equivalent stable matching)
    }
}
