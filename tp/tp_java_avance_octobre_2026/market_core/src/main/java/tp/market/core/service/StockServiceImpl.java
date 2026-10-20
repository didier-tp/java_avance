package tp.market.core.service;

import lombok.extern.slf4j.Slf4j;
import tp.market.core.model.MyStockMarket;
import tp.market.core.model.Stock;
import tp.market.core.model.StockOrderBook;
import tp.market.spi.repository.DataRepositories;
import tp.market.spi.repository.GlobalTransaction;
import tp.market.spi.repository.StockLoader;
import tp.market.spi.repository.StockSaver;

@Slf4j
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
        StockSaver stockSaver = DataRepositories.INSTANCE.getStockSaver();
        if(stockSaver!=null) {
            //log.info("saveOrUpdate stock="+stock);
            stockSaver.saveOrUpdate(stock);
        }
    }

    @Override
    public void updateStock(Stock stock) {
        StockSaver stockSaver = DataRepositories.INSTANCE.getStockSaver();
        StockLoader stockLoader = DataRepositories.INSTANCE.getStockLoader();
        GlobalTransaction globalTransaction = DataRepositories.INSTANCE.getGlobalTransaction();
        if(stockSaver==null || stockLoader==null || globalTransaction==null){
            System.err.println("persistence not initialized , cannot update stock in database ..."); return;
        }
        globalTransaction.begin(); //done by @Transactional with Spring or jakartaEE additional framework
        try {
            double oldStockEntityQuote = stockLoader.findById(stock.getSymbol()).getCurrentQuote();
            double newStockEntityQuote = stock.getCurrentQuote();
            stockSaver.updateExisting(stock);
            //règle métier: la valeur de l'action ne peut varier au maximum que de 50%
            //ici vérification volontairement tardive (à postériori) pour vérifier bon comportement transactionnel
            double pctVariation = 100 * Math.abs(newStockEntityQuote - oldStockEntityQuote) / oldStockEntityQuote;
            log.info("oldStockEntityQuote="+oldStockEntityQuote+ " newStockEntityQuote="+newStockEntityQuote+ " pctVariation="+pctVariation);
            if(pctVariation>50) {
                throw new RuntimeException("trop grande variation, update rejected for stockId=" + stock.getSymbol());
            }
            globalTransaction.commit(); //done by @Transactional with Spring or jakartaEE additional framework
        } catch (Exception e) {
            log.error("rollbackGlobalJpaTransaction after e="+e.getMessage());
            globalTransaction.rollback(); //done by @Transactional with Spring or jakartaEE additional framework
            throw new RuntimeException(e);
        }
    }

    @Override
    public Stock findStockById(String stockId) {
        StockLoader stockLoader = DataRepositories.INSTANCE.getStockLoader();
        if(stockLoader!=null) {
            return stockLoader.findById(stockId);
        }else{
            System.err.println("no stockLoader available ...");
            return null;
        }
    }
}
