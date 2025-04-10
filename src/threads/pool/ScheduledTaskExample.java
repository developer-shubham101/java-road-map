package threads.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledTaskExample {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Schedule a task to run after a delay
        scheduler.schedule(() -> System.out.println("Task executed after 2 seconds delay."), 2, TimeUnit.SECONDS);

        // Schedule a task to run periodically
        scheduler.scheduleAtFixedRate(() -> System.out.println("Periodic task executed."), 0, 5, TimeUnit.SECONDS);
        
        // Note: You might want to call shutdown() eventually to stop the scheduler.
    }
}