package tp.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class SyncWithAtomicApp {

    public static class ValuesAndStats{
        public int[] subvalues = { 1 , 2 , 3 , 1, 2 , 3, 1 ,2 ,3 }; //sous tableau de 9 éléments avec moyenne de 2
        public int[] values; //tableau complet avec 1000 répétition du sous tableau --> moyenne attendue = 2
        public AtomicLong somme = new AtomicLong(0);
        public ValuesAndStats(){
            int n = subvalues.length; //9 ou ...
            this.values = new int[n * 1000];
            for(int i=0;i<1000;i++){
                for(int j=0;j<n;j++){
                    int k = i*n+j;
                    this.values[k]=this.subvalues[j];
                }
            }
        }
    }


    public static class PartialSumTask implements Runnable{
        private ValuesAndStats valuesAndStats;
        private int firstIndex;
        private int lastExclusiseIndex;

        public PartialSumTask(ValuesAndStats valuesAndStats,int firstIndex,int lastExclusiseIndex){
            this.firstIndex=firstIndex;
            this.lastExclusiseIndex=lastExclusiseIndex;
            this.valuesAndStats = valuesAndStats;
        }

        public void run(){
            for(int i=firstIndex;i<lastExclusiseIndex;i++){
                   // valuesAndStats.somme += valuesAndStats.values[i];
                valuesAndStats.somme.getAndAdd(valuesAndStats.values[i]); //atomic incrementation or addition --> threadSafe (no need of syncrhonized )
            }
        }

        public void run_v2(){
            AtomicInteger subSomme = new AtomicInteger();
            IntStream.range(firstIndex,lastExclusiseIndex)
                    .peek(i -> {
                        subSomme.addAndGet(valuesAndStats.values[i]); //cannot compile : Variable used in lambda expression should be final or effectively final
                    })
                    .forEach( i -> {
                        valuesAndStats.somme.getAndAdd(valuesAndStats.values[i]);
                    });
            System.out.println("subSomme="+subSomme);
        }
    }

    public static void main(String[] args) {
        System.out.println("SyncWithAtomicApp");
        try {
            long td =System.nanoTime();
            ValuesAndStats valuesAndStats = new ValuesAndStats();
            Thread t1 = Thread.ofVirtual().unstarted(new PartialSumTask(valuesAndStats,0,1500));
            Thread t2 = Thread.ofVirtual().unstarted(new PartialSumTask(valuesAndStats,1500,3000));
            Thread t3 = Thread.ofVirtual().unstarted(new PartialSumTask(valuesAndStats,3000,4500));
            Thread t4 = Thread.ofVirtual().unstarted(new PartialSumTask(valuesAndStats,4500,6000));
            Thread t5 = Thread.ofVirtual().unstarted(new PartialSumTask(valuesAndStats,6000,7500));
            Thread t6 = Thread.ofVirtual().unstarted(new PartialSumTask(valuesAndStats,7500,9000));
            t1.start();t2.start();t3.start();t4.start(); t5.start(); t6.start();
            t1.join(); t2.join(); t3.join();t4.join(); t5.join(); t6.join();
            int nbValues = valuesAndStats.values.length;
            double average = valuesAndStats.somme.get() / nbValues;
            long tf =System.nanoTime();
            long tempCalcul = (tf - td);
            System.out.println("nbValues="+nbValues);
            System.out.println("temps de calcul  en ns : " + tempCalcul);
            System.out.println("temps de calcul en ms : " + tempCalcul/ 1000000);
            System.out.println("average="+average); //sans aucune synchronisation , average = 2.0 ou 1.576 ou .... pas fiable
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
