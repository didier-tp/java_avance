package tp.market.persistence.dao;

import tp.market.persistence.entity.StockEntity;

import java.util.List;

public interface StockDAO {
    public List<StockEntity> findAll();
    public StockEntity findById(String stockId);
    public StockEntity saveNew(StockEntity stock);
    public void update(StockEntity stock);
    public void deleteById(String stockId);
}
