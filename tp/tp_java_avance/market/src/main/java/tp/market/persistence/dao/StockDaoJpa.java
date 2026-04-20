package tp.market.persistence.dao;

import jakarta.persistence.EntityManager;
import tp.market.persistence.emf.MyJpaUtil;
import tp.market.persistence.entity.StockEntity;

import java.util.List;

public class StockDaoJpa implements StockDAO{
    @Override
    public List<StockEntity> findAll() {
        List<StockEntity> stockEntityList = null;
        EntityManager entityManager = MyJpaUtil.entityManager();
        stockEntityList=entityManager.createQuery("SELECT s FROM StockEntity s",StockEntity.class).getResultList();
        entityManager.close();
        return stockEntityList;
    }

    @Override
    public StockEntity findById(String stockId) {
        StockEntity stockEntity = null;
        EntityManager entityManager = MyJpaUtil.entityManager();
        stockEntity=entityManager.find(StockEntity.class,stockId);
        entityManager.close();
        return stockEntity;
    }

    @Override
    public StockEntity saveNew(StockEntity stock) {
        MyJpaUtil.doInTransaction(entityManager -> {
            entityManager.persist(stock);
        });
        return stock;
    }

    @Override
    public void update(StockEntity stock) {
        MyJpaUtil.doInTransaction(entityManager -> {
            entityManager.merge(stock);
        });
    }

    @Override
    public void deleteById(String stockId) {
        MyJpaUtil.doInTransaction(entityManager -> {
            StockEntity stockEntity=entityManager.find(StockEntity.class,stockId);
            entityManager.remove(stockEntity);
        });
    }
}
