package tp.market.persistence.dao;

import tp.market.persistence.entity.StockEntity;
import tp.market.persistence.generic.GenericDaoJpa;
import tp.market.persistence.jpa.MyJpaUtil;

import java.util.List;

public class StockDaoJpa extends GenericDaoJpa<StockEntity,String> implements StockDAO{

    public StockDaoJpa(){
        super(StockEntity.class);
    }

    @Override
    //query with FETCH keywork to avoid LazyInitializationException
    public StockEntity findByIdWithExchanges(String stockId) {
        StockEntity stockEntity = null;
        stockEntity = (StockEntity) MyJpaUtil.execInTransaction(entityManager ->
                entityManager.createQuery("SELECT s FROM StockEntity s LEFT JOIN FETCH s.stockExchanges se WHERE s.symbol = :stockId",
                        StockEntity.class).setParameter("stockId",stockId).getSingleResultOrNull()
        );
        return stockEntity;
    }

    @Override
    public StockEntity findByIsin(String isin) {
        StockEntity stockEntity = null;
        stockEntity = (StockEntity) MyJpaUtil.execInTransaction(entityManager ->
                entityManager.createQuery("SELECT s FROM StockEntity s  WHERE s.isin = :isin",
                        StockEntity.class).setParameter("isin",isin).getSingleResultOrNull()
        );
        return stockEntity;
    }

    @Override //to activate cache on this query
    public List<StockEntity> findAll() {
        List<StockEntity> entityList = null;
        entityList = (List<StockEntity>) MyJpaUtil.execInTransaction(entityManager ->
                entityManager.createQuery("SELECT e FROM StockEntity e",StockEntity.class)
                        .setHint("org.hibernate.cacheable", true)
                        .getResultList()
        );
        return entityList;
    }
}
