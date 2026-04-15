package tp.market.service;


import lombok.extern.slf4j.Slf4j;
import tp.market.model.MyStockMarket;
import tp.market.model.Stock;
import tp.market.model.TradingOrder;

import java.util.Random;

@Slf4j
public class OrderServiceImpl implements  OrderService{

    private Random random = new Random();

    private static long lastOrderId = 0;

    public static synchronized  long incrementLastOrderId(){
        return ++lastOrderId;
    }

    @Override
    public TradingOrder newRandomOrder(String traderId) {
        return newRandomOrderWithTrendPercent(traderId,0.0);
    }

    //trendPct = 0.0 ou -3.0 ou 3.0 ou autre
    public TradingOrder newRandomOrderWithTrendPercent(String traderId,double trendPct) {

        var stockMap = MyStockMarket.getInstance().getStockMap();
        int nbStocks = stockMap.size();
        int rStockIndex = random.nextInt(nbStocks);//between 0 and nbStocks
        Stock rStock = stockMap.values().stream().skip(rStockIndex).findFirst().orElse(null);
        log.trace("random stock="+rStock);


        TradingOrder.OrderType rOrderType = random.nextBoolean()? TradingOrder.OrderType.SALE: TradingOrder.OrderType.PURCHASE;
        double trendCoeff = 1 + trendPct/100;
        double coeff = random.nextBoolean()?(trendCoeff + random.nextDouble() / 10) : (trendCoeff - random.nextDouble() / 10); //up or down
        double rPrice  = coeff * rStock.getCurrentQuote();
        int rQuantity  = random.nextInt(20);
        TradingOrder tradingOrder = new TradingOrder(rOrderType , rStock.getSymbol() , rPrice ,rQuantity  );
        tradingOrder.setOrderId(String.valueOf(incrementLastOrderId()));
        tradingOrder.setTraderId(traderId);
        log.trace("random tradingOrder="+tradingOrder);
        return tradingOrder;
        }
    }

