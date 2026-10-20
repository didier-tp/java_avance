package tp.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/*
NB: ce code conduit à un blocage ("deadlock") si seulement N forkSemaphore.
pour éviter l'inter-blocage , il faut un autre sémaphore "room" (salle où seulement N-1 philosophes peuvent entrer pour manger)
 */

public class PhilosopherTaskWithoutDeadLock implements Runnable {
    public static final int N = 5;  // Number of philosophers
    public static final Semaphore[] forkSemaphore = new Semaphore[N];
    public static final Semaphore roomSemaphore = new Semaphore(N-1); //représente le nombre de places libres dans la salle à manger
    //N-1 pour qu'il y ait assez de fourchettes pour tout le monde


    public static void init(){
        // Initialize fork semaphores
        for (int i = 0; i < N; i++) {
            PhilosopherTaskWithoutDeadLock.forkSemaphore[i] = new Semaphore(1);
        }
    }

    private final int id;

    PhilosopherTaskWithoutDeadLock(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Philosopher " + id + " is thinking...");
            sleep(1);

            //enter dinner room (essential , without this : deadlock) , //INDISPENSABLE:
            try { roomSemaphore.acquire();  } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println("Philosopher " + id + " is in dinning room ...");

            // Pick up left fork
            try { forkSemaphore[id].acquire(); } catch (InterruptedException e) { e.printStackTrace(); }

            // Pick up right fork
            try { forkSemaphore[(id + 1) % N].acquire(); } catch (InterruptedException e) { e.printStackTrace(); }

            System.out.println("Philosopher " + id + " is eating...");
            sleep(1);

            // Put down left fork
            forkSemaphore[id].release();

            // Put down right fork
            forkSemaphore[(id + 1) % N].release();

            //exit (dinner room) :
            roomSemaphore.release();
            System.out.println("Philosopher " + id + " has leave the dinning room ...");

            System.out.println("Philosopher " + id + " finished eating and put down forks.");
        }
    }

    private void sleep(int seconds) {
        try { TimeUnit.SECONDS.sleep(seconds); } catch (InterruptedException e) { e.printStackTrace(); }
    }
}