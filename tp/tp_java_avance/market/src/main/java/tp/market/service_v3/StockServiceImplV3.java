package tp.market.service_v3;

import lombok.extern.slf4j.Slf4j;
import tp.market.model.MyStockMarket;
import tp.market.model.Stock;
import tp.market.model.StockOrderBook;
import tp.market.persistence.dao.StockDAO;
import tp.market.persistence.dao.StockDaoJpa;
import tp.market.persistence.entity.StockEntity;
import tp.market.persistence.jpa.MyJpaUtil;
import tp.market.util.MyConverter;

@Slf4j
public class StockServiceImplV3 implements StockServiceV3 {

   private  StockDAO stockDAO;

   public StockServiceImplV3(){
       stockDAO=new StockDaoJpa();
    }

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
        StockEntity newStockEntity= MyConverter.map(stock,StockEntity.class);
        stockDAO.saveOrUpdate(newStockEntity,newStockEntity.getSymbol());
    }

    @Override
    public void updateStock(Stock stock) {
        MyJpaUtil.beginGlobalJpaTransaction(); //done by @Transactional with Spring or jakartaEE additional framework
        try {
            double oldStockEntityQuote = stockDAO.findById(stock.getSymbol()).getCurrentQuote();
            double newStockEntityQuote = stock.getCurrentQuote();
            StockEntity newStockEntity= MyConverter.map(stock,StockEntity.class);
            stockDAO.update(newStockEntity);
            //règle métier: la valeur de l'action ne peut varier au maximum que de 50%
            //ici vérification volontairement tardive (à postériori) pour vérifier bon comportement transactionnel
            double pctVariation = 100 * Math.abs(newStockEntityQuote - oldStockEntityQuote) / oldStockEntityQuote;
            log.info("oldStockEntityQuote="+oldStockEntityQuote+ " newStockEntityQuote="+newStockEntityQuote+ " pctVariation="+pctVariation);
            if(pctVariation>50) {
                throw new RuntimeException("trop grande variation, update rejected for stockId=" + stock.getSymbol());
            }
            MyJpaUtil.commitGlobalJpaTransaction(); //done by @Transactional with Spring or jakartaEE additional framework
        } catch (Exception e) {
            log.error("rollbackGlobalJpaTransaction after e="+e.getMessage());
            MyJpaUtil.rollbackGlobalJpaTransaction(); //done by @Transactional with Spring or jakartaEE additional framework
            throw new RuntimeException(e);
        }
    }

    @Override
    public Stock findStockById(String stockId) {
        StockEntity stockEntity = stockDAO.findById(stockId);
        return MyConverter.map(stockEntity,Stock.class);
    }
}
