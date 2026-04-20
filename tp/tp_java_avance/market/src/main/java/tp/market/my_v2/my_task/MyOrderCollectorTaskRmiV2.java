package tp.market.my_v2.my_task;

import tp.market.v2.rmi.MarketRmiImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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

