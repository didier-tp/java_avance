package tp.market.persistence.my_dao;

import tp.market.persistence.generic.GenericDaoJpa;
import tp.market.persistence.jpa.MyJpaUtil;
import tp.market.persistence.my_entity.MyStockExchangeEntity;
import tp.market.persistence.my_entity.aggregation.MyExchangeEssential;

import java.util.List;

public class MyStockExchangeDaoJpa extends GenericDaoJpa<MyStockExchangeEntity,Long> implements MyStockExchangeDAO {

    public MyStockExchangeDaoJpa(){
        super(MyStockExchangeEntity.class);
    }

    @Override
    public List<MyStockExchangeEntity> findExchangesByStockId(String stockId) {
        return (List<MyStockExchangeEntity>)
                MyJpaUtil.execInTransaction(entityManager ->
                   entityManager.createQuery("SELECT e FROM MyStockExchangeEntity e WHERE e.stock.symbol = :stockId",
                        MyStockExchangeEntity.class).setParameter("stockId",stockId).getResultList()
        );

    }


    //plus méthodes complémentaires des futurs Tps ...
}
