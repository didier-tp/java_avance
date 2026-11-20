package tp.tls;

class SimpleTLSTask implements Runnable {

    private SharedData sharedData;

    public SimpleTLSTask(SharedData sharedData){
        this.sharedData=sharedData;
    }

    public void run(){
        for(int i=0;i<3;i++){
            sharedData.incrementCompteurGlobal();
            sharedData.incrementCompteurParThread();
        }
        try {
            Thread.sleep(500);//500ms de pause
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("from Thread="+Thread.currentThread().getName() + " compteurGlobal="+sharedData.getCompteurGlobal());
        System.out.println("from Thread="+Thread.currentThread().getName() + " compteurParThread="+sharedData.getCompteurParThread());
    }
}

public class TLSApp {
    public static void main(String[] args) {
        SharedData sharedData= new SharedData();
        Thread t1 = Thread.ofPlatform().name("t1").unstarted(new SimpleTLSTask(sharedData));
        Thread t2 = Thread.ofPlatform().name("t2").unstarted(new SimpleTLSTask(sharedData));
        t1.start();t2.start();
    }
}
