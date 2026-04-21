package tp.market.persistence.my_dao;

import tp.market.persistence.entity.StockEntity;
import tp.market.persistence.generic.GenericDaoJpa;
import tp.market.persistence.jpa.MyJpaUtil;
import tp.market.persistence.my_entity.MyStockEntity;

import java.util.List;

public class MyStockDaoJpa extends GenericDaoJpa<MyStockEntity,String> implements MyStockDAO {

    public MyStockDaoJpa(){
        super(MyStockEntity.class);
    }

    //plus méthodes complémentaires des futurs Tps ...
}
