package tp.thread;

public class MyCounterApp {
    public static void main(String[] args) {
        MyCounterTaskV1 compterPair = new MyCounterTaskV1(true);
        MyCounterTaskV1 compterImpair = new MyCounterTaskV1(false);
        Thread tPair = new Thread(compterPair); tPair.start();
        Thread tImpair = new Thread(compterImpair); tImpair.start();
    }
}
