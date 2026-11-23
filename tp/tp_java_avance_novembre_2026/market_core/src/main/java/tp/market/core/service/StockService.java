package tp.market.core.service;


import tp.market.core.model.Stock;

public interface StockService {
    public void addStock(Stock stock);
    public void updateStock(Stock stock);
    public Stock findStockById(String stockId);
}
