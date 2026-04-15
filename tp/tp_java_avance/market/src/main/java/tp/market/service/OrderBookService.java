package tp.market.service;

import tp.market.model.StockOrderBook;
import tp.market.model.TradingOrder;

public interface OrderBookService {
    public void addOrder(TradingOrder order);
    public void processPossibleOrders();
    public void updateSomeQuotes();
}
