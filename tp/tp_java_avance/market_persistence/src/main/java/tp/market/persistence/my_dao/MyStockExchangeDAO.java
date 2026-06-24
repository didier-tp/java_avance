package tp.market.persistence.my_dao;

import tp.market.persistence.generic.GenericDAO;
import tp.market.persistence.my_entity.MyStockExchangeEntity;

import java.util.List;

public interface MyStockExchangeDAO extends GenericDAO<MyStockExchangeEntity,Long> {
    //plus méthodes complémentaires au sein des futurs TPs ...
    List<MyStockExchangeEntity> findExchangesByStockId(String stockId);
}
