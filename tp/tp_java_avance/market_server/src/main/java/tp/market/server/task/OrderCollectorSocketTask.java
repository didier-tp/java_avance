package tp.market.server.task;

import lombok.extern.slf4j.Slf4j;
import tp.market.message.*;
import tp.market.core.model.MyStockMarket;
import tp.market.core.model.Stock;
import tp.market.core.model.TradingOrder;
import tp.market.core.service.OrderBookService;
import tp.market.core.service.OrderBookServiceImpl;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderCollectorSocketTask  implements Runnable {
    private Socket socketClient;
    private boolean stop=false;

    public OrderCollectorSocketTask(Socket socketClient){
        this.socketClient=socketClient;
    }
    @Override
    public void run() {
        try {
            OrderBookService orderBookService = new OrderBookServiceImpl();
            InputStream in = socketClient.getInputStream();
            OutputStream out = socketClient.getOutputStream();
            int n=0;
            while(!stop) {
                //1. read message type (ORDER_MESSAGE_TYPE or ...):
                int messageType = 0;
                try {
                    messageType = BytesMessage.readIntegerFromBytes(in);
                    log.trace("OrderCollectorSocketTask , input message type=" + messageType);
                } catch (EndReadLoopException e) {
                    //no more things to read , end of loop
                    stop=true; continue;
                }
                //2. load other message part
                switch (messageType) {
                    case TradingMessage.STOCK_LIST_REQUEST_MESSAGE_TYPE:
                        StockListRequestMessage stockListRequestMessage = new StockListRequestMessage();
                        stockListRequestMessage.readSizeAndDataMessageBytes(in);
                        log.info("received stockListRequestMessage , building and sending response ... ");

                        List<Stock> stockList = new ArrayList<>(MyStockMarket.getInstance().getStockMap().values());
                        StockListResponseMessage stockListResponseMessage = new StockListResponseMessage(stockList);
                        stockListResponseMessage.writeMessageBytes(out);
                        break;

                    case TradingMessage.ORDER_MESSAGE_TYPE:
                        TradingOrderMessage tradingOrderMessage = new TradingOrderMessage();
                        tradingOrderMessage.readSizeAndDataMessageBytes(in);
                        log.trace("OrderCollectorSocketTask , input message size=" + tradingOrderMessage.getSize());
                        log.trace("OrderCollectorSocketTask , input message data=" + tradingOrderMessage.getData());
                        TradingOrder tradingOrder = tradingOrderMessage.getTradingOrder();
                        log.trace("received tradingOrder=" + tradingOrder);
                        n++;
                        orderBookService.addOrder(tradingOrder);
                        break;
                }
            }
            log.info("*** received tradingOrders , n=" + n);
         }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
