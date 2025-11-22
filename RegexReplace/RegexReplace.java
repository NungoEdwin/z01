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

        // 1) Obfuscate username
        String obfUser;
        if (username.matches(".*[._-].*")) {
            // If there is a separator (., _, -), keep up to it then add ***
            Pattern p = Pattern.compile("^([^._-]+)([._-]).*");
            Matcher m = p.matcher(username);
            obfUser = m.replaceAll((MatchResult mr) -> {
                return mr.group(1) + mr.group(2) + "***";
            });
        } else {
            // No separator
            int len = username.length();
            if (len <= 3) {
                obfUser = username.replaceAll(".", "*");
            } else {
                // Keep first 3 chars, mask the rest
                Pattern p = Pattern.compile("^(.{3})(.*)$");
                Matcher m2 = p.matcher(username);
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

        // 2) Obfuscate domain
        String[] parts = domain.split("\\.");
        String obfDomain;
        if (parts.length == 3) {
            // third-level.second-level.top-level
            // Use 7 stars for the first subdomain
            obfDomain = "*******" + "." + parts[1] + "." + "***";
        } else if (parts.length == 2) {
            String tld = parts[1];
            if ("com".equals(tld) || "org".equals(tld) || "net".equals(tld)) {
                obfDomain = "*******." + tld;
            } else {
                obfDomain = "*******." + "**";
            }
        } else {
            // fallback
            obfDomain = "*******";
        }

        return obfUser + "@" + obfDomain;
    }

    public static void main(String[] args) {
        // Test removeUnits
        System.out.println(removeUnits("32cm et 50€"));       // -> "32 et 50"
        System.out.println(removeUnits("32 cm et 50 €"));    // -> "32 cm et 50 €"
        System.out.println(removeUnits("32cms et 50€!"));    // -> "32cms et 50€!"

        // Test obfuscateEmail
        System.out.println(obfuscateEmail("john.doe@example.com"));      // expected: john.***@*******.com
        System.out.println(obfuscateEmail("jann@example.co.edu"));      // expected: jan*@*******.co.***
        System.out.println(obfuscateEmail("jackob@example.fr"));         // expected: jac***@*******.**
    }
}
