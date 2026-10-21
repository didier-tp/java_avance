package tp.market.my_server.my_rmi;

import tp.market.rmi_api.MarketRemoteFactory;
import tp.market.rmi_api.OrderRmiApi;
import tp.market.rmi_api.StockRmiApi;
import tp.market.server.rmi.StockRmiApiImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MyMarketRmiImpl extends UnicastRemoteObject implements MarketRemoteFactory {

    public MyMarketRmiImpl() throws RemoteException {
        super();
    }

    @Override
    public OrderRmiApi orderApi() throws RemoteException {
        return null;
    }

    @Override
    public StockRmiApi stockApi() throws RemoteException {
        return new StockRmiApiImpl();
    }

}
