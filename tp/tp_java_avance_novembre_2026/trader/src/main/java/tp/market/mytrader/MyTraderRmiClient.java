package tp.market.mytrader;

import tp.market.rmi_api.MarketRemoteFactory;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
            //...

            //...

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
