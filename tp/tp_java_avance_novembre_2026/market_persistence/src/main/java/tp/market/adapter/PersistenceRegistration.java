package tp.market.adapter;

import tp.market.persistence.dao.StockDAO;
import tp.market.persistence.dao.StockDaoJpa;
import tp.market.spi.repository.DataRepositories;

//class qui enregistre automatique certains adaptateurs SPI
//au sein de tp.market.spi.repository.DataRepositories car ici pas de Spring ni d'injection de dépendance
public class PersistenceRegistration {
  public static void init(){
      DataRepositories.INSTANCE.setPersistenceSetting(new MyJpaPersistenceSetting());
      DataRepositories.INSTANCE.setGlobalTransaction(new MyJpaUtilGlobalTransaction());

      StockDAO stockDAO=new StockDaoJpa();
      DataRepositories.INSTANCE.setStockLoader(new StockLoaderAdapter(stockDAO));
      DataRepositories.INSTANCE.setStockSaver(new StockSaverAdapter(stockDAO));
      //après cet enregistrement , ces accès à la persistence pourront être utilisés depuis la partie "market_core"
  }

}
