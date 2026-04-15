package tp.market.service;

import tp.market.model.MyStockMarket;
import tp.market.model.Stock;
import tp.market.model.StockOrderBook;

public class StockServiceImpl implements StockService {

    @Override
    public void addStock(Stock stock){
        if(stock.getSymbol()==null)
            throw new RuntimeException("invalid stock symbol : should not be null");
        MyStockMarket myStockMarket = MyStockMarket.getInstance();
        synchronized (myStockMarket.getStockMap()) {
            myStockMarket.getStockMap().put(stock.getSymbol(), stock);
        }
        synchronized (myStockMarket.getStockBooksMap()) {
            myStockMarket.getStockBooksMap().put(stock.getSymbol(), new StockOrderBook(stock.getSymbol(), stock.getCurrentQuote()));
        }
    }
}
