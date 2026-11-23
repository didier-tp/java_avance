package tp.market.persistence.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tp.market.persistence.dao.StockDAO;
import tp.market.persistence.dao.StockDaoJpa;
import tp.market.persistence.jpa.MyJpaUtil;
import tp.market.persistence.entity.StockEntity;
import tp.market.persistence.util.DatabaseUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class TestStockDAO {

    private static StockDAO stockDAO;

    @BeforeAll
    public static void initStockDao(){
        MyJpaUtil.setHbm2ddlAuto(true);
        stockDAO=new StockDaoJpa();
        DatabaseUtil.reInitDataSet(stockDAO); //reinit database content with cac40 stocks
    }

    @Test
    public void testFindAllStock(){
        List<StockEntity> stockEntityList = stockDAO.findAll();
        assertTrue(stockEntityList.size()>=40);
        log.info("stockEntityList="+stockEntityList);
    }

    @Test
    public void testCrudStock(){
        String pk="XYZ.PA";
        stockDAO.saveNew(new StockEntity("Xyz" , pk , "FR1234567890" , 2.02 ));
        //StockEntity sRelu = stockDAO.findById(pk);
        StockEntity sRelu = stockDAO.findByIsin("FR1234567890"); //efficient search with index on unique isin (not pk)
        log.info("sRelu="+sRelu);
        assertEquals("Xyz",sRelu.getName());
        assertEquals(2.02,sRelu.getCurrentQuote(),0.00001);
        sRelu.setName("Xyz2"); sRelu.setCurrentQuote(4.04);
        stockDAO.update(sRelu);
        StockEntity sRelu2 = stockDAO.findById(pk);
        log.info("sRelu2="+sRelu2);
        assertEquals("Xyz2",sRelu2.getName());
        assertEquals(4.04,sRelu2.getCurrentQuote(),0.00001);
        stockDAO.deleteById(pk);
        StockEntity sRelu3 = stockDAO.findById(pk);
        assertNull(sRelu3);
    }


}
