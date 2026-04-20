package tp.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static tp.thread.PhilosopherTask.N;

public class DinerPhilosophesApp {
    public static void main(String[] args) {

        //PhilosopherTaskWithDeadLock.init();//V1 with blocking deadlock (with semaphore)
        PhilosopherTaskWithDeadLockV2.init();//V2 with blocking deadlock (with ReentrantLock)
       // PhilosopherTask.init();//V2 ok
        //PhilosopherTaskV2.init();//autre V2 ok

        // Create philosopher threads
        for (int i = 0; i < N; i++) {
            //(new Thread(new PhilosopherTaskWithDeadLock(i))).start(); //V1 with blocking deadlock (with semaphore)
            (new Thread(new PhilosopherTaskWithDeadLockV2(i))).start(); //V2 with blocking deadlock (with ReentrantLock)
            //(new Thread(new PhilosopherTask(i))).start(); //V2 ok
           // (new Thread(new PhilosopherTaskV2(i))).start(); //autre V2 ok
        }

        detectDeadlockAfterDelay(10*1000); //programatic way (using ThreadMXBean)
    }

    private static void detectDeadlockAfterDelay(long msDelay) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();

        try {
            Thread.currentThread().sleep(msDelay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long[] deadlockedIds = bean.findDeadlockedThreads();

        if (deadlockedIds == null) {
            System.out.println("No deadlock detected.");
            return;
        }

        ThreadInfo[] infos = bean.getThreadInfo(deadlockedIds, true, true);
        System.out.println("Deadlock detected! Threads involved:");
        for (ThreadInfo info : infos) {
            System.out.printf(
                    "  Thread '%s' (id=%d) is waiting on a lock held by '%s'%n",
                    info.getThreadName(),
                    info.getThreadId(),
                    info.getLockOwnerName()
            );
        }
    }
}
