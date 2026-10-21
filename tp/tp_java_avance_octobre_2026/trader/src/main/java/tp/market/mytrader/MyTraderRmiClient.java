package tp.market.mytrader;

import tp.market.core.model.Stock;
import tp.market.rmi_api.MarketRemoteFactory;
import tp.market.rmi_api.StockRmiApi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class MyTraderRmiClient {
    public static void main(String[] args) {
        System.out.println("MyTraderRmiClient");
        try {
            String chHostname = "localhost";
            if(args.length>0){
                chHostname = args[0];
            }
            System.out.println("demarrage de appli MiniBankSimpleClient ...");
            Registry rmiRegistry = LocateRegistry.getRegistry(chHostname, 1099);
            MarketRemoteFactory myMarket = (MarketRemoteFactory) rmiRegistry.lookup("myMarket");
            System.out.println("connexion a la factory <<myMarket>> effectuee .");

            StockRmiApi stockRmiApi = myMarket.stockApi();
            Stock actionBnp = stockRmiApi.getStock("BNP.PA");
            System.out.println("actionBnp="+actionBnp);
            long td =System.nanoTime();
            List<Stock> listeActions = stockRmiApi.getCurrentStockList();
            long tf =System.nanoTime();
            long tempReponse = (tf - td);
            System.out.println("tempReponse (via rmi) en ns : " + tempReponse);
            System.out.println("tempReponse (via rmi) en ms : " + tempReponse/ 1000000);
            System.out.println("listeActions="+listeActions);

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}
