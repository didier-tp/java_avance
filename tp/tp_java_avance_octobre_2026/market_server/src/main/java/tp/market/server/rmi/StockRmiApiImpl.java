package tp.market.server.rmi;

import lombok.extern.slf4j.Slf4j;
import tp.market.core.model.MyStockMarket;
import tp.market.core.model.Stock;
import tp.market.rmi_api.StockRmiApi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StockRmiApiImpl extends UnicastRemoteObject implements StockRmiApi {

    private static final long serialVersionUID = 1L;

    public StockRmiApiImpl() throws RemoteException {
        super(); // pour exportation
    }

    @Override
    public List<Stock> getCurrentStockList() throws RemoteException {
        log.info("returning stockList via rmi");
        return new ArrayList<>(MyStockMarket.getInstance().getStockMap().values());
    }

    @Override
    public Stock getStock(String stockId) throws RemoteException {
        return MyStockMarket.getInstance().getStockMap().get(stockId);
    }
}
