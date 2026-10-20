package tp.market.my_server.my_task;

public class MyOrderCollectorTaskRmiV2 implements Runnable{

    private String traderId;

    public MyOrderCollectorTaskRmiV2(String traderId) {
        this.traderId = traderId;
    }

    public MyOrderCollectorTaskRmiV2(){
        this("_trader_"+Thread.currentThread().getName());
    }

    @Override
    public void run() {
        //à coder librement en Tp
        System.out.println("MyOrderCollectorTaskRmiV2.run");
    }
}

