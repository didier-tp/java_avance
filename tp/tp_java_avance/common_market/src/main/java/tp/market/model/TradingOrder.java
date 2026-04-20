package tp.market.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
Pour des raisons de simplicité , on considera que l'on aura toujours affaire à des ordres à cours limité .
 */

@Getter @Setter @ToString @NoArgsConstructor
public class TradingOrder implements Serializable{
    public enum OrderType { SALE , PURCHASE }
    public enum OrderStatus { NEW , PENDING , FILLED , PARTIAL_FILLED , REJECTED  } //...

    private OrderType orderType;
    private OrderStatus status;
    private String orderId; //pk
    private String traderId;//may be null
    private String stockId; // symbol
    private long quantity;  //initial qty to buy or sell
    private long remainingQuantity;
    private double price; //for sale = min_price , for purchase = max_price
    private LocalDateTime emitDateTime;
    //private LocalDateTime maxValidDateTime; //may be null (close time by default)

    public TradingOrder(OrderType orderType,String stockId,double price,long quantity){
        this.orderType=orderType;
        this.status=OrderStatus.NEW;
        this.emitDateTime=LocalDateTime.now();
        this.stockId=stockId;
        this.price=price;
        this.quantity=quantity; this.remainingQuantity = this.quantity;
    }


    public void manageExchangedQuantity(long exchanged_quantity){
        this.remainingQuantity -=  exchanged_quantity;
        if(this.remainingQuantity==0) this.status = OrderStatus.FILLED;
           else this.status = OrderStatus.PARTIAL_FILLED;
    }
}
