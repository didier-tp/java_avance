package tp.market.app;

import tp.market.adapter.PersistenceRegistration;
import tp.market.core.model.Stock;
import tp.market.dataset.DefaultDataSet;
import tp.market.exec.BasicMarketExecution;
import tp.market.task.OrderCollectorTaskV1;
import tp.market.task.OrderExecutorTaskV1;
import tp.market.task.RefreshExchangesTaskV1;

import java.util.ArrayList;
import java.util.List;

public class BasicMarketAppV1 {
    public static void main(String[] args) {
        //PersistenceRegistration.init();
        System.out.println("basic market app (v1: multithread, sans persistance,  ramdom_orders sans requêtes )");
        BasicMarketExecution.INSTANCE.setInitialStokDataSetSupplier(DefaultDataSet::initStockDataSet);
        int n=5;
        for(int i=1;i<=5;i++){
            BasicMarketExecution.INSTANCE.addOrderCollectorTask(new OrderCollectorTaskV1(String.valueOf(i)));
        }
        BasicMarketExecution.INSTANCE.setOrderExecutorTask(new OrderExecutorTaskV1());
        BasicMarketExecution.INSTANCE.setRefreshExchangesTask(new RefreshExchangesTaskV1());
        BasicMarketExecution.INSTANCE.executeRegisteredTasks(BasicMarketExecution.ONE_MINUTE_MS);

    }


}
