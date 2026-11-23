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
        try {
            while (true) {
                System.out.println("Philosopher " + id + " is thinking...");
                sleep(1);

                // Pick up left fork
                //forkLock[id].lock();
                if(forkLock[id].tryLock(5,TimeUnit.SECONDS)) {

                    // Pick up right fork
                    if(forkLock[(id + 1) % N].tryLock(5,TimeUnit.SECONDS)) {

                        System.out.println("Philosopher " + id + " is eating (with 2 forks)");
                        sleep(1);

                        // Put down right fork
                        forkLock[(id + 1) % N].unlock();
                    }
                    else{
                        System.out.println("Philosopher " + id + " cannot eat , no right fork !!!");
                    }

                    // Put down left fork
                    forkLock[id].unlock();

                }
                else{
                    System.out.println("Philosopher " + id + " cannot eat , no left fork !!!");
                }

                System.out.println("Philosopher " + id + " finished eating and put down forks.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sleep(int seconds) {
        try { TimeUnit.SECONDS.sleep(seconds); } catch (InterruptedException e) { e.printStackTrace(); }
    }
}