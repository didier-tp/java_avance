package tp.market.trader;

import tp.market.core.model.Stock;
import tp.market.core.model.TradingOrder;
import tp.market.rmi_api.MarketRemoteFactory;
import tp.market.rmi_api.OrderRmiApi;
import tp.market.rmi_api.StockRmiApi;
import tp.market.core.service.OrderService;
import tp.market.core.service.OrderServiceImpl;
import tp.market.util.MyThreadUtil;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class TraderRmiClient {

    public static void main(String[] args) {

        try {
            String chHostname = "localhost";
            if (args.length > 0) {
                chHostname = args[0];
            }

            System.out.println("demarrage de appli TraderRmiClient  ...");

            Registry rmiRegistry = LocateRegistry.getRegistry(chHostname, 1099);
            MarketRemoteFactory marketRmi = (MarketRemoteFactory) rmiRegistry.lookup("marketRmi");

            System.out.println("connexion a la factory <<marketRmi>> effectuee .");

            StockRmiApi stockRmiApi =  marketRmi.stockApi();
            //System.out.println("reference obtenue vers un objet distant de type StockRmiApi.");

            List<Stock> stockList = stockRmiApi.getCurrentStockList();
            System.out.println("stockList (from Rmi)="+stockList);

            OrderRmiApi orderRmiApi =  marketRmi.orderApi();
            sendTradingOrderMessages(orderRmiApi,stockList);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static void sendTradingOrderMessages(OrderRmiApi orderRmiApi, List<Stock> stockList){
        try {
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
                orderRmiApi.sendTradingOrder(tradingOrder);
                if (n > 500) stop = true;
            }
            System.out.println("tradingOrders was sent via rmi , n="+n);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

