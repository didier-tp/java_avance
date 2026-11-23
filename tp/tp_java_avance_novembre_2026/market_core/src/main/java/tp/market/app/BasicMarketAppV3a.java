package tp.market.app;

import tp.market.adapter.PersistenceRegistration;
import tp.market.core.model.Stock;
import tp.market.exec.BasicMarketExecution;
import tp.market.spi.repository.DataRepositories;
import tp.market.spi.repository.StockLoader;
import tp.market.task.OrderCollectorTaskV1;
import tp.market.task.OrderExecutorTaskV1;
import tp.market.task.RefreshExchangesTaskV1;

import java.util.List;



public class BasicMarketAppV3a {
    public static void main(String[] args) {
        System.out.println("basic market app (v3a: multithread , avec persistance , ramdom_orders sans requêtes )");
        PersistenceRegistration.init();
        BasicMarketExecution.INSTANCE.setInitialStokDataSetSupplier(BasicMarketAppV3a::loadStockDataSet);
        int n=5;
        for(int i=1;i<=5;i++){
            BasicMarketExecution.INSTANCE.addOrderCollectorTask(new OrderCollectorTaskV1(String.valueOf(i)));
        }
        BasicMarketExecution.INSTANCE.setOrderExecutorTask(new OrderExecutorTaskV1());
        BasicMarketExecution.INSTANCE.setRefreshExchangesTask(new RefreshExchangesTaskV1());
        BasicMarketExecution.INSTANCE.executeRegisteredTasks(BasicMarketExecution.ONE_MINUTE_MS);

    }

    //load Stock DataSet from database
    public static List<Stock> loadStockDataSet(){
        StockLoader stockLoader = DataRepositories.INSTANCE.getStockLoader();
        return stockLoader.loadAll();
    }
}
