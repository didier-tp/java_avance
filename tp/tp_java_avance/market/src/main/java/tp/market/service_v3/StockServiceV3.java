package tp.market.service_v3;

import tp.market.model.Stock;

public interface StockServiceV3 {
    public void addStock(Stock stock);
    public void updateStock(Stock stock);
    public Stock findStockById(String stockId);
}
