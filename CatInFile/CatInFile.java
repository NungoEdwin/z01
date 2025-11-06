import java.io.*;

public class CatInFile {
    public static void cat(String[] args) throws IOException {
        if (args == null || args.length == 0 || args[0] == null) {
            return;
        }
        
        String filename = args[0];
        
        try (FileOutputStream outputStream = new FileOutputStream(filename)) {
            byte[] buffer = new byte[8192]; // 8KB buffer
            int bytesRead;
            
            // Read directly from System.in and write to file
            while ((bytesRead = System.in.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}