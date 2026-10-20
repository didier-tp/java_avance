package tp.market.persistence.my_entity.aggregation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
public class MyExchangeEssential {
    private String StockId;
    private double price;
    private long quantity;

    public MyExchangeEssential(String stockId, double price, long quantity) {
        StockId = stockId;
        this.price = price;
        this.quantity = quantity;
    }
}
