package tp.market.service;

import lombok.extern.slf4j.Slf4j;
import tp.market.model.MyStockMarket;
import tp.market.model.Stock;
import tp.market.model.StockOrderBook;
import tp.market.model.TradingOrder;

import java.util.ConcurrentModificationException;

@Slf4j
public class OrderBookServiceImpl implements OrderBookService {

    @Override
    public void addOrder(TradingOrder order){
        var stockMap = MyStockMarket.getInstance().getStockMap();
        var stockBookMap = MyStockMarket.getInstance().getStockBooksMap();
        StockOrderBook stockOrderBook = stockBookMap.get(order.getStockId());
        if(stockOrderBook==null){
            synchronized (stockBookMap) {
                stockOrderBook = new StockOrderBook(order.getStockId(), stockMap.get(order.getStockId()).getCurrentQuote());
                stockBookMap.put(order.getStockId(), stockOrderBook);
            }
        }
        stockOrderBook.addNewOrder(order);
    }

    @Override
    public synchronized void processPossibleOrders(){
        var stockBookMap = MyStockMarket.getInstance().getStockBooksMap();
        try {
            for(StockOrderBook stockOrderBook : stockBookMap.values()){
                stockOrderBook.processPossibleStockOrders();
            }
        } catch (ConcurrentModificationException e) {
            log.warn("ConcurrentModificationException while iterating on stockBookMap.values() , continuing ... ");
        }
    }

    @Override
    public synchronized void updateSomeQuotes(){
        var stockBookMap = MyStockMarket.getInstance().getStockBooksMap();
        var stockMap = MyStockMarket.getInstance().getStockMap();
        for(StockOrderBook stockOrderBook : stockBookMap.values()){
            if(stockOrderBook.reAjustCurrentQuote()){
                Stock stock =  stockMap.get(stockOrderBook.getStockId());
                double oldQuote = 0;
                synchronized(stock) {
                    oldQuote = stock.getCurrentQuote();
                    stock.setCurrentQuote(stockOrderBook.getCurrentQuote());
                }
                log.info("new quote for "+ stockOrderBook.getStockId()+ " is " + stockOrderBook.getCurrentQuote() + " old quote was " + oldQuote);
            }
        }
    }


}
