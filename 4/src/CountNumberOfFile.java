import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CountNumberOfFile {
    static File file1 ;
    public static void main(String[] args) {
        System.out.println(countFilesInDirectory(new File("/home/farzad/Desktop/file/Maktabsharif/Tamrin/hw10/code milad/")));
    }

    public static Long countFilesInDirectory(File directory) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
        Task task1 = new Task();
        Long count = 0L;
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                Task task = new Task(String.valueOf(file),task1.getCount());
                //executor.execute(task);
                executor.submit(task);
                //count += printFileSizeNIO(String.valueOf(file));
            }
            if (file.isDirectory()) {
                count += countFilesInDirectory(file);
            }
        }
        executor.shutdown();
        //System.out.println(task1.getCount());
        return task1.getCount();
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
