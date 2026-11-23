package tp.market.trader;

import tp.market.core.model.Stock;
import tp.market.core.model.TradingOrder;
import tp.market.core.service.OrderService;
import tp.market.core.service.OrderServiceImpl;
import tp.market.message.MyMessageWithLengthAndDataBuffer;
import tp.market.message.MySimpleMessage;
import tp.market.message.MyTradingMessage;
import tp.market.util.MyThreadUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class TraderSocketClient {

    static int port = 9632;

    public static void main(String[] args) {
        String host = (args.length>0)?args[0]:"localhost";
        InetAddress serveur = null;
        try {
            serveur = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        try  (Socket socket = new Socket(serveur, port)){
            System.out.println("connected to serveur socket");
            InputStream in = socket.getInputStream();
            DataInputStream dataIn = new DataInputStream(in);
            OutputStream out = socket.getOutputStream();
            DataOutputStream dataOut = new DataOutputStream(out);
            List<Stock> stockList = stockListRequestResponseMessage(dataIn,dataOut);
           sendTradingOrderMessages(dataOut,stockList);
        } catch (Exception e) {
            e.printStackTrace();
        } //try with auto_closeable resource=socket .
    }

    static List<Stock> stockListRequestResponseMessage(DataInputStream dataIn, DataOutputStream dataOut){
        MySimpleMessage.writeSimpleMessage(dataOut,MyTradingMessage.STOCK_LIST_REQUEST_MESSAGE);
        System.out.println("stockList request sent , waiting for response ...");

        //Recuperer la réponse (liste d'actions)
        String responseMessageType=MySimpleMessage.readSimpleMessage(dataIn);
        System.out.println("received response type:" + responseMessageType);
        List<Stock> stockList = (List<Stock>)  MyMessageWithLengthAndDataBuffer.readMessageDataObject(dataIn);
        System.out.println("stockList (from response)=" + stockList);
        return stockList;
    }


    static void sendTradingOrderMessages(DataOutputStream dataOut,List<Stock> stockList){
        try {
            boolean stop=false;
            //TradingOrder tradingOrder = new TradingOrder(TradingOrder.OrderType.SALE,"OR.PA",349.0 , 20);
            double trendPct = 1; //1% de hausse (tendance journalière)
            String traderId="1";
            OrderService orderService = new OrderServiceImpl(stockList);
            int n=0;
            System.out.println("OrderCollectorTask for traderId="+traderId);
            while(!stop) {
                n++;
                MyThreadUtil.pause(10); //10ms
                TradingOrder tradingOrder = orderService.newRandomOrderWithTrendPercent(traderId, trendPct);
                //send request to server:
                MyMessageWithLengthAndDataBuffer.writeMessageWithSerializedData(dataOut,MyTradingMessage.ORDER_MESSAGE,tradingOrder);
                if (n > 500) stop = true;
            }
            System.out.println("tradingOrders was sent , n="+n);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
