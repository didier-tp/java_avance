package tp.market.persistence.my_dao;

import tp.market.persistence.generic.GenericDAO;
import tp.market.persistence.my_entity.MyStockExchangeEntity;
import tp.market.persistence.my_entity.aggregation.MyExchangeEssential;

import java.util.List;

public interface MyStockExchangeDAO extends GenericDAO<MyStockExchangeEntity,Long> {
    //plus méthodes complémentaires au sein des futurs TPs ...
    List<MyStockExchangeEntity> findExchangesByStockId(String stockId);//JPQL
    //List<MyStockExchangeEntity> findByCriteriaStockId(String stockId);//criteria api
    //List<MyExchangeEssential> findExchangeEssentialListByStockId(String stockId);//with projection/aggregation
}
