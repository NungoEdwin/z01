pub fn edit_distance(source: &str, target: &str) -> usize {
    // Convert the source and target strings into vectors of characters for easier indexing.
    let source_chars: Vec<char> = source.chars().collect();
    let target_chars: Vec<char> = target.chars().collect();
    let m = source_chars.len(); // Length of the source string.
    let n = target_chars.len(); // Length of the target string.

    // Create a 2D vector `dp` where `dp[i][j]` will store the edit distance between
    // the first `i` characters of `source` and the first `j` characters of `target`.
    let mut dp = vec![vec![0; n + 1]; m + 1];

    // Initialize the first column of the DP table.
    // The edit distance between any prefix of `source` and an empty string is the length of the prefix.
    for i in 0..=m {
        dp[i][0] = i;
    }

    // Initialize the first row of the DP table.
    // The edit distance between an empty string and any prefix of `target` is the length of the prefix.
    for j in 0..=n {
        dp[0][j] = j;
    }

    // Fill the DP table by comparing each character of `source` and `target`.
    for i in 1..=m {
        for j in 1..=n {
            if source_chars[i - 1] == target_chars[j - 1] {
                // If the current characters match, no operation is needed.
                // The edit distance is the same as for the previous characters.
                dp[i][j] = dp[i - 1][j - 1];
            } else {
                // If the characters don't match, consider all possible operations:
                // 1. Deletion: dp[i-1][j] (remove a character from `source`).
                // 2. Insertion: dp[i][j-1] (add a character to `source`).
                // 3. Substitution: dp[i-1][j-1] (replace a character in `source`).
                // Take the minimum of these three options and add 1 (for the current operation).
                dp[i][j] = 1 + std::cmp::min(
                    dp[i - 1][j], // Deletion
                    std::cmp::min(
                        dp[i][j - 1], // Insertion
                        dp[i - 1][j - 1], // Substitution
                    ),
                );
            }
        }
    }

    // The value at dp[m][n] contains the edit distance between the entire `source` and `target`.
    dp[m][n]
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn it_works() {
        let source = "alignment";
        let target = "assignment";

        
        assert_eq!(edit_distance(source, target),2 );
    }
}
