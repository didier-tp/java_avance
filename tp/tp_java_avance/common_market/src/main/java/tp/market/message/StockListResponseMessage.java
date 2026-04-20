package tp.market.message;

import lombok.Getter;
import lombok.Setter;
import tp.market.model.Stock;
import tp.market.model.TradingOrder;

import java.io.*;
import java.util.List;

@Getter
@Setter
public class StockListResponseMessage extends TradingMessage{

    private List<Stock> stockList;

    @Override
    public void initMessageType() {
        this.type = TradingMessage.STOCK_LIST_RESPONSE_MESSAGE_TYPE;
    }

    public StockListResponseMessage() {
        super();
    }

    public StockListResponseMessage(List<Stock> stockList){
        super();
        this.stockList=stockList;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(stockList);
            this.data=baos.toByteArray();
            this.size=this.data.length;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Stock> getStockList() {
        if(this.stockList==null && data!=null){
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(this.data);
                ObjectInputStream ois = new ObjectInputStream(bais);
                this.stockList = (List<Stock>) ois.readObject();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return stockList;
    }
}
