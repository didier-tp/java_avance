package tp.market.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MyStockMarket {
    private static MyStockMarket instance=null;

    public static synchronized  MyStockMarket getInstance(){
        if(instance==null) instance = new MyStockMarket();
        return instance;
    }


    private Map<String,Stock> stockMap = new HashMap<>();

    //map (key = StockId / symbol , value = StockOrderBook)
    private Map<String,StockOrderBook> stockBooksMap = new HashMap<>();

    private Map<String, List<DailyStockExchange>> dailyStockExchanges = new HashMap<>();

    //plus simples statistiques globales ...

    public int numberOfStockOrderBooks(){
        return this.stockBooksMap.size();
    }

    public int numberOfStocks(){
        return this.stockMap.size();
    }

    public int totalNumberOfBids(){
       int n=0;
       synchronized (stockBooksMap) {
           for (StockOrderBook b : this.stockBooksMap.values()) {
                   n += b.getBids().size();
           }
       }
       return n;
    }

    public int totalNumberOfAsks(){
        int n=0;
        synchronized (stockBooksMap) {
            for (StockOrderBook b : this.stockBooksMap.values()) {
                n += b.getAsks().size();
            }
        }
        return n;
    }

    public int totalNumberOfExchanges(){
        int n=0;
        synchronized (stockBooksMap) {
            for (StockOrderBook b : this.stockBooksMap.values()) {
                n += ( b.getExchanges().size() + b.getOldExchanges().size());
            }
        }
        return n;
    }
}
