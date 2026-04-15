package tp.thread;

/*
classe "cas d'école" pour visualiser les problèmes de concurrence d'accès
 et les besoins de synchonisations élementaires
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyNumberConcurrentListV1WithBugs implements Runnable{

    public enum ThreadRole { ADD_RANDOM , RETREIVE_EVEN , RETREIVE_ODD }
    private ThreadRole threadRole;
    private long delay=0; //en ms : /pause en deux itérations
    private int n=30; //nombre d'itérations

    private List<Integer> numbers ; //référence sur liste commune

    public MyNumberConcurrentListV1WithBugs(ThreadRole threadRole, List<Integer> numbers){
        this.threadRole=threadRole;
        this.numbers=numbers;
        delay = 1 + (long) (Math.random() * 2);  //environ 1 à 3ms
    }

    public static void main(String[] args) {
        System.out.println("MyNumberConcurrentList");
        List<Integer> commonNumbers = new ArrayList<>();
        Thread addRandomThread = new Thread(new MyNumberConcurrentListV1WithBugs(ThreadRole.ADD_RANDOM , commonNumbers));
        Thread retreiveEvenThread = new Thread(new MyNumberConcurrentListV1WithBugs(ThreadRole.RETREIVE_EVEN, commonNumbers));
        Thread retreiveOddThread = new Thread(new MyNumberConcurrentListV1WithBugs(ThreadRole.RETREIVE_ODD, commonNumbers));
        addRandomThread.start();retreiveEvenThread.start();retreiveOddThread.start();
    }

    @Override
    public void run() {
        switch (this.threadRole){
            case ADD_RANDOM : addRandomNumberInList(); break;
            case RETREIVE_EVEN : retreiveEvenNumbersInList(); break;
            case RETREIVE_ODD: retreiveOddNumbersInList(); break;
        }
    }

    private void addRandomNumberInList() {
        for(int i=0;i<n;i++){
            for(int j=0;j<10;j++){
                int randomInt = (new Random()).nextInt(0,255);

                    numbers.add(randomInt); //ajouter 6 nombres aléatoires entre 0 et 255 sans arret ou bien toutes les 2ms environ

            }
            try { if(delay>0) Thread.sleep(delay); } catch (InterruptedException e) {  e.printStackTrace();  }
        }
    }

    private void retreiveEvenNumbersInList() {
        for(int i=0;i<n;i++){
            //extraire et afficher les nombres pairs sans arret ou bien toutes les 2ms environ:
            System.out.println("extracted even numbers : " + extractNumbers(true));
            try { if(delay>0) Thread.sleep(delay); } catch (InterruptedException e) {  e.printStackTrace();  }
        }
    }

    private void retreiveOddNumbersInList() {
        for(int i=0;i<n;i++){
            //extraire et afficher les nombres impairs sans arret ou bien toutes les 2ms environ:
            System.out.println("extracted odd numbers : " + extractNumbers(false));
            try { if(delay>0) Thread.sleep(delay); } catch (InterruptedException e) {  e.printStackTrace();  }
        }
    }

    private List<Number> extractNumbers(boolean shouldBeEven){
        List<Number> extractedList = new ArrayList<>();
        //collect and removeAll after loop to avoid ConcurrentModificationException on java.util.List

            for (Integer number : this.numbers) {
                if (number % 2 == 0) {
                    //even/pair
                    if (shouldBeEven) {
                        extractedList.add(number);
                        this.numbers.remove(number);
                    }
                } else {
                    //odd/impair
                    if (!shouldBeEven) {
                        extractedList.add(number);
                        this.numbers.remove(number);
                    }
                }
            }
           //this.numbers.removeAll(extractedList);

        return extractedList;
    }


}
