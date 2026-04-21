package tp.market.persistence.my_dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tp.market.persistence.entity.StockEntity;
import tp.market.persistence.jpa.MyJpaUtil;
import tp.market.persistence.my_util.MyDatabaseUtil;
import tp.market.persistence.util.DatabaseUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class TestMyStockDAO {

    private static MyStockDAO stockDAO;

    //@BeforeAll
    public static void initStockDao(){
        MyJpaUtil.setHbm2ddlAuto(true);
        stockDAO=new MyStockDaoJpa();
        MyDatabaseUtil.reInitDataSet(stockDAO); //reinit database content with cac40 stocks
    }

    @Test
    public void testFindAllStock(){
        //à coder en TP
        log.info("testFindAllStock");
        //appeler .findAll() sur le dao
        //afficher le résultat via log.info()
        //vérifier via assertTrue(...) que la taille de la liste est au moins égale à 40 (taille de ce qui est initialisé par  MyDatabaseUtil.reInitDataSet(...))
    }

    @Test
    public void testCrudStock(){
        //à coder en TP
        log.info("testCrudStock");
        //ajouter une nouvelle action (stock)
        //relire et vérifier l'ajout
        //modifier certaines valeurs
        //relire et vérifier les mises à jour
        //supprimer l'action
        //vérifier la suppression
    }


}
