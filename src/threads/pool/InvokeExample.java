package threads.pool;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class InvokeExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<Callable<Integer>> tasks = Arrays.asList(
            () -> { Thread.sleep(1000); return 1; },
            () -> { Thread.sleep(500); return 2; },
            () -> { Thread.sleep(200); return 3; }
        );

        try {
            // Invoke all tasks
            List<Future<Integer>> futures = executor.invokeAll(tasks);
            for (Future<Integer> future : futures) {
                System.out.println("Result: " + future.get());
            }

            // Invoke any task
            Integer result = executor.invokeAny(tasks);
            System.out.println("First completed task result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}