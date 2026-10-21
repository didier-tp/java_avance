package tp.thread;

public class MyCounterApp {
    public static void main(String[] args) {
        //MyCounterTaskV1 compterPair = new MyCounterTaskV1(true);
        //MyCounterTaskV1 compterImpair = new MyCounterTaskV1(false);
        MyCounterTaskV2 compterPair = new MyCounterTaskV2(true);
        MyCounterTaskV2 compterImpair = new MyCounterTaskV2(false);
        //Thread tPair = new Thread(compterPair); tPair.start();
        Thread tPair = Thread.ofVirtual().name("tPair").start(compterPair);
        //Thread tPair = Thread.ofPlatform().name("tPair").start(compterPair);
        Thread tImpair = Thread.ofVirtual().name("tImpair").unstarted(compterImpair); tImpair.start();
        try {
            tPair.join();
            tImpair.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
