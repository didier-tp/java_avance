package tp.market.persistence.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tp.market.persistence.entity.StockEntity;
import tp.market.persistence.entity.StockExchangeEntity;
import tp.market.persistence.jpa.MyJpaUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class TestStockExchangeDAO {

    private static StockExchangeDAO stockExchangeDAO;
    private static StockDAO stockDAO;

    @BeforeAll
    public static void initDao(){
        MyJpaUtil.setHbm2ddlAuto(true);
        stockDAO=new StockDaoJpa();
        stockExchangeDAO=new StockExchangeDaoJpa();
    }

    @Test
    public void testStockExchanges(){
        String stockId1="XYZ.PA";
        StockEntity s1 = stockDAO.saveNew(new StockEntity("Xyz" , stockId1 , " FR1234567890" , 2.02 ));
        StockExchangeEntity se1a = new StockExchangeEntity(s1,2.01,4,"buyer_a", "seller_b");
        stockExchangeDAO.saveNew(se1a);
        StockExchangeEntity se1b = new StockExchangeEntity(s1,2.005,6,"buyer_c", "seller_d");
        stockExchangeDAO.saveNew(se1b);

        String stockId2="ABC.PA";
        StockEntity s2 = stockDAO.saveNew(new StockEntity("Abc" , stockId2 , " FR9876543210" , 3.03 ));
        StockExchangeEntity se2a = new StockExchangeEntity(s2,3.01,8,"buyer_e", "seller_f");
        stockExchangeDAO.saveNew(se2a);
        StockExchangeEntity se2b = new StockExchangeEntity(s2,3.005,10,"buyer_g", "seller_h");
        stockExchangeDAO.saveNew(se2b);


        //List<StockExchangeEntity> exchanges = stockExchangeDAO.badFindByStockIdWithSqlInjectionProblem(stockId1); //via JPQL query and without .setParameter
        //List<StockExchangeEntity> exchanges = stockExchangeDAO.badFindByStockIdWithSqlInjectionProblem(stockId1+"' OR se.stock.symbol = 'ABC.PA"); //hacked JPQL query and without .setParameter
        //List<StockExchangeEntity> exchanges = stockExchangeDAO.badFindByStockIdWithSqlInjectionProblem(stockId1+"' OR 1=1"); //hacked JPQL query and without .setParameter
        List<StockExchangeEntity> exchanges = stockExchangeDAO.findByStockId(stockId1); //via JPQL query
        //List<StockExchangeEntity> exchanges = stockExchangeDAO.findByStockId(stockId1+"' OR se.stock.symbol = 'ABC.PA"); //via JPQL query and setParameter , error on sql injection
        //List<StockExchangeEntity> exchanges = stockExchangeDAO.findByStockId(stockId1+" OR 1=1"); //via JPQL query and setParameter , error on sql injection
        //List<StockExchangeEntity> exchanges = stockExchangeDAO.findByCriteriaStockId(stockId1); //via Criteria api
        //List<ExchangeEssential> exchanges = stockExchangeDAO.findExchangeEssentialListByStockId(stockId1); //with projection/aggregation

        //List<StockExchangeEntity> exchanges =stockDAO.findById(stockId1).getStockExchanges(); //with LazyInitializationException
        //List<StockExchangeEntity> exchanges =stockDAO.findByIdWithExchanges(stockId1).getStockExchanges(); //with LazyInitializationException


        log.info("exchanges="+exchanges);
        assertTrue(exchanges.size()==2);


        //test de suppression de s2 avec ou sans "cascade delete" :
        stockDAO.deleteById(stockId2);
        //Message d'erreur sans cascade: Cannot delete or update a parent row: a foreign key constraint fails (`market`.`stock_exchange`, CONSTRAINT `FKoxn5cpvnf7rcx1og6pi86d91g` FOREIGN KEY (`stockId`) REFERENCES `stock` (`symbol`))] [delete from stock where symbol=?]]
        //pas d'erreur si cascade = { CascadeType.REMOVE} sur @OneToMany
    }


}
