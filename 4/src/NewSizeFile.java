import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class NewSizeFile
{
    Semaphore semaphore = new Semaphore(1);

    public void acquireConnection() throws InterruptedException
    {
        semaphore.acquire();
    }

    public void releaseConnection() throws InterruptedException
    {
        semaphore.release();
    }

    static AtomicLong count = new AtomicLong(0);

    public static void main(String[] args)
    {
        long bytes = countFilesInDirectory(new File("/home/farzad/Desktop/file/Maktabsharif/Tamrin/hw10/code milad/"));
        System.out.println(humanReadableByteCountBin(bytes));
    }

    public static Long countFilesInDirectory(File directory)
    {
        NewSizeFile connection = new NewSizeFile();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);

        for (File file : Objects.requireNonNull(directory.listFiles()))
        {

            executor.execute(() -> {
                if (file.isFile())
                {
                    try
                    {
                        connection.acquireConnection();
                        count.addAndGet(printFileSizeNIO(String.valueOf(file)));
                        connection.releaseConnection();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                if (file.isDirectory())
                {
                    countFilesInDirectory(file);
                }
            });
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        return count.get();
    }

    public static Long printFileSizeNIO(String fileName)
    {

        Path path = Paths.get(fileName);
        long bytes = 0L;

        try
        {
            bytes = Files.size(path);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return bytes;
    }

    public static String humanReadableByteCountBin(long bytes)
    {
        long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
        if (absB < 1024) {
            return bytes + " B";
        }
        long value = absB;
        CharacterIterator ci = new StringCharacterIterator("KMGTPE");
        for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10)
        {
            value >>= 10;
            ci.next();
        }
        value *= Long.signum(bytes);
        return String.format("%.1f %ciB", value / 1024.0, ci.current());
    }

}
