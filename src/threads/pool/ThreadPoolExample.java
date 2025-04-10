package threads.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExample {
    public static void main(String[] args) {
        // Create a fixed thread pool with 2 threads
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Submit tasks to the executor
        executor.submit(() -> {
            System.out.println("Executing Task " + 1 + " by " + Thread.currentThread().getName());
        });

        executor.submit(() -> {
            System.out.println("Executing Task " + 2 + " by " + Thread.currentThread().getName());
        });

        // Shutdown the executor
        executor.shutdown();
    }
}