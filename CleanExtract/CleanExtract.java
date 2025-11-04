public class CleanExtract{
     public static String extract(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        String[] parts = s.split("\\|", -1);  // keep empty segments
        StringBuilder out = new StringBuilder();
        for (String part : parts) {
            String trimmed = part.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            int firstDot = trimmed.indexOf('.');
            int lastDot  = trimmed.lastIndexOf('.');
            // Only extract if there are at least two distinct dot positions
            if (firstDot >= 0 && lastDot > firstDot) {
                String between = trimmed.substring(firstDot + 1, lastDot).trim();
                if (!between.isEmpty()) {
                    if (out.length() > 0) {
                        out.append(" ");
                    }
                    out.append(between);
                }
            } else {
                // If there is exactly one dot, maybe we still take the part *after* the dot?
                // For example: "Who am .I" → we might want "I"
                if (firstDot >= 0 && lastDot == firstDot) {
                    String after = trimmed.substring(firstDot + 1).trim();
                    if (!after.isEmpty()) {
                        if (out.length() > 0) {
                            out.append(" ");
                        }
                        out.append(after);
                    }
                }
                // else: no dots or other cases → skip
            }
        }
        return out.toString();
    }
public static void main(String args[]){
System.out.println(extract("The|. quick brown. | what do you ..| .fox .|. Jumps over the lazy dog. ."));
        System.out.println(extract("  | Who am .I  | .love coding,  |  |.  Coding is fun . | ...  "));
}
}
