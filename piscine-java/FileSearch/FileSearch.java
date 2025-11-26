import java.io.File;

public class FileSearch {
    public static String searchFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }
        
        File documentsFolder = new File("documents");
        return searchRecursive(documentsFolder, fileName, "documents");
    }
    
    private static String searchRecursive(File currentDir, String fileName, String currentPath) {
        if (!currentDir.exists() || !currentDir.isDirectory()) {
            return null;
        }
        
        File[] files = currentDir.listFiles();
        if (files != null) {
            for (File file : files) {
                String newPath = currentPath + "/" + file.getName();
                
                if (file.isDirectory()) {
                    String result = searchRecursive(file, fileName, newPath);
                    if (result != null) {
                        return result;
                    }
                } else if (file.getName().equals(fileName)) {
                    return newPath;
                }
            }
        }
        
        return null;
    }
}