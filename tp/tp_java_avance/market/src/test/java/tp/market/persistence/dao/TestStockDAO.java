package tp.market.persistence.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tp.market.model.Stock;
import tp.market.persistence.entity.StockEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class TestStockDAO {

    private static StockDAO stockDAO;

    @BeforeAll
    public static void initStockDao(){
        stockDAO=new StockDaoJpa();
    }

    @Test
    public void testSaveNewAndFindAll(){
        stockDAO.saveNew(new StockEntity("ACCOR" , "AC.PA" , " FR0000120404" , 45.06 ));
        stockDAO.saveNew(new StockEntity("AIR LIQUIDE" , "AI.PA" , " FR0000120073" , 183.18 ));
        List<StockEntity> stockEntityList = stockDAO.findAll();
        assertTrue(stockEntityList.size()>=2);
        log.info("stockEntityList="+stockEntityList);
    }
}
