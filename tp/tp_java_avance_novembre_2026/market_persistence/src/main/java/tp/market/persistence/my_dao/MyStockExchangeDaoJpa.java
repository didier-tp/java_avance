package tp.market.persistence.my_dao;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.criteria.*;
import tp.market.persistence.generic.GenericDaoJpa;
import tp.market.persistence.jpa.MyJpaUtil;
import tp.market.persistence.my_entity.MyStockEntity;
import tp.market.persistence.my_entity.MyStockExchangeEntity;
import tp.market.persistence.my_entity.aggregation.MyExchangeEssential;

import java.util.List;

public class MyStockExchangeDaoJpa extends GenericDaoJpa<MyStockExchangeEntity,Long> implements MyStockExchangeDAO {

    public MyStockExchangeDaoJpa(){
        super(MyStockExchangeEntity.class);
    }

    @Override
    public List<MyStockExchangeEntity> findExchangesByStockId(String stockId) {
        return (List<MyStockExchangeEntity>) MyJpaUtil.execInTransaction(entityManager -> {
                    return entityManager.createQuery(
                                    "SELECT se FROM MyStockExchangeEntity se WHERE se.stock.symbol = :stockIdParam", MyStockExchangeEntity.class)
                            .setParameter("stockIdParam", stockId)
                            .getResultList();
                }
        );
    }

    @Override
    public List<MyStockExchangeEntity> badFindByStockIdWithSqlInjectionProblem(String stockId) {
        return (List<MyStockExchangeEntity>) MyJpaUtil.execInTransaction(entityManager -> {
                    return entityManager.createQuery(
                                    "SELECT se FROM MyStockExchangeEntity se WHERE se.stock.symbol = '" + stockId + "'", MyStockExchangeEntity.class)
                            .getResultList();
                }
        );
    }

    @Override
    public List<MyStockExchangeEntity> findByCriteriaStockId(String stockId) {
        return (List<MyStockExchangeEntity>) MyJpaUtil.execInTransaction(entityManager -> {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<MyStockExchangeEntity> criteriaQuery = cb.createQuery(MyStockExchangeEntity.class);
            Root<MyStockEntity> stockRoot = criteriaQuery.from(MyStockEntity.class);
            Predicate pEqStockSymbol = cb.equal(stockRoot.get("symbol") , stockId);
            Join<MyStockEntity, MyStockExchangeEntity> joinExchangesOfStock = stockRoot.join("exchanges");
            criteriaQuery.select(joinExchangesOfStock);
            criteriaQuery.where(pEqStockSymbol);
            return entityManager.createQuery(criteriaQuery).getResultList();
           }
        );

    }

    @Override
    public List<MyExchangeEssential> findExchangeEssentialListByStockId(String stockId) {
        return (List<MyExchangeEssential>) MyJpaUtil.execInTransaction(entityManager -> {
                    return entityManager.createQuery(
                                    "SELECT new tp.market.persistence.my_entity.aggregation.MyExchangeEssential(se.stock.id,se.price,se.quantity) " +
                                            " FROM MyStockExchangeEntity se WHERE se.stock.symbol = :stockIdParam", MyExchangeEssential.class)
                            .setParameter("stockIdParam", stockId)
                            .getResultList();
                }
        );
    }


    //plus méthodes complémentaires des futurs Tps ...
}
