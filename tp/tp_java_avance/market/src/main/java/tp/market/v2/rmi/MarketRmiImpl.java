package tp.market.v2.rmi;

import tp.market.rmi_api.MarketRemoteFactory;
import tp.market.rmi_api.OrderRmiApi;
import tp.market.rmi_api.StockRmiApi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MarketRmiImpl extends UnicastRemoteObject implements MarketRemoteFactory {

    private static final long serialVersionUID = 1L;


    public MarketRmiImpl() throws RemoteException {
        super(); // pour exportation
    }

    @Override
    public OrderRmiApi orderApi() throws RemoteException {
        return new OrderRmiApiImpl();
    }

    @Override
    public StockRmiApi stockApi() throws RemoteException {
        return new StockRmiApiImpl();
    }
}
