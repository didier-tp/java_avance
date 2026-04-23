package tp.market.server;


import tp.market.dataset.DefaultDataSet;
import tp.market.exec.BasicMarketExecution;
import tp.market.task.OrderExecutorTaskV1;
import tp.market.task.RefreshExchangesTaskV1;
import tp.market.server.task.OrderCollectorTaskJmsV2;
import tp.market.server.task.OrderCollectorTaskRmiV2;
import tp.market.server.task.OrderCollectorTaskSocketV2;

public class BasicMarketServerAppV2 {
    public static void main(String[] args) {
        System.out.println("basic market app (v2: multithread , as  server , sans persistance et avec ramdom order )");

        BasicMarketExecution.INSTANCE.setInitialStokDataSetSupplier(DefaultDataSet::initStockDataSet);

        //BasicMarketExecution.INSTANCE.addOrderCollectorTask(new OrderCollectorTaskV1(String.valueOf(0)));
        BasicMarketExecution.INSTANCE.addOrderCollectorTask(new OrderCollectorTaskSocketV2(String.valueOf(1)));
        BasicMarketExecution.INSTANCE.addOrderCollectorTask(new OrderCollectorTaskRmiV2(String.valueOf(2)));
        BasicMarketExecution.INSTANCE.addOrderCollectorTask(new OrderCollectorTaskJmsV2(String.valueOf(3)));

        BasicMarketExecution.INSTANCE.setOrderExecutorTask(new OrderExecutorTaskV1());
        BasicMarketExecution.INSTANCE.setRefreshExchangesTask(new RefreshExchangesTaskV1());
        BasicMarketExecution.INSTANCE.executeRegisteredTasks(BasicMarketExecution.ONE_MINUTE_MS);

    }

}
