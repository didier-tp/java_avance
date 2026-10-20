package tp.market.persistence.dao;

import tp.market.persistence.entity.StockEntity;
import tp.market.persistence.generic.GenericDAO;

public interface StockDAO extends GenericDAO<StockEntity,String> {
    public StockEntity findByIdWithExchanges(String stockId);
    public StockEntity findByIsin(String isin); //efficient search with index idx_isin
}
