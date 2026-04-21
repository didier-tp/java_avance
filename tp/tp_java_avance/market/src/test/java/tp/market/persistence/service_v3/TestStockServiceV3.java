package tp.market.persistence.service_v3;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tp.market.model.Stock;
import tp.market.persistence.jpa.MyJpaUtil;
import tp.market.service_v3.StockServiceImplV3;
import tp.market.service_v3.StockServiceV3;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class TestStockServiceV3 {

    private static StockServiceV3 stockServiceV3;

    @BeforeAll
    public static void initStockDao(){
        MyJpaUtil.setHbm2ddlAuto(true);
        stockServiceV3=new StockServiceImplV3();
    }

    @Test
    public void testUpdateStock(){
        String stockId="Sabc.PA";
        stockServiceV3.addStock(new Stock("Sabc" ,  stockId, " FR0000120404" , 43.06 ));
        Stock stock = stockServiceV3.findStockById(stockId);
        double newQuote=23.7;
        stock.setCurrentQuote(newQuote);
        stockServiceV3.updateStock(stock);
        Stock reloadedStock = stockServiceV3.findStockById(stockId);
        log.info("reloadedStock="+reloadedStock);
        assertEquals(newQuote,reloadedStock.getCurrentQuote(),0.0001);
    }

    @Test
    public void testRejectedUpdateStock(){
        String stockId="Sabc.PA";
        stockServiceV3.addStock(new Stock("Sabc" ,  stockId, " FR0000120404" , 43.06 ));
        Stock stock = stockServiceV3.findStockById(stockId);
        double newQuote=13.7;  //% variation >50 et update normalement refusé
        stock.setCurrentQuote(newQuote);
        try {
            stockServiceV3.updateStock(stock);
        } catch (Exception e) {
            System.err.println("echec attendu/normal ici="+e.getMessage());
        }
        Stock reloadedStock = stockServiceV3.findStockById(stockId);
        log.info("reloadedStock="+reloadedStock);
        assertEquals(43.06,reloadedStock.getCurrentQuote(),0.0001); //pas de changement en base si rejet/refus attendu
    }




}
