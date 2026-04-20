package tp.market.service;

import tp.market.model.TradingOrder;

public interface OrderService {

    TradingOrder newRandomOrder(String traderId);
    TradingOrder newRandomOrderWithTrendPercent(String traderId,double trendPct);
}
