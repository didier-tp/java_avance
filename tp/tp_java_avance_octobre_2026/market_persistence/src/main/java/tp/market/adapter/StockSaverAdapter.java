package tp.market.adapter;

import tp.market.core.model.Stock;
import tp.market.persistence.dao.StockDAO;
import tp.market.persistence.entity.StockEntity;
import tp.market.spi.repository.StockSaver;

public class StockSaverAdapter
        extends GenericSaverAdapter<Stock, StockEntity,String>
        implements StockSaver {


    private StockDAO stockDAO;

    public StockSaverAdapter(StockDAO stockDAO) {
        super(Stock.class,StockEntity.class,stockDAO);
        this.stockDAO=stockDAO;
    }
}
