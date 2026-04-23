package tp.market.dataset;

import tp.market.core.model.Stock;

import java.util.ArrayList;
import java.util.List;

public class DefaultDataSet {

    public static List<Stock> initStockDataSet(){
        List<Stock> stockList = new ArrayList<>();
        stockList.add(new Stock("ACCOR" , "AC.PA" , " FR0000120404" , 45.06 ));
        stockList.add(new Stock("AIR LIQUIDE" , "AI.PA" , " FR0000120073" , 183.18));
        stockList.add(new Stock("AIRBUS GROUP" , "AIR.PA" , "NL0000235190" , 174.95 ));
        stockList.add(new Stock("ARCELORMITAL" , "MT.PA" , "LU1598757687" , 51.12 ));
        stockList.add(new Stock("AXA" , "CS.PA" , "FR0000120628" , 41.46));
        stockList.add(new Stock("BNP PARIBAS" , "BNP.PA" , "FR0000131104 " , 89.96 ));
        stockList.add(new Stock("BOUYGUES" , "EN.PA" , "FR0000120503" , 52.60 ));
        stockList.add(new Stock("BUREAU VERITAS" , "BVI.PA" , "FR0006174348" , 27.45 ));
        stockList.add(new Stock("CAPGEMINI" , "CAP.PA" , "FR0000125338" , 103.85 ));
        stockList.add(new Stock("CARREFOUR" , "CA.PA" , "FR0000120172" , 16.33 ));
        stockList.add(new Stock("CREDIT AGRICOLE" , "ACA.PA" , "FR0000045072" , 17.34 ));
        stockList.add(new Stock("DANONE" , "BN.PA" , "FR0000120644" , 69.28 ));
        stockList.add(new Stock("DASSAULT SYSTEMES" , "DSY.PA" , "FR0014003TT8" , 17.66 ));
        stockList.add(new Stock("EIFFAGE" , "FGR.PA" , "FR0000130452" , 141.95 ));
        stockList.add(new Stock("ENGIE" , "ENGI.PA" , "FR0010208488" , 29.05 ));
        stockList.add(new Stock("ESSILOR" , "EL.PA" , "FR0000121667" , 201.20 ));
        stockList.add(new Stock("EUROFINS SCIENTIFIC" , "ERF.PA" , "FR0014000MR3" , 65.30));
        stockList.add(new Stock("EURONEXT" , "ENX.PA" , "NL0006294274" , 143 ));
        stockList.add(new Stock("HERMES" , "RMS.PA" , "FR0000052292" , 1759 ));
        stockList.add(new Stock("KERING" , "KER.PA" , "FR0000121485" , 275 ));
        stockList.add(new Stock("LEGRAND" , "LR.PA" , "FR0010307819" , 147 ));
        stockList.add(new Stock("L'OREAL" , "OR.PA" , "FR0000120321" , 366.25 , 533728223L));
        stockList.add(new Stock("LVMH" , "MC.PA" , "FR0000121014" , 499));
        stockList.add(new Stock("MICHELIN" , "ML.PA" , "FR001400AJ45" , 30.5));
        stockList.add(new Stock("ORANGE" , "ORA.PA" , "FR0000133308" , 17.98 ));
        stockList.add(new Stock("PERNOD RICARD" , "RI.PA" , "FR0000120693" , 65 ));
        stockList.add(new Stock("PUBLICIS" , "PUB.PA" , "FR0000130577" , 75 ));
        stockList.add(new Stock("RENAULT" , "RNO" , "FR0000131906 " , 31.53 , 295722284L));
        stockList.add(new Stock("SAFRAN" , "SAF.PA" , "FR0000073272" , 312.60 ));
        stockList.add(new Stock("SAINT-GOBIN" , "SGO.PA" , "FR0000125007" , 76.40 ));
        stockList.add(new Stock("SANOFI" , "SAN.PA" , "FR0000120578" , 80.46 ));
        stockList.add(new Stock("SCHNEIDER ELECTRIC" , "SU.PA" , "FR0000121972" , 255.30 ));
        stockList.add(new Stock("SOCIETE GENERALE" , "GLE.PA" , "FR0000130809" , 80 ));
        stockList.add(new Stock("STELLANTIS" , "STLAP.PA" , "NL00150001Q9" , 6.72 ));
        stockList.add(new Stock("STMICROELECTRONICS" , "STMPA.PA" , "NL0000226223" , 31.94 ));
        stockList.add(new Stock("THALES" , "HO.PA" , "FR0000121329" , 269.70 ));
        stockList.add(new Stock("TOTALENERGIES" , "TTE.PA" , "FR0000120271" , 76.31 ));
        stockList.add(new Stock("UNIBAIL-DODAMCO-WESTFIELD" , "URW.PA" , "FR0013326246" , 102.55 ));
        stockList.add(new Stock("VEOLIA Environnement" , "VIE.PA" , "FR0000124141" , 34.62 ));
        stockList.add(new Stock("VINCI" , "DG.PA" , "FR0000125486" , 136.45 ));
        return stockList;

    }
}
