public class Main
{
    public static void main(String[] args)
    {
        Runnable application = new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    System.out.println("Hello");
                    try
                    {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException exception)
                    {
                        System.out.println("interrupted");
                        break;
                    }
                }
            }
        };
        Thread thread = new Thread(application);
        thread.start();
        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException exception)
        {
            System.out.println(exception.getMessage());
        }
        thread.interrupt();
    }
}
