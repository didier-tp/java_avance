package tp.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/*
NB: ce code conduit à un blocage ("deadlock") si seulement N forkSemaphore.
pour éviter l'inter-blocage , on peut tenter tryLock ou tryAcquire() avec timeout
 */

public class PhilosopherTaskV2 implements Runnable {
    public static final int N = 5;  // Number of philosophers
    public static final Semaphore[] forkSemaphore = new Semaphore[N];


    public static void init(){
        // Initialize fork semaphores
        for (int i = 0; i < N; i++) {
            PhilosopherTaskV2.forkSemaphore[i] = new Semaphore(1);
        }
    }

    private final int id;

    PhilosopherTaskV2(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Philosopher " + id + " is thinking...");
            sleep(1);
            boolean needToEat = true;
            try {
                while(needToEat) {
                    // try to Pick up left fork
                    if (forkSemaphore[id].tryAcquire(5, TimeUnit.MILLISECONDS)) {
                        // try to Pick up right fork
                        if (forkSemaphore[(id + 1) % N].tryAcquire(5, TimeUnit.MILLISECONDS)) {
                            System.out.println("Philosopher " + id + " is eating...");
                            sleep(1);
                            // Put down right fork
                            forkSemaphore[(id + 1) % N].release();
                            needToEat=false;
                        }
                        // Put down left fork
                        forkSemaphore[id].release();
                    }
                }
                System.out.println("Philosopher " + id + " finished eating and put down forks.");
            } catch (InterruptedException e) { e.printStackTrace(); }

        }
    }

    private void sleep(int seconds) {
        try { TimeUnit.SECONDS.sleep(seconds); } catch (InterruptedException e) { e.printStackTrace(); }
    }
}