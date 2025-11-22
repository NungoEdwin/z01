import java.io.IOException;

public class RegexReplace {

    public static String removeUnits(String s) {
        if (s == null) return null;
        // Remove units "cm" or "€" only when they immediately follow a number
        // Regex explanation:
        //   (?<=\\d) → positive lookbehind, ensures preceding character is a digit
        //   (?:cm|€) → either "cm" or "€"
        //   (?=\\s) → positive lookahead, ensures the unit is followed by a space
        return s.replaceAll("(?<=\\d)(?:cm|€)(?=\\s|$)", "");
    }

    public static String obfuscateEmail(String s) {
        if (s == null) return null;
        // First, split username and domain
        int atIndex = s.indexOf('@');
        if (atIndex < 1) {
            // not a valid email format; just return as is (or choose to return null / "")
            return s;
        }
        String username = s.substring(0, atIndex);
        String domain = s.substring(atIndex + 1); // after @

        // --- Obfuscate username ---
        String obfUser;
        // If username contains -, . or _
        if (username.contains("-") || username.contains(".") || username.contains("_")) {
            // Hide any character next to -, . or _ if they exist.
            // For simplicity: replace any character that is immediately before or after these separators with '*'
            // We'll build a char array to do it manually
            char[] userArr = username.toCharArray();
            for (int i = 0; i < userArr.length; i++) {
                char c = userArr[i];
                if (c == '-' || c == '.' || c == '_') {
                    // mask previous char, if exists and is not itself a separator
                    if (i - 1 >= 0 && userArr[i - 1] != '-' && userArr[i - 1] != '.' && userArr[i - 1] != '_') {
                        userArr[i - 1] = '*';
                    }
                    // mask next char, if exists
                    if (i + 1 < userArr.length && userArr[i + 1] != '-' && userArr[i + 1] != '.' && userArr[i + 1] != '_') {
                        userArr[i + 1] = '*';
                    }
                }
            }
            obfUser = new String(userArr);
        } else {
            // Otherwise, hide 3 characters if length > 3
            if (username.length() > 3) {
                StringBuilder sb = new StringBuilder(username);
                // hide the middle 3 characters (or just characters from index 1 to 3)
                // you can adjust which 3 to hide; here: from index 1 to index 3 (inclusive start, exclusive end)
                for (int i = 1; i <= 3 && i < sb.length(); i++) {
                    sb.setCharAt(i, '*');
                }
                obfUser = sb.toString();
            } else {
                // if username too short, just mask everything except maybe first char
                StringBuilder sb = new StringBuilder(username);
                for (int i = 0; i < sb.length(); i++) {
                    sb.setCharAt(i, '*');
                }
                obfUser = sb.toString();
            }
        }

        // --- Obfuscate domain ---
        // Check domain levels: split by "."
        String[] parts = domain.split("\\.");
        String obfDomain;
        if (parts.length == 3) {
            // third-level domain present: e.g. sub.second.tld
            // Hide third-level and top-level
            // parts[0] = third-level, parts[1] = second-level, parts[2] = top-level
            obfDomain = "******" + "." + parts[1] + "." + "***";
        } else {
            // no third-level, or more/fewer parts
            String sld = parts.length >= 2 ? parts[parts.length - 2] : "";
            String tld = parts.length >= 1 ? parts[parts.length - 1] : "";
            boolean tldIsStandard = tld.equals("com") || tld.equals("org") || tld.equals("net");
            if (tldIsStandard) {
                // hide only second-level domain
                // e.g. example.com → *******.com  (we mask domain part before .com)
                obfDomain = "*******." + tld;
            } else {
                // hide second-level and top-level
                obfDomain = "*******." + "**";
            }
        }

        return obfUser + "@" + obfDomain;
    }
    public static void main(String[] args) throws IOException {
        System.out.println(RegexReplace.removeUnits("32cm et 50€"));
        System.out.println(RegexReplace.removeUnits("32 cm et 50 €"));
        System.out.println(RegexReplace.removeUnits("32cms et 50€!"));
        
        System.out.println(RegexReplace.obfuscateEmail("john.doe@example.com"));
        System.out.println(RegexReplace.obfuscateEmail("jann@example.co.org"));
        System.out.println(RegexReplace.obfuscateEmail("jackob@example.fr"));
    }

}
