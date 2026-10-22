package tp.market.persistence.my_dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tp.market.persistence.entity.StockEntity;
import tp.market.persistence.jpa.MyJpaUtil;
import tp.market.persistence.my_entity.MyStockEntity;
import tp.market.persistence.my_entity.MyStockExchangeEntity;
import tp.market.persistence.my_util.MyDatabaseUtil;
import tp.market.persistence.util.DatabaseUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class TestMyStockDAO {

    private static MyStockExchangeDAO stockExchangeDAO;
    private static MyStockDAO stockDAO; //à tester

    @BeforeAll
    public static void initStockDao(){
        MyJpaUtil.setHbm2ddlAuto(true);  //Hbm2ddlAuto=true en (debut) developpement , surtout pas en production
        stockDAO=new MyStockDaoJpa();
        stockExchangeDAO = new MyStockExchangeDaoJpa();
        MyDatabaseUtil.reInitDataSet(stockDAO); //reinit database content with cac40 stocks
    }


    @Test
    public void testFindByIdWithExchanges(){
        //créer et sauvegarder une première action (ex: Xyz)
        MyStockEntity s1 = new MyStockEntity("Xyz","S1111","FR0000120321",342.25);

        //créer et sauvegarder deux nouvelles instances de  MyStockExchangeEntity rattachées à la première action
        MyStockExchangeEntity se1a = new MyStockExchangeEntity(s1, 345.7, 4, "buyer1", "seller_2");
        MyStockExchangeEntity se1b = new MyStockExchangeEntity(s1, 346.7, 10, "buyer2", "seller_6");
        s1.getExchanges().add(se1a); //relier se1a et se1b à s1 du coté @OneToMany pour les besoins de la cascade
        s1.getExchanges().add(se1b);
        s1 =this.stockDAO.saveNew(s1); //save en cascade avec CascadeType.PERSIST sur  @OneToMany
        //this.stockExchangeDAO.saveNew(se1a);
        //this.stockExchangeDAO.saveNew(se1b);

        //créer et sauvegarder une deuxième action (ex: Abc)
        MyStockEntity s2 = new MyStockEntity("Abc","S2222","FR0000120351",642.25);
        s2 =this.stockDAO.saveNew(s2);
        //créer et sauvegarder deux nouvelles instances de  MyStockExchangeEntity rattachées à la deuxième action
        MyStockExchangeEntity se2a = new MyStockExchangeEntity(s2, 645.7, 40, "buyer10", "seller_20");
        MyStockExchangeEntity se2b = new MyStockExchangeEntity(s2, 646.7, 12, "buyer20", "seller_60");
        this.stockExchangeDAO.saveNew(se2a);
        this.stockExchangeDAO.saveNew(se2b);

        MyStockEntity stockEntityReluAvecEchanges = stockDAO.findByIdWithExchanges("S2222");
        assertEquals("Abc",stockEntityReluAvecEchanges.getName());
        /*
        assertTrue(stockEntityReluAvecEchanges.getExchanges().size()==2);
        log.info("echanges pour s2222: " + stockEntityReluAvecEchanges.getExchanges());
         */
    }

    @Test
    public void testFindByQuoteMini(){
        log.info("testFindByQuoteMini");
        //appeler .findByQuoteMini() sur le dao
        List<MyStockEntity> listeActions =  this.stockDAO.findByQuoteMini(300.0);
        log.info("listeActions avec quoteMini=300 : " + listeActions);
        //afficher le résultat via log.info()
        assertTrue(listeActions.size()>=3); //au moins 3 dans le jeux de données initialisé par @BeforeAll et  MyDatabaseUtil.reInitDataSet()
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
