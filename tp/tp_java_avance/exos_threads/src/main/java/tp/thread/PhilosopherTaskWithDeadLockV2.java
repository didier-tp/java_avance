package tp.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/*
NB: ce code conduit à un blocage ("deadlock") .
version sans sémaphore mais avec ReentrantLock
--> les deadlocks sont plus facilement détectables avec des ReentrantLocks qu'avec des Semaphores
car un lock appartient à un instant t à un thread alors qu'un sémaphore n'appartient pas à un thread : il peut être acquis par un thread et rendu par un autre
 */

public class PhilosopherTaskWithDeadLockV2 implements Runnable {
    public static final int N = 5;  // Number of philosophers
    public static final ReentrantLock[] forkLock = new ReentrantLock[N];


    public static void init(){
        // Initialize fork semaphores
        for (int i = 0; i < N; i++) {
            PhilosopherTaskWithDeadLockV2.forkLock[i] = new ReentrantLock();
        }
    }

    private final int id;

    PhilosopherTaskWithDeadLockV2(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Philosopher " + id + " is thinking...");
            sleep(1);

            // Pick up left fork
            forkLock[id].lock();

            // Pick up right fork
            forkLock[(id + 1) % N].lock();

            System.out.println("Philosopher " + id + " is eating...");
            sleep(1);

            // Put down left fork
            forkLock[id].unlock();

            // Put down right fork
            forkLock[(id + 1) % N].unlock();


            System.out.println("Philosopher " + id + " finished eating and put down forks.");
        }
    }

    private void sleep(int seconds) {
        try { TimeUnit.SECONDS.sleep(seconds); } catch (InterruptedException e) { e.printStackTrace(); }
    }
}