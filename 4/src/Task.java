
import java.util.concurrent.TimeUnit;

public class Task implements Runnable {
    private String address;
    private volatile static Long count = 0L;
    private CountNumberOfFile countNumberOfFile = new CountNumberOfFile();

    Task(){

    }

    public Task(String address, Long count) {
        this.address = address;
        this.count = count;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public synchronized void run() {
        setCount(countNumberOfFile.printFileSizeNIO(getAddress()) + getCount());
        //System.out.println(Thread.currentThread().getName());
    }
}

