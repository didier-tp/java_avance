package tp.market.v1;

import tp.market.model.Stock;
import tp.market.service.StockService;
import tp.market.service.StockServiceImpl;
import tp.market.v1.task.OrderCollectorTask;
import tp.market.v1.task.OrderExecutorTask;
import tp.market.v1.task.RefreshExchangesTask;

public class BasicMarketApp {
    public static void main(String[] args) {
        System.out.println("basic market app (v1: multithread )");
        initDataSet();

        //principale structure commune : MyStockMarket (singleton)
        //n(=5) threads de type "collect_order"
        int n=5;
        for(int i=1;i<=5;i++){
            Thread orderCollectorThread = new Thread(new OrderCollectorTask(String.valueOf(i)));
            orderCollectorThread.start();
        }
        //1 thread de type "execute_orders" :
        Thread orderExecutorThread = new Thread(new OrderExecutorTask());
        orderExecutorThread.start();

        //1 thread de type "refresh_exchanges":
        Thread refreshExchangesThread = new Thread(new RefreshExchangesTask());
        refreshExchangesThread.start();
    }

    public static void initDataSet(){
        StockService stockService = new StockServiceImpl();
        stockService.addStock(new Stock("ACCOR" , "AC.PA" , " FR0000120404" , 45.06 ));
        stockService.addStock(new Stock("AIR LIQUIDE" , "AI.PA" , " FR0000120073" , 183.18));
        stockService.addStock(new Stock("AIRBUS GROUP" , "AIR.PA" , "NL0000235190" , 174.95 ));
        stockService.addStock(new Stock("ARCELORMITAL" , "MT.PA" , "LU1598757687" , 51.12 ));
        stockService.addStock(new Stock("AXA" , "CS.PA" , "FR0000120628" , 41.46));
        stockService.addStock(new Stock("BNP PARIBAS" , "BNP.PA" , "FR0000131104 " , 89.96 ));
        stockService.addStock(new Stock("BOUYGUES" , "EN.PA" , "FR0000120503" , 52.60 ));
        stockService.addStock(new Stock("BUREAU VERITAS" , "BVI.PA" , "FR0006174348" , 27.45 ));
        stockService.addStock(new Stock("CAPGEMINI" , "CAP.PA" , "FR0000125338" , 103.85 ));
        stockService.addStock(new Stock("CARREFOUR" , "CA.PA" , "FR0000120172" , 16.33 ));
        stockService.addStock(new Stock("CREDIT AGRICOLE" , "ACA.PA" , "FR0000045072" , 17.34 ));
        stockService.addStock(new Stock("DANONE" , "BN.PA" , "FR0000120644" , 69.28 ));
        stockService.addStock(new Stock("DASSAULT SYSTEMES" , "DSY.PA" , "FR0014003TT8" , 17.66 ));
        stockService.addStock(new Stock("EIFFAGE" , "FGR.PA" , "FR0000130452" , 141.95 ));
        stockService.addStock(new Stock("ENGIE" , "ENGI.PA" , "FR0010208488" , 29.05 ));
        stockService.addStock(new Stock("ESSILOR" , "EL.PA" , "FR0000121667" , 201.20 ));
        stockService.addStock(new Stock("EUROFINS SCIENTIFIC" , "ERF.PA" , "FR0014000MR3" , 65.30));
        stockService.addStock(new Stock("EURONEXT" , "ENX.PA" , "NL0006294274" , 143 ));
        stockService.addStock(new Stock("HERMES" , "RMS.PA" , "FR0000052292" , 1759 ));
        stockService.addStock(new Stock("KERING" , "KER.PA" , "FR0000121485" , 275 ));
        stockService.addStock(new Stock("LEGRAND" , "LR.PA" , "FR0010307819" , 147 ));
        stockService.addStock(new Stock("L'OREAL" , "OR.PA" , "FR0000120321" , 366.25 , 533728223L));
        stockService.addStock(new Stock("LVMH" , "MC.PA" , "FR0000121014" , 499));
        stockService.addStock(new Stock("MICHELIN" , "ML.PA" , "FR001400AJ45" , 30.5));
        stockService.addStock(new Stock("ORANGE" , "ORA.PA" , "FR0000133308" , 17.98 ));
        stockService.addStock(new Stock("PERNOD RICARD" , "RI.PA" , "FR0000120693" , 65 ));
        stockService.addStock(new Stock("PUBLICIS" , "PUB.PA" , "FR0000130577" , 75 ));
        stockService.addStock(new Stock("RENAULT" , "RNO" , "FR0000131906 " , 31.53 , 295722284L));
        stockService.addStock(new Stock("SAFRAN" , "SAF.PA" , "FR0000073272" , 312.60 ));
        stockService.addStock(new Stock("SAINT-GOBIN" , "SGO.PA" , "FR0000125007" , 76.40 ));
        stockService.addStock(new Stock("SANOFI" , "SAN.PA" , "FR0000120578" , 80.46 ));
        stockService.addStock(new Stock("SCHNEIDER ELECTRIC" , "SU.PA" , "FR0000121972" , 255.30 ));
        stockService.addStock(new Stock("SOCIETE GENERALE" , "GLE.PA" , "FR0000130809" , 80 ));
        stockService.addStock(new Stock("STELLANTIS" , "STLAP.PA" , "NL00150001Q9" , 6.72 ));
        stockService.addStock(new Stock("STMICROELECTRONICS" , "STMPA.PA" , "NL0000226223" , 31.94 ));
        stockService.addStock(new Stock("THALES" , "HO.PA" , "FR0000121329" , 269.70 ));
        stockService.addStock(new Stock("TOTALENERGIES" , "TTE.PA" , "FR0000120271" , 76.31 ));
        stockService.addStock(new Stock("UNIBAIL-DODAMCO-WESTFIELD" , "URW.PA" , "FR0013326246" , 102.55 ));
        stockService.addStock(new Stock("VEOLIA Environnement" , "VIE.PA" , "FR0000124141" , 34.62 ));
        stockService.addStock(new Stock("VINCI" , "DG.PA" , "FR0000125486" , 136.45 ));


    }
}
