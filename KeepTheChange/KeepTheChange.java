import java.util.*;

public class KeepTheChange {
    public static List<Integer> computeChange(int amount, Set<Integer> coins) {
        // Convert coins to a sorted list (ascending)
        List<Integer> coinList = new ArrayList<>(coins);
        Collections.sort(coinList);

        // dp[i] = minimum number of coins needed to make amount i
        int[] dp = new int[amount + 1];
        // To reconstruct the solution: which coin was last used to reach optimal for i
        int[] lastCoin = new int[amount + 1];

        // Initialize dp with a large value (infinite)
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        // Build up dp table
        for (int i = 1; i <= amount; i++) {
            for (int coin : coinList) {
                if (coin <= i && dp[i - coin] != Integer.MAX_VALUE) {
                    int candidate = dp[i - coin] + 1;
                    if (candidate < dp[i]) {
                        dp[i] = candidate;
                        lastCoin[i] = coin;
                    }
                }
            }
        }

        // Now reconstruct which coins were used
        List<Integer> result = new ArrayList<>();
        int current = amount;

        // If dp[amount] is still "infinite", no solution (though you said tests guarantee a solution)
        if (dp[amount] == Integer.MAX_VALUE) {
            return result;  // or throw exception
        }

        while (current > 0) {
            int coin = lastCoin[current];
            result.add(coin);
            current -= coin;
        }

        // Optionally, you can sort result in descending order (or any order) — your example is [7, 7, 3, 1].
        // By reconstructing like this, it's in "used order" but may already yield that.
        Collections.sort(result, Collections.reverseOrder());
        return result;
    }

    // For testing
    public static void main(String[] args) {
        System.out.println(KeepTheChange.computeChange(18, Set.of(1, 3, 7)));  // should print [7, 7, 3, 1]
    }
}
