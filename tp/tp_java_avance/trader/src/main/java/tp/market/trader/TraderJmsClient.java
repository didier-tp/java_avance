package tp.market.trader;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;
import tp.market.jms.MarketMessage;
import tp.market.model.Stock;
import tp.market.model.TradingOrder;
import tp.market.service.OrderService;
import tp.market.service.OrderServiceImpl;
import tp.market.util.MyJsonUtil;
import tp.market.util.MyThreadUtil;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.List;
import java.util.Properties;

@Slf4j
public class TraderJmsClient {
    public static void main(String[] args) {
        System.out.println("TraderJmsClient");

        try{
            //Artemis
            ConnectionFactory connectionFactory = null;
            Properties props = new Properties();
            props.put("java.naming.factory.initial", "org.apache.activemq.artemis.jndi.ActiveMQInitialContextFactory");
            //props.put(Context.PROVIDER_URL, "tcp://localhost:5445?type=CF"); //port number for hornetq
            props.put(Context.PROVIDER_URL, "tcp://localhost:61616?type=CF"); //port number for actimemq/artemis
            props.put(Context.SECURITY_PRINCIPAL, "admin");//username , "..."
            props.put(Context.SECURITY_CREDENTIALS, "admin"); //"pwd", "..."

            InitialContext ic = new InitialContext(props);
            connectionFactory = (ConnectionFactory) ic.lookup("ConnectionFactory");
            System.out.println("connectionFactory="+connectionFactory);


            Connection connection = connectionFactory.createConnection();
            System.out.println("connection="+connection);

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            System.out.println("session="+session);


            Queue requestQueue=session.createQueue("queue.req_market"); //NB: createQueue() create a new queue or open an existing one
            Queue responseQueue=session.createQueue("queue.resp_market");

            sendGetStockListMessage(session,requestQueue);

            List<Stock> stockList = receiveStockListMessage(connection,session,responseQueue);
            log.info("stockList="+stockList);
            if(stockList!=null)
                sendOrderMessages(session,requestQueue,stockList);

            session.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendOrderMessages(Session session, Queue requestQueue,List<Stock> stockList){

        try {
            MessageProducer messageProducer = session.createProducer(requestQueue);
            boolean stop=false;
            //TradingOrder tradingOrder = new TradingOrder(TradingOrder.OrderType.SALE,"OR.PA",349.0 , 20);
            double trendPct = 1; //1% de hausse (tendance journalière)
            String traderId="2";
            OrderService orderService = new OrderServiceImpl(stockList);
            int n=0;
            while(!stop) {
                n++;
                MyThreadUtil.pause(10); //10ms
                TradingOrder tradingOrder = orderService.newRandomOrderWithTrendPercent(traderId, trendPct);
                //send tradingOrder to server:
                TextMessage message = session.createTextMessage();
                message.setStringProperty(MarketMessage.MARKET_MESSAGE_TYPE , MarketMessage.TRADING_ORDER);
                message.setText(MyJsonUtil.toJsonString(tradingOrder));
                log.trace("Sending JMS message: " + message.getText());
                messageProducer.send(message);
                if (n > 500) stop = true;
            }
            System.out.println("tradingOrders was sent via JMS , n="+n);
            messageProducer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Stock> receiveStockListMessage(Connection connection , Session session, Queue responseQueue){
        List<Stock> stockList = null;
        try {
            MessageConsumer messageConsumer =  session.createConsumer(responseQueue);

            connection.start();  //don't forget to call .start() for receiving message !!!

            long receivingTimeout = 1000; //1000ms = 1s
            Message message = messageConsumer.receive(receivingTimeout);
            while(message != null) {
                if(message instanceof TextMessage txtMessage) {
                    String jsonResponse = txtMessage.getText();
                    log.info("received message=" + jsonResponse);
                    stockList= MyJsonUtil.fromJsonString(jsonResponse, new TypeReference<List<Stock>>() {});
                }
                message = messageConsumer.receiveNoWait();
            }

            messageConsumer.close();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
        return stockList;
    }

    public static void sendGetStockListMessage(Session session, Queue requestQueue){
        try {
            MessageProducer messageProducer = session.createProducer(requestQueue);
            TextMessage message = session.createTextMessage();
            String s="get_stock_list";
            message.setStringProperty(MarketMessage.MARKET_MESSAGE_TYPE , MarketMessage.GET_STOCK_LIST);
            message.setText(s);
            System.out.println("Sending JMS message: " + message.getText());
            messageProducer.send(message);
            messageProducer.close();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
