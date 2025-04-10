package threads;

class MyThread extends Thread {
    String title = "";
    MyThread (String title) {
        this.title = title;
    }
    public void run() {
        System.out.println(title + "Thread running with priority: " + this.getPriority());
    }
}

public class ThreadPriority {
    public static void main(String[] args) {
        MyThread t1 = new MyThread("T1:");
        MyThread t2 = new MyThread("T2:");
        MyThread t3 = new MyThread("T3:");

        // Setting priorities
        t1.setPriority(Thread.MIN_PRIORITY); // Priority 1
        t2.setPriority(Thread.NORM_PRIORITY); // Priority 5
        t3.setPriority(Thread.MAX_PRIORITY); // Priority 10

        // Starting threads
        t1.start();
        t2.start();
        t3.start();

        System.out.println("This is main thread"); // Main thread always has high priority
    }
}