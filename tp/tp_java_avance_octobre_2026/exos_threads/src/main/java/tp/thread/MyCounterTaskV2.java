package tp.thread;

import java.util.concurrent.locks.ReentrantLock;

public class MyCounterTaskV2 implements Runnable{
    private boolean pair=false;
    //NE PAS OUBLIER static devant ReentrantLock chacunSonTourlock
    static ReentrantLock chacunSonTourlock = new ReentrantLock(true); //true pour "fair" lock (selon ordre des appels à .lock())
    public MyCounterTaskV2(boolean pair){
        this.pair = pair;
    }



    @Override
    public void run() {
        int n=0;
       if(!this.pair){
           n=1;
       }
       while(n<25){
           chacunSonTourlock.lock();  //avec contrôle de l'ordre d'execution (surtout en mode "fair")
           //synchronized(this) { //sans contrôle de l'ordre d'execution
               System.out.print(" " + n);
               n = n + 2;
           //}
           chacunSonTourlock.unlock();
       }
        //System.out.println("code executé par " + Thread.currentThread().toString());
    }
}
