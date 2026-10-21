package tp.market.my_server.my_task;

import tp.market.my_server.my_rmi.MyMarketRmiImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MyOrderCollectorTaskRmiV2 implements Runnable{

    private String traderId;

    public MyOrderCollectorTaskRmiV2(String traderId) {
        this.traderId = traderId;
    }

    public MyOrderCollectorTaskRmiV2(){
        this("_trader_"+Thread.currentThread().getName());
    }

    @Override
    public void run() {
        //à coder librement en Tp
        System.out.println("MyOrderCollectorTaskRmiV2.run");
        try {
            Registry rmiRegistry = LocateRegistry.createRegistry(1099);
            System.out.println("demarrage du la partie RMI de <<market_server>> ...");
            System.out.println("... creation et exportation de la factory <<myMarket>> ");
            MyMarketRmiImpl myMarket = new MyMarketRmiImpl();
            String chNom="myMarket";
            System.out.println("... enregistrement sous le nom "+chNom);
            //Naming.rebind(chNom,miniBank);
            rmiRegistry.rebind(chNom,myMarket);
            System.out.println("... serveur initialise en attente des requetes");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

