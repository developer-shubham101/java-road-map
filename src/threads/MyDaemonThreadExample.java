package threads;


public class MyDaemonThreadExample {
    public static void main(String[] args) {
        MyDaemonThread daemonThread = new MyDaemonThread();
//        daemonThread.setDaemon(true); // Marking thread as daemon
        daemonThread.start();

        // Main thread will exit, and JVM will shut down, killing daemon thread
        System.out.println("Main thread finished");
    }
}

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
