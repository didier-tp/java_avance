package tp.market.persistence.my_dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import tp.market.persistence.jpa.MyJpaUtil;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class TestMyStockExchangeDAO {

    //private static MyStockExchangeDAO stockExchangeDAO;
    private static MyStockDAO stockDAO;

    //@BeforeAll
    public static void initDao(){
        MyJpaUtil.setHbm2ddlAuto(true);
        stockDAO=new MyStockDaoJpa();
        //stockExchangeDAO=new StockExchangeDaoJpa();
    }

    @Test
    public void testStockExchanges(){
        //a completer en Tp ...
        log.info("testStockExchanges");

        //créer et sauvegarder une première action (ex: Xyz)
        //créer et sauvegarder deux nouvelles instances de  MyStockExchangeEntity rattachées à la première action


        //créer et sauvegarder une deuxième action (ex: Abc)
        //créer et sauvegarder deux nouvelles instances de  MyStockExchangeEntity rattachées à la deuxième action


        //appeler une méthode récupérant tous les échanges associés à la première action
        //afficher le résultat via log.info
        //verifier la taille (==2) via un assertTrue(...) approprié
    }


}
