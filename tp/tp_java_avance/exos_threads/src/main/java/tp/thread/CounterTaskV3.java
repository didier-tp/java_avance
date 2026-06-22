package tp.thread;

import java.util.concurrent.locks.ReentrantLock;

//Autre Version threads even/odd en fonctionnement alterné avec:
//ReentrantLock() avec true en argument du constructeur
// pour ReentrantLockLock en mode "fair" = selon l'odre des requêtes avec .lock()
//et nano pause de 1 nano seconde entre deux iterations

public class CounterTaskV3 implements Runnable {
    private boolean even; //pair , odd : impair
    private int max;
    private int counter;

    private static ReentrantLock alternateLock = new ReentrantLock(true);

    public CounterTaskV3(boolean even, int max) {
        this.even = even;
        this.max = max;
        this.counter = even?0:1;
    }

    public CounterTaskV3(boolean even){
        this(even,25);
    }

    public CounterTaskV3(){
        this(false);
    }


    @Override
    public void run() {
        do {
            alternateLock.lock();
            System.out.print(this.counter + " ");
            this.counter+=2;
            try { Thread.sleep(0 , 1); } catch (InterruptedException e) {   throw new RuntimeException(e);  }
            alternateLock.unlock();
        }while(this.counter<=this.max);
    }
}

/*
exemple avec deux threads pair/even et impair/odd :
0 2 4 6 8 10 1 3 5 7 9 11 13 15 17 19 21 23 25 12 14 16 18 20 22 24
 */