public class TrueCheckingAccount
{
    private volatile int balance;

    public TrueCheckingAccount(int initialBalance)
    {
        balance = initialBalance;
    }

    public synchronized boolean withdraw(int amount)
    {
        if (amount <= balance)
        {
            try
            {
                Thread.sleep((int) (Math.random() * 200));
            }
            catch (InterruptedException ie)
            {
            }
            balance -= amount;
            return true;
        }
        return false;
    }

    public static void main(String[] args)
    {
        final TrueCheckingAccount ca = new TrueCheckingAccount(100);
        Runnable r = new Runnable()
        {
            @Override
            public void run()
            {
                String name = Thread.currentThread().getName();
                for (int i = 0; i < 10; i++)
                    System.out.println (name + " withdraws $10: " +
                            ca.withdraw(10));
            }
        };
        Thread thdHusband = new Thread(r);
        thdHusband.setName("Husband");
        Thread thdWife = new Thread(r);
        thdWife.setName("Wife");
        thdHusband.start();
        thdWife.start();
    }
}
