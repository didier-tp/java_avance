package tp.market.trader;

import tp.market.message.*;
import tp.market.core.model.Stock;
import tp.market.core.model.TradingOrder;
import tp.market.core.service.OrderService;
import tp.market.core.service.OrderServiceImpl;
import tp.market.util.MyThreadUtil;

import java.io.*;  import java.net.UnknownHostException;
import java.net.InetAddress;     import java.net.Socket;
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
            OutputStream out = socket.getOutputStream();
            List<Stock> stockList = stockListRequestResponseMessage(out,in);
            sendTradingOrderMessages(out,stockList);
        } catch (Exception e) {
            e.printStackTrace();
        } //try with auto_closeable resource=socket .
    }

    static List<Stock> stockListRequestResponseMessage(OutputStream out, InputStream in){
        StockListRequestMessage stockListRequestMessage = new StockListRequestMessage();
        stockListRequestMessage.writeMessageBytes(out); System.out.println("stockListRequestMessage was sent");
        //waiting , receive , display response:
        int messageType = BytesMessage.readIntegerFromBytes(in);
        System.out.println("StockList response message type=" + messageType);
        StockListResponseMessage stockListResponseMessage = new StockListResponseMessage();
        stockListResponseMessage.readSizeAndDataMessageBytes(in);
        List<Stock> stockList = stockListResponseMessage.getStockList();
        System.out.println("stockList (from response)=" + stockList);
        return stockList;
    }

    static void sendTradingOrderMessages(OutputStream out,List<Stock> stockList){
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
                TradingOrderMessage tradingOrderMessage = new TradingOrderMessage(tradingOrder);
                tradingOrderMessage.writeMessageBytes(out);
                if (n > 500) stop = true;
            }
            System.out.println("tradingOrders was sent , n="+n);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
