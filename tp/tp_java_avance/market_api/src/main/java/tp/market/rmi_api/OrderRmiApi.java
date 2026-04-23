package tp.market.rmi_api;

import tp.market.core.model.TradingOrder;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OrderRmiApi extends Remote {
    public void sendTradingOrder(TradingOrder order) throws RemoteException;
    //...
}
