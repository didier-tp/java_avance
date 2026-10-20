package tp.market.task;

import lombok.extern.slf4j.Slf4j;
import tp.market.core.model.MyStockMarket;
import tp.market.core.service.OrderBookService;
import tp.market.core.service.OrderBookServiceImpl;
import tp.market.exec.MarketTaskControl;
import tp.market.util.MyThreadUtil;

@Slf4j
public class RefreshExchangesTaskV1 implements Runnable{

    private boolean stop=false;

    @Override
    public void run() {
        MyStockMarket myStockMarket = MyStockMarket.getInstance();
        OrderBookService orderBookService = new OrderBookServiceImpl();
        log.trace("RefreshExchangesTask");
        int n=0;
        log.info("number of stocks:" + myStockMarket.numberOfStocks());
        while(!stop){
            n++;
            MyThreadUtil.pause(100); //100ms
            log.info("current trading orders: (bids=" + myStockMarket.totalNumberOfBids()
                                          +  ", asks="+myStockMarket.totalNumberOfAsks()
                                           +  ", exchanges="+myStockMarket.totalNumberOfExchanges()
                                             +")" );
            orderBookService.updateSomeQuotes(); //with log.info() when change of cquote
            //if(n>100) stop=true;
            stop= MarketTaskControl.isStop();
        }
    }
}
