import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

public class InnerClass {
    Semaphore semaphore = new Semaphore(1);
    private List<String> availableConnections = new ArrayList();


    public InnerClass(){
        this.availableConnections.add("A");
    }

    public String acquireConnection() throws InterruptedException {
        semaphore.acquire();
        //System.out.println("Acquiring connection " + Thread.currentThread().getName());
        return availableConnections.remove(0);
    }

    static volatile Long count = 0L;
    static File file1 ;


    public static void main(String[] args) {
        System.out.println(countFilesInDirectory(new File("/home/farzad/Desktop/file/Maktabsharif/Tamrin/hw10/code milad/")));


    }

    public static Long countFilesInDirectory(File directory) {
        InnerClass connection = new InnerClass();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                executor.execute(new Runnable() {
                    @Override
                    public synchronized void run() {
                        try {
                            String far = connection.acquireConnection();
                            count += printFileSizeNIO(String.valueOf(file));
                            connection.acquireConnection();
                            executor.shutdown();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //System.out.println(printFileSizeNIO(String.valueOf(file)));
                        //executor.shutdownNow();
                    }
                });
            }
            if (file.isDirectory()) {
                count += countFilesInDirectory(file);
            }
        }
        //executor.shutdownNow();
        //System.out.println(task1.getCount());
        //return task1.getCount();
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
