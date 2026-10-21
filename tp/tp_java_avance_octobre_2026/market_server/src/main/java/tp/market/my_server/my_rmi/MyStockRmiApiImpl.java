package tp.market.my_server.my_rmi;

import tp.market.core.model.MyStockMarket;
import tp.market.core.model.Stock;
import tp.market.rmi_api.StockRmiApi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class MyStockRmiApiImpl extends UnicastRemoteObject implements StockRmiApi {

    public MyStockRmiApiImpl() throws RemoteException {
        super();
    }

    @Override
    public List<Stock> getCurrentStockList() throws RemoteException {
        return new ArrayList<>(MyStockMarket.getInstance().getStockMap().values());
    }

    @Override
    public Stock getStock(String stockId) throws RemoteException {
        return MyStockMarket.getInstance().getStockMap().get(stockId);
    }

}
