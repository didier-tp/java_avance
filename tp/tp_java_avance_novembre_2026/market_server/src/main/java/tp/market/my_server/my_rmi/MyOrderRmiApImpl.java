package tp.market.my_server.my_rmi;

import tp.market.core.model.TradingOrder;
import tp.market.core.service.OrderBookService;
import tp.market.core.service.OrderBookServiceImpl;
import tp.market.rmi_api.OrderRmiApi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MyOrderRmiApImpl extends UnicastRemoteObject implements OrderRmiApi {
    protected MyOrderRmiApImpl() throws RemoteException {
        super();
    }

    @Override
    public void sendTradingOrder(TradingOrder order) throws RemoteException {
        OrderBookService orderBookService = new OrderBookServiceImpl();
        orderBookService.addOrder(order);
        //effet indirect escompté : peut être échnange et réactualisation cours de l'action
    }
}
