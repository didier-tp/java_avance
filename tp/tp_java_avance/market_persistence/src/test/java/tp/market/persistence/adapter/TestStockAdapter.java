package tp.market.persistence.adapter;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tp.market.adapter.StockLoaderAdapter;
import tp.market.adapter.StockSaverAdapter;
import tp.market.core.model.Stock;
import tp.market.persistence.dao.StockDAO;
import tp.market.persistence.dao.StockDaoJpa;
import tp.market.persistence.jpa.MyJpaUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class TestStockAdapter {

    private static StockLoaderAdapter stockLoaderAdapter;
    private static StockSaverAdapter stockSaverAdapter;
    private static StockDAO stockDAO;

    @BeforeAll
    public static void initStockDao(){
        MyJpaUtil.setHbm2ddlAuto(true);
        stockDAO=new StockDaoJpa();
        stockLoaderAdapter=new StockLoaderAdapter(stockDAO);
        stockSaverAdapter=new StockSaverAdapter(stockDAO);
    }

    @Test
    public void testUpdateStock(){
        String stockId="Sabc.PA";
        stockSaverAdapter.saveNew(new Stock("Sabc" ,  stockId, " FR0000120404" , 43.06 ));
        Stock stock = stockLoaderAdapter.findById(stockId);
        double newQuote=23.7;
        stock.setCurrentQuote(newQuote);
        stockSaverAdapter.updateExisting(stock);
        Stock reloadedStock = stockLoaderAdapter.findById(stockId);
        log.info("reloadedStock="+reloadedStock);
        assertEquals(newQuote,reloadedStock.getCurrentQuote(),0.0001);
    }






}
