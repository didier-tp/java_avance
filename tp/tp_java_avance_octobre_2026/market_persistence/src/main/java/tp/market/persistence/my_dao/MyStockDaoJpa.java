package tp.market.persistence.my_dao;

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
                        entityManager.createQuery("SELECT s FROM StockEntity s WHERE s.currentQuote > :coursMini" ,
                                StockEntity.class).setParameter("coursMini" , minimumQuote).getResultList()
                );
    }

    //plus méthodes complémentaires des futurs Tps ...
}
