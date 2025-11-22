import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexReplace {

    public static String removeUnits(String s) {
        if (s == null) return null;
        return s.replaceAll("(?<=\\d)(?:cm|€)(?=\\s|$)", "");
    }

    public static String obfuscateEmail(String s) {
        if (s == null) return null;

        int at = s.indexOf('@');
        if (at < 1) {
            return s;
        }

        String username = s.substring(0, at);
        String domain = s.substring(at + 1);

        String obfUser;

        // Case 1: username contains separator . _ or -
        if (username.matches(".*[._-].*")) {
            Pattern p = Pattern.compile("^([^._-]+)([._-]).*");
            Matcher m = p.matcher(username);
            obfUser = m.replaceAll((MatchResult mr) -> {
                return mr.group(1) + mr.group(2) + "***";
            });
        } else {
            // No separator in username
            int len = username.length();
            if (len <= 3) {
                // Mask all if <= 3 chars
                obfUser = username.replaceAll(".", "*");
            } else {
                // If longer than 3, keep first 3 characters, then mask the rest
                Pattern p = Pattern.compile("^(.{3})(.*)$");
                Matcher m2 = p.matcher(username);
                obfUser = m2.replaceAll((MatchResult mr) -> {
                    String first3 = mr.group(1);
                    // Replace the rest with stars, same length as the rest
                    String rest = mr.group(2);
                    StringBuilder stars = new StringBuilder();
                    for (int i = 0; i < rest.length(); i++) {
                        stars.append("*");
                    }
                    return first3 + stars;
                });
            }
        }

        // Obfuscate domain
        String[] parts = domain.split("\\.");
        String obfDomain;
        if (parts.length == 3) {
            // third-level.second-level.top-level
            obfDomain = "******" + "." + parts[1] + "." + "***";
        } else if (parts.length == 2) {
            String tld = parts[1];
            if ("com".equals(tld) || "org".equals(tld) || "net".equals(tld)) {
                obfDomain = "*******." + tld;
            } else {
                obfDomain = "*******." + "**";
            }
        } else {
            obfDomain = "*******";
        }

        return obfUser + "@" + obfDomain;
    }

    public static void main(String[] args) {
        System.out.println(removeUnits("32cm et 50€"));       // → "32 et 50"
        System.out.println(removeUnits("32 cm et 50 €"));    // → "32 cm et 50 €"
        System.out.println(removeUnits("32cms et 50€!"));    // → "32cms et 50€!"

        System.out.println(obfuscateEmail("john.doe@example.com"));   // → "john.***@*******.com"
        System.out.println(obfuscateEmail("jann@example.co.org"));    // → "jan*@*******.co.***"
        System.out.println(obfuscateEmail("jackob@example.fr"));      // → "jac***@*******.**"
    }
}
