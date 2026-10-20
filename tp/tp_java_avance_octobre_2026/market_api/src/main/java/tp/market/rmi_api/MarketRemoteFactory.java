package tp.market.rmi_api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MarketRemoteFactory extends Remote {
    public OrderRmiApi orderApi() throws RemoteException;
    public StockRmiApi stockApi() throws RemoteException;
}
