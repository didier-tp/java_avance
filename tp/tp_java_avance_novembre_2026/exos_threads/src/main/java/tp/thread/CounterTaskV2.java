package tp.thread;

import java.util.concurrent.Semaphore;

public class CounterTaskV2 implements Runnable {
    private boolean even; //pair , odd : impair
    private int max;
    private int counter;

    private static Semaphore readyEvenMutexAsSemaphore = new Semaphore(1);
    private static Semaphore readyOddMutexAsSemaphore = new Semaphore(0);

    public CounterTaskV2(boolean even, int max) {
        this.even = even;
        this.max = max;
        this.counter = even?0:1;
    }

    public CounterTaskV2(boolean even){
        this(even,25);
    }

    public CounterTaskV2(){
        this(false);
    }

    private void waitOurTurn() throws  InterruptedException{
        if(this.even)
            readyEvenMutexAsSemaphore.acquire();
        else
            readyOddMutexAsSemaphore.acquire();
    }

    private void notifyYourTurn(){
        if(this.even)
            readyOddMutexAsSemaphore.release();
        else
            readyEvenMutexAsSemaphore.release();
    }


    @Override
    public void run() {
        do {
            try {
                waitOurTurn();
                System.out.print(this.counter + " ");
                this.counter+=2;
                notifyYourTurn();
            } catch (/*Interrupted*/Exception e) {
                throw new RuntimeException(e);
            }
        }while(this.counter<=this.max);
    }
}

/*
Without waitOurTurn(); and notifyYourTurn() :
1 3 5 0 7 2 4 9 6 11 8 10 12 14 16 18 20 22 24 13 15 17 19 21 23 25
or
1 3 5 7 9 11 13 15 17 19 21 23 25 0 2 4 6 8 10 12 14 16 18 20 22 24
or ...
====
With waitOurTurn(); and notifyYourTurn() :
0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25
 */