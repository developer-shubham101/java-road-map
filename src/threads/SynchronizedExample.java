package threads;

public class SynchronizedExample {
    private int counter = 0;

    public SynchronizedExample() {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                increment();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final Counter Value: " + getCounter()); // Should be 2000
    }

    public synchronized void increment() {
        counter++; // Synchronized method to ensure thread safety
    }

    public int getCounter() {
        return counter;
    }
}
