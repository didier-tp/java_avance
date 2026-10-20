package tp.thread;

public class MyCounterTaskV1 implements Runnable{
    private boolean pair=false;

    public MyCounterTaskV1(boolean pair){
        this.pair = pair;
    }



    @Override
    public void run() {
        int n=0;
       if(!this.pair){
           n=1;
       }
       while(n<25){
           System.out.print(" " +n);
           n=n+2;
       }
    }
}
