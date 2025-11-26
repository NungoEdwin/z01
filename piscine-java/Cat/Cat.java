import java.io.*;

public class Cat {
    public static void cat(String[] args) throws IOException {
        if (args == null || args.length == 0 || args[0] == null) {
            return;
        }
        
        String filename = args[0];
        File file = new File(filename);
        
        if (!file.exists() || !file.isFile()) {
            return;
        }
        
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buffer = new byte[8192]; // 8KB buffer
            int bytesRead;
            
            while ((bytesRead = bis.read(buffer)) != -1) {
                System.out.write(buffer, 0, bytesRead);
            }
        }
    }
}