package tp.market.adapter;

import tp.market.core.model.Stock;
import tp.market.persistence.dao.StockDAO;
import tp.market.persistence.entity.StockEntity;
import tp.market.spi.repository.StockLoader;
import tp.market.util.MyConverter;

import java.util.List;

public class StockLoaderAdapter  extends GenericLoaderAdapter<Stock, StockEntity,String>
        implements StockLoader {

    private StockDAO stockDAO;

    public StockLoaderAdapter(StockDAO stockDAO) {
        super(Stock.class,StockEntity.class,stockDAO);
        this.stockDAO=stockDAO;
    }

    @Override
    public Stock findByIsin(String isin) {
        StockEntity stockEntity= stockDAO.findByIsin(isin);
        return MyConverter.map(stockEntity,Stock.class);
    }
}
