package tp.market.server.rmi;

import lombok.extern.slf4j.Slf4j;
import tp.market.core.model.TradingOrder;
import tp.market.rmi_api.OrderRmiApi;
import tp.market.core.service.OrderBookService;
import tp.market.core.service.OrderBookServiceImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@Slf4j
public class OrderRmiApiImpl  extends UnicastRemoteObject implements OrderRmiApi {

    private static final long serialVersionUID = 1L;
    private OrderBookService orderBookService = new OrderBookServiceImpl();

    public OrderRmiApiImpl() throws RemoteException {
        super(); // pour exportation
    }

    @Override
    public void sendTradingOrder(TradingOrder order) throws RemoteException {
        orderBookService.addOrder(order);
        log.info("via RMI, received tradingOrder=" + order);
    }
}
