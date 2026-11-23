package tp.market.jms;

public interface MarketMessage {
    public static final String MARKET_MESSAGE_TYPE="MARKET_MESSAGE_TYPE";
    public static final String GET_STOCK_LIST="GET_STOCK_LIST";
    public static final String STOCK_LIST="STOCK_LIST"; //response
    public static final String TRADING_ORDER="TRADING_ORDER";
}
