import java.io.*;
import java.nio.file.*;

public class FileManager {
    public static void createFile(String fileName, String content) throws IOException {
        if (fileName == null || content == null) {
            return;
        }
        
        // Use Files.write to create the file with content
        Files.write(Paths.get(fileName), content.getBytes());
    }
    
    public static String getContentFile(String fileName) throws IOException {
        if (fileName == null) {
            return "";
        }
        
        File file = new File(fileName);
        if (!file.exists() || !file.isFile()) {
            return "";
        }
        
        // Read all bytes from the file and convert to String
        byte[] bytes = Files.readAllBytes(Paths.get(fileName));
        return new String(bytes);
    }
    
    public static void deleteFile(String fileName) {
        if (fileName == null) {
            return;
        }
        
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }
}