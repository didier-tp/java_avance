import org.junit.jupiter.api.Test;
import tp.market.message.TradingOrderMessage;
import tp.market.model.TradingOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestTradingOrderMessage {


    @Test
    public void testTradingOrderMessageSerializing(){
        TradingOrder tradingOrder = new TradingOrder(TradingOrder.OrderType.SALE,"OR.PA",349.0 , 20);
        TradingOrderMessage tradingOrderMessage = new TradingOrderMessage(tradingOrder);
        assertNotNull(tradingOrderMessage.getData());
        System.out.println("tradingOrder.data="+tradingOrderMessage.getData());
        System.out.println("tradingOrder.size="+tradingOrderMessage.getSize());
        tradingOrderMessage.setTradingOrder(null);
        TradingOrder extractedTradingOrder = tradingOrderMessage.getTradingOrder();
        assertNotNull(extractedTradingOrder);
        assertEquals("OR.PA",extractedTradingOrder.getStockId());
        System.out.println("extractedTradingOrder="+extractedTradingOrder);
    }
}
