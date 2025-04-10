package threads;

public class JoinThreadExample {
    public JoinThreadExample() {
        try {
            Thread one = new FirstThread();
            Thread two = new Thread(new SecondThread());

            one.start();
            two.start();

            one.join();
            two.join();

            System.out.println("Done execution the thread");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new JoinThreadExample();
    }

}

class FirstThread extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Thread 1 : " + i);
        }
    }
}

class SecondThread implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 25; i++) {
            System.out.println("Thread 2 : " + i);
        }
    }
}
