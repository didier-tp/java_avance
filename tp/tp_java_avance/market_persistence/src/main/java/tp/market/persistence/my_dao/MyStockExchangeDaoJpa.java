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


    //plus méthodes complémentaires des futurs Tps ...
}
