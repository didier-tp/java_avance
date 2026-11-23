package tp.market.rmi_api;

import tp.market.core.model.Stock;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface StockRmiApi extends Remote {
    public List<Stock> getCurrentStockList()  throws RemoteException; ;
    public Stock getStock(String stockId)  throws RemoteException; ;
}
