package tp.market.persistence.my_dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tp.market.persistence.entity.StockEntity;
import tp.market.persistence.jpa.MyJpaUtil;
import tp.market.persistence.my_entity.MyStockEntity;
import tp.market.persistence.my_util.MyDatabaseUtil;
import tp.market.persistence.util.DatabaseUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class TestMyStockDAO {

    private static MyStockDAO stockDAO;

    @BeforeAll
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
        List<MyStockEntity> listeActions =  this.stockDAO.findAll();
        log.info("listeActions = " + listeActions);
        //afficher le résultat via log.info()
        assertTrue(listeActions.size()>=40);
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
        String pk="XYZ.PA";
        stockDAO.saveNew(new MyStockEntity("Xyz" , pk , "FR1234567890" , 2.02 ));
        MyStockEntity sRelu = stockDAO.findById(pk);
        //MyStockEntity sRelu = stockDAO.findByIsin("FR1234567890"); //efficient search with index on unique isin (not pk)
        log.info("sRelu="+sRelu);
        assertEquals("Xyz",sRelu.getName());
        assertEquals(2.02,sRelu.getCurrentQuote(),0.00001);
        sRelu.setName("Xyz2"); sRelu.setCurrentQuote(4.04);
        stockDAO.update(sRelu);
        MyStockEntity sRelu2 = stockDAO.findById(pk);
        log.info("sRelu2="+sRelu2);
        assertEquals("Xyz2",sRelu2.getName());
        assertEquals(4.04,sRelu2.getCurrentQuote(),0.00001);
        stockDAO.deleteById(pk);
        MyStockEntity sRelu3 = stockDAO.findById(pk);
        assertNull(sRelu3);
    }


}
