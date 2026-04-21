package tp.market.persistence.dao;

import jakarta.persistence.Cache;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tp.market.persistence.entity.StockEntity;
import tp.market.persistence.jpa.MyJpaUtil;
import tp.market.persistence.util.DatabaseUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class TestCache {

    private static StockDAO stockDAO;
    private static EntityManagerFactory emf;

    @BeforeAll
    public static void initStockDao(){
        MyJpaUtil.setHbm2ddlAuto(true);
        emf=MyJpaUtil.entityManagerFactory();
        stockDAO=new StockDaoJpa();
        DatabaseUtil.reInitDataSet(stockDAO); //reinit database content with cac40 stocks
    }

    @Test
    public void testSaveAndFindWithCache(){
        String pk="sxx.PA";
        stockDAO.saveNew(new StockEntity("Sxx" , pk , "FR12367890" , 3.03 ));
        String pk2="syy.PA";
        stockDAO.saveNew(new StockEntity("Syy" , pk2 , "FR1236780" , 4.03 ));
        long t1 = System.currentTimeMillis();
        StockEntity sRelu = stockDAO.findById(pk);
        long t2 = System.currentTimeMillis();
        Cache cache = emf.getCache();//2nd Level cache (shared by all users/threads/sessions/entityManager)
        boolean wasCached = cache.contains(StockEntity.class, pk);
        log.info("findById execution time (with or woithout cache)="+(t2-t1));//ex: 12ms with cache , 35ms without cache
        log.info("sRelu="+sRelu);
        log.info("wasCached="+wasCached);
        assertEquals("Sxx",sRelu.getName());
        assertTrue(wasCached);
    }

    @Test
    public void testFindAllStockWithCache(){
        //NB:  .setHint("org.hibernate.cacheable", true) was placed in StockDaoJpa.findAll()
        List<StockEntity> stockEntityList = null;
        long t1 = System.currentTimeMillis();
        stockEntityList = stockDAO.findAll();
        long t2 = System.currentTimeMillis();
        stockEntityList = stockDAO.findAll(); //(wee see or not sql query in log) if .setHint("org.hibernate.cacheable", true) or not [ with temp <property name="hibernate.show_sql" value="true"/> ]
        long t3 = System.currentTimeMillis();
        assertTrue(stockEntityList.size()>=40);
        log.info("stockEntityList="+stockEntityList);
        log.info("first call execution time (without cache)="+(t2-t1));//ex: 266 ms
        log.info("**second call execution time (with cache)="+(t3-t2));//ex: 6 ms with cache (but not diff without cache)
    }
}

/*
pour activer le cache de second niveau , il faut :
* ajouter les dépendances hibernate-jcache et ehcache (avec bon réglages jaxb-runtime) dans pom.xml
* préparer le fichier src/main/resources/ehcache.xml
* ajouter les propriétés hibernate nécessaires dans META-INF/persistence.xml (ou ailleurs)
* ajouter @Cacheable près de @Entity  : réglage INDISPENSABLE
* effectuer un test comme celui ci
 */