package tp.market.message;

public class StockListRequestMessage extends TradingMessage{
    @Override
    public void initMessageType() {
        this.type = TradingMessage.STOCK_LIST_REQUEST_MESSAGE_TYPE;
    }

    public StockListRequestMessage(){
        super(); this.size=0;
    }
}
