package tp.market.server.task;

import tp.market.server.rmi.MarketRmiImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class OrderCollectorTaskRmiV2 implements Runnable{

    private String traderId;

    public OrderCollectorTaskRmiV2(String traderId) {
        this.traderId = traderId;
    }

    public OrderCollectorTaskRmiV2(){
        this("_trader_"+Thread.currentThread().getName());
    }

    @Override
    public void run() {
        try {
            Registry rmiRegistry = LocateRegistry.createRegistry(1099);

            System.out.println("demarrage de la partie OrderCollectorTaskRmiV2 de <<BasicMarketServerAppV2>> ...");
            System.out.println("... creation et exportation de la factory <<MarketRmiImpl>> ");
            MarketRmiImpl marketRmi = new MarketRmiImpl();
            String chNom="marketRmi";
            System.out.println("... enregistrement sous le nom "+chNom);
            //Naming.rebind(chNom,marketRmi);
            rmiRegistry.rebind(chNom,marketRmi);
            System.out.println("... sous serveur RMI initialise en attente des requetes");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }
}

