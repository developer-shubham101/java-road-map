**Daemon threads** in Java are special types of threads that run in the background and perform tasks such as garbage collection, monitoring, and other low-priority operations. They are typically used for service-like functions that the application doesn't need to wait for when exiting. Unlike normal (non-daemon) threads, daemon threads do not prevent the JVM from shutting down when the main program finishes.

### Key Characteristics of Daemon Threads:

1. **Background Role**: Daemon threads usually perform background tasks such as cleaning up resources, performing monitoring tasks, or serving as utility threads (e.g., garbage collection).

2. **Lifecycle**: If all non-daemon threads in the application have completed their execution, the JVM will exit, and any remaining daemon threads will be abruptly terminated without completing their tasks.

3. **Lower Priority**: Daemon threads typically have lower priority than non-daemon threads because their role is supportive rather than central to the application.

4. **Non-Blocking**: Since the JVM does not wait for daemon threads to finish, they don't block the termination of the program. Once all user threads have finished, the JVM terminates regardless of the state of daemon threads.

5. **Creation**: You can create a daemon thread by calling the `setDaemon(true)` method on a thread before it is started.

### Example of a Daemon Thread:
```java
class MyDaemonThread extends Thread {
    public void run() {
        while (true) {
            System.out.println("Daemon thread running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        MyDaemonThread daemonThread = new MyDaemonThread();
        daemonThread.setDaemon(true); // Marking thread as daemon
        daemonThread.start();
        
        // Main thread will exit, and JVM will shut down, killing daemon thread
        System.out.println("Main thread finished");
    }
}
```

### How to Identify a Daemon Thread:
You can check whether a thread is a daemon by using the `isDaemon()` method.

```java
Thread thread = new Thread();
System.out.println(thread.isDaemon());  // Returns false for a normal thread
```

### Key Points to Remember:
- Daemon threads should not be used for critical tasks because they can be terminated without notice.
- The main purpose of daemon threads is to support the application with low-priority, background tasks.
- Daemon threads can be abruptly terminated when the JVM exits, so avoid performing sensitive operations in them (like saving data or completing important transactions).

### Use Cases of Daemon Threads:
- **Garbage Collector**: The Java garbage collector runs as a daemon thread, periodically reclaiming unused memory.
- **Timer Services**: Monitoring services that perform periodic checks, such as checking for idle resources or memory leaks.
- **Background Logging**: Writing log entries to a file asynchronously in the background.
