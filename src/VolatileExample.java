public class VolatileExample {
    private volatile boolean running = true;

    public VolatileExample() {
        Thread worker = new Thread(() -> {
            while (running) {
                System.out.println("In While Loop");
            }
            System.out.println("Worker thread stopped.");
        });

        worker.start();

        try {
            Thread.sleep(100); // Let the worker run for a second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        running = false; // Change the state to stop the worker thread
        System.out.println("Main thread changed running to false.");
    }
}
