package tp.market.persistence.util;

import tp.market.persistence.dao.StockDAO;
import tp.market.persistence.entity.StockEntity;

public class DatabaseUtil {

    public static void reInitDataSet(StockDAO stockDAO){
        stockDAO.saveNew(new StockEntity("ACCOR" , "AC.PA" , " FR0000120404" , 45.06 ));
        stockDAO.saveNew(new StockEntity("AIR LIQUIDE" , "AI.PA" , " FR0000120073" , 183.18));
        stockDAO.saveNew(new StockEntity("AIRBUS GROUP" , "AIR.PA" , "NL0000235190" , 174.95 ));
        stockDAO.saveNew(new StockEntity("ARCELORMITAL" , "MT.PA" , "LU1598757687" , 51.12 ));
        stockDAO.saveNew(new StockEntity("AXA" , "CS.PA" , "FR0000120628" , 41.46));
        stockDAO.saveNew(new StockEntity("BNP PARIBAS" , "BNP.PA" , "FR0000131104 " , 89.96 ));
        stockDAO.saveNew(new StockEntity("BOUYGUES" , "EN.PA" , "FR0000120503" , 52.60 ));
        stockDAO.saveNew(new StockEntity("BUREAU VERITAS" , "BVI.PA" , "FR0006174348" , 27.45 ));
        stockDAO.saveNew(new StockEntity("CAPGEMINI" , "CAP.PA" , "FR0000125338" , 103.85 ));
        stockDAO.saveNew(new StockEntity("CARREFOUR" , "CA.PA" , "FR0000120172" , 16.33 ));
        stockDAO.saveNew(new StockEntity("CREDIT AGRICOLE" , "ACA.PA" , "FR0000045072" , 17.34 ));
        stockDAO.saveNew(new StockEntity("DANONE" , "BN.PA" , "FR0000120644" , 69.28 ));
        stockDAO.saveNew(new StockEntity("DASSAULT SYSTEMES" , "DSY.PA" , "FR0014003TT8" , 17.66 ));
        stockDAO.saveNew(new StockEntity("EIFFAGE" , "FGR.PA" , "FR0000130452" , 141.95 ));
        stockDAO.saveNew(new StockEntity("ENGIE" , "ENGI.PA" , "FR0010208488" , 29.05 ));
        stockDAO.saveNew(new StockEntity("ESSILOR" , "EL.PA" , "FR0000121667" , 201.20 ));
        stockDAO.saveNew(new StockEntity("EUROFINS SCIENTIFIC" , "ERF.PA" , "FR0014000MR3" , 65.30));
        stockDAO.saveNew(new StockEntity("EURONEXT" , "ENX.PA" , "NL0006294274" , 143 ));
        stockDAO.saveNew(new StockEntity("HERMES" , "RMS.PA" , "FR0000052292" , 1759 ));
        stockDAO.saveNew(new StockEntity("KERING" , "KER.PA" , "FR0000121485" , 275 ));
        stockDAO.saveNew(new StockEntity("LEGRAND" , "LR.PA" , "FR0010307819" , 147 ));
        stockDAO.saveNew(new StockEntity("L'OREAL" , "OR.PA" , "FR0000120321" , 366.25 , 533728223L));
        stockDAO.saveNew(new StockEntity("LVMH" , "MC.PA" , "FR0000121014" , 499));
        stockDAO.saveNew(new StockEntity("MICHELIN" , "ML.PA" , "FR001400AJ45" , 30.5));
        stockDAO.saveNew(new StockEntity("ORANGE" , "ORA.PA" , "FR0000133308" , 17.98 ));
        stockDAO.saveNew(new StockEntity("PERNOD RICARD" , "RI.PA" , "FR0000120693" , 65 ));
        stockDAO.saveNew(new StockEntity("PUBLICIS" , "PUB.PA" , "FR0000130577" , 75 ));
        stockDAO.saveNew(new StockEntity("RENAULT" , "RNO" , "FR0000131906 " , 31.53 , 295722284L));
        stockDAO.saveNew(new StockEntity("SAFRAN" , "SAF.PA" , "FR0000073272" , 312.60 ));
        stockDAO.saveNew(new StockEntity("SAINT-GOBIN" , "SGO.PA" , "FR0000125007" , 76.40 ));
        stockDAO.saveNew(new StockEntity("SANOFI" , "SAN.PA" , "FR0000120578" , 80.46 ));
        stockDAO.saveNew(new StockEntity("SCHNEIDER ELECTRIC" , "SU.PA" , "FR0000121972" , 255.30 ));
        stockDAO.saveNew(new StockEntity("SOCIETE GENERALE" , "GLE.PA" , "FR0000130809" , 80 ));
        stockDAO.saveNew(new StockEntity("STELLANTIS" , "STLAP.PA" , "NL00150001Q9" , 6.72 ));
        stockDAO.saveNew(new StockEntity("STMICROELECTRONICS" , "STMPA.PA" , "NL0000226223" , 31.94 ));
        stockDAO.saveNew(new StockEntity("THALES" , "HO.PA" , "FR0000121329" , 269.70 ));
        stockDAO.saveNew(new StockEntity("TOTALENERGIES" , "TTE.PA" , "FR0000120271" , 76.31 ));
        stockDAO.saveNew(new StockEntity("UNIBAIL-DODAMCO-WESTFIELD" , "URW.PA" , "FR0013326246" , 102.55 ));
        stockDAO.saveNew(new StockEntity("VEOLIA Environnement" , "VIE.PA" , "FR0000124141" , 34.62 ));
        stockDAO.saveNew(new StockEntity("VINCI" , "DG.PA" , "FR0000125486" , 136.45 ));
    }
}
