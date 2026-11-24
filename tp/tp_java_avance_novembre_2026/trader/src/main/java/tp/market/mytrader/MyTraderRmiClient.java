package tp.market.mytrader;

import tp.market.core.model.Stock;
import tp.market.rmi_api.MarketRemoteFactory;
import tp.market.rmi_api.StockRmiApi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class MyTraderRmiClient {
    public static void main(String[] args) {
        System.out.println("MyTraderRmiClient");
        try{
            String chHostname = "localhost";
            if(args.length>0){
                chHostname = args[0];
            }
            System.out.println("demarrage de appli MyTraderRmiClient ...");
            Registry rmiRegistry = LocateRegistry.getRegistry(chHostname, 1099);
            MarketRemoteFactory myMarketRmi = (MarketRemoteFactory) rmiRegistry.lookup("myMarketRmi");
            System.out.println("connexion a la factory <<myMarketRmi>> effectuee .");
            //access indirect à l'objet distant StockRmiApi depuis la factory:
            StockRmiApi stockRmiApi =  myMarketRmi.stockApi();
            List<Stock> listeActions = stockRmiApi.getCurrentStockList();
            System.out.println("listeActions="+listeActions);
            //...

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
