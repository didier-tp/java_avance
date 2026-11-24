package tp.market.my_server.my_rmi;

import tp.market.rmi_api.MarketRemoteFactory;
import tp.market.rmi_api.OrderRmiApi;
import tp.market.rmi_api.StockRmiApi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MyMarketRmiImpl extends UnicastRemoteObject implements MarketRemoteFactory {
    protected MyMarketRmiImpl() throws RemoteException {
        super();
    }

    @Override
    public OrderRmiApi orderApi() throws RemoteException {
        return null;   //A compléter librement en TP
    }

    @Override
    public StockRmiApi stockApi() throws RemoteException {
        return null;
    }

}
