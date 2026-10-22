package tp.market.persistence.my_dao;

import tp.market.persistence.generic.GenericDAO;
import tp.market.persistence.my_entity.MyStockEntity;

import java.util.List;

public interface MyStockDAO extends GenericDAO<MyStockEntity,String> {
    //plus méthodes complémentaires au sein des futurs TPs ...
    List<MyStockEntity> findByQuoteMini(double minimumQuote);
    public MyStockEntity findByIdWithExchanges(String stockId);
}
