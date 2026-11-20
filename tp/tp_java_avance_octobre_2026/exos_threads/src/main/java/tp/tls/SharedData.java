package tp.tls;

public class SharedData {
    private volatile int compteurGlobal=0;
    private ThreadLocal<Integer> compteurParThread = new ThreadLocal<>();

    public synchronized void  incrementCompteurGlobal(){
        compteurGlobal++;
    }

    public synchronized void  incrementCompteurParThread(){
        Integer currValue = compteurParThread.get();
        if(currValue==null){
            compteurParThread.set(1);
        }else compteurParThread.set(currValue+1);
    }

    public int getCompteurGlobal(){
        return compteurGlobal;
    }

    public int getCompteurParThread(){
        return compteurParThread.get();
    }
}
