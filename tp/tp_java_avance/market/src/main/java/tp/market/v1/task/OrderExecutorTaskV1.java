package tp.market.v1.task;

import tp.market.service.OrderBookService;
import tp.market.service.OrderBookServiceImpl;
import tp.market.util.MyThreadUtil;

public class OrderExecutorTaskV1 implements Runnable{

    private boolean stop=false;

    @Override
    public void run() {
        System.out.println("OrderExecutorTask : execute possible exchanges");
        OrderBookService orderBookService = new OrderBookServiceImpl();
        int n=0;
        while(!stop){
            n++;
            MyThreadUtil.pause(100); //100ms
            orderBookService.processPossibleOrders();
            if(n>100) stop=true;
        }
    }
}
