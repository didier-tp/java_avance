package tp.market.persistence.my_dao;

import jakarta.persistence.EntityGraph;
import tp.market.persistence.entity.StockEntity;
import tp.market.persistence.generic.GenericDaoJpa;
import tp.market.persistence.jpa.MyJpaUtil;
import tp.market.persistence.my_entity.MyStockEntity;

public class MyStockDaoJpa extends GenericDaoJpa<MyStockEntity,String> implements MyStockDAO {

    public MyStockDaoJpa(){
        super(MyStockEntity.class);
    }

    @Override
    public MyStockEntity findByIdWithExchanges(String stockId) {
        return (MyStockEntity) MyJpaUtil.execInTransaction( entityManager -> {
                      EntityGraph entityGraph = entityManager.getEntityGraph("entity-graph-stock-exchanges");
                     return entityManager.createQuery(
                            "SELECT s FROM MyStockEntity s WHERE s.symbol = ?1", MyStockEntity.class)
                    .setParameter(1, stockId)
                    .setHint("javax.persistence.fetchgraph", entityGraph)
                    .getSingleResult();
                }
        );
    }

    //plus méthodes complémentaires des futurs Tps ...
}
