package tp.thread;

public class CounterApp {
    public static void main(String[] args) {
        System.out.println("CounterApp (even,odd) with 2 threads , alternate execution (v2)");
        //Thread evenThread = new Thread(new CounterTaskV1(true));
        //Thread oddThread = new Thread(new CounterTaskV1(false));
        //Thread evenThread = new Thread(new CounterTaskV2(true));
        //Thread oddThread = new Thread(new CounterTaskV2(false));
        //Thread evenThread = new Thread(new CounterTaskV3(true));
        Thread evenThread = Thread.ofVirtual().unstarted(new CounterTaskV3(true));
        //Thread oddThread = new Thread(new CounterTaskV3(false));
        Thread oddThread = Thread.ofVirtual().unstarted(new CounterTaskV3(false));
        evenThread.start();
        oddThread.start();
        try {
            evenThread.join();
            oddThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
