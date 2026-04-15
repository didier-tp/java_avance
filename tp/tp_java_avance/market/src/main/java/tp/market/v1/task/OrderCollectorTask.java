package tp.market.v1.task;

import tp.market.model.TradingOrder;
import tp.market.service.OrderBookService;
import tp.market.service.OrderBookServiceImpl;
import tp.market.service.OrderService;
import tp.market.service.OrderServiceImpl;
import tp.market.util.MyThreadUtil;

public class OrderCollectorTask implements Runnable{

    private boolean stop=false;
    private String traderId;

    public OrderCollectorTask(String traderId) {
        this.traderId = traderId;
    }

    public OrderCollectorTask(){
        this("_trader_"+Thread.currentThread().getName());
    }

    @Override
    public void run() {
        int n=0;
        OrderService orderService = new OrderServiceImpl();
        OrderBookService orderBookService = new OrderBookServiceImpl();
        System.out.println("OrderCollectorTask for traderId="+traderId);
        while(!stop){
            n++;
            MyThreadUtil.pause(100); //100ms
            double trendPct = 1; //1% de hausse (tendance journalière)
            TradingOrder tradingOrder = orderService.newRandomOrderWithTrendPercent(traderId,trendPct);
            orderBookService.addOrder(tradingOrder);
            if(n>100) stop=true;
        }
    }
}

