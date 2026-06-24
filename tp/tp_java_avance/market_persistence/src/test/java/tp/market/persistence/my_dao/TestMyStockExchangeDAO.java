package tp.market.persistence.my_dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import tp.market.persistence.dao.StockExchangeDaoJpa;
import tp.market.persistence.entity.StockEntity;
import tp.market.persistence.entity.StockExchangeEntity;
import tp.market.persistence.jpa.MyJpaUtil;
import tp.market.persistence.my_entity.MyStockEntity;
import tp.market.persistence.my_entity.MyStockExchangeEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class TestMyStockExchangeDAO {

    private static MyStockExchangeDAO stockExchangeDAO;
    private static MyStockDAO stockDAO;

    @BeforeAll
    public static void initDao(){
        MyJpaUtil.setHbm2ddlAuto(true);
        stockDAO=new MyStockDaoJpa();
        stockExchangeDAO=new MyStockExchangeDaoJpa();
    }

    @Test
    public void testStockExchanges(){
        //a completer en Tp ...
        log.info("testStockExchanges");

        //créer et sauvegarder une première action (ex: Xyz)
        MyStockEntity s1 = new MyStockEntity("Xyz","S1111","FR0000120321",342.25);
        s1 =this.stockDAO.saveNew(s1);
        //créer et sauvegarder deux nouvelles instances de  MyStockExchangeEntity rattachées à la première action
        MyStockExchangeEntity se1a = new MyStockExchangeEntity(s1, 345.7, 4, "buyer1", "seller_2");
        MyStockExchangeEntity se1b = new MyStockExchangeEntity(s1, 346.7, 10, "buyer2", "seller_6");
        this.stockExchangeDAO.saveNew(se1a);
        this.stockExchangeDAO.saveNew(se1b);

        //créer et sauvegarder une deuxième action (ex: Abc)
        MyStockEntity s2 = new MyStockEntity("Abc","S2222","FR0000120351",642.25);
        s2 =this.stockDAO.saveNew(s2);
        //créer et sauvegarder deux nouvelles instances de  MyStockExchangeEntity rattachées à la deuxième action
        MyStockExchangeEntity se2a = new MyStockExchangeEntity(s2, 645.7, 40, "buyer10", "seller_20");
        MyStockExchangeEntity se2b = new MyStockExchangeEntity(s2, 646.7, 12, "buyer20", "seller_60");
        this.stockExchangeDAO.saveNew(se2a);
        this.stockExchangeDAO.saveNew(se2b);

        //appeler une méthode récupérant tous les échanges associés à la première action
        List<MyStockExchangeEntity> exchangesS1= this.stockExchangeDAO.findExchangesByStockId(s1.getSymbol());
        //afficher le résultat via log.info
        log.info("exchangesS1"+exchangesS1);
        //verifier la taille (==2) via un assertTrue(...) approprié
        assertTrue(exchangesS1.size()==2);
    }


}
