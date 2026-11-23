package tp.market.exec;

import lombok.extern.slf4j.Slf4j;
import tp.market.core.model.Stock;
import tp.market.core.service.StockService;
import tp.market.core.service.StockServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Slf4j
public class BasicMarketExecution {

    public static final long ONE_MINUTE_MS = 1000 * 60;

    public static BasicMarketExecution INSTANCE=new BasicMarketExecution();

    //référence vers fonction retournant la liste initiale des actions/stocks
    //ex: fonction retournant une liste en dur ou bien retournant une liste récupérée en base
    private java.util.function.Supplier<List<Stock>> initialStokDataSetSupplier;

    //tâche qui va récolter des ordres de bourse (achats ou ventes) à ultérieurement exécuter
    private List<Runnable> orderCollectorTasks = new ArrayList<>();

    //tâche qui va executer les échanges (achats/ventes) possibles
    private Runnable orderExecutorTask;

    //tâche qui va réactualiser le cours d'une action et d'autres choses:
    private Runnable refreshExchangesTask;

    public void executeRegisteredTasks(Long maxExecTimeMs){
        log.info("BasicMarketExecution.executeRegisteredTasks with maxExecTimeMs="+maxExecTimeMs);
        //les tâches seront exécutées en s'appuyant sur la principale structure commune : MyStockMarket (singleton)

        //tâche d'initialisation de la liste des actions/stocks disponibles
        //directement effectuée par le thread courant:
        List<Stock> stockList = this.initialStokDataSetSupplier.get();
        log.info("stockList.size="+stockList.size());
        log.info("stockList="+stockList);
        StockService stockService = new StockServiceImpl();
        for(Stock s : stockList) {
            stockService.addStock(s);
        }
        //n threads pour collecter des ordres de bourses (via sockets tcp/ip ou rmi ou jms ou autres)
        for(Runnable orderCollectorTask : orderCollectorTasks){
            Thread orderCollectorThread = new Thread(orderCollectorTask);
            orderCollectorThread.start();
        }
        //1 thread de type "execute_orders" :
        Thread orderExecutorThread = new Thread(orderExecutorTask);
        orderExecutorThread.start();

        //1 thread de type "refresh_exchanges":
        Thread refreshExchangesThread = new Thread(refreshExchangesTask);
        refreshExchangesThread.start();

        if(maxExecTimeMs!=null){
            try {
                Thread.sleep(maxExecTimeMs);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("automatic stop after maxExecTimeMs="+maxExecTimeMs);
            MarketTaskControl.setStop(true);
        }
    }

    public Runnable getOrderExecutorTask() {
        return orderExecutorTask;
    }

    public void setOrderExecutorTask(Runnable orderExecutorTask) {
        this.orderExecutorTask = orderExecutorTask;
    }

    public Runnable getRefreshExchangesTask() {
        return refreshExchangesTask;
    }

    public void setRefreshExchangesTask(Runnable refreshExchangesTask) {
        this.refreshExchangesTask = refreshExchangesTask;
    }

    public List<Runnable> getOrderCollectorTasks() {
        return orderCollectorTasks;
    }

    public void addOrderCollectorTask(Runnable collectorTask){
        this.orderCollectorTasks.add(collectorTask);
    }

    public void setInitialStokDataSetSupplier(Supplier<List<Stock>> initialStokDataSetSupplier) {
        this.initialStokDataSetSupplier = initialStokDataSetSupplier;
    }
}
