package tp.market.persistence.my_entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

//....
@Getter
@Setter
@NoArgsConstructor
public class MyStockExchangeEntity {

    //... //auto_incr in database
    private Long id;

   //lien n-1 , ...
    private MyStockEntity stock;

    private double price;

    private long quantity;


    private String buyerId;


    private String sellerId;


    private LocalDateTime exchangeDateTime;

    public MyStockExchangeEntity(MyStockEntity stock, double price, long quantity, String buyerId, String sellerId) {
        this.stock = stock;
        this.price = price;
        this.quantity = quantity;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.exchangeDateTime=LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "MyStockExchangeEntity{" +
                "id=" + id +
                ", stock=" + stock +
                ", price=" + price +
                ", quantity=" + quantity +
                ", buyerId='" + buyerId + '\'' +
                ", sellerId='" + sellerId + '\'' +
                ", exchangeDateTime=" + exchangeDateTime +
                '}';
    }
}
