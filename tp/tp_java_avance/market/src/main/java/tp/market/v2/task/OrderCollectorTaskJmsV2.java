package tp.market.v2.task;

import lombok.extern.slf4j.Slf4j;
import tp.market.v2.jms.MarketJmsListener;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

@Slf4j
public class OrderCollectorTaskJmsV2 implements Runnable{

    private String traderId;

    public OrderCollectorTaskJmsV2(String traderId) {
        this.traderId = traderId;
    }

    public OrderCollectorTaskJmsV2(){
        this("_trader_"+Thread.currentThread().getName());
    }


    private Connection getJmsConnection(){
        Connection connection=null;
        try {
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

            connection = connectionFactory.createConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void run() {
        boolean stopServ=false;
        try{
            Connection connection = this.getJmsConnection();
            if(connection==null){
                log.info("cannot etablish jms broker connection , Artemis server/broker should be started first");
                return;
            }
            log.info("jms connection="+connection);

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            log.info("jms session="+session);

            String queueName = "queue.req_market";
            //String queueName = "queue.resp_market";
            Queue queue=session.createQueue(queueName); //NB: createQueue() create a new queue or open an existing one

            MessageConsumer messageConsumer =  session.createConsumer(queue);

            MarketJmsListener tradingListener= new MarketJmsListener(session);
            messageConsumer.setMessageListener(tradingListener);

            connection.start();  //don't forget to call .start() for receiving message !!!

            while(!stopServ) {
                Thread.sleep(1000 * 15); //15s
                //do to: set stopServ to true , v1 : server always running
            }
            messageConsumer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

