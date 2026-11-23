package tp.thread;

public class MyCounterApp {
    public static void main(String[] args) {
        System.out.println("CounterApp (even,odd) with 2 threads , alternate execution (v2)");
        //Thread evenThread = new Thread(new MyCounterTaskV1(true));
        //Thread evenThread = Thread.ofPlatform().unstarted(new MyCounterTaskV1(true));
        //Thread evenThread = Thread.ofVirtual().unstarted(new MyCounterTaskV1(true));
        //Thread evenThread = Thread.ofVirtual().unstarted(new MyCounterTaskV2AvecSemaphore(true));
        Thread evenThread = Thread.ofVirtual().unstarted(new MyCounterTaskV2AvecLock(true));
        //Thread oddThread = new Thread(new MyCounterTaskV1(false));
        //Thread oddThread = Thread.ofPlatform().unstarted(new MyCounterTaskV1(false));
        //Thread oddThread = Thread.ofVirtual().unstarted(new MyCounterTaskV1(false));
        //Thread oddThread = Thread.ofVirtual().unstarted(new MyCounterTaskV2AvecSemaphore(false));
        Thread oddThread = Thread.ofVirtual().unstarted(new MyCounterTaskV2AvecLock(false));
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
