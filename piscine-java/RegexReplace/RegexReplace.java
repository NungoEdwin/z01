import java.io.IOException;
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

        int atIndex = s.indexOf('@');
        if (atIndex < 1) {
            return s;
        }

        String username = s.substring(0, atIndex);
        String domain = s.substring(atIndex + 1);
        String obfUser;

        // Case: username contains ., _, or -
        if (username.matches(".*[._-].*")) {
            Pattern p = Pattern.compile("^([^._-]+)([._-])(.*)$");
            Matcher m = p.matcher(username);
            obfUser = m.replaceAll((MatchResult mr) -> {
                String before = mr.group(1);        // part before separator
                String sep = mr.group(2);           // the separator itself
                String after = mr.group(3);         // part after separator
                // If the part after separator is only 1 char, hide only that one char with a single "*"
                if (after.length() == 1) {
                    return before + sep + "*";
                } else {
                    // Otherwise, hide it with three "*"
                    return before + sep + "***";
                }
            });
        } else {
            // No separator
            int len = username.length();
            if (len <= 3) {
                obfUser = username.replaceAll(".", "*");
            } else {
                Pattern p2 = Pattern.compile("^(.{3})(.*)$");
                Matcher m2 = p2.matcher(username);
                obfUser = m2.replaceAll((MatchResult mr) -> {
                    String first3 = mr.group(1);
                    String rest = mr.group(2);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < rest.length(); i++) {
                        sb.append("*");
                    }
                    return first3 + sb;
                });
            }
        }

        // Domain obfuscation (unchanged)
        String[] parts = domain.split("\\.");
        String obfDomain;
        if (parts.length == 3) {
            obfDomain = "*******" + "." + parts[1] + "." + "***";
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

    public static void main(String[] args) throws IOException {
        System.out.println(RegexReplace.removeUnits("32cm et 50€"));
        System.out.println(RegexReplace.removeUnits("32 cm et 50 €"));
        System.out.println(RegexReplace.removeUnits("32cms et 50€!"));
        
        System.out.println(RegexReplace.obfuscateEmail("john.doe@example.com"));
        System.out.println(RegexReplace.obfuscateEmail("jann@example.co.org"));
        System.out.println(RegexReplace.obfuscateEmail("jackob@example.fr"));
    }
}
