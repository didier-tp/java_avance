package tp.market.task;

import tp.market.core.service.OrderBookService;
import tp.market.core.service.OrderBookServiceImpl;
import tp.market.exec.MarketTaskControl;
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
           // if(n>100) stop=true;
            stop= MarketTaskControl.isStop();
        }
    }
}
