package tp.market.spi.repository;
/*
abstract persitence access
if a loader (or saver) in null --> no existing , not available
GlobalTransaction is a abstract for MyJpaUtil transactions
 */
public class DataRepositories {

    public static DataRepositories INSTANCE=new DataRepositories();
    private PersistenceSetting persistenceSetting;
    private GlobalTransaction globalTransaction;

    private StockLoader stockLoader;
    private StockSaver stockSaver;

    public StockLoader getStockLoader() {
        return stockLoader;
    }

    public void setStockLoader(StockLoader stockLoader) {
        this.stockLoader = stockLoader;
    }

    public GlobalTransaction getGlobalTransaction() {
        return globalTransaction;
    }

    public void setGlobalTransaction(GlobalTransaction globalTransaction) {
        this.globalTransaction = globalTransaction;
    }

    public StockSaver getStockSaver() {
        return stockSaver;
    }

    public void setStockSaver(StockSaver stockSaver) {
        this.stockSaver = stockSaver;
    }

    public PersistenceSetting getPersistenceSetting() {
        return persistenceSetting;
    }

    public void setPersistenceSetting(PersistenceSetting persistenceSetting) {
        this.persistenceSetting = persistenceSetting;
    }
}
