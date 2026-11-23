package tp.market.server;

import tp.market.adapter.PersistenceRegistration;
import tp.market.core.model.Stock;
import tp.market.exec.BasicMarketExecution;
import tp.market.server.task.OrderCollectorTaskJmsV2;
import tp.market.server.task.OrderCollectorTaskRmiV2;
import tp.market.server.task.OrderCollectorTaskSocketV2;
import tp.market.spi.repository.DataRepositories;
import tp.market.spi.repository.StockLoader;
import tp.market.task.OrderCollectorTaskV1;
import tp.market.task.OrderExecutorTaskV1;
import tp.market.task.RefreshExchangesTaskV1;

import java.util.List;


public class BasicMarketServerAppV3b {
    public static void main(String[] args) {
        System.out.println("basic market server app (v3b: multithread , avec persistance ,avec requêtes socket ou rmi ou jms)");
        PersistenceRegistration.init();
        BasicMarketExecution.INSTANCE.setInitialStokDataSetSupplier(BasicMarketServerAppV3b::loadStockDataSet);
        //BasicMarketExecution.INSTANCE.addOrderCollectorTask(new OrderCollectorTaskV1(String.valueOf(0)));
        BasicMarketExecution.INSTANCE.addOrderCollectorTask(new OrderCollectorTaskSocketV2(String.valueOf(1)));
        BasicMarketExecution.INSTANCE.addOrderCollectorTask(new OrderCollectorTaskRmiV2(String.valueOf(2)));
        BasicMarketExecution.INSTANCE.addOrderCollectorTask(new OrderCollectorTaskJmsV2(String.valueOf(3)));
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
