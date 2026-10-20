package tp.market.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="stock_exchange")
@Getter
@Setter
@NoArgsConstructor
public class StockExchangeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto_incr in database
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stockId")
    private StockEntity stock;

    private double price;

    private long quantity;

    @Column(name="buyer_id")
    private String buyerId;

    @Column(name="seller_id")
    private String sellerId;

    @Column(name="exchange_date_time")
    private LocalDateTime exchangeDateTime;

    public StockExchangeEntity(StockEntity stock, double price, long quantity, String buyerId, String sellerId) {
        this.stock = stock;
        this.price = price;
        this.quantity = quantity;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.exchangeDateTime=LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "StockExchangeEntity{" +
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
