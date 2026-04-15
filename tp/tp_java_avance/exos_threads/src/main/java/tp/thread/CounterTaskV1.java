package tp.thread;

import java.util.concurrent.Semaphore;

public class CounterTaskV1 implements Runnable {
    private boolean even; //pair , odd : impair
    private int max;
    private int counter;


    public CounterTaskV1(boolean even, int max) {
        this.even = even;
        this.max = max;
        this.counter = even?0:1;
    }

    public CounterTaskV1(boolean even){
        this(even,25);
    }

    public CounterTaskV1(){
        this(false);
    }


    @Override
    public void run() {
        do {
            System.out.print(this.counter + " ");
            this.counter+=2;
        }while(this.counter<=this.max);
    }
}

/*
exemple avec deux threads pair/even et impair/odd :
0 2 4 6 8 10 1 3 5 7 9 11 13 15 17 19 21 23 25 12 14 16 18 20 22 24
 */