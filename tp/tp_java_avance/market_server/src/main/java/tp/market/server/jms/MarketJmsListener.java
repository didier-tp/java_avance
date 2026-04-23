package tp.market.server.jms;

import lombok.extern.slf4j.Slf4j;
import tp.market.jms.MarketMessage;
import tp.market.core.model.MyStockMarket;
import tp.market.core.model.Stock;
import tp.market.core.model.TradingOrder;
import tp.market.core.service.OrderBookService;
import tp.market.core.service.OrderBookServiceImpl;
import tp.market.util.MyJsonUtil;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MarketJmsListener implements MessageListener {
    private Session session;
    private OrderBookService orderBookService = new OrderBookServiceImpl();

    public MarketJmsListener(Session session){
        this.session=session;
    }

    public void sendStockListInResponseQueue(List<Stock> stockList){
        try {
            Queue queue=session.createQueue("queue.resp_market"); //NB: createQueue() create a new queue or open an existing one
            MessageProducer messageProducer = session.createProducer(queue);
            TextMessage message = session.createTextMessage();
            message.setStringProperty(MarketMessage.MARKET_MESSAGE_TYPE , MarketMessage.STOCK_LIST);
            message.setText(MyJsonUtil.toJsonString(stockList));
            log.trace("MarketJmsListener Sending stockList as message in resp_market: " + message.getText());
            messageProducer.send(message);
            messageProducer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        //System.out.println("MessageListener , request_message= "+message);
        try {
            String marketMessageType = message.getStringProperty(MarketMessage.MARKET_MESSAGE_TYPE);
            log.trace("MarketJmsListener receiving message of type="+marketMessageType);
            switch(marketMessageType){
                case MarketMessage.GET_STOCK_LIST :
                    List<Stock> stockList = new ArrayList<>(MyStockMarket.getInstance().getStockMap().values());
                    sendStockListInResponseQueue(stockList);
                    break;
                case MarketMessage.TRADING_ORDER:
                    TextMessage textMessage = (TextMessage) message;
                    String orderJsonString = textMessage.getText();
                    log.trace("MarketJmsListener receiving order request in req_market queue: " + orderJsonString);
                    TradingOrder order = MyJsonUtil.fromJsonString(orderJsonString,TradingOrder.class);
                    orderBookService.addOrder(order);
                    log.info("via JMS, received tradingOrder=" + order);
                    break;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
