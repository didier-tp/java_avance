package tp.market.spi.repository;



import tp.market.core.model.Stock;

import java.util.List;

public interface StockLoader extends GenericLoader<Stock,String> {

    //with .loadById(id,"withOperations" as wishedDetails) impl

    Stock  findByIsin(String isin);

}
