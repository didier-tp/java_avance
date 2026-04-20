package tp.market.message;

import lombok.Getter;
import lombok.Setter;
import tp.market.model.TradingOrder;

import java.io.*;

/*
TradingOrderMessage transported by sockets or ...
 */
@Getter
@Setter
public class TradingOrderMessage extends TradingMessage{

    private TradingOrder tradingOrder;

    public TradingOrderMessage() {
        super();
    }

    public TradingOrderMessage(TradingOrder tradingOrder) {
        super();
        this.tradingOrder=tradingOrder;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(tradingOrder);
            this.data=baos.toByteArray();
            this.size=this.data.length;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TradingOrder getTradingOrder() {
        if(this.tradingOrder==null && data!=null){
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(this.data);
                ObjectInputStream ois = new ObjectInputStream(bais);
                this.tradingOrder = (TradingOrder) ois.readObject();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return tradingOrder;
    }

    @Override
    public void initMessageType() {
        this.type=TradingMessage.ORDER_MESSAGE_TYPE;
    }
}
