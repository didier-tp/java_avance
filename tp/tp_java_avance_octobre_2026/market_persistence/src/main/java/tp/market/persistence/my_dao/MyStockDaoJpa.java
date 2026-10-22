package tp.market.persistence.my_dao;

import jakarta.persistence.EntityGraph;
import tp.market.persistence.entity.StockEntity;
import tp.market.persistence.generic.GenericDaoJpa;
import tp.market.persistence.jpa.MyJpaUtil;
import tp.market.persistence.my_entity.MyStockEntity;
import tp.market.persistence.my_entity.MyStockExchangeEntity;

import java.util.List;

public class MyStockDaoJpa extends GenericDaoJpa<MyStockEntity,String> implements MyStockDAO {

    public MyStockDaoJpa(){
        super(MyStockEntity.class);
    }

    @Override
    public List<MyStockEntity> findByQuoteMini(double minimumQuote) {
        return (List<MyStockEntity>)
                MyJpaUtil.execInTransaction(entityManager ->
                        entityManager.createQuery("SELECT s FROM MyStockEntity s WHERE s.currentQuote > :coursMini" ,
                                MyStockEntity.class).setParameter("coursMini" , minimumQuote).getResultList()
                );
    }

    @Override
    public MyStockEntity findByIdWithExchanges(String stockId) {
        /*
        //variante (via le mot clef fetch page 134 )
        return (MyStockEntity) MyJpaUtil.execInTransaction(entityManager ->
                entityManager.createQuery("SELECT s FROM MyStockEntity s LEFT JOIN FETCH s.exchanges WHERE s.symbol = :stockId" ,
                        MyStockEntity.class).setParameter("stockId" , stockId).getSingleResult()
        );
        */
        //variante  via entityGraph pages 156,157
        return (MyStockEntity) MyJpaUtil.execInTransaction(entityManager -> {
                       EntityGraph entityGraph = entityManager.getEntityGraph("entity-graph-stock-exchanges");
                       return entityManager.createQuery("SELECT s FROM MyStockEntity s WHERE s.symbol = :stockId" ,
                        MyStockEntity.class)
                        .setHint("javax.persistence.fetchgraph", entityGraph)
                        .setParameter("stockId" , stockId)
                        .getSingleResult();
                      });
    }

    //plus méthodes complémentaires des futurs Tps ...
}
