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
                        entityManager.createQuery("SELECT s FROM MyStockEntity s WHERE s.currentQuote > :coursMini" ,
                                MyStockEntity.class).setParameter("coursMini" , minimumQuote).getResultList()
                );
    }

    @Override
    public MyStockEntity findByIdWithExchanges(String stockId) {
        //à peaufiner/améliorer en Tp (via le mot clef fetch page 134 ou via entityGraph ....)
        return (MyStockEntity) MyJpaUtil.execInTransaction(entityManager ->
                entityManager.createQuery("SELECT s FROM MyStockEntity s WHERE s.symbol = :stockId" ,
                        MyStockEntity.class).setParameter("stockId" , stockId).getSingleResult()
        );
    }

    //plus méthodes complémentaires des futurs Tps ...
}
