package tp.market.persistence.dao;

import jakarta.persistence.criteria.*;
import tp.market.persistence.entity.StockEntity;
import tp.market.persistence.entity.StockExchangeEntity;
import tp.market.persistence.entity.aggregation.ExchangeEssential;
import tp.market.persistence.generic.GenericDaoJpa;
import tp.market.persistence.jpa.MyJpaUtil;

import java.util.List;

public class StockExchangeDaoJpa extends GenericDaoJpa<StockExchangeEntity,Long> implements StockExchangeDAO{

    public StockExchangeDaoJpa(){
        super(StockExchangeEntity.class);
    }

    @Override
    public List<StockExchangeEntity> badFindByStockIdWithSqlInjectionProblem(String stockId) {
        List<StockExchangeEntity> exchanges = null;
        exchanges = (List<StockExchangeEntity>) MyJpaUtil.execInTransaction(entityManager ->
                entityManager.createQuery("SELECT se FROM StockExchangeEntity se WHERE se.stock.symbol = '"+stockId+"'",
                                           StockExchangeEntity.class).getResultList()
        );
        return exchanges;
    }

    public List<StockExchangeEntity> findByStockId(String stockId) {
        List<StockExchangeEntity> exchanges = null;
        exchanges = (List<StockExchangeEntity>) MyJpaUtil.execInTransaction(entityManager ->
                entityManager.createQuery("SELECT se FROM StockExchangeEntity se WHERE se.stock.symbol = :stockId",
                        StockExchangeEntity.class).setParameter("stockId",stockId).getResultList()
        );
        return exchanges;
    }

    @Override
    public List<StockExchangeEntity> findByCriteriaStockId(String stockId) {
        List<StockExchangeEntity> exchanges = null;
        exchanges = (List<StockExchangeEntity>) MyJpaUtil.execInTransaction(entityManager -> {

                    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                    CriteriaQuery<StockExchangeEntity> criteriaQuery = cb.createQuery(StockExchangeEntity.class);

                    Root<StockEntity> stockEntityRoot = criteriaQuery.from(StockEntity.class);

                    Predicate pEqStockId = cb.equal(stockEntityRoot.get("symbol") , stockId);

                    Join<StockEntity, StockExchangeEntity> joinExchangesOfStock=  stockEntityRoot.join("stockExchanges");

                    criteriaQuery.select(joinExchangesOfStock);
                    criteriaQuery.where(pEqStockId);

                    return entityManager.createQuery(criteriaQuery).getResultList();
                }

        );
        return exchanges;
    }

    @Override
    public List<ExchangeEssential> findExchangeEssentialListByStockId(String stockId) {
        List<ExchangeEssential> exchanges = null;
        exchanges = (List<ExchangeEssential>) MyJpaUtil.execInTransaction(entityManager ->
                entityManager.createQuery("SELECT new tp.market.persistence.entity.aggregation.ExchangeEssential(se.stock.symbol , se.price, se.quantity) FROM StockExchangeEntity se WHERE se.stock.symbol = :stockId",
                        ExchangeEssential.class).setParameter("stockId",stockId).getResultList()
        );
        return exchanges;
    }
}
