class StarvationDemo extends Thread {
    static int count = 1;
    public void run()
    {
        System.out.println(count  +
                " starts");
        System.out.println("completes");
        count++;
    }
    public static void main(String[] args)
            throws InterruptedException
    {
        System.out.println("Main starts");

        StarvationDemo thread1 = new StarvationDemo();
        thread1.setPriority(10);
        StarvationDemo thread2 = new StarvationDemo();
        thread2.setPriority(9);
        StarvationDemo thread3 = new StarvationDemo();
        thread3.setPriority(8);
        StarvationDemo thread4 = new StarvationDemo();
        thread4.setPriority(7);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        System.out.println("Main completes");
    }
}
