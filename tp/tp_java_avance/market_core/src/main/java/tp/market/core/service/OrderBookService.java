package tp.market.core.service;


import tp.market.core.model.TradingOrder;

public interface OrderBookService {
    public void addOrder(TradingOrder order);
    public void processPossibleOrders();
    public void updateSomeQuotes();
}
