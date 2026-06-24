package tp.market.persistence.my_entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

//....
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="my_stock_exchange")
public class MyStockExchangeEntity {

    //... //auto_incr in database
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   //lien n-1 , ...
    @ManyToOne
    @JoinColumn(name="stock_id")
    private MyStockEntity stock;

    private double price;

    private long quantity;


    @Column(name="buyer_id")
    private String buyerId;

    @Column(name="seller_id")
    private String sellerId;

    @Column(name="exchange_date_time")
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
