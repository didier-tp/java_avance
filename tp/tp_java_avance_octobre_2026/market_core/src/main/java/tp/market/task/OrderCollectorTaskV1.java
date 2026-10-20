package tp.market.task;

import tp.market.core.model.MyStockMarket;
import tp.market.core.model.TradingOrder;
import tp.market.core.service.OrderBookService;
import tp.market.core.service.OrderBookServiceImpl;
import tp.market.core.service.OrderService;
import tp.market.core.service.OrderServiceImpl;
import tp.market.exec.MarketTaskControl;
import tp.market.util.MyThreadUtil;

public class OrderCollectorTaskV1 implements Runnable{

    private boolean stop=false;
    private String traderId;

    public OrderCollectorTaskV1(String traderId) {
        this.traderId = traderId;
    }

    public OrderCollectorTaskV1(){
        this("_trader_"+Thread.currentThread().getName());
    }

    @Override
    public void run() {
        int n=0;
        OrderService orderService = new OrderServiceImpl(MyStockMarket.getInstance().getStockMap());
        OrderBookService orderBookService = new OrderBookServiceImpl();
        System.out.println("OrderCollectorTask for traderId="+traderId);
        while(!stop){
            n++;
            MyThreadUtil.pause(100); //100ms
            double trendPct = 1; //1% de hausse (tendance journalière)
            TradingOrder tradingOrder = orderService.newRandomOrderWithTrendPercent(traderId,trendPct);
            orderBookService.addOrder(tradingOrder);
            //if(n>100) stop=true;
            stop= MarketTaskControl.isStop();
        }
    }
}

