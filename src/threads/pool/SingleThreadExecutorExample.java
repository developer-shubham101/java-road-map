package threads.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutorExample {
    public static void main(String[] args) {
        // Create a SingleThreadExecutor
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Submit tasks to the executor
        for (int i = 1; i <= 5; i++) {
            executor.submit(new Task(i));
        }

        // Shutdown the executor
        executor.shutdown();


    }
}

class Task implements Runnable {
    int taskNumber;

    Task(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void run() {
        System.out.println("Executing Task " + taskNumber + " by " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);  // Simulate some work with a 1 second delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}