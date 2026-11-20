package tp.market.server.task;

import lombok.extern.slf4j.Slf4j;
import tp.market.message.*;
import tp.market.core.model.MyStockMarket;
import tp.market.core.model.Stock;
import tp.market.core.model.TradingOrder;
import tp.market.core.service.OrderBookService;
import tp.market.core.service.OrderBookServiceImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
            DataInputStream dataIn = new DataInputStream(in);
            DataOutputStream dataOut = new DataOutputStream(out);
            int n=0;

            while(!stop) {
                //1. read message type (ORDER_MESSAGE_TYPE or ...):
                String requestMessage = null;
                try {
                    requestMessage = MySimpleMessage.readSimpleMessage(dataIn);
                    log.trace("OrderCollectorSocketTask , input message type=" + requestMessage);
                } catch (EndReadLoopException e) {
                    //no more things to read , end of loop
                    stop=true; continue;
                }

                //2. load other message part
                switch (requestMessage) {
                    case MyTradingMessage.STOCK_LIST_REQUEST_MESSAGE:
                        log.info("received stockListRequestMessage , building and sending response ... ");
                        List<Stock> listeActions = new ArrayList<>(MyStockMarket.getInstance().getStockMap().values());
                        MyMessageWithLengthAndDataBuffer.writeMessageWithSerializedData(dataOut,
                                MyTradingMessage.STOCK_LIST_RESPONSE_MESSAGE,
                                listeActions);
                        //log.info("STOCK_LIST_RESPONSE_MESSAGE sent with nbActions="+listeActions.size());
                        System.out.println("STOCK_LIST_RESPONSE_MESSAGE sent with nbActions="+listeActions.size());
                        break;

                    case MyTradingMessage.ORDER_MESSAGE:
                        TradingOrder tradingOrder = (TradingOrder) MyMessageWithLengthAndDataBuffer.readMessageDataObject(dataIn);
                        log.trace("received tradingOrder=" + tradingOrder);
                        n++;
                        orderBookService.addOrder(tradingOrder);
                        break;
                }
            }
            //log.info("*** received tradingOrders , n=" + n);
            System.out.println("*** received tradingOrders , n=" + n);


         }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
