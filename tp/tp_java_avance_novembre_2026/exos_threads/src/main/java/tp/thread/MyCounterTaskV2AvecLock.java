package tp.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class MyCounterTaskV2AvecLock implements Runnable  {

        private boolean even; //pair , odd : impair
        private int max;
        private int counter;

    //NE PAS OUBLIER static devant ReentrantLock chacunSonTourlock
    static ReentrantLock chacunSonTourlock = new ReentrantLock(true); //true pour "fair" lock (selon ordre des appels à .lock())


    public MyCounterTaskV2AvecLock(boolean even, int max) {
            this.even = even;
            this.max = max;
            this.counter = even?0:1;
        }

    public MyCounterTaskV2AvecLock(boolean even){
            this(even,25);
        }

    public MyCounterTaskV2AvecLock(){
            this(false);
        }


        @Override
        public void run() {
                do {
                    chacunSonTourlock.lock();  //avec contrôle de l'ordre d'execution (surtout en mode "fair")
                    //synchronized(this) { //sans contrôle de l'ordre d'execution
                    System.out.print(this.counter + " ");
                    this.counter+=2;
                    //}
                    chacunSonTourlock.unlock();
                    /*
                    try {
                        Thread.sleep(5);//2ms de pause  MAIS pas d'aternance parfaite si verrour par en mode "fair"
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }*/
                }while(this.counter<=this.max);
        }

}
