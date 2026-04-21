package tp.market.persistence.entity.aggregation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
public class ExchangeEssential {
    private String StockId;
    private double price;
    private long quantity;

    public ExchangeEssential(String stockId, double price, long quantity) {
        StockId = stockId;
        this.price = price;
        this.quantity = quantity;
    }
}
