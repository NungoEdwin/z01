import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Capitalize {
    public static void capitalize(String[] args) throws IOException {
        if (args == null || args.length != 2) {
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Remove leading/trailing spaces and reduce multiple internal spaces to one
                String normalizedLine = line.trim().replaceAll("\\s+", " ");
                
                // Handle empty lines or lines with only spaces after normalization
                if (normalizedLine.isEmpty()) {
                    writer.write("");
                    writer.newLine();
                    continue;
                }

                // Apply Title Case capitalization (first letter of each word)
                StringBuilder capitalizedLineBuilder = new StringBuilder();
                String[] words = normalizedLine.split(" ");

                for (int i = 0; i < words.length; i++) {
                    String word = words[i];
                    if (!word.isEmpty()) { // Ensure word is not empty after splitting (e.g., from " " split)
                        capitalizedLineBuilder.append(Character.toUpperCase(word.charAt(0)));
                        if (word.length() > 1) {
                            capitalizedLineBuilder.append(word.substring(1).toLowerCase()); // Rest of the word to lowercase
                        }
                    }
                    if (i < words.length - 1) {
                        capitalizedLineBuilder.append(" "); // Add space between words
                    }
                }
                String finalCapitalizedLine = capitalizedLineBuilder.toString();
                
                // Debugging print
                System.err.println("Original Line: '" + line + "'");
                System.err.println("Normalized Line (trimmed & single-spaced): '" + normalizedLine + "'");
                System.err.println("Final Capitalized (Title Case) Line: '" + finalCapitalizedLine + "'");

                writer.write(finalCapitalizedLine);
                writer.newLine();
            }
        }
    }
}