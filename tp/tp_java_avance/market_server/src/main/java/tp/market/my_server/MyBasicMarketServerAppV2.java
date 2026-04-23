package tp.market.my_server;

import tp.market.core.model.Stock;
import tp.market.dataset.DefaultDataSet;
import tp.market.exec.BasicMarketExecution;
import tp.market.my_server.my_task.MyOrderCollectorTaskJmsV2;
import tp.market.my_server.my_task.MyOrderCollectorTaskRmiV2;
import tp.market.my_server.my_task.MyOrderCollectorTaskSocketV2;
import tp.market.core.service.StockService;
import tp.market.core.service.StockServiceImpl;
import tp.market.server.task.OrderCollectorTaskJmsV2;
import tp.market.server.task.OrderCollectorTaskRmiV2;
import tp.market.server.task.OrderCollectorTaskSocketV2;
import tp.market.task.OrderExecutorTaskV1;
import tp.market.task.RefreshExchangesTaskV1;


public class MyBasicMarketServerAppV2 {
    public static void main(String[] args) {
        System.out.println("MyBasicMarketServerAppV2(multithread , as  server , sans persistance et avec ramdom order )");

        BasicMarketExecution.INSTANCE.setInitialStokDataSetSupplier(DefaultDataSet::initStockDataSet);

        //BasicMarketExecution.INSTANCE.addOrderCollectorTask(new OrderCollectorTaskV1(String.valueOf(0)));
        BasicMarketExecution.INSTANCE.addOrderCollectorTask(new MyOrderCollectorTaskSocketV2(String.valueOf(1)));
        BasicMarketExecution.INSTANCE.addOrderCollectorTask(new MyOrderCollectorTaskRmiV2(String.valueOf(2)));
        BasicMarketExecution.INSTANCE.addOrderCollectorTask(new MyOrderCollectorTaskJmsV2(String.valueOf(3)));

        BasicMarketExecution.INSTANCE.setOrderExecutorTask(new OrderExecutorTaskV1());
        BasicMarketExecution.INSTANCE.setRefreshExchangesTask(new RefreshExchangesTaskV1());
        BasicMarketExecution.INSTANCE.executeRegisteredTasks(BasicMarketExecution.ONE_MINUTE_MS);
    }


}
