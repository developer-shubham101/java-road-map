import threads.SynchronizedExample;

public class Main {
    public static void main(String[] args) {
//        StringExample stringExample = new StringExample();
//        stringExample.memoryCheck();

//        new AtomicExample();
//        new VolatileExample();
        new SynchronizedExample();


        ThreadSafeSingleton.getInstance();

    }
}