package tp.market.persistence.dao;

import tp.market.persistence.entity.StockExchangeEntity;
import tp.market.persistence.entity.aggregation.ExchangeEssential;
import tp.market.persistence.generic.GenericDAO;

import java.util.List;


public interface StockExchangeDAO extends GenericDAO<StockExchangeEntity,Long> {
    List<StockExchangeEntity> badFindByStockIdWithSqlInjectionProblem(String stockId);  //without .setParameter()
    List<StockExchangeEntity> findByStockId(String stockId); //JPQL
    List<StockExchangeEntity> findByCriteriaStockId(String stockId);//criteria api
    List<ExchangeEssential> findExchangeEssentialListByStockId(String stockId);//with projection/aggregation
}
