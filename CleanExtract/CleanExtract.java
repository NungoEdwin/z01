public class CleanExtract{
        public static String extract(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        String[] parts = s.split("\\|");
        StringBuilder out = new StringBuilder();
        for (String part : parts) {
            // trim leading/trailing whitespace
            String trimmed = part.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            int firstDot = trimmed.indexOf('.');
            int lastDot = trimmed.lastIndexOf('.');
            // require at least two dots (or same dot counts if they coincide) so there is something between them
            if (firstDot >= 0 && lastDot >= 0 && lastDot > firstDot) {
                String between = trimmed.substring(firstDot + 1, lastDot);
                between = between.trim();
                if (!between.isEmpty()) {
                    if (out.length() > 0) {
                        out.append(" ");
                    }
                    out.append(between);
                }
            }
            // if no proper dot‐pair found, we skip that part
        }
        return out.toString();
    }
public static void main(String args[]){
System.out.println(extract("The|. quick brown. | what do you ..| .fox .|. Jumps over the lazy dog. ."));
        System.out.println(extract("  | Who am .I  | .love coding,  |  |.  Coding is fun . | ...  "));
}
}
