package tp.market.core.service;

import tp.market.core.model.TradingOrder;

public interface OrderService {

    TradingOrder newRandomOrder(String traderId);
    TradingOrder newRandomOrderWithTrendPercent(String traderId,double trendPct);
}
