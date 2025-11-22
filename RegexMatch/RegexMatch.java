import java.io.IOException;

public class RegexMatch {

    public static boolean containsOnlyAlpha(String s) {
        if (s == null) return false;
        // matches only letters (at least one)
        return s.matches("^[A-Za-z]+$");
    }

    public static boolean startWithLetterAndEndWithNumber(String s) {
        if (s == null) return false;
        // ^ : start of string  
        // [A-Za-z] : one letter  
        // .* : any (zero or more) chars in between  
        // [0-9] : one digit at end  
        // $ : end of string  
        return s.matches("^[A-Za-z].*[0-9]$");
    }

    public static boolean containsAtLeast3SuccessiveA(String s) {
        if (s == null) return false;
        // We want at least three 'A' in a row (uppercase A)
        // ".*A{3,}.*" means: any chars, then A repeated 3 or more times, then any chars
        return s.matches(".*A{3,}.*");
    }
     public static void main(String[] args) throws IOException {
        System.out.println(RegexMatch.containsOnlyAlpha("azejkdfhjsdf"));
        System.out.println(RegexMatch.containsOnlyAlpha("azejkd fhjsdf"));
        System.out.println(RegexMatch.startWithLetterAndEndWithNumber("asjd jd34jds jkfd6f5"));
        System.out.println(RegexMatch.startWithLetterAndEndWithNumber("asjd jd34jds jkfd6."));
        System.out.println(RegexMatch.containsAtLeast3SuccessiveA("sdjkAAAAAsdjksj"));
        System.out.println(RegexMatch.containsAtLeast3SuccessiveA("sdjkAAsdaaasdjksj"));
    }

}
