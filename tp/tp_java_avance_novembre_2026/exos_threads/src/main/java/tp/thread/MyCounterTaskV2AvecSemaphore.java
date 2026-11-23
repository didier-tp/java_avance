package tp.thread;

import java.util.concurrent.Semaphore;

public class MyCounterTaskV2AvecSemaphore implements Runnable  {

        private boolean even; //pair , odd : impair
        private int max;
        private int counter;

    private static Semaphore readyEvenMutexAsSemaphore = new Semaphore(1);
    private static Semaphore readyOddMutexAsSemaphore = new Semaphore(0);

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


    public MyCounterTaskV2AvecSemaphore(boolean even, int max) {
            this.even = even;
            this.max = max;
            this.counter = even?0:1;
        }

    public MyCounterTaskV2AvecSemaphore(boolean even){
            this(even,25);
        }

    public MyCounterTaskV2AvecSemaphore(){
            this(false);
        }


        @Override
        public void run() {
            try {
                do {
                    waitOurTurn();
                    System.out.print(this.counter + " ");
                    this.counter+=2;
                    notifyYourTurn();
                }while(this.counter<=this.max);
            } catch (InterruptedException e) {
                //throw new RuntimeException(e);
                e.printStackTrace();
            }
        }

}
