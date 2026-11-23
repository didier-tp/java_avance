package tp.thread;

import java.util.concurrent.BlockingQueue;

public class ValuesProducerTask implements Runnable{
    private long delay;
    private BlockingQueue<Double> queue;

    public ValuesProducerTask(BlockingQueue<Double> queue,long delay) {
        this.queue = queue;   this.delay = delay;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                double val = Math.random() * 100;
                queue.put(val); // Produces an item and puts it into the queue
                System.out.println("Produced and sent value: " + val);
                Thread.sleep(delay);
            }
            queue.put(-1.0); // Indicates end of production by adding a sentinel "-1.0" value
            //nb: cannot put "null" value : nullPointerException
        } catch (InterruptedException e) { Thread.currentThread().interrupt();  }
    }
}
