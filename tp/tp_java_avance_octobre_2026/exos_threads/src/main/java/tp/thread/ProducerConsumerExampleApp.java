package tp.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerExampleApp {

    public static void main(String[] args) {
        BlockingQueue<Double> queue = new LinkedBlockingQueue<>(); // Shared queue
        Thread producerThread = new Thread(new ValuesProducerTask(queue,2));//delay=2ms
        Thread consumerThread = new Thread(new ValuesConsumerTask(queue,4));//delay=4ms (chacun son rythme)
        producerThread.start();  consumerThread.start(); //same order
    }
}
