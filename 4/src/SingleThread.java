import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SingleThread {
    public static void main(String[] args) {
        System.out.println(countFilesInDirectory(new File("/home/farzad/Desktop/file/Maktabsharif/Tamrin/hw10/code milad/")));
    }

    public static Long countFilesInDirectory(File directory) {
        Long count = 0L;
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                count += printFileSizeNIO(String.valueOf(file));
            }
            if (file.isDirectory()) {
                count += countFilesInDirectory(file);
            }
        }
        return count;
    }


    public static Long printFileSizeNIO(String fileName) {

        Path path = Paths.get(fileName);
        Long bytes = 0L;

        try {
            bytes = Files.size(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

}

