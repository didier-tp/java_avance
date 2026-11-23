package tp.thread;

public class MyCounterTaskV1 implements Runnable  {

        private boolean even; //pair , odd : impair
        private int max;
        private int counter;


    public MyCounterTaskV1(boolean even, int max) {
            this.even = even;
            this.max = max;
            this.counter = even?0:1;
        }

    public MyCounterTaskV1(boolean even){
            this(even,25);
        }

    public MyCounterTaskV1(){
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
