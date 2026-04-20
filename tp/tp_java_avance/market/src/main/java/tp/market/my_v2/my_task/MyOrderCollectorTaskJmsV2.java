package tp.market.my_v2.my_task;

import lombok.extern.slf4j.Slf4j;
import tp.market.v2.jms.MarketJmsListener;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

@Slf4j
public class MyOrderCollectorTaskJmsV2 implements Runnable {

    private String traderId;

    public MyOrderCollectorTaskJmsV2(String traderId) {
        this.traderId = traderId;
    }

    public MyOrderCollectorTaskJmsV2() {
        this("_trader_" + Thread.currentThread().getName());
    }

    @Override
    public void run() {
        //à coder librement en Tp
        System.out.println("MyOrderCollectorTaskJmsV2.run");
    }


    //à si besoin librement adapter en Tp
    private Connection getJmsConnection() {
        Connection connection = null;
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
            System.out.println("connectionFactory=" + connectionFactory);

            connection = connectionFactory.createConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }


}
