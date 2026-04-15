package tp.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/*
NB: ce code conduit à un blocage ("deadlock") si seulement N forkSemaphore.
pour éviter l'inter-blocage , il faut un autre sémaphore "room" (salle où seulement N-1 philosophes peuvent entrer pour manger)
 */

public class PhilosopherTask implements Runnable {
    public static final int N = 5;  // Number of philosophers
    public static final Semaphore[] forkSemaphore = new Semaphore[N];
    public static final Semaphore roomSemaphore = new Semaphore(N-1);

    public static void init(){
        // Initialize fork semaphores
        for (int i = 0; i < N; i++) {
            PhilosopherTask.forkSemaphore[i] = new Semaphore(1);
        }
    }

    private final int id;

    PhilosopherTask(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Philosopher " + id + " is thinking...");
            sleep(1);

            /************************/
            //enter dinner room (essential , without this : deadlock) , //INDISPENSABLE:
            try { roomSemaphore.acquire(); } catch (InterruptedException e) { e.printStackTrace(); }
            /************************/

            // Pick up left fork
            try { forkSemaphore[id].acquire(); } catch (InterruptedException e) { e.printStackTrace(); }

            // Pick up right fork
            try { forkSemaphore[(id + 1) % N].acquire(); } catch (InterruptedException e) { e.printStackTrace(); }

            System.out.println("Philosopher " + id + " is eating...");
            sleep(2);

            // Put down left fork
            forkSemaphore[id].release();

            // Put down right fork
            forkSemaphore[(id + 1) % N].release();

            /************************/
            //exit (dinner room) :
            roomSemaphore.release();  //INDISPENSABLE
            /************************/

            System.out.println("Philosopher " + id + " finished eating and put down forks.");
        }
    }

    private void sleep(int seconds) {
        try { TimeUnit.SECONDS.sleep(seconds); } catch (InterruptedException e) { e.printStackTrace(); }
    }
}