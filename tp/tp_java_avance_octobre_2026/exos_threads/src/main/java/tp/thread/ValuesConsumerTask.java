package tp.thread;

import java.util.concurrent.BlockingQueue;

public class ValuesConsumerTask implements Runnable {
    private long delay;
    private BlockingQueue<Double> queue;

    public ValuesConsumerTask(BlockingQueue<Double> queue,long delay) {
        this.queue = queue; this.delay=delay;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Double value = queue.take(); // Consumes an item from the queue
                if (value <= -1.0) {
                    break; // If sentinel value is encountered, stop consuming
                }
                System.out.println("extracted/consumed value: " + value);
                Thread.sleep(delay);
            }
        } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
