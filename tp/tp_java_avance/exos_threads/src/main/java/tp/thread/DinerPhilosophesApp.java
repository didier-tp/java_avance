package tp.thread;

import java.util.concurrent.Semaphore;

import static tp.thread.PhilosopherTask.N;

public class DinerPhilosophesApp {
    public static void main(String[] args) {

        PhilosopherTask.init();

        // Create philosopher threads
        for (int i = 0; i < N; i++) {
            (new Thread(new PhilosopherTask(i))).start();
        }
    }
}
