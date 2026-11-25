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

    private static MyStockExchangeDAO stockExchangeDAO;  //à tester
    private static MyStockDAO stockDAO; //aider à tester

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

        s1.getExchanges().add(se1a); s1.getExchanges().add(se1b);
        //this.stockDAO.update(s1);

        this.stockExchangeDAO.saveNew(se1a);
        this.stockExchangeDAO.saveNew(se1b);

        //MyStockEntity s1Relu = this.stockDAO.findById("S1111");
        MyStockEntity s1Relu = this.stockDAO.findByIdWithExchanges("S1111");
        log.info("s1Relu="+s1Relu);
        for(MyStockExchangeEntity e : s1Relu.getExchanges()){
            log.info("\t e="+e.toString()); //avec ou sans lazy...Exception
        }


        //créer et sauvegarder une deuxième action (ex: Abc)
        MyStockEntity s2 = new MyStockEntity("Abc","S2222","FR0000120351",642.25);
        s2 =this.stockDAO.saveNew(s2);
        //créer et sauvegarder deux nouvelles instances de  MyStockExchangeEntity rattachées à la deuxième action
        MyStockExchangeEntity se2a = new MyStockExchangeEntity(s2, 645.7, 40, "buyer10", "seller_20");
        MyStockExchangeEntity se2b = new MyStockExchangeEntity(s2, 646.7, 12, "buyer20", "seller_60");
        this.stockExchangeDAO.saveNew(se2a);
        this.stockExchangeDAO.saveNew(se2b);


        //this.stockDAO.deleteById(s2.getSymbol()); //ok avec cascade


        //appeler une méthode récupérant tous les échanges associés à la première action
        //List<MyStockExchangeEntity> exchangesS1= this.stockExchangeDAO.findExchangesByStockId(s1.getSymbol());
        List<MyStockExchangeEntity> exchangesS1= this.stockExchangeDAO.findByCriteriaStockId(s1.getSymbol());
        //afficher le résultat via log.info
        log.info("exchangesS1="+exchangesS1);
        //verifier la taille (==2) via un assertTrue(...) approprié
        assertTrue(exchangesS1.size()==2);


    }


}
