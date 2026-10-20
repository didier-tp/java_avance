package tp.market.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

//transaction , stockExchange
@Getter @Setter @ToString
public class StockExchange {

    private String stockId;
    private double price;
    private long quantity;
    private String buyerId;
    private String sellerId;
    private LocalDateTime exchangeDateTime;

    public StockExchange(String stockId, double price, long quantity, String buyerId, String sellerId) {
        this.stockId = stockId;
        this.price = price;
        this.quantity = quantity;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        exchangeDateTime=LocalDateTime.now();
    }
}
