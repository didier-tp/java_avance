package tp.market.core.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tp.market.adapter.PersistenceRegistration;
import tp.market.core.model.Stock;
import tp.market.spi.repository.DataRepositories;


import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class TestStockService {

    private static StockService stockService;

    @BeforeAll
    public static void init(){
        PersistenceRegistration.init();
        DataRepositories.INSTANCE.getPersistenceSetting().activateAutoDDL(true);
        stockService=new StockServiceImpl();
    }

    @Test
    public void testUpdateStock(){
        String stockId="Sabc.PA";
        stockService.addStock(new Stock("Sabc" ,  stockId, " FR0000120404" , 43.06 ));
        Stock stock = stockService.findStockById(stockId);
        double newQuote=23.7;
        stock.setCurrentQuote(newQuote);
        stockService.updateStock(stock);
        Stock reloadedStock = stockService.findStockById(stockId);
        log.info("reloadedStock="+reloadedStock);
        assertEquals(newQuote,reloadedStock.getCurrentQuote(),0.0001);
    }

    @Test
    public void testRejectedUpdateStock(){
        String stockId="Sabc.PA";
        stockService.addStock(new Stock("Sabc" ,  stockId, " FR00000000" , 43.06 ));
        Stock stock = stockService.findStockById(stockId);
        double newQuote=13.7;  //% variation >50 et update normalement refusé
        stock.setCurrentQuote(newQuote);
        try {
            stockService.updateStock(stock);
        } catch (Exception e) {
            System.err.println("echec attendu/normal ici="+e.getMessage());
        }
        Stock reloadedStock = stockService.findStockById(stockId);
        log.info("reloadedStock="+reloadedStock);
        assertEquals(43.06,reloadedStock.getCurrentQuote(),0.0001); //pas de changement en base si rejet/refus attendu
    }




}
